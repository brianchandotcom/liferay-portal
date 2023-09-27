/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.taglib.aui;

import java.io.IOException;
import java.io.Writer;

import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public interface PortletDataRenderer {

	public void write(Collection<PortletData> portletDatas, Writer writer)
		throws IOException;

}