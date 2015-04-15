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

package com.liferay.portal.security.xml;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;

import java.net.ConnectException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventReader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Tomas Polesovsky
 */
@PrepareForTest({PropsValues.class})
@RunWith(PowerMockRunner.class)
public class SecureXMLFactoryProviderImplTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_xmlBombBillionLaughsXML = readDependency(
			"xml-bomb-billion-laughs.xml");
		_xmlBombQuadraticBlowupXML = readDependency(
			"xml-bomb-quadratic-blowup.xml");
		_xxeGeneralEntitiesXML = readDependency("xxe-general-entities.xml");
		_xxeGeneralEntitiesXML2 = readDependency("xxe-general-entities-2.xml");
		_xxeParameterEntitiesXML = readDependency("xxe-parameter-entities.xml");
		_xxeParameterEntitiesXML2 = readDependency(
			"xxe-parameter-entities-2.xml");
	}

	@Before
	public void setUp() throws Exception {
		_secureXMLFactoryProvider = new SecureXMLFactoryProviderImpl();
	}

	@Test
	public void testNewDocumentBuilderFactory() throws Throwable {
		XMLSecurityTest documentBuilderTestCase = new XMLSecurityTest() {

			@Override
			public void run(String xml) throws Exception {
				DocumentBuilderFactory documentBuilderFactory =
					_secureXMLFactoryProvider.newDocumentBuilderFactory();

				DocumentBuilder documentBuilder =
					documentBuilderFactory.newDocumentBuilder();

				documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
			}

		};

		// billion laughs

		disableXMLSecurity();
		runXMLSecurityTest(
			documentBuilderTestCase, _xmlBombBillionLaughsXML,
			OutOfMemoryError.class, "Billion Laughs XML attack doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			documentBuilderTestCase, _xmlBombBillionLaughsXML,
			SAXParseException.class,
			"Vulnerable to Billion Laughs XML attack!");

		// quadratic blowup

		disableXMLSecurity();
		runXMLSecurityTest(
			documentBuilderTestCase, _xmlBombQuadraticBlowupXML,
			OutOfMemoryError.class,
			"Quadratic Blowup XML attack doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			documentBuilderTestCase, _xmlBombQuadraticBlowupXML,
			SAXParseException.class,
			"Vulnerable to Quadratic Blowup XML attack!");

		// general XXE using SYSTEM entity

		disableXMLSecurity();
		runXMLSecurityTest(
			documentBuilderTestCase, _xxeGeneralEntitiesXML,
			ConnectException.class,
			"General Entities XXE attack using SYSTEM entity doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			documentBuilderTestCase, _xxeGeneralEntitiesXML,
			SAXParseException.class,
			"Vulnerable to General Entities XXE attack using SYSTEM entity!");

		// general XXE using PUBLIC entity

		disableXMLSecurity();
		runXMLSecurityTest(
			documentBuilderTestCase, _xxeGeneralEntitiesXML2,
			ConnectException.class,
			"General Entities XXE attack using PUBLIC entity doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			documentBuilderTestCase, _xxeGeneralEntitiesXML2,
			SAXParseException.class,
			"Vulnerable to General Entities XXE attack using PUBLIC entity!");

		// parameter XXE using SYSTEM entity

		disableXMLSecurity();
		runXMLSecurityTest(
			documentBuilderTestCase, _xxeParameterEntitiesXML,
			ConnectException.class,
			"Parameter Entities XXE using SYSTEM entity doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			documentBuilderTestCase, _xxeParameterEntitiesXML,
			SAXParseException.class,
			"Vulnerable to Parameter Entities XXE using SYSTEM entity!");

		// parameter XXE using PUBLIC entity

		disableXMLSecurity();
		runXMLSecurityTest(
			documentBuilderTestCase, _xxeParameterEntitiesXML2,
			ConnectException.class,
			"Parameter Entities XXE attack using PUBLIC entity doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			documentBuilderTestCase, _xxeParameterEntitiesXML2,
			SAXParseException.class,
			"Vulnerable to Parameter Entities XXE attack using PUBLIC entity!");
	}

	@Test
	public void testNewXMLInputFactory() throws Throwable {
		XMLSecurityTest xmlInputFactoryTest = new XMLSecurityTest() {

			@Override
			public void run(String xml) throws Exception {
				XMLEventReader xmlEventReader =
					_secureXMLFactoryProvider.newXMLInputFactory().
						createXMLEventReader(new StringReader(xml));

				while (xmlEventReader.hasNext()) {
					xmlEventReader.next();
				}
			}

		};

		// billion laughs

		disableXMLSecurity();
		runXMLSecurityTest(
			xmlInputFactoryTest, _xmlBombBillionLaughsXML,
			OutOfMemoryError.class, "Billion Laughs XML attack doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			xmlInputFactoryTest, _xmlBombBillionLaughsXML, null,
			"Vulnerable to Billion Laughs XML attack!");

		// quadratic blowup

		disableXMLSecurity();
		runXMLSecurityTest(
			xmlInputFactoryTest, _xmlBombQuadraticBlowupXML,
			OutOfMemoryError.class,
			"Quadratic Blowup XML attack doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			xmlInputFactoryTest, _xmlBombQuadraticBlowupXML, null,
			"Vulnerable to Quadratic Blowup XML attack!");

		// general XXE using SYSTEM entity

		disableXMLSecurity();
		runXMLSecurityTest(
			xmlInputFactoryTest, _xxeGeneralEntitiesXML, ConnectException.class,
			"General Entities XXE attack using SYSTEM entity doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			xmlInputFactoryTest, _xxeGeneralEntitiesXML, null,
			"Vulnerable to General Entities XXE attack using SYSTEM entity!");

		// general XXE using PUBLIC entity

		disableXMLSecurity();
		runXMLSecurityTest(
			xmlInputFactoryTest, _xxeGeneralEntitiesXML2,
			ConnectException.class,
			"General Entities XXE attack using PUBLIC entity doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			xmlInputFactoryTest, _xxeGeneralEntitiesXML2, null,
			"Vulnerable to  General Entities XXE attack using PUBLIC entity!");

		// parameter XXE using SYSTEM entity

		disableXMLSecurity();
		runXMLSecurityTest(
			xmlInputFactoryTest, _xxeParameterEntitiesXML,
			ConnectException.class,
			"Parameter Entities XXE using SYSTEM entity doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			xmlInputFactoryTest, _xxeParameterEntitiesXML, null,
			"Vulnerable to Parameter Entities XXE using SYSTEM entity!");

		// parameter XXE using PUBLIC entity

		disableXMLSecurity();
		runXMLSecurityTest(
			xmlInputFactoryTest, _xxeParameterEntitiesXML2,
			ConnectException.class,
			"Parameter Entities XXE attack using PUBLIC entity doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			xmlInputFactoryTest, _xxeParameterEntitiesXML2, null,
			"Vulnerable to Parameter Entities XXE attack using PUBLIC entity!");
	}

	@Test
	public void testNewXMLReader() throws Throwable {
		XMLSecurityTest xmlReaderTest = new XMLSecurityTest() {

			@Override
			public void run(String xml) throws Exception {
				XMLReader xmlReader = _secureXMLFactoryProvider.newXMLReader();

				if (xmlReader instanceof StripDoctypeXMLReader) {
					xmlReader =
						((StripDoctypeXMLReader)xmlReader).getXmlReader();
				}

				xmlReader.setContentHandler(
					new DefaultHandler() {

						@Override
						public void characters(
							char[] ch, int start, int length) {

							_contentLenght += length;

							if (_contentLenght > (1024 * 1024 * 10)) {
								throw new RuntimeException(
									new OutOfMemoryError());
							}
						}

						private int _contentLenght = 0;

					});

				xmlReader.parse(new InputSource(new StringReader(xml)));
			}

		};

		// billion laughs

		disableXMLSecurity();
		runXMLSecurityTest(
			xmlReaderTest, _xmlBombBillionLaughsXML, OutOfMemoryError.class,
			"Billion Laughs XML attack doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			xmlReaderTest, _xmlBombBillionLaughsXML, SAXParseException.class,
			"Vulnerable to Billion Laughs XML attack!");

		// quadratic blowup

		disableXMLSecurity();
		runXMLSecurityTest(
			xmlReaderTest, _xmlBombQuadraticBlowupXML, OutOfMemoryError.class,
			"Quadratic Blowup XML attack doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			xmlReaderTest, _xmlBombQuadraticBlowupXML, SAXParseException.class,
			"Vulnerable to Quadratic Blowup XML attack!");

		// general XXE using SYSTEM entity

		disableXMLSecurity();
		runXMLSecurityTest(
			xmlReaderTest, _xxeGeneralEntitiesXML, ConnectException.class,
			"General Entities XXE attack using SYSTEM entity doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			xmlReaderTest, _xxeGeneralEntitiesXML, SAXParseException.class,
			"Vulnerable to General Entities XXE attack using SYSTEM entity!");

		// general XXE using PUBLIC entity

		disableXMLSecurity();
		runXMLSecurityTest(
			xmlReaderTest, _xxeGeneralEntitiesXML2, ConnectException.class,
			"General Entities XXE attack using PUBLIC entity doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			xmlReaderTest, _xxeGeneralEntitiesXML2, SAXParseException.class,
			"Vulnerable to General Entities XXE attack using PUBLIC entity!");

		// parameter XXE using SYSTEM entity

		disableXMLSecurity();
		runXMLSecurityTest(
			xmlReaderTest, _xxeParameterEntitiesXML, ConnectException.class,
			"Parameter Entities XXE using SYSTEM entity doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			xmlReaderTest, _xxeParameterEntitiesXML, SAXParseException.class,
			"Vulnerable to Parameter Entities XXE using SYSTEM entity!");

		// parameter XXE using PUBLIC entity

		disableXMLSecurity();
		runXMLSecurityTest(
			xmlReaderTest, _xxeParameterEntitiesXML2, ConnectException.class,
			"Parameter Entities XXE attack using PUBLIC entity doesn't work!");

		enableXMLSecurity();
		runXMLSecurityTest(
			xmlReaderTest, _xxeParameterEntitiesXML2, SAXParseException.class,
			"Vulnerable to Parameter Entities XXE attack using PUBLIC entity!");
	}

	protected static String readDependency(String name) throws IOException {
		return StringUtil.read(
			SecureXMLFactoryProviderImplTest.class.getResourceAsStream(
				"dependencies/" + name));
	}

	protected void disableXMLSecurity() {
		Whitebox.setInternalState(
			PropsValues.class, "XML_SECURITY_ENABLED", false);
	}

	protected void enableXMLSecurity() {
		Whitebox.setInternalState(
			PropsValues.class, "XML_SECURITY_ENABLED", true);
	}

	protected void runXMLSecurityTest(
			XMLSecurityTest xmlSecurityTest, String xml,
			Class<? extends Throwable> expectedException, String failMessage)
		throws Throwable {

		try {
			xmlSecurityTest.run(xml);

			if (expectedException != null) {
				Assert.fail(failMessage);
			}
		}
		catch (Throwable t) {
			if (expectedException == null) {
				throw t;
			}

			Throwable cause = t;

			while (cause.getCause() != null) {
				cause = cause.getCause();
			}

			Class<?> causeClass = cause.getClass();

			if (!causeClass.isAssignableFrom(expectedException)) {
				throw t;
			}
		}
	}

	private static String _xmlBombBillionLaughsXML;
	private static String _xmlBombQuadraticBlowupXML;
	private static String _xxeGeneralEntitiesXML;
	private static String _xxeGeneralEntitiesXML2;
	private static String _xxeParameterEntitiesXML;
	private static String _xxeParameterEntitiesXML2;

	private SecureXMLFactoryProviderImpl _secureXMLFactoryProvider;

	private abstract class XMLSecurityTest {

		public abstract void run(String xml) throws Exception;

	}

}