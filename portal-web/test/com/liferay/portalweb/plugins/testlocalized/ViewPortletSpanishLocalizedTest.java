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

package com.liferay.portalweb.plugins.testlocalized;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletSpanishLocalizedTest extends BaseTestCase {
	public void testViewPortletSpanishLocalized() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Test Localized Page");
		selenium.clickAt("link=Test Localized Page",
			RuntimeVariables.replace("Test Localized Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Spanish"),
			selenium.getText("//section/div/div/div/a[4]"));
		selenium.click(RuntimeVariables.replace("//section/div/div/div/a[4]"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Override a portal language entry from a hook."),
			selenium.getText("//td[1]"));
		assertEquals(RuntimeVariables.replace("first-name"),
			selenium.getText("//td[2]"));
		assertEquals(RuntimeVariables.replace("first-name"),
			selenium.getText("//td[3]"));
		assertEquals(RuntimeVariables.replace("Nombre 2.0"),
			selenium.getText("//td[4]"));
		assertEquals(RuntimeVariables.replace("PASSED"),
			selenium.getText("//td[5]"));
		assertEquals(RuntimeVariables.replace(
				"Override a portal language entry from a portlet."),
			selenium.getText("//tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("welcome"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Recepci\u00f3n 2.0"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("Recepci\u00f3n 2.0"),
			selenium.getText("//tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("PASSED"),
			selenium.getText("//tr[3]/td[5]"));
		assertEquals(RuntimeVariables.replace(
				"Add a new portlet language entry from a hook."),
			selenium.getText("//tr[4]/td[1]"));
		assertEquals(RuntimeVariables.replace("playing-basketball-is-fun"),
			selenium.getText("//tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("playing-basketball-is-fun"),
			selenium.getText("//tr[4]/td[3]"));
		assertEquals(RuntimeVariables.replace(
				"Jugar a baloncesto es diversi\u00f3n."),
			selenium.getText("//tr[4]/td[4]"));
		assertEquals(RuntimeVariables.replace("PASSED"),
			selenium.getText("//tr[4]/td[5]"));
		assertEquals(RuntimeVariables.replace(
				"Add a new portlet language entry from a portlet."),
			selenium.getText("//tr[5]/td[1]"));
		assertEquals(RuntimeVariables.replace("please-take-a-cool-drink"),
			selenium.getText("//tr[5]/td[2]"));
		assertEquals(RuntimeVariables.replace(
				"Tome por favor una bebida fresca."),
			selenium.getText("//tr[5]/td[3]"));
		assertEquals(RuntimeVariables.replace(
				"Tome por favor una bebida fresca."),
			selenium.getText("//tr[5]/td[4]"));
		assertEquals(RuntimeVariables.replace("PASSED"),
			selenium.getText("//tr[5]/td[5]"));
		assertEquals(RuntimeVariables.replace(
				"Add a new unlocalized language entry from a hook."),
			selenium.getText("//tr[6]/td[1]"));
		assertEquals(RuntimeVariables.replace(
				"this-is-an-unlocalized-hook-message"),
			selenium.getText("//tr[6]/td[2]"));
		assertEquals(RuntimeVariables.replace(
				"this-is-an-unlocalized-hook-message"),
			selenium.getText("//tr[6]/td[3]"));
		assertEquals(RuntimeVariables.replace(
				"This is an unlocalized hook message."),
			selenium.getText("//tr[6]/td[4]"));
		assertEquals(RuntimeVariables.replace("PASSED"),
			selenium.getText("//tr[6]/td[5]"));
		assertEquals(RuntimeVariables.replace(
				"Add a new unlocalized language entry from a portlet."),
			selenium.getText("//tr[7]/td[1]"));
		assertEquals(RuntimeVariables.replace(
				"this-is-an-unlocalized-portlet-message"),
			selenium.getText("//tr[7]/td[2]"));
		assertEquals(RuntimeVariables.replace(
				"This is an unlocalized portlet message."),
			selenium.getText("//tr[7]/td[3]"));
		assertEquals(RuntimeVariables.replace(
				"This is an unlocalized portlet message."),
			selenium.getText("//tr[7]/td[4]"));
		assertEquals(RuntimeVariables.replace("PASSED"),
			selenium.getText("//tr[7]/td[5]"));
		assertEquals(RuntimeVariables.replace(
				"Override the default modifier for a portal language entry from a hook."),
			selenium.getText("//tr[8]/td[1]"));
		assertEquals(RuntimeVariables.replace("post"),
			selenium.getText("//tr[8]/td[2]"));
		assertEquals(RuntimeVariables.replace("post"),
			selenium.getText("//tr[8]/td[3]"));
		assertEquals(RuntimeVariables.replace("Mensaje"),
			selenium.getText("//tr[8]/td[4]"));
		assertEquals(RuntimeVariables.replace("PASSED"),
			selenium.getText("//tr[8]/td[5]"));
		assertEquals(RuntimeVariables.replace(
				"Override the adjective modifier for a portal language entry from a hook."),
			selenium.getText("//tr[9]/td[1]"));
		assertEquals(RuntimeVariables.replace("post[adjective]"),
			selenium.getText("//tr[9]/td[2]"));
		assertEquals(RuntimeVariables.replace("post[adjective]"),
			selenium.getText("//tr[9]/td[3]"));
		assertEquals(RuntimeVariables.replace("Mensaje"),
			selenium.getText("//tr[9]/td[4]"));
		assertEquals(RuntimeVariables.replace("PASSED"),
			selenium.getText("//tr[9]/td[5]"));
		assertEquals(RuntimeVariables.replace(
				"Override the noun modifier for a portal language entry from a hook."),
			selenium.getText("//tr[10]/td[1]"));
		assertEquals(RuntimeVariables.replace("post[noun]"),
			selenium.getText("//tr[10]/td[2]"));
		assertEquals(RuntimeVariables.replace("post[noun]"),
			selenium.getText("//tr[10]/td[3]"));
		assertEquals(RuntimeVariables.replace("Mensaje 2.0"),
			selenium.getText("//tr[10]/td[4]"));
		assertEquals(RuntimeVariables.replace("PASSED"),
			selenium.getText("//tr[10]/td[5]"));
		assertEquals(RuntimeVariables.replace(
				"Override the verb modifier for a portal language entry from a hook."),
			selenium.getText("//tr[11]/td[1]"));
		assertEquals(RuntimeVariables.replace("post[verb]"),
			selenium.getText("//tr[11]/td[2]"));
		assertEquals(RuntimeVariables.replace("post[verb]"),
			selenium.getText("//tr[11]/td[3]"));
		assertEquals(RuntimeVariables.replace("Publicar 2.0"),
			selenium.getText("//tr[11]/td[4]"));
		assertEquals(RuntimeVariables.replace("PASSED"),
			selenium.getText("//tr[11]/td[5]"));
		assertEquals(RuntimeVariables.replace(
				"Override the default modifier for a portal language entry from a portlet."),
			selenium.getText("//tr[12]/td[1]"));
		assertEquals(RuntimeVariables.replace("comment"),
			selenium.getText("//tr[12]/td[2]"));
		assertEquals(RuntimeVariables.replace("comment"),
			selenium.getText("//tr[12]/td[3]"));
		assertEquals(RuntimeVariables.replace("Comentario"),
			selenium.getText("//tr[12]/td[4]"));
		assertEquals(RuntimeVariables.replace("PASSED"),
			selenium.getText("//tr[12]/td[5]"));
		assertEquals(RuntimeVariables.replace(
				"Override the adjective modifier for a portal language entry from a portlet."),
			selenium.getText("//tr[13]/td[1]"));
		assertEquals(RuntimeVariables.replace("comment[adjective]"),
			selenium.getText("//tr[13]/td[2]"));
		assertEquals(RuntimeVariables.replace("comment[adjective]"),
			selenium.getText("//tr[13]/td[3]"));
		assertEquals(RuntimeVariables.replace("Comentario"),
			selenium.getText("//tr[13]/td[4]"));
		assertEquals(RuntimeVariables.replace("PASSED"),
			selenium.getText("//tr[13]/td[5]"));
		assertEquals(RuntimeVariables.replace(
				"Override the noun modifier for a portal language entry from a portlet."),
			selenium.getText("//tr[14]/td[1]"));
		assertEquals(RuntimeVariables.replace("comment[noun]"),
			selenium.getText("//tr[14]/td[2]"));
		assertEquals(RuntimeVariables.replace("Comentario 2.0"),
			selenium.getText("//tr[14]/td[3]"));
		assertEquals(RuntimeVariables.replace("Comentario 2.0"),
			selenium.getText("//tr[14]/td[4]"));
		assertEquals(RuntimeVariables.replace("PASSED"),
			selenium.getText("//tr[14]/td[5]"));
		assertEquals(RuntimeVariables.replace(
				"Override the verb modifier for a portal language entry from a portlet."),
			selenium.getText("//tr[15]/td[1]"));
		assertEquals(RuntimeVariables.replace("comment[verb]"),
			selenium.getText("//tr[15]/td[2]"));
		assertEquals(RuntimeVariables.replace("Comentar 2.0"),
			selenium.getText("//tr[15]/td[3]"));
		assertEquals(RuntimeVariables.replace("Comentar 2.0"),
			selenium.getText("//tr[15]/td[4]"));
		assertEquals(RuntimeVariables.replace("PASSED"),
			selenium.getText("//tr[15]/td[5]"));
	}
}