/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

/* eslint-env jest */

const {RuleTester} = require('eslint');

let counter;

RuleTester.describe = function (description, method) {
	counter = 1;

	describe(description, method);
};

const MAX_DESCRIPTION_LENGTH = 40;

RuleTester.it = function (description, method) {
	let prettifiedDescription = description.replace(
		/\\u0009|\\u000a/g,
		(match) => {
			if (match === '\\u0009') {
				return '\\t';
			}
			else {
				return '\\n';
			}
		}
	);

	if (prettifiedDescription.length > MAX_DESCRIPTION_LENGTH) {
		prettifiedDescription =
			prettifiedDescription.slice(0, MAX_DESCRIPTION_LENGTH) + '...';
	}

	it(`example ${counter++}: ${prettifiedDescription}`, method);
};
