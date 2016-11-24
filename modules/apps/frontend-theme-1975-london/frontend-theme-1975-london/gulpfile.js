'use strict';

var gulp = require('gulp');
var metal = require('gulp-metal');
var liferayThemeTasks = require('liferay-theme-tasks');

var srcBlob = 'src/**/*.es.js';
var testBlob = 'test/**/*.es.js';

metal.registerTasks({
	buildSrc: [srcBlob],
	formatGlobs: [srcBlob, testBlob],
	lintGlobs: [srcBlob, testBlob]
});

liferayThemeTasks.registerTasks(
	{
		gulp: gulp
	}
);