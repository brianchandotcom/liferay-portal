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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.exception.NoSuchTemplateVersionException;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
import com.liferay.dynamic.data.mapping.service.base.DDMTemplateVersionLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.util.comparator.TemplateVersionVersionComparator;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMTemplateVersion",
	service = AopService.class
)
public class DDMTemplateVersionLocalServiceImpl
	extends DDMTemplateVersionLocalServiceBaseImpl {

	@Override
	public void deleteTemplateVersions(long templateId) {
		ddmTemplateVersionPersistence.removeByTemplateId(templateId);
	}

	@Override
	public DDMTemplateVersion getLatestTemplateVersion(long templateId)
		throws PortalException {

		List<DDMTemplateVersion> templateVersions =
			ddmTemplateVersionPersistence.findByTemplateId(templateId);

		if (templateVersions.isEmpty()) {
			throw new NoSuchTemplateVersionException(
				"No template versions found for template ID " + templateId);
		}

		templateVersions = ListUtil.copy(templateVersions);

		Collections.sort(
			templateVersions, new TemplateVersionVersionComparator());

		return templateVersions.get(0);
	}

	@Override
	public DDMTemplateVersion getTemplateVersion(long templateVersionId)
		throws PortalException {

		return ddmTemplateVersionPersistence.findByPrimaryKey(
			templateVersionId);
	}

	@Override
	public DDMTemplateVersion getTemplateVersion(
			long templateId, String version)
		throws PortalException {

		return ddmTemplateVersionPersistence.findByT_V(templateId, version);
	}

	@Override
	public List<DDMTemplateVersion> getTemplateVersions(long templateId) {
		return ddmTemplateVersionPersistence.findByTemplateId(templateId);
	}

	@Override
	public List<DDMTemplateVersion> getTemplateVersions(
		long templateId, int start, int end,
		OrderByComparator<DDMTemplateVersion> orderByComparator) {

		return ddmTemplateVersionPersistence.findByTemplateId(
			templateId, start, end, orderByComparator);
	}

	@Override
	public int getTemplateVersionsCount(long templateId) {
		return ddmTemplateVersionPersistence.countByTemplateId(templateId);
	}

}