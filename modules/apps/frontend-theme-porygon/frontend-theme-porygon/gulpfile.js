'use strict';

var ConfigGenerator = require('liferay-module-config-generator/lib/config-generator');
var fs = require('fs');
var gulp = require('gulp');
var liferayThemeTasks = require('liferay-theme-tasks');
var metal = require('gulp-metal');
var path = require('path');
var runSequence = require('run-sequence').use(gulp);

var outputFolder = path.join(__dirname, 'build', 'META-INF');

var configModules = function(done) {
	var configGenerator = new ConfigGenerator(
		{
			args: [path.join(__dirname, 'build', 'js')],
			config: '',
			extension: '',
			filePattern: '**/*.es.js',
			format: ['/-/g', '_'],
			ignorePath: true,
			moduleConfig: path.join(__dirname, 'package.json'),
			moduleRoot: path.join(__dirname, 'build'),
			output: path.join(outputFolder, 'config.json')
		}
	);
};

gulp.task(
	'configModules',
	function(done) {
		console.log('holakease');
		fs.open(
			outputFolder,
			'wx',
			function(error, fd) {
				console.log(error);
				if (error && error.code !== 'EEXIST') {
					throw error;
				}

				configModules(done);
			}
		);
	}
);

metal.registerTasks(
	{
		base: 'src/js',
		buildAmdDest: 'build',
		buildSrc: 'src/js/**/*.es.js',
		moduleName: 'js',
		taskPrefix: 'metal:'
	}
);

liferayThemeTasks.registerTasks(
	{
		gulp: gulp,
		hookFn: function(gulp) {
			gulp.hook(
				'after:build:src',
				function(done) {
					runSequence(
						'metal:build:amd',
						'configModules',
						done
					);
				}
			);
		}
	}
);