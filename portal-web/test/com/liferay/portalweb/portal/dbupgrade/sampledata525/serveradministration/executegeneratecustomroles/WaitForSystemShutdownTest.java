/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.dbupgrade.sampledata525.serveradministration.executegeneratecustomroles;

import com.liferay.portalweb.portal.BaseTestCase;

/**
 * @author Brian Wing Shun Chan
 */
public class WaitForSystemShutdownTest extends BaseTestCase {

	public void testWaitForSystemShutdown() throws Exception {
		for (int second = 0;; second++) {
			if (second >= 240) {
				fail("timeout");
			}

			try {
				if (selenium.isTextPresent(
						"Welcome to Liferay Portal")) {

					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

}