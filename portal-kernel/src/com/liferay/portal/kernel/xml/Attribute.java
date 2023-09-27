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
public interface Attribute extends Node {

	public Object getData();

	public Namespace getNamespace();

	public String getNamespacePrefix();

	public String getNamespaceURI();

	public QName getQName();

	public String getQualifiedName();

	public String getValue();

	public void setData(Object data);

	public void setNamespace(Namespace namespace);

	public void setValue(String value);

}