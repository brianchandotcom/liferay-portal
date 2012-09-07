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

package com.liferay.portalweb.portlet.translator;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TranslateJapaneseEnglishTest extends BaseTestCase {
	public void testTranslateJapaneseEnglish() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Translator Test Page");
		selenium.clickAt("link=Translator Test Page",
			RuntimeVariables.replace("Translator Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//textarea[@id='_26_text']",
			RuntimeVariables.replace(
				"\u79c1\u306e\u540d\u524d\u306f\u30b3\u30df\u30e5\u30cb\u30b1\u30fc\u30b7\u30e7\u30f3\u306e6,000,000\u306e\u5f62\u614b\u306e\u6d41\u66a2\u306aLiferay\u306e\u8a33\u8005\u3067\u3042\u308b\u3002"));
		selenium.select("//select[@id='_26_id']",
			RuntimeVariables.replace("Japanese to English"));
		selenium.clickAt("//input[@value='Translate']",
			RuntimeVariables.replace("Translate"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"My name is the translator of fluent Liferay of form of 6,000,000 of communication."));
	}
}