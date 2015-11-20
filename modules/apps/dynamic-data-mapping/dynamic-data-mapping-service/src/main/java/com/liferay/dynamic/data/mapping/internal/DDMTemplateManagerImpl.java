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

package com.liferay.dynamic.data.mapping.internal;

import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.DDMTemplateManager;

import java.io.File;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true)
public class DDMTemplateManagerImpl implements DDMTemplateManager {

	@Override
	public DDMTemplate addTemplate(
			long userId, long groupId, long classNameId, long classPK,
			long resourceClassNameId, String templateKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String type, String mode, String language, String script,
			boolean cacheable, boolean smallImage, String smallImageURL,
			File smallImageFile, ServiceContext serviceContext)
		throws PortalException {

		com.liferay.dynamic.data.mapping.model.DDMTemplate ddmTemplate =
			_ddmTemplateLocalService.addTemplate(
				userId, groupId, classNameId, classPK, resourceClassNameId,
				templateKey, nameMap, descriptionMap, type, mode, language,
				script, cacheable, smallImage, smallImageURL, smallImageFile,
				serviceContext);

		return new DDMTemplateImpl(ddmTemplate);
	}

	@Override
	public DDMTemplate fetchTemplate(
		long groupId, long classNameId, String templateKey) {

		com.liferay.dynamic.data.mapping.model.DDMTemplate ddmTemplate =
			_ddmTemplateLocalService.fetchTemplate(
				groupId, classNameId, templateKey);

		if (ddmTemplate == null) {
			return null;
		}

		return new DDMTemplateImpl(ddmTemplate);
	}

	@Override
	public DDMTemplate getTemplate(long templateId) throws PortalException {
		com.liferay.dynamic.data.mapping.model.DDMTemplate ddmTemplate =
			_ddmTemplateLocalService.getTemplate(templateId);

		return new DDMTemplateImpl(ddmTemplate);
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	private volatile DDMTemplateLocalService _ddmTemplateLocalService;

}