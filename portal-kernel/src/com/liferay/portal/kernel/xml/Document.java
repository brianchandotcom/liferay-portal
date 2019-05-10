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

package com.liferay.portal.kernel.xml;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface Document extends Branch, Cloneable {

	public Document addComment(String comment);

	public Document addDocumentType(
		String name, String publicId, String systemId);

	public Document clone();

	public DocumentType getDocumentType();

	public Element getRootElement();

	public String getXMLEncoding();

	public void setRootElement(Element rootElement);

	public void setXMLEncoding(String encoding);

}