/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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