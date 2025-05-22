/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * This interface is to be implemented by POM (page object model) classes.
 */
export default interface POM {

	/**
	 * Navigate directly to the page represented by this POM object (usually by
	 * typing its URL in the address bar).
	 *
	 * Once the function returns the page is guaranteed to be fully actionable
	 * as specified in {@link waitFor}.
	 */
	goto(): Promise<void>;

	/**
	 * Wait for the POM object to be fully actionable, ie: fully rendered and
	 * with all even handlers attached so that tests don't result in flakyness.
	 */
	waitFor(): Promise<void>;
}
