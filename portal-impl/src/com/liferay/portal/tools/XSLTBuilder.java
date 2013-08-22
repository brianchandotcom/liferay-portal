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

package com.liferay.portal.tools;

import java.io.FileOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Brian Wing Shun Chan
 */
public class XSLTBuilder {

	public static void main(String[] args) {
		if (args.length == 3) {
			new XSLTBuilder(args[0], args[1], args[2]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public XSLTBuilder(String xml, String xsl, String html) {
		try {
			String systemSeparator = System.getProperty("line.separator");
			
			String tempSeparator = "\n";
			
			System.setProperty("line.separator", tempSeparator);
			
			TransformerFactory transformerFactory =
				TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer(
				new StreamSource(xsl));

			transformer.transform(
				new StreamSource(xml),
				new StreamResult(new FileOutputStream(html)));
			
			System.setProperty("line.separator", systemSeparator);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}