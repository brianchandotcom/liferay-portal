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
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

/**
 * @author David Mendez Gonzalez
 */
public class EmailAddressStagedModelDataHandler
	extends BaseStagedModelDataHandler<EmailAddress> {

	public static final String[] CLASS_NAMES = {EmailAddress.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, EmailAddress emailAddress)
		throws Exception {

		Element emailAddressElement = portletDataContext.getExportDataElement(
			emailAddress);

		portletDataContext.addClassedModel(
			emailAddressElement,
			ExportImportPathUtil.getModelPath(emailAddress), emailAddress,
			UsersAdminPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, EmailAddress emailAddress)
		throws Exception {

		long userId = portletDataContext.getUserId(emailAddress.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			emailAddress, UsersAdminPortletDataHandler.NAMESPACE);

		EmailAddress existingEmailAddress =
			EmailAddressLocalServiceUtil.fetchEmailAddressByUuidAndCompanyId(
				emailAddress.getUuid(), portletDataContext.getCompanyId());

		EmailAddress importedEmailAddress = null;

		if (existingEmailAddress == null) {
			serviceContext.setUuid(emailAddress.getUuid());

			importedEmailAddress = EmailAddressLocalServiceUtil.addEmailAddress(
				userId, emailAddress.getClassName(), emailAddress.getClassPK(),
				emailAddress.getAddress(), emailAddress.getTypeId(),
				emailAddress.isPrimary(), serviceContext);
		}
		else {
			importedEmailAddress =
				EmailAddressLocalServiceUtil.updateEmailAddress(
					existingEmailAddress.getEmailAddressId(),
					emailAddress.getAddress(), emailAddress.getTypeId(),
					emailAddress.isPrimary());
		}

		portletDataContext.importClassedModel(
			emailAddress, importedEmailAddress,
			UsersAdminPortletDataHandler.NAMESPACE);
	}

}