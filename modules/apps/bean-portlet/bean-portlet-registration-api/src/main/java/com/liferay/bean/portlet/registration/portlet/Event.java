/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.registration.portlet;

import java.util.List;

import javax.xml.namespace.QName;

/**
 * @author Neil Griffin
 */
public interface Event {

	public List<QName> getAliasQNames();

	public QName getQName();

	public String getValueType();

}