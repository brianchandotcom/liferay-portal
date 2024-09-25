/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iván Zaera Avellón
 */
public class CSPComplianceCheckTest {

	@Test
	public void testGetEnclosingTagStart() {
		CSPComplianceCheck cspComplianceCheck = new CSPComplianceCheck();

		String html = StringBundler.concat(
			"<div>BEFORE</div><aui:a hidden=\"<%= (commerceAddressId > 0) || ",
			"!hasManageAddressesPermission %>\" id=\"<portlet:namespace/>\" ",
			"href=\"javascript:void(0);\" label=\"+-add-address-line\" ",
			"onClick='<%= liferayPortletResponse.getNamespace() + ",
			"\"addStreetAddress();\" %>' /><div>AFTER</div>");

		Assert.assertEquals(
			StringBundler.concat(
				"<aui:a hidden=\"<%= (commerceAddressId > 0) || ",
				"!hasManageAddressesPermission %>\" ",
				"id=\"<portlet:namespace/>\" href=\"javascript:void(0);\" ",
				"label=\"+-add-address-line\" "),
			cspComplianceCheck.getEnclosingTagStart(
				html, html.indexOf("onClick='")));
	}

	@Test
	public void testGetEnclosingTagStartInsideJavaCode() {
		CSPComplianceCheck cspComplianceCheck = new CSPComplianceCheck();

		String html =
			"<div>BEFORE</div><a id=\"anId\" <%= Validator.isNull(onclick) ? " +
				"\"\" : \"onclick='\" + onclick + \"'\" %> /><div>AFTER</div>";

		// This test is a bit weird because it checks for a seemingly invalid
		// return from getEnclosingTagStart method. At first sight it looks like
		// it should return the tag including the starting "<a", however that
		// would make the getEnclosingTagStart implementation much harder for no
		// benefit, since onclick handlers inside <%= %> blocks can only appear
		// for raw HTML tags, ie: using that construct inside an aui:button, for
		// example, wouldn't work. Thus, CSPComplianceCheck will work even with
		// this incomplete return value and we don't need to make the
		// getEnclosingTagStart method overly complex.

		Assert.assertEquals(
			"<%= Validator.isNull(onclick) ? \"\" : \"",
			cspComplianceCheck.getEnclosingTagStart(
				html, html.indexOf("onclick='")));
	}

}