'use strict';

var gulp = require('gulp');
var path = require('path');
var runSequence = require('run-sequence');

var registerUnitTestsTasks = require('liferay-forms-gulp-tasks');

registerUnitTestsTasks(
	{
		karmaCoverageFile: path.resolve(__dirname, 'karma.coverage.conf.js'),
		karmaCoverageOutput: path.resolve(__dirname, 'build/coverage'),
		karmaFile: path.resolve(__dirname, 'karma.conf.js')
	}
);

gulp.task(
	'test',
	function(done) {
		runSequence('test:unit', done);
	}
);

gulp.task(
	'test:coverage',
	function(done) {
		runSequence('test:unit:coverage', done);
	}
);