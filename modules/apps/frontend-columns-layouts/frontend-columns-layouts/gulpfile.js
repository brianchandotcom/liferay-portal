'use strict';

var gulp = require('gulp');
var liferayPluginTasks = require('liferay-plugin-node-tasks');

liferayPluginTasks.registerTasks({
	gulp: gulp
});
