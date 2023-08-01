/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './static-env-utils';

(function setupIntersectionObserverMock({
	root = null,
	rootMargin = '',
	thresholds = [],
	disconnect = () => null,
	observe = () => null,
	takeRecords = () => null,
	unobserve = () => null,
} = {}) {
	class MockIntersectionObserver {
		constructor() {
			this.root = root;
			this.rootMargin = rootMargin;
			this.thresholds = thresholds;
			this.disconnect = disconnect;
			this.observe = observe;
			this.takeRecords = takeRecords;
			this.unobserve = unobserve;
		}
	}

	Object.defineProperty(window, 'IntersectionObserver', {
		configurable: true,
		value: MockIntersectionObserver,
		writable: true,
	});

	Object.defineProperty(global, 'IntersectionObserver', {
		configurable: true,
		value: MockIntersectionObserver,
		writable: true,
	});
})();
