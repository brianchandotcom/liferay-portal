/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {READ_MINIMUM_SCROLL_DEPTH} from './constants';

/**
 * Returns if there is vertical scroll bar on the client.
 */
function hasVerticalScrollBar() {
	const {clientHeight, scrollHeight} = document.documentElement;

	return (clientHeight * 100) / scrollHeight <= READ_MINIMUM_SCROLL_DEPTH;
}

class ReadTracker {
	depthReached: boolean;
	timeoutId?: NodeJS.Timeout;
	timeReached: boolean;

	constructor() {
		this.timeReached = false;
		this.depthReached = !hasVerticalScrollBar();
	}

	dispose() {
		this.timeReached = false;
		this.depthReached = !hasVerticalScrollBar();

		if (this.timeoutId) {
			clearTimeout(this.timeoutId);
		}
	}

	/**
	 * Set the expected time to be considered read
	 */
	setExpectedViewDuration(fn: () => void, expectedViewDuration: number) {
		if (expectedViewDuration && !this.timeoutId) {
			this.timeoutId = setTimeout(
				() => this.onTimeReached(fn),
				~~expectedViewDuration
			);
		}
	}

	/**
	 * Set depth reached
	 */
	onDepthReached(fn: () => void) {
		if (!this.depthReached) {
			this.depthReached = true;
			this.checkIsRead(fn);
		}
	}

	/**
	 * Set time reached
	 */
	onTimeReached(fn: () => void) {
		this.timeReached = true;
		this.checkIsRead(fn);
	}

	/**
	 * Check if the client reached both conditions to be considered read
	 * If the client reaches both it sends an analytics event
	 */
	checkIsRead(fn: () => void) {
		if (this.timeReached && this.depthReached) {
			fn();
		}
	}
}

export {ReadTracker};
export default ReadTracker;
