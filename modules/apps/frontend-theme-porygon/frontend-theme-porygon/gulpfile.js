'use strict';

var gulp = require('gulp');
var liferayThemeTasks = require('liferay-theme-tasks');
var metal = require('gulp-metal');
var runSequence = require('run-sequence').use(gulp);

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
						done
					);
				}
			);
		}
	}
);