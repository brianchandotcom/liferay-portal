/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI().use('aui', () => {
	Liferay.Test = Liferay.Test || {};

	var includes = function(array, value) {
		return array.indexOf(value) != -1;
	};

	var assertSameItems = function(expected, actual) {
		var message = 'Expected [' + expected + ']; got [' + actual + ']';

		assert.equal(expected.length, actual.length, message);

		expected.forEach(item => {
			assert(includes(actual, item), message);
		});
	};

	Liferay.Test.assertSameItems = assertSameItems;

	var assertEmpty = function(array) {
		assert.equal(0, array.length);
	};

	Liferay.Test.assertEmpty = assertEmpty;
});
