/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.xmlrpc;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.xml.StAXReaderUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class XmlRpcParser {

	public static String buildMethod(String methodName, Object[] arguments)
		throws XmlRpcException {

		StringBundler sb = new StringBundler((arguments.length * 3) + 8);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<methodCall>");
		sb.append("<methodName>");
		sb.append(methodName);
		sb.append("</methodName>");
		sb.append("<params>");

		for (Object argument : arguments) {
			sb.append("<param>");
			sb.append(wrapValue(argument));
			sb.append("</param>");
		}

		sb.append("</params>");
		sb.append("</methodCall>");

		return sb.toString();
	}

	public static Tuple parseMethod(String xml) throws IOException {
		XMLStreamReader xmlStreamReader = null;

		try {
			XMLInputFactory xmlInputFactory =
				StAXReaderUtil.getXMLInputFactory();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			xmlStreamReader.nextTag();
			xmlStreamReader.nextTag();
			xmlStreamReader.next();

			String methodName = xmlStreamReader.getText();

			List<Object> arguments = new ArrayList<>();

			xmlStreamReader.nextTag();

			String name = xmlStreamReader.getLocalName();

			while (!name.equals("methodCall")) {
				xmlStreamReader.nextTag();

				name = xmlStreamReader.getLocalName();

				if (!name.equals("param")) {
					continue;
				}

				xmlStreamReader.nextTag();

				name = xmlStreamReader.getLocalName();

				int event = xmlStreamReader.next();

				if (event == XMLStreamConstants.START_ELEMENT) {
					name = xmlStreamReader.getLocalName();

					xmlStreamReader.next();

					String text = xmlStreamReader.getText();

					if (name.equals("string")) {
						arguments.add(text);
					}
					else if (name.equals("int") || name.equals("i4")) {
						arguments.add(GetterUtil.getInteger(text));
					}
					else if (name.equals("double")) {
						arguments.add(GetterUtil.getDouble(text));
					}
					else if (name.equals("boolean")) {
						arguments.add(GetterUtil.getBoolean(text));
					}
					else {
						throw new IOException(
							"XML-RPC not implemented for " + name);
					}

					xmlStreamReader.nextTag();
					xmlStreamReader.nextTag();
					xmlStreamReader.nextTag();
				}
				else {
					arguments.add(xmlStreamReader.getText());

					xmlStreamReader.nextTag();
					xmlStreamReader.nextTag();
				}

				name = xmlStreamReader.getLocalName();
			}

			return new Tuple(methodName, arguments.toArray());
		}
		catch (Exception exception) {
			throw new IOException(exception);
		}
		finally {
			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception);
					}
				}
			}
		}
	}

	public static Response parseResponse(String xml) throws XmlRpcException {
		XMLStreamReader xmlStreamReader = null;

		try {
			XMLInputFactory xmlInputFactory =
				StAXReaderUtil.getXMLInputFactory();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			xmlStreamReader.nextTag();
			xmlStreamReader.nextTag();

			String name = xmlStreamReader.getLocalName();

			if (name.equals("params")) {
				String description = null;

				xmlStreamReader.nextTag();
				xmlStreamReader.nextTag();

				int event = xmlStreamReader.next();

				if (event == XMLStreamConstants.START_ELEMENT) {
					xmlStreamReader.next();

					description = xmlStreamReader.getText();
				}
				else {
					description = xmlStreamReader.getText();
				}

				return XmlRpcUtil.createSuccess(description);
			}
			else if (name.equals("fault")) {
				int code = 0;
				String description = null;

				xmlStreamReader.nextTag();
				xmlStreamReader.nextTag();

				for (int i = 0; i < 2; i++) {
					xmlStreamReader.nextTag();
					xmlStreamReader.nextTag();

					xmlStreamReader.next();

					String valueName = xmlStreamReader.getText();

					if (valueName.equals("faultCode")) {
						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();

						name = xmlStreamReader.getLocalName();

						if (name.equals("int") || name.equals("i4")) {
							xmlStreamReader.next();

							code = GetterUtil.getInteger(
								xmlStreamReader.getText());
						}

						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();
					}
					else if (valueName.equals("faultString")) {
						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();

						int event = xmlStreamReader.next();

						if (event == XMLStreamConstants.START_ELEMENT) {
							xmlStreamReader.next();

							description = xmlStreamReader.getText();

							xmlStreamReader.nextTag();
						}
						else {
							description = xmlStreamReader.getText();
						}

						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();
					}
				}

				return XmlRpcUtil.createFault(code, description);
			}

			return null;
		}
		catch (Exception exception) {
			throw new XmlRpcException(xml, exception);
		}
		finally {
			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception);
					}
				}
			}
		}
	}

	public static String wrapValue(Object value) throws XmlRpcException {
		if (value == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append("<value>");

		if (value instanceof String) {
			sb.append("<string>");
			sb.append(value.toString());
			sb.append("</string>");
		}
		else if (value instanceof Integer || value instanceof Short) {
			sb.append("<i4>");
			sb.append(value.toString());
			sb.append("</i4>");
		}
		else if (value instanceof Double || value instanceof Float) {
			sb.append("<double>");
			sb.append(value.toString());
			sb.append("</double>");
		}
		else if (value instanceof Boolean) {
			sb.append("<boolean>");

			if ((Boolean)value) {
				sb.append(CharPool.NUMBER_1);
			}
			else {
				sb.append(CharPool.NUMBER_0);
			}

			sb.append("</boolean>");
		}
		else {
			throw new XmlRpcException("Unsupported type " + value.getClass());
		}

		sb.append("</value>");

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(XmlRpcParser.class);

}