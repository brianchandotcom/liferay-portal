'use strict';

var ConfigGenerator = require('liferay-module-config-generator/lib/config-generator');
var gulp = require('gulp');
var liferayThemeTasks = require('liferay-theme-tasks');
var metal = require('gulp-metal');
var path = require('path');
var runSequence = require('run-sequence').use(gulp);

gulp.task(
	'configModules',
	function(done) {
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
				output: path.join(__dirname, 'build', 'META-INF', 'config.json')
			}
		);

		configGenerator.process().then(
			function() {
				done();
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