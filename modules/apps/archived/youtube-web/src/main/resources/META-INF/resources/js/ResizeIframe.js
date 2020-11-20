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

import {debounce} from 'frontend-js-web';

export default function ({height, namespace, width}) {
	const resizeIframe = () => {
		const youtubeIframe = document.getElementById(`${namespace}iframe`);

		let nextWidth = width;

		var parentWidth = youtubeIframe.parentElement.offsetWidth;

		if (nextWidth > parentWidth) {
			nextWidth = parentWidth;
		}

		youtubeIframe.setAttribute('height', height);
		youtubeIframe.setAttribute('width', nextWidth);
	};

	let eventHandle = null;

	const addDragAndDropListener = () => {
		if (!Liferay.Layout) {
			setTimeout(() => addDragAndDropListener(), 5000);
		}
		else {
			const debouncedResizeIframe = debounce(resizeIframe, 500);

			eventHandle = Liferay.Layout.on(['drag:end', 'drag:start'], () =>
				debouncedResizeIframe()
			);
		}
	};

	Liferay.on('allPortletsReady', addDragAndDropListener);

	Liferay.on('endNavigate', resizeIframe);
	Liferay.on('portletReady', resizeIframe);

	window.addEventListener('resize', resizeIframe);

	return {
		dispose() {
			Liferay.detach('allPortletsReady', addDragAndDropListener);

			Liferay.detach('endNavigate', resizeIframe);
			Liferay.detach('portletReady', resizeIframe);

			window.removeEventListener('resize', resizeIframe);

			if (eventHandle) {
				eventHandle.detach();
			}
		},
	};
}
