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

import {useEffect} from 'react';

/**
 * Hook for adding an event listener on mount and removing it on
 * unmount.
 *
 * Note that in general you should be using React's built-in delegated
 * event handling (ie. via `onclick`, `onfocus` etc attributes). This
 * hook is for those rarer cases where you need to attach a listener
 * outside of your component's DOM (eg. attaching a "scroll" or "resize"
 * listener to the `window`).
 */
export default function useEventListener(eventName, handler, phase, target) {
	useEffect(() => {
		if (target) {
			target.addEventListener(eventName, handler, phase);

			return () => {
				target.removeEventListener(eventName, handler, phase);
			};
		}
	}, [eventName, handler, phase, target]);
}
