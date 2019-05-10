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

package com.liferay.dynamic.data.mapping.kernel;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.File;

import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marcellus Tavares
 */
@ProviderType
public interface DDMTemplateManager {

	public static final String TEMPLATE_MODE_CREATE = "create";

	public static final String TEMPLATE_TYPE_DISPLAY = "display";

	public static final String TEMPLATE_TYPE_MACRO = "macro";

	public static final String TEMPLATE_VERSION_DEFAULT = "1.0";

	public DDMTemplate addTemplate(
			long userId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, String templateKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String mode, String language, String script,
			boolean cacheable, boolean smallImage, String smallImageURL,
			File smallImageFile, ServiceContext serviceContext)
		throws PortalException;

	public DDMTemplate fetchTemplate(
		long groupId, long classNameId, String templateKey);

	public DDMTemplate getTemplate(long templateId) throws PortalException;

	public default DDMTemplate updateTemplate(
			long userId, long templateId, long classPK,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String mode, String language, String script,
			boolean cacheable, boolean smallImage, String smallImageURL,
			File smallImageFile, ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

}