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

package com.liferay.portalweb.demo.knowledgebase;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewKBAArticleSectionsPortletsTest extends BaseTestCase {
	public void testViewKBAArticleSectionsPortlets() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Knowledge Base Display Test Page",
			RuntimeVariables.replace("Knowledge Base Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("KB Admin Article"),
			selenium.getText("//tr[contains(.,'KB Admin Article')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[contains(.,'KB Admin Article')]/td[3]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'KB Admin Article')]/td[4]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'KB Admin Article')]/td[5]/a"));
		assertEquals(RuntimeVariables.replace("0 (Approved)"),
			selenium.getText("//tr[contains(.,'KB Admin Article')]/td[6]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'KB Admin Article')]/td[7]/a"));
		selenium.clickAt("//tr[contains(.,'KB Admin Article')]/td[2]/a",
			RuntimeVariables.replace("KB Admin Article"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("KB Admin Article"),
			selenium.getText("//div[@class='kb-title']"));
		assertEquals(RuntimeVariables.replace(
				"This is an article created from the KB Admin"),
			selenium.getText("//div[@class='kb-entity-body']/p"));
	}
}