'use strict';

var gulp = require('gulp');
var runSequence = require('run-sequence');
var path = require('path');

var registerUnitTestsTasks = require('liferay-forms-gulp-tasks');

registerUnitTestsTasks(
	{
		karmaCoverageFile: path.resolve(__dirname, 'src/testFrontend/karma.coverage.conf.js'),
		karmaCoverageOutput: path.resolve(__dirname, 'build/coverage'),
		karmaFile: path.resolve(__dirname, 'src/testFrontend/karma.conf.js')
	}
);

gulp.task(
	'test',
	function(done) {
		runSequence('test:unit', done);
	}
);