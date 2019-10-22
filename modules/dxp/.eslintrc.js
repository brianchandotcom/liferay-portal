
const path = require('path');

module.exports = {
	rules: {
		'notice/notice': [
			'error',
			{
				templateFile: path.resolve('copyright.js')
			}
		]
	}
};
