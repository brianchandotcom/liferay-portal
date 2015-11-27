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

package com.liferay.users.admin.internal.lar;

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.AddressLocalService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Mendez Gonzalez
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class AddressStagedModelDataHandler
	extends BaseStagedModelDataHandler<Address> {

	public static final String[] CLASS_NAMES = {Address.class.getName()};

	@Override
	public void deleteStagedModel(Address address) {
		_addressLocalService.deleteAddress(address);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		Address address = _addressLocalService.fetchAddressByUuidAndCompanyId(
			uuid, group.getCompanyId());

		if (address != null) {
			deleteStagedModel(address);
		}
	}

	@Override
	public void doExportStagedModel(
			PortletDataContext portletDataContext, Address address)
		throws Exception {

		Element addressElement = portletDataContext.getExportDataElement(
			address);

		portletDataContext.addClassedModel(
			addressElement, ExportImportPathUtil.getModelPath(address),
			address);
	}

	@Override
	public List<Address> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<Address> addresses = new ArrayList<>();

		addresses.add(
			_addressLocalService.fetchAddressByUuidAndCompanyId(
				uuid, companyId));

		return addresses;
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Address address)
		throws Exception {

		long userId = portletDataContext.getUserId(address.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			address);

		Address existingAddress =
			_addressLocalService.fetchAddressByUuidAndCompanyId(
				address.getUuid(), portletDataContext.getCompanyId());

		Address importedAddress = null;

		if (existingAddress == null) {
			serviceContext.setUuid(address.getUuid());

			importedAddress = _addressLocalService.addAddress(
				userId, address.getClassName(), address.getClassPK(),
				address.getStreet1(), address.getStreet2(),
				address.getStreet3(), address.getCity(), address.getZip(),
				address.getRegionId(), address.getCountryId(),
				address.getTypeId(), address.getMailing(), address.isPrimary(),
				serviceContext);
		}
		else {
			importedAddress = _addressLocalService.updateAddress(
				existingAddress.getAddressId(), address.getStreet1(),
				address.getStreet2(), address.getStreet3(), address.getCity(),
				address.getZip(), address.getRegionId(), address.getCountryId(),
				address.getTypeId(), address.getMailing(), address.isPrimary());
		}

		portletDataContext.importClassedModel(address, importedAddress);
	}

	@Reference(unbind = "-")
	protected void setAddressLocalService(
		AddressLocalService addressLocalService) {

		_addressLocalService = addressLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	private volatile AddressLocalService _addressLocalService;
	private volatile GroupLocalService _groupLocalService;

}