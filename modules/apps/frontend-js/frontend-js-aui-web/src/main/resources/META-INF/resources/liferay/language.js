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

(function(A, Liferay) {
	var Language = {};

	Language.get = function(key) {
		return key;
	};

	A.use('io-base', A => {
		Language.get = A.cached((key, extraParams) => {
			var url =
				themeDisplay.getPathContext() +
				'/language/' +
				themeDisplay.getLanguageId() +
				'/' +
				key +
				'/';

			if (extraParams) {
				if (typeof extraParams == 'string') {
					url += extraParams;
				} else if (Array.isArray(extraParams)) {
					url += extraParams.join('/');
				}
			}

			var headers = {
				'X-CSRF-Token': Liferay.authToken
			};

			var value = '';

			A.io(url, {
				headers,
				method: 'GET',
				on: {
					complete(i, o) {
						value = o.responseText;
					}
				},
				sync: true
			});

			return value;
		});
	});

	Liferay.Language = Language;
})(AUI(), Liferay);
