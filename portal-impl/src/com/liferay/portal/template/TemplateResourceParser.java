/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template;

import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;

/**
 * @author Tina Tian
 */
public interface TemplateResourceParser {

	public TemplateResource getTemplateResource(String templateId)
		throws TemplateException;

	public boolean isTemplateResourceValid(String templateId, String langType);

}