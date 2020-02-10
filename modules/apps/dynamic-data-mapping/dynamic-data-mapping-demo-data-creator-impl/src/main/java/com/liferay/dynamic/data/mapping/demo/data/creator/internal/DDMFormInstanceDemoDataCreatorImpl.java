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
	public DDMFormInstance create(long userId, long groupId, Date createDate)
		throws PortalException {

		DDMStructure ddmStructure = _ddmStructureDemoDataCreator.create(
			userId, groupId);

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
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceLocalService.addFormInstance(
				ddmStructure.getUserId(), ddmStructure.getGroupId(),
				ddmStructure.getStructureId(), ddmStructure.getNameMap(),
				ddmStructure.getNameMap(), ddmFormValues, serviceContext);

		if (createDate != null) {
			ddmFormInstance.setCreateDate(createDate);

			ddmFormInstance =
				_ddmFormInstanceLocalService.updateDDMFormInstance(
					ddmFormInstance);
		}

		_ddmFormInstanceIds.add(ddmFormInstance.getFormInstanceId());

		return ddmFormInstance;
	}

	@Override
	public void delete() throws PortalException {
		for (Long ddmFormInstanceId : _ddmFormInstanceIds) {
			_ddmFormInstanceIds.remove(ddmFormInstanceId);

			_ddmFormInstanceLocalService.deleteFormInstance(ddmFormInstanceId);
		}

		_ddmStructureDemoDataCreator.delete();
	}

	private final List<Long> _ddmFormInstanceIds = new CopyOnWriteArrayList<>();

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference
	private DDMStructureDemoDataCreator _ddmStructureDemoDataCreator;

}