/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.template;

/**
 * @author Tina Tian
 * @author Raymond Augé
 */
public interface TemplateManager {

	public void destroy();

	public String getName();

	public String[] getRestrictedVariables();

	public Template getTemplate(
		TemplateResource templateResource, boolean restricted);

	public void init() throws TemplateException;

}