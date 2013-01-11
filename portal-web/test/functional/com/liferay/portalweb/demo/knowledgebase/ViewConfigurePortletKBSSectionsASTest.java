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
public class ViewConfigurePortletKBSSectionsASTest extends BaseTestCase {
	public void testViewConfigurePortletKBSSectionsAS()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Knowledge Base Section Test Page",
			RuntimeVariables.replace("Knowledge Base Section Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Application Server"),
			selenium.getText("//div[@class='kb-articles-sections-title']"));
		assertEquals(RuntimeVariables.replace("The third"),
			selenium.getText("//div[@class='kb-articles']/div/span/a/span"));
		selenium.clickAt("//div[@class='kb-articles']/div/span/a/span",
			RuntimeVariables.replace("The third"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("The third"),
			selenium.getText("//div[@class='kb-title']"));
		assertEquals(RuntimeVariables.replace(
				"Number three detailing the specifics of Tomcat and Jboss"),
			selenium.getText("//div[@class='kb-entity-body']/p"));
	}
}