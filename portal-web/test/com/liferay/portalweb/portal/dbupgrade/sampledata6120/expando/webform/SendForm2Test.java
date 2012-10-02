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

package com.liferay.portalweb.portal.dbupgrade.sampledata6120.expando.webform;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SendForm2Test extends BaseTestCase {
	public void testSendForm2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/expando-web-form-community/");
		selenium.clickAt("link=Web Form Page",
			RuntimeVariables.replace("Web Form Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@type='text']",
			RuntimeVariables.replace("Saechang"));
		selenium.clickAt("//input[contains(@id,'_field2Checkbox')]",
			RuntimeVariables.replace("Checkbox"));
		selenium.clickAt("//input[@value='Male']",
			RuntimeVariables.replace("Male"));
		selenium.select("//select", RuntimeVariables.replace("label=Excellent"));
		selenium.type("//textarea",
			RuntimeVariables.replace("This is a comment. Saechang."));
		selenium.clickAt("//input[@value='Send']",
			RuntimeVariables.replace("Send"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"The form information was sent successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}