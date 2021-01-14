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

import '../dev/public/js/static-env-utils';

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
