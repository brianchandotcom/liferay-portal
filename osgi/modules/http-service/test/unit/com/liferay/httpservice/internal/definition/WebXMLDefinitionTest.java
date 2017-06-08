/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal.definition;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class WebXMLDefinitionTest {

	@Before
	public void setUp() {
		_webXmlDefinition = new WebXMLDefinition();
	}

	@Test
	public void testAddMultipleFilterDefinition() {
		addFilterDefinitionsAndVerify(5);
	}

	@Test
	public void testAddMultipleListenerDefinition() {
		addListenerDefinitionsAndVerify(5);
	}

	@Test
	public void testAddMultipleServletDefinition() {
		addServletDefinitionAndVerify(5);
	}

	@Test
	public void testAddOneFilterDefinition() {
		addFilterDefinitionsAndVerify(1);
	}

	@Test
	public void testAddOneListenerDefinition() {
		addListenerDefinitionsAndVerify(1);
	}

	@Test
	public void testAddOneServletDefinition() {
		addServletDefinitionAndVerify(1);
	}

	protected void addFilterDefinitionsAndVerify(int count) {
		for (int i = 0; i < count; i++) {
			String s = String.valueOf(i);

			FilterDefinition filterDefinition = buildFilterDefinition(s, s);

			_webXmlDefinition.setFilterDefinition(s, filterDefinition);
		}

		Assert.assertEquals(
			count, _webXmlDefinition.getFilterDefinitions().size());
	}

	protected void addListenerDefinitionsAndVerify(int count) {
		for (int i = 0; i < count; i++) {
			ListenerDefinition listenerDefinition = buildListenerDefinition();

			_webXmlDefinition.addListenerDefinition(listenerDefinition);
		}

		Assert.assertEquals(
			count, _webXmlDefinition.getListenerDefinitions().size());
	}

	protected void addServletDefinitionAndVerify(int count) {
		for (int i = 0; i < count; i++) {
			String s = String.valueOf(i);

			ServletDefinition servletDefinition = buildServletDefinition(s, s);

			_webXmlDefinition.setServletDefinition(s, servletDefinition);
		}

		Assert.assertEquals(
			count, _webXmlDefinition.getServletDefinitions().size());
	}

	protected FilterDefinition buildFilterDefinition(
		String name, String urlPattern) {

		FilterDefinition filterDefinition = new FilterDefinition();

		filterDefinition.addURLPattern(urlPattern);
		filterDefinition.setName(name);

		return filterDefinition;
	}

	protected ListenerDefinition buildListenerDefinition() {
		ListenerDefinition listenerDefinition = new ListenerDefinition();

		listenerDefinition.setListener(new Object());

		return listenerDefinition;
	}

	protected ServletDefinition buildServletDefinition(
		String name, String urlPattern) {

		ServletDefinition servletDefinition = new ServletDefinition();

		servletDefinition.addURLPattern(urlPattern);
		servletDefinition.setName(name);

		return servletDefinition;
	}

	private WebXMLDefinition _webXmlDefinition;

}