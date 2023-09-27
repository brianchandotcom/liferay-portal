/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.display;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Jorge Ferrer
 */
public interface ConfigurationEntry {

	public String getCategory();

	public String getEditURL(
		RenderRequest renderRequest, RenderResponse renderResponse);

	public String getKey();

	public String getName();

	public String getScope();

}