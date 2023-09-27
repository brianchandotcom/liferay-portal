/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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