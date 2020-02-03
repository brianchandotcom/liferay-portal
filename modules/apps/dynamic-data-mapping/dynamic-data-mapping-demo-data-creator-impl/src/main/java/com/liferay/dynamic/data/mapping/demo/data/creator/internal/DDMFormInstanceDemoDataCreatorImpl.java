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

package com.liferay.dynamic.data.mapping.demo.data.creator.internal;

import com.liferay.dynamic.data.mapping.demo.data.creator.DDMFormInstanceDemoDataCreator;
import com.liferay.dynamic.data.mapping.demo.data.creator.DDMStructureDemoDataCreator;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = DDMFormInstanceDemoDataCreator.class)
public class DDMFormInstanceDemoDataCreatorImpl
	implements DDMFormInstanceDemoDataCreator {

	@Override
	public DDMFormInstance create(
			long companyId, Date createDate, long groupId, long userId)
		throws PortalException {

		DDMStructure structure = _ddmStructureDemoDataCreator.create(
			groupId, userId);

		DDMFormValues ddmFormValues = new DDMFormValues(
			new DDMForm() {
				{
					setAvailableLocales(Collections.singleton(LocaleUtil.US));
					setDefaultLocale(LocaleUtil.US);
				}
			}) {

			{
				setAvailableLocales(Collections.singleton(LocaleUtil.US));
				setDefaultLocale(LocaleUtil.US);
			}
		};

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(companyId);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		DDMFormInstance formInstance =
			_ddmFormInstanceLocalService.addFormInstance(
				structure.getUserId(), structure.getGroupId(),
				structure.getStructureId(), structure.getNameMap(),
				structure.getNameMap(), ddmFormValues, serviceContext);

		if (createDate != null) {
			formInstance.setCreateDate(createDate);
			formInstance.setModifiedDate(createDate);

			formInstance = _ddmFormInstanceLocalService.updateDDMFormInstance(
				formInstance);
		}

		_formInstanceIds.add(formInstance.getFormInstanceId());

		return formInstance;
	}

	@Override
	public void delete() throws PortalException {
		for (Long formInstanceId : _formInstanceIds) {
			_formInstanceIds.remove(formInstanceId);

			_ddmFormInstanceLocalService.deleteFormInstance(formInstanceId);
		}

		_ddmStructureDemoDataCreator.delete();
	}

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference
	private DDMStructureDemoDataCreator _ddmStructureDemoDataCreator;

	private final List<Long> _formInstanceIds = new CopyOnWriteArrayList<>();

}