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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationpage.addorganizationprivatepage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewOrganizationPrivatePageTest extends BaseTestCase {
	public void testViewOrganizationPrivatePage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/group/organization-name/");
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText("//div[@id='heading']/h1/span"));
		assertEquals(RuntimeVariables.replace("Private Page"),
			selenium.getText("//nav[contains(.,'Private Page')]/ul/li/a"));
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText(
				"//nav[contains(.,'Organization Name')]/ul/li/span/a"));
		assertEquals(RuntimeVariables.replace("Private Page"),
			selenium.getText(
				"//nav[contains(.,'Private Page')]/ul/li[2]/span/a"));
	}
}