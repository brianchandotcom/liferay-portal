/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

/**
 * This interface is to be implemented by POM (page object model) classes.
 */
export default abstract class POM {
	protected readonly page: Page;
	protected readonly url: string;

	protected constructor(page: Page, url: string) {
		this.page = page;
		this.url = url;
	}

	/**
	 * Navigate directly to the page represented by this POM object (usually by
	 * typing its URL in the address bar).
	 *
	 * Once the function returns the page is guaranteed to be fully actionable
	 * as specified in {@link waitFor}.
	 */
	async goto() {
		await this.page.goto(this.url);

		await this.waitFor();
	}

	/**
	 * Wait for the POM object to be fully actionable, ie: fully rendered and
	 * with all even handlers attached so that tests don't result in flakyness.
	 */
	abstract waitFor(): Promise<void>;
}
