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

window.Liferay.Util.sub = function (string = '', data) {
	const REGEX_SUB = /(?<=-|^)x(?=-|\s)/g;

	if (
		arguments.length > 2 ||
		(typeof data !== 'object' && typeof data !== 'function')
	) {
		data = Array.prototype.slice.call(arguments, 1);
	}

	const dataCopy = [...data];
	const max = REGEX_SUB.exec(string).length;
	let replacedValues = 0;

	const replacestring = string.replace
		? string.replace(REGEX_SUB, () => {
				replacedValues = replacedValues + 1;
				const lastReplacement = replacedValues >= max;

				if (lastReplacement) {
					return dataCopy.join('');
				}
				else {
					return dataCopy.shift();
				}
		  })
		: string;

	return replacestring;
};
