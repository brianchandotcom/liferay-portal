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

package com.liferay.users.admin.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Mendez Gonzalez
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class PhoneStagedModelDataHandler
	extends BaseStagedModelDataHandler<Phone> {

	public static final String[] CLASS_NAMES = {Phone.class.getName()};

	@Override
	public void deleteStagedModel(Phone phone) {
		_phoneLocalService.deletePhone(phone);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		Phone phone = _phoneLocalService.fetchPhoneByUuidAndCompanyId(
			uuid, group.getCompanyId());

		if (phone != null) {
			deleteStagedModel(phone);
		}
	}

	@Override
	public List<Phone> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return ListUtil.fromArray(
			_phoneLocalService.fetchPhoneByUuidAndCompanyId(uuid, companyId));
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Phone phone)
		throws Exception {

		Element phoneElement = portletDataContext.getExportDataElement(phone);

		portletDataContext.addClassedModel(
			phoneElement, ExportImportPathUtil.getModelPath(phone), phone);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Phone phone)
		throws Exception {

		long userId = portletDataContext.getUserId(phone.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			phone);

		Phone existingPhone = _phoneLocalService.fetchPhoneByUuidAndCompanyId(
			phone.getUuid(), portletDataContext.getCompanyId());

		Phone importedPhone = null;

		if (existingPhone == null) {
			serviceContext.setUuid(phone.getUuid());

			importedPhone = _phoneLocalService.addPhone(
				userId, phone.getClassName(), phone.getClassPK(),
				phone.getNumber(), phone.getExtension(), phone.getTypeId(),
				phone.isPrimary(), serviceContext);
		}
		else {
			importedPhone = _phoneLocalService.updatePhone(
				existingPhone.getPhoneId(), phone.getNumber(),
				phone.getExtension(), phone.getTypeId(), phone.isPrimary());
		}

		portletDataContext.importClassedModel(phone, importedPhone);
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setPhoneLocalService(PhoneLocalService phoneLocalService) {
		_phoneLocalService = phoneLocalService;
	}

	private GroupLocalService _groupLocalService;
	private PhoneLocalService _phoneLocalService;

}