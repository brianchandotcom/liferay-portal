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

package com.liferay.portalweb.plugins.knowledgebase.knowledgebasesearch.portlet.removeportletkbsr;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemovePortletKBSrTest extends BaseTestCase {
	public void testRemovePortletKBSr() throws Exception {
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Knowledge Base Search Test Page",
			RuntimeVariables.replace("Knowledge Base Search Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//section"));
		selenium.clickAt("//img[@title='Remove']",
			RuntimeVariables.replace("Remove"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if ("Are you sure you want to remove this component?".equals(
							selenium.getConfirmation())) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isElementNotPresent("//section"));
	}
}