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

package com.liferay.portal.kernel.templateparser;

import com.liferay.portal.kernel.xml.Document;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Tina Tina
 */
public interface TransformerListener {

	public String onOutput(
		String output, String languageId, Map<String, String> tokens);

	public String onScript(
		String script, Document document, String languageId,
		Map<String, String> tokens);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #onScript(
	 *			   String, Document, String, Map)}
	 */
	@Deprecated
	public String onScript(
		String script, String xml, String languageId,
		Map<String, String> tokens);

	public Document onXml(
		Document document, String languageId, Map<String, String> tokens);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #onXml(
	 *			   Document, String, Map)}
	 */
	@Deprecated
	public String onXml(
		String xml, String languageId, Map<String, String> tokens);

}