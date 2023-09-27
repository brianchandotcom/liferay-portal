/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.loader.modules.extender.npm;

import com.liferay.portal.kernel.servlet.PortalWebResources;

/**
 * Should be implemented by every {@link PortalWebResources} that is based on
 * Javascript content and needs to be notified every time a {@link JSBundle}
 * gets started in order, for example, to refresh its cached content.
 *
 * @author Mariano Álvaro Sáiz
 */
public interface JavaScriptAwarePortalWebResources extends PortalWebResources {

	public void updateLastModifed(long lastModified);

}