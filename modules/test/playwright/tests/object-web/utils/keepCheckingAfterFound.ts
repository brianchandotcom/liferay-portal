/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

interface KeepCheckingAfterFound {
	duration: number;
	iframeSelector?: string;
	selector: string;
}

interface EvaluateKeepCheckingAfterFound extends KeepCheckingAfterFound {
	page: Page;
}

/**
 * Continuously checks if an element remains attached to the DOM for a specified duration after it is found.
 *
 * This function runs in the browser.
 *
 * @param duration - The time in milliseconds to keep checking for the element's presence.
 * @param iframeSelector - If provided, the function searches for the selector within the specified iframe.
 * @param selector - The CSS selector of the element to monitor after it is found.
 * @param page - A Playwright Page instance.
 */

const keepCheckingAfterFound = async ({
	duration,
	iframeSelector,
	selector,
}: KeepCheckingAfterFound) => {
	return new Promise((resolve) => {
		let iframeDocument: Document | undefined;

		if (iframeSelector) {
			const iframe = document.querySelector(
				iframeSelector
			) as HTMLIFrameElement;

			if (!iframe) {
				resolve(false);
			}

			iframeDocument =
				iframe.contentDocument || iframe.contentWindow?.document;
		}

		const documentElement = iframeDocument || document;

		let isElementAttached: boolean =
			!!documentElement.querySelector(selector);

		if (!isElementAttached) {
			resolve(false);
		}
		else {
			const startTime = Date.now();

			const checkForElement = async () => {
				await new Promise((resolve) => setTimeout(resolve, 50));
				const elapsedTime = Date.now() - startTime;

				isElementAttached = !!documentElement.querySelector(selector);

				if (elapsedTime < duration && isElementAttached) {
					checkForElement();
				}
				else {
					resolve(isElementAttached);
				}
			};

			checkForElement();
		}
	});
};

const evaluateKeepCheckingAfterFound = async ({
	duration,
	iframeSelector,
	page,
	selector,
}: EvaluateKeepCheckingAfterFound) => {
	return await page.evaluate(keepCheckingAfterFound, {
		duration,
		iframeSelector,
		selector,
	});
};

export default evaluateKeepCheckingAfterFound;
