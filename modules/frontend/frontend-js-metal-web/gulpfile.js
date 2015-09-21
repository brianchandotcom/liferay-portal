'use strict';

var gulp = require('gulp');

gulp.task('default', ['build:amd:jquery']);

var gulpMetal = require('gulp-metal');

gulpMetal.registerTasks(
	{
		buildAmdJqueryDest: 'classes/META-INF/resources',
		buildSrc: 'bower_components/crystal-*/src/**/*.js',
		bundleFileName: 'crystal.js',
		cssSrc: 'bower_components/crystal-*/src/**/*.css',
		globalName: 'crystal',
		scssSrc: 'bower_components/crystal-*/src/**/*.scss'
	}
);