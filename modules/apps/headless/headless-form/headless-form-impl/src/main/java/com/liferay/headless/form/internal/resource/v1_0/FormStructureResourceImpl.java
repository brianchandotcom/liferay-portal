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

package com.liferay.headless.form.internal.resource.v1_0;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.headless.form.dto.v1_0.FormStructure;
import com.liferay.headless.form.internal.dto.v1_0.util.StructureUtil;
import com.liferay.headless.form.resource.v1_0.FormStructureResource;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 * @author Victor Oliveira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/form-structure.properties",
	scope = ServiceScope.PROTOTYPE, service = FormStructureResource.class
)
public class FormStructureResourceImpl extends BaseFormStructureResourceImpl {

	@Override
	public FormStructure getFormStructure(Long formStructureId)
		throws Exception {

		return StructureUtil.toFormStructure(
			contextAcceptLanguage.isAcceptAllLanguages(),
			_ddmStructureLocalService.getStructure(formStructureId),
			contextAcceptLanguage.getPreferredLocale(), _portal,
			_userLocalService);
	}

	@Override
	public Page<FormStructure> getSiteFormStructuresPage(
			Long siteId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddmStructureLocalService.getStructures(
					siteId, _getClassNameId(), pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				ddmStructure -> StructureUtil.toFormStructure(
					contextAcceptLanguage.isAcceptAllLanguages(), ddmStructure,
					contextAcceptLanguage.getPreferredLocale(), _portal,
					_userLocalService)),
			pagination,
			_ddmStructureLocalService.getStructuresCount(
				siteId, _getClassNameId()));
	}

	private long _getClassNameId() {
		return _portal.getClassNameId(DDMFormInstance.class.getName());
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}