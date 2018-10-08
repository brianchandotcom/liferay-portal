const {name, version} = require('./package.json');
const bs = require('browser-sync').create();
const exec = require('child_process').exec;
const fs = require('fs');
const babel = require('babel-core');
const path = require('path');

const cwd = process.cwd();
const src = path.join(cwd, 'src/main/resources/META-INF/resources');
const dest = path.join(cwd, 'classes/META-INF/resources');
const npmbundlerrc = fs.readFileSync('.npmbundlerrc');
const parsedBundlerConfig = JSON.parse(npmbundlerrc);

function compile(fileName, callback) {
	const optsManager = new babel.OptionManager;
	optsManager.mergeOptions(
		{
			alias: 'base',
			loc: path.dirname(fileName)
		}
	);
	const opts = optsManager.init({ filename: fileName });
	opts.babelrc = true;
	opts.sourceMap = true;
	opts.ast = false;

	return babel.transformFile(
		fileName,
		opts,
		(error, result) => {
			callback(error, result);
		}
	);
}

function buildSoy(fileName, callback) {
	const relativeFilename = path.relative(src, fileName);
	const completeName = path.join(src, relativeFilename);
	let dirName = completeName.split(path.sep);
	dirName.pop();
	exec(
		`npm run build-soy -- -s ${fileName} -d ${dirName.join(path.sep)}`,
		(error, stdout, stderr) => {
			if (stderr) {
				console.log(stdout);
				console.log(stderr);
				callback(stderr);
			}
			else {
				callback();
			}
		}
	);
}

function buildJS(fileName, callback) {
	const relativeFilename = path.relative(src, fileName);
	const destinationName = path.join(dest, relativeFilename);
	compile(
		fileName,
		(error, result) => {
			if (error) {
				console.log(error);
				return callback(error);
			}
			fs.writeFileSync(destinationName, result.code);
			parsedBundlerConfig.ignore = [
				'**/*.js',
				`!${relativeFilename}`
			];
			fs.writeFileSync('.npmbundlerrc', JSON.stringify(parsedBundlerConfig));
			exec(
				'npm run bundler -- --no-tracking',
				function() {
					fs.writeFileSync('.npmbundlerrc', npmbundlerrc);
					callback(null, result);
				}
			);
		}
	);
}

function sendEvent(instance, eventName, data) {
	instance.io.sockets.emit(
		eventName,
		Object.assign(
			{
				moduleName: name
			},
			data
		)
	);
}

bs.init(
	{
		cors: true,
		plugins: [
			{
				hooks: {
					'client:js': fs.readFileSync('reloader.js', 'utf-8')
				},
				plugin: function() { }
			}
		],
		port: 3000,
		proxy: 'http://localhost:8080/',
		serveStatic: [
			{
				route: `/o/js/resolved-module/${name}@${version}/metal`,
				dir: 'classes/META-INF/resources/metal'
			}
		],
		watch: false
	},
	function (error, instance) {
		if (error) {
			console.error(error);
		}
		else {
			let buildingSoy = false;
			bs.watch('src/**/*.soy').on(
				'change',
				fileName => {
					if (buildingSoy) {
						return;
					}
					buildingSoy = true;
					sendEvent(instance, 'building', {fileName});
					buildSoy(
						fileName,
						(error) => {
							buildingSoy = false;
							if (error) {
								sendEvent(instance, 'error', {fileName, error});
							}
							else {
								sendEvent(instance, 'done', {fileName});
							}
						}
					);
				}
			);
			let buildingJS = false;
			bs.watch('src/main/resources/META-INF/resources/metal/**/*.js').on(
				'change',
				fileName => {
					if (buildingJS) {
						return;
					}
					buildingJS = true;
					sendEvent(instance, 'building', {fileName})
					buildJS(
						fileName,
						(error) => {
							buildingJS = false;
							if (error) {
								sendEvent(instance, 'error', {fileName, error});
							}
							else {
								sendEvent(instance, 'done', {fileName});
							}
						}
					);
				}
			);
		}
	}
);