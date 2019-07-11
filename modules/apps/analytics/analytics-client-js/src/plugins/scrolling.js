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

import {debounce} from '../utils/debounce';
import {DEBOUNCE} from '../utils/constants';
import {ScrollTracker} from '../utils/scroll';

const applicationId = 'Page';

/**
 * Plugin function that registers listener against scroll event
 * @param {object} analytics The Analytics client
 */
function scrolling(analytics) {
	let scrollTracker = new ScrollTracker();

	const onScroll = debounce(() => {
		scrollTracker.onDepthReached(depth => {
			analytics.send('pageDepthReached', applicationId, {depth});
		});
	}, DEBOUNCE);

	document.addEventListener('scroll', onScroll);

	// Reset levels on SPA-enabled environments

	const onLoad = () => {
		scrollTracker.dispose();
		scrollTracker = new ScrollTracker();
	};

	window.addEventListener('load', onLoad);

	return () => {
		document.removeEventListener('scroll', onScroll);
		window.removeEventListener('load', onLoad);
	};
}

export {scrolling};
export default scrolling;
