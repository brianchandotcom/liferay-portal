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

package com.liferay.petra.xml;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;

import java.io.IOException;

import org.dom4j.DocumentException;

/**
 * @author Leonardo Barros
 */
public class XMLUtil {

	public static String formatXML(String xml) {

		// If the closing token of a CDATA container is found inside the CDATA
		// container, split the CDATA container into two separate CDATA
		// containers. This is generally accepted method of "escaping" for this
		// case since there is no real way to escape those characters. See
		// LPS-85393 for more information.

		xml = StringUtil.replace(xml, "]]><", "[$SPECIAL_CHARACTER$]");
		xml = StringUtil.replace(xml, "]]>", "]]]]><![CDATA[>");
		xml = StringUtil.replace(xml, "[$SPECIAL_CHARACTER$]", "]]><");

		// This is only supposed to format your xml, however, it will also
		// unwantingly change &#169; and other characters like it into their
		// respective readable versions

		try {
			xml = StringUtil.replace(xml, "&#", "[$SPECIAL_CHARACTER$]");
			xml = Dom4jUtil.toString(xml, StringPool.DOUBLE_SPACE);
			xml = StringUtil.replace(xml, "[$SPECIAL_CHARACTER$]", "&#");

			return xml;
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
		catch (DocumentException documentException) {
			throw new SystemException(documentException);
		}
	}

}