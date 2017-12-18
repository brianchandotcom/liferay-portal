var gulp = require('gulp');
var exec = require('child_process').exec;

const defaults = {
		  encoding: 'utf8',
		  timeout: 0,
		  maxBuffer: 200 * 1024,
		  killSignal: 'SIGTERM',
		  cwd: __dirname + '/target/classes/META-INF/resources',
		  env: null
		};

gulp.task('config-js-modules', function(cb) {

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
	cb(err);
}, defaults);
})

gulp.task('transpile-js', function(cb) {

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
	cb(err);
}, defaults);
})