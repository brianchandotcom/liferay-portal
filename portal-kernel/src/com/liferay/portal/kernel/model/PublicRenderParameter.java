/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.xml.QName;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public interface PublicRenderParameter extends Serializable {

	public String getIdentifier();

	public PortletApp getPortletApp();

	public QName getQName();

	public void setIdentifier(String identifier);

	public void setPortletApp(PortletApp portletApp);

	public void setQName(QName qName);

}