var exec = require('child_process').exec;
var gulp = require('gulp');

const defaults = {
	cwd: __dirname + '/target/classes/META-INF/resources',
	encoding: 'utf8',
	env: null,
	killSignal: 'SIGTERM',
	maxBuffer: 200 * 1024,
	timeout: 0
};

gulp.task('config-js-modules', function(callback) {
	process.chdir(__dirname + '/target/classes/META-INF/resources');

	exec(__dirname + '/target/node/node ' + __dirname + '/node_modules/liferay-module-config-generator/bin/index.js ' +
		'--config= ' +
		'--extension= ' +
		'--filePattern **/resources/*.js ' +
		'--format /_/g,- ' +
		'--ignorePath true ' +
		'--moduleConfig ' + __dirname + '/package.json ' +
		'--namespace Liferay.Loader ' +
		'--output ' + __dirname + '/target/classes/META-INF/config.json ' +
		'--moduleRoot ' + __dirname + '/target/classes/META-INF/resources ' +
		__dirname + '/target/classes/META-INF', function(err, stdout, stderr) {

			console.log(stdout);
			console.log(stderr);

			callback(err);

		}, defaults);
	})

gulp.task('transpile-js', function(callback) {
	process.chdir(__dirname + '/target/classes/META-INF/resources');

	exec(__dirname + '/target/node/node ' + __dirname + '/node_modules/metal-cli/index.js build ' +
		'--bundleFileName= ' +
		'--dest ' + __dirname + '/target/classes/META-INF/resources ' +
		'--format amd ' +
		'--globalName= ' +
		'--moduleName= ' +
		'--soyDeps ' +
		__dirname + '/node_modules/lexicon*/src/**/*.soy ' +
		__dirname + '/node_modules/metal*/src/**/*.soy ' +
		'--soyDest ' + __dirname + '/target/classes/META-INF/resources ' +
		'--soySrc **/*.soy ' +
		'--src **/*.es.js ' +
		'**/*.soy.js', function(err, stdout, stderr) {

			console.log(stdout);
			console.log(stderr);

			callback(err);
		}, defaults);
	})