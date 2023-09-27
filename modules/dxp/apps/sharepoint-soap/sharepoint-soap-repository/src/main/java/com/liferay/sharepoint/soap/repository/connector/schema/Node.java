/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema;

import com.liferay.portal.kernel.xml.simple.Element;

/**
 * @author Iván Zaera
 */
public interface Node {

	public void attach(Element element);

	public String toXmlString();

}