'use strict';

const fs = require('fs-extra');
const gulp = require('gulp');
const path = require('path');

const liferayFontAwesome = path.dirname(
	require.resolve('liferay-font-awesome/package.json')
);
const liferayThemeTasks = require('liferay-theme-tasks');

liferayThemeTasks.registerTasks({
	gulp: gulp,
	hookFn: function(gulp) {
		gulp.hook('before:build:war', function(done) {
			fs.copy(
				path.join(liferayFontAwesome, 'font'),
				path.join('build', 'font')
			)
				.then(done)
				.catch(done);
		});
	}
});
