'use strict';

var gulp = require('gulp');
var metal = require('gulp-metal');

gulp.task('default', ['build:amd:jquery']);

metal.registerTasks(
	{
		buildAmdJqueryDest: 'classes/META-INF/resources',
		buildSrc: 'bower_components/crystal-*/src/**/*.js',
		bundleFileName: 'crystal.js',
		cssSrc: 'bower_components/crystal-*/src/**/*.css',
		globalName: 'crystal',
		scssSrc: 'bower_components/crystal-*/src/**/*.scss'
	}
);