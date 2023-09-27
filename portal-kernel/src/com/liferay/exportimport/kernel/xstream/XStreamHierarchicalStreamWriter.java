/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.xstream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public interface XStreamHierarchicalStreamWriter {

	public void addAttribute(String key, String value);

	public void close();

	public void endNode();

	public void flush();

	public void setValue(String value);

	public void startNode(String name);

	public XStreamHierarchicalStreamWriter underlyingWriter();

}