/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.frontend.js.web.internal.servlet.taglib.aui;

import com.liferay.frontend.js.web.internal.servlet.taglib.aui.part.AMDPortletDataPart;
import com.liferay.frontend.js.web.internal.servlet.taglib.aui.part.ESMPortletDataPart;
import com.liferay.portal.kernel.servlet.taglib.aui.AMDRequire;
import com.liferay.portal.kernel.servlet.taglib.aui.ESMImport;
import com.liferay.portal.kernel.servlet.taglib.aui.PortletData;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Iván Zaera Avellón
 */
public class PortletDataCollectionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGenerateVariableDoesNotCollide() {
		PortletData portletData = new PortletData();

		portletData.add(
			new AMDPortletDataPart(
				"content", "frontend-js-web as frontendJsWeb"));

		portletData.add(
			new ESMPortletDataPart(
				"content", "frontend-js-web as frontendJsWeb",
				Collections.singleton(
					new ESMImport(
						"symbol", "frontendJsWeb", "frontend-js-web"))));

		PortletDataCollection portletDataCollection = new PortletDataCollection(
			Collections.singleton(portletData));

		List<String> variables = new ArrayList<>();

		for (ESMImport esmImport : portletDataCollection.getESMImports()) {
			variables.add(esmImport.getAlias());
		}

		for (AMDRequire amdRequire : portletDataCollection.getAMDRequires()) {
			variables.add(amdRequire.getAlias());
		}

		assertVariables(variables, "frontendJsWeb", "frontendJsWeb0");
	}

	@Test
	public void testGenerateVariableReplacesInvalidFirstCharacter() {
		PortletData portletData = new PortletData();

		portletData.add(
			new AMDPortletDataPart("content", "_Var,1Var,*Var,/Var"));

		PortletDataCollection portletDataCollection = new PortletDataCollection(
			Collections.singleton(portletData));

		Collection<AMDRequire> amdRequires =
			portletDataCollection.getAMDRequires();

		List<String> variables = new ArrayList<>();

		for (AMDRequire amdRequire : amdRequires) {
			variables.add(amdRequire.getAlias());
		}

		assertVariables(variables, "_Var", "_Var1", "_Var2", "_Var3");
	}

	@Test
	public void testGenerateVariableStripsInvalidCharacters() throws Exception {
		PortletData portletData = new PortletData();

		portletData.add(
			new AMDPortletDataPart("content", "var,V ar,va*r,var/"));

		PortletDataCollection portletDataCollection = new PortletDataCollection(
			Collections.singleton(portletData));

		Collection<AMDRequire> amdRequires =
			portletDataCollection.getAMDRequires();

		List<String> variables = new ArrayList<>();

		for (AMDRequire amdRequire : amdRequires) {
			variables.add(amdRequire.getAlias());
		}

		assertVariables(variables, "_var", "vAr", "vaR", "var1");
	}

	@Test
	public void testGenerateVariableStripsLastInvalidCharacter() {
		PortletData portletData = new PortletData();

		portletData.add(new AMDPortletDataPart("content", "var!"));

		PortletDataCollection portletDataCollection = new PortletDataCollection(
			Collections.singleton(portletData));

		Collection<AMDRequire> amdRequires =
			portletDataCollection.getAMDRequires();

		List<String> variables = new ArrayList<>();

		for (AMDRequire amdRequire : amdRequires) {
			variables.add(amdRequire.getAlias());
		}

		assertVariables(variables, "_var");
	}

	protected void assertVariables(
		List<String> variables, String... expectedVariables) {

		for (String expectedVariable : expectedVariables) {
			Assert.assertTrue(
				"Missing variable: " + expectedVariable,
				variables.remove(expectedVariable));
		}

		Assert.assertTrue(
			"Unexpected variables found: " + variables, variables.isEmpty());
	}

}