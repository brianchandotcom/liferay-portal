/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
export default function useEventListener(
	eventName: string,
	handler: EventListenerOrEventListenerObject,
	phase: boolean,
	target: Node
): void;
