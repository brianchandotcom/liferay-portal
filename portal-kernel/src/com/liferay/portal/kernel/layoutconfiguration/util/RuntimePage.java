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

package com.liferay.portal.kernel.layoutconfiguration.util;

import com.liferay.portal.kernel.layoutconfiguration.util.xml.RuntimeLogic;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.StringBundler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
@ProviderType
public interface RuntimePage {

	public StringBundler getProcessedTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String portletId,
			TemplateResource templateResource)
		throws Exception;

	public void processCustomizationSettings(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			TemplateResource templateResource)
		throws Exception;

	public void processCustomizationSettings(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			TemplateResource templateResource, String langType)
		throws Exception;

	public void processTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String portletId,
			TemplateResource templateResource)
		throws Exception;

	public void processTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String portletId,
			TemplateResource templateResource, String langType)
		throws Exception;

	public void processTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			TemplateResource templateResource)
		throws Exception;

	public void processTemplate(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			TemplateResource templateResource, String langType)
		throws Exception;

	public String processXML(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String content)
		throws Exception;

	public String processXML(
			HttpServletRequest httpServletRequest, String content,
			RuntimeLogic runtimeLogic)
		throws Exception;

}