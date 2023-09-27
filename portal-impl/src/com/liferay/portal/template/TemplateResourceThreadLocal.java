/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.template.TemplateResource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tina Tian
 */
public class TemplateResourceThreadLocal {

	public static TemplateResource getTemplateResource(String templateType) {
		Map<String, TemplateResource> templateResources =
			_templateResources.get();

		return templateResources.get(templateType);
	}

	public static void setTemplateResource(
		String templateType, TemplateResource templateResource) {

		Map<String, TemplateResource> templateResources =
			_templateResources.get();

		templateResources.put(templateType, templateResource);
	}

	private static final ThreadLocal<Map<String, TemplateResource>>
		_templateResources = new CentralizedThreadLocal<>(
			TemplateResourceThreadLocal.class.getName() + "._templateResources",
			HashMap::new, false);

}