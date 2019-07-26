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

package com.liferay.portal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Eric Yan
 */
@RunWith(Arquillian.class)
public class LocalizationImplDefaultXmlFactoryImplTest
	extends LocalizationImplTest {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_xmlInputFactoryClassName = System.getProperty(
			XMLInputFactory.class.getName());

		System.setProperty(
			XMLInputFactory.class.getName(),
			"com.sun.xml.internal.stream.XMLInputFactoryImpl");

		_xmlOutputFactoryClassName = System.getProperty(
			XMLOutputFactory.class.getName());

		System.setProperty(
			XMLOutputFactory.class.getName(),
			"com.sun.xml.internal.stream.XMLOutputFactoryImpl");
	}

	@After
	public void tearDown() {
		if (_xmlInputFactoryClassName == null) {
			System.clearProperty(XMLInputFactory.class.getName());
		}
		else {
			System.setProperty(
				XMLInputFactory.class.getName(), _xmlInputFactoryClassName);
		}

		if (_xmlOutputFactoryClassName == null) {
			System.clearProperty(XMLOutputFactory.class.getName());
		}
		else {
			System.setProperty(
				XMLOutputFactory.class.getName(), _xmlOutputFactoryClassName);
		}
	}

	private String _xmlInputFactoryClassName;
	private String _xmlOutputFactoryClassName;

}