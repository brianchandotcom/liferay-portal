'use strict';

var del = require('del');
var gulp = require('gulp');
var metal = require('gulp-metal');
var path = require('path');
var runSequence = require('run-sequence');

gulp.task('clean-build', function(callback) {
	del(['build'], callback);
});

gulp.task('copy-resources', function() {
	return gulp.src(path.join('build', 'amd-jquery', '**/*'))
		.pipe(gulp.dest(path.join('.js-cache', 'META-INF', 'resources')));
});

gulp.task('default', function(callback) {
	runSequence('build:amd:jquery', 'copy-resources', 'clean-build', callback);
});

metal.registerTasks({
	buildSrc: 'bower_components/crystal-*/src/**/*.js',
	bundleFileName: 'crystal.js',
	cssSrc: 'bower_components/crystal-*/src/**/*.css',
	globalName: 'crystal',
	scssSrc: 'bower_components/crystal-*/src/**/*.scss'
});