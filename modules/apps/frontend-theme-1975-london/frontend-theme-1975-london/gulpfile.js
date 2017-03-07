'use strict';

var gulp = require('gulp');
var liferayThemeTasks = require('liferay-theme-tasks');

liferayThemeTasks.registerTasks(
	{
		distName: require('./package.json').name.replace(/^liferay-/, ''),
		gulp: gulp
	});