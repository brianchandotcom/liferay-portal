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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationpage.addorganizationpublicpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewOrganizationPublicPageTest extends BaseTestCase {
	public void testViewOrganizationPublicPage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/organization-name/");
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText("//h1[@class='site-title']/span"));
		assertEquals(RuntimeVariables.replace("Public Page"),
			selenium.getText("link=Public Page"));
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText(
				"//nav[@id='breadcrumbs']/ul/li/span/a[contains(.,'Organization Name')]"));
		assertEquals(RuntimeVariables.replace("Public Page"),
			selenium.getText(
				"//nav[@id='breadcrumbs']/ul/li[2]/span/a[contains(.,'Public Page')]"));
	}
}