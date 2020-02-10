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

import com.liferay.dynamic.data.mapping.demo.data.creator.DDMFormInstanceRecordDemoDataCreator;
import com.liferay.dynamic.data.mapping.demo.data.creator.DDMStructureDemoDataCreator;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = DDMFormInstanceRecordDemoDataCreator.class)
public class DDMFormInstanceRecordDemoDataCreatorImpl
	implements DDMFormInstanceRecordDemoDataCreator {

	@Override
	public DDMFormInstanceRecord create(
			long userId, long groupId, Date createDate, long ddmFormInstanceId)
		throws PortalException {

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceLocalService.getDDMFormInstance(ddmFormInstanceId);

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm) {
			{
				setAvailableLocales(ddmForm.getAvailableLocales());
				setDefaultLocale(ddmForm.getDefaultLocale());
			}
		};

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			if (ddmFormField.isLocalizable()) {
				ddmFormValues.addDDMFormFieldValue(
					new DDMFormFieldValue() {
						{
							setInstanceId(StringUtil.randomString());
							setName(ddmFormField.getName());
							setValue(
								new LocalizedValue(LocaleUtil.US) {
									{
										addString(
											LocaleUtil.US,
											StringUtil.randomString());
									}
								});
						}
					});
			}
			else {
				ddmFormValues.addDDMFormFieldValue(
					new DDMFormFieldValue() {
						{
							setInstanceId(StringUtil.randomString());
							setName(ddmFormField.getName());
							setValue(
								new UnlocalizedValue(
									StringUtil.randomString()));
						}
					});
			}
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		DDMFormInstanceRecord ddmFormInstanceRecord =
			_ddmFormInstanceRecordLocalService.addFormInstanceRecord(
				userId, groupId, ddmFormInstanceId, ddmFormValues,
				serviceContext);

		if (createDate != null) {
			ddmFormInstanceRecord.setCreateDate(createDate);

			ddmFormInstanceRecord =
				_ddmFormInstanceRecordLocalService.updateDDMFormInstanceRecord(
					ddmFormInstanceRecord);
		}

		_ddmFormInstanceRecordIds.add(
			ddmFormInstanceRecord.getFormInstanceRecordId());

		return ddmFormInstanceRecord;
	}

	@Override
	public void delete() throws PortalException {
		for (Long ddmFormInstanceRecordId : _ddmFormInstanceRecordIds) {
			_ddmFormInstanceRecordIds.remove(ddmFormInstanceRecordId);

			_ddmFormInstanceRecordLocalService.deleteFormInstanceRecord(
				ddmFormInstanceRecordId);
		}

		_ddmStructureDemoDataCreator.delete();
	}

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	private final List<Long> _ddmFormInstanceRecordIds =
		new CopyOnWriteArrayList<>();

	@Reference
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	@Reference
	private DDMStructureDemoDataCreator _ddmStructureDemoDataCreator;

}