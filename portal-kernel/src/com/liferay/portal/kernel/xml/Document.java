/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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