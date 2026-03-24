/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

class ResolvablePromise {
	static new() {
		const capture = {};

		const promise = new Promise((resolve, reject) => {
			capture.resolve = resolve;
			capture.reject = reject;
		});

		promise.resolve = capture.resolve;
		promise.reject = capture.reject;

		return promise;
	}
}

export default ResolvablePromise;
