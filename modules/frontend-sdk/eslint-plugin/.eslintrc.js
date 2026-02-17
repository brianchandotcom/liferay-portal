/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const path = require('path');

module.exports = {
	rules: {
		'notice/notice': [
			'error',
			{
				templateFile: path.join(__dirname, 'copyright.js'),
			},
		],
	},
};
