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
import com.liferay.portal.model.Address;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

/**
 * @author David Mendez Gonzalez
 */
public class AddressStagedModelDataHandler
	extends BaseStagedModelDataHandler<Address> {

	public static final String[] CLASS_NAMES = {Address.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Address address)
		throws Exception {

		Element addressElement = portletDataContext.getExportDataElement(
			address);

		portletDataContext.addClassedModel(
			addressElement, ExportImportPathUtil.getModelPath(address), address,
			UsersAdminPortletDataHandler.NAMESPACE);

	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Address address)
		throws Exception {

		long userId = portletDataContext.getUserId(address.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			address, UsersAdminPortletDataHandler.NAMESPACE);

		Address existingAddress =
			AddressLocalServiceUtil.fetchAddressByUuidAndCompanyId(
				address.getUuid(), portletDataContext.getCompanyId());

		Address importedAddress = null;

		if (existingAddress == null) {
			serviceContext.setUuid(address.getUuid());

			importedAddress = AddressLocalServiceUtil.addAddress(
				userId, address.getClassName(), address.getClassPK(),
				address.getStreet1(), address.getStreet2(),
				address.getStreet3(), address.getCity(), address.getZip(),
				address.getRegionId(), address.getCountryId(),
				address.getTypeId(), address.getMailing(), address.isPrimary(),
				serviceContext);
		}
		else {
			importedAddress = AddressLocalServiceUtil.updateAddress(
				existingAddress.getAddressId(), address.getStreet1(),
				address.getStreet2(), address.getStreet3(), address.getCity(),
				address.getZip(), address.getRegionId(), address.getCountryId(),
				address.getTypeId(), address.getMailing(), address.isPrimary());
		}

		portletDataContext.importClassedModel(
			address, importedAddress, UsersAdminPortletDataHandler.NAMESPACE);
	}

}