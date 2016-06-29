'use strict';

var path = require('path');

module.exports = {
	replaceModulePath: function(file, content) {
		var filePath = file.path.split(path.sep);

		var fileName = filePath.pop();

		if (fileName === 'config.js') {
			while (filePath.length && filePath[filePath.length - 1] != 'resources') {
				filePath.pop();
			}

			var modulePath = filePath.join(path.sep);

			content = content.replace(/MODULE_PATH/g, '\'' + modulePath + '\'');
		}

		return content;
	}
};