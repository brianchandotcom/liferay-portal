var fs = require('fs');
var npm = require('npm');
var path = require('path');

var PATH_PACKAGE_JSON = path.join(process.cwd(), 'package.json');

var npmScripts;

var packageJson = require(PATH_PACKAGE_JSON);

prepublish();

function clearNpmScripts() {
	npmScripts = packageJson.scripts;

	packageJson.scripts = {};

	writePackageJson(packageJson);
}

function killProcess(force) {
	process.kill(process.pid);
}

function npmPublish() {
	npm.load(
		function() {
			npm.commands.publish(
				function(err, another) {
					revertNpmScripts();

					if (err) {
						throw err;
					}

					killProcess();
				}
			);
		}
	);
}

function prepublish() {
	clearNpmScripts();

	var exec = require('child_process').exec;

	var antBuildThemes = exec(
		'ant compile',
		{
			cwd: path.join(__dirname, '../../../')
		},
		function(error, stdout, stderr) {
			if (error) {
				stdoutWrite('\nAborting npm publish due to build failure\n');

				revertNpmScripts();

				killProcess();
			}

			npmPublish();
		}
	);

	antBuildThemes.stderr.on('data', stdoutWrite);
	antBuildThemes.stdout.on('data', stdoutWrite);
}

function revertNpmScripts() {
	packageJson.scripts = npmScripts;

	writePackageJson(packageJson);
}

function stdoutWrite(data) {
	process.stdout.write(data);
}

function writePackageJson(json) {
	fs.writeFileSync(PATH_PACKAGE_JSON, JSON.stringify(json, null, '\t'));
}