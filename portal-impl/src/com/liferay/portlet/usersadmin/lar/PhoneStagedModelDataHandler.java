/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.usersadmin.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Phone;
import com.liferay.portal.service.PhoneLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

/**
 * @author David Mendez Gonzalez
 */
public class PhoneStagedModelDataHandler
	extends BaseStagedModelDataHandler<Phone> {

	public static final String[] CLASS_NAMES = {Phone.class.getName()};

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
			phoneElement, ExportImportPathUtil.getModelPath(phone), phone,
			UsersAdminPortletDataHandler.NAMESPACE);

	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Phone phone)
		throws Exception {

		long userId = portletDataContext.getUserId(phone.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			phone, UsersAdminPortletDataHandler.NAMESPACE);

		Phone existingPhone =
			PhoneLocalServiceUtil.fetchPhoneByUuidAndCompanyId(
				phone.getUuid(), portletDataContext.getCompanyId());

		Phone importedPhone = null;

		if (existingPhone == null) {
			serviceContext.setUuid(phone.getUuid());

			importedPhone = PhoneLocalServiceUtil.addPhone(
				userId, phone.getClassName(), phone.getClassPK(),
				phone.getNumber(), phone.getExtension(), phone.getTypeId(),
				phone.isPrimary(), serviceContext);
		}
		else {
			importedPhone = PhoneLocalServiceUtil.updatePhone(
				existingPhone.getPhoneId(), phone.getNumber(),
				phone.getExtension(), phone.getTypeId(), phone.isPrimary());
		}

		portletDataContext.importClassedModel(
			phone, importedPhone, UsersAdminPortletDataHandler.NAMESPACE);
	}

}