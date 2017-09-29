/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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