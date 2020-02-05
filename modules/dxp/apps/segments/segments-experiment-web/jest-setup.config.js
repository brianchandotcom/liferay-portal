/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

window.Liferay.Util.sub = function(string = '', data) {
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

window.Liferay.Util.openToast = () => true;
