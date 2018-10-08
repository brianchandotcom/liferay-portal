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

package com.liferay.users.admin.web.internal.helper;

import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.service.OrgLaborLocalService;
import com.liferay.portal.kernel.service.OrgLaborService;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Samuel Trong Tran
 */
public class OrgLaborContactInformationHelper
	extends BaseContactInformationHelper<OrgLabor> {

	public OrgLaborContactInformationHelper(
		long entityClassPK, OrgLaborLocalService orgLaborLocalService,
		OrgLaborService orgLaborService) {

		_entityClassPK = entityClassPK;
		_orgLaborLocalService = orgLaborLocalService;
		_orgLaborService = orgLaborService;
	}

	@Override
	protected OrgLabor addEntry(OrgLabor orgLabor) throws Exception {
		return _orgLaborService.addOrgLabor(
			_entityClassPK, orgLabor.getTypeId(), orgLabor.getSunOpen(),
			orgLabor.getSunClose(), orgLabor.getMonOpen(),
			orgLabor.getMonClose(), orgLabor.getTueOpen(),
			orgLabor.getTueClose(), orgLabor.getWedOpen(),
			orgLabor.getWedClose(), orgLabor.getThuOpen(),
			orgLabor.getThuClose(), orgLabor.getFriOpen(),
			orgLabor.getFriClose(), orgLabor.getSatOpen(),
			orgLabor.getSatClose());
	}

	@Override
	protected OrgLabor constructEntry(ActionRequest actionRequest)
		throws Exception {

		long orgLaborId = ParamUtil.getLong(actionRequest, "primaryKey");

		long typeId = ParamUtil.getLong(actionRequest, "orgLaborTypeId");
		int sunOpen = ParamUtil.getInteger(actionRequest, "sunOpen", -1);
		int sunClose = ParamUtil.getInteger(actionRequest, "sunClose", -1);
		int monOpen = ParamUtil.getInteger(actionRequest, "monOpen", -1);
		int monClose = ParamUtil.getInteger(actionRequest, "monClose", -1);
		int tueOpen = ParamUtil.getInteger(actionRequest, "tueOpen", -1);
		int tueClose = ParamUtil.getInteger(actionRequest, "tueClose", -1);
		int wedOpen = ParamUtil.getInteger(actionRequest, "wedOpen", -1);
		int wedClose = ParamUtil.getInteger(actionRequest, "wedClose", -1);
		int thuOpen = ParamUtil.getInteger(actionRequest, "thuOpen", -1);
		int thuClose = ParamUtil.getInteger(actionRequest, "thuClose", -1);
		int friOpen = ParamUtil.getInteger(actionRequest, "friOpen", -1);
		int friClose = ParamUtil.getInteger(actionRequest, "friClose", -1);
		int satOpen = ParamUtil.getInteger(actionRequest, "satOpen", -1);
		int satClose = ParamUtil.getInteger(actionRequest, "satClose", -1);

		OrgLabor orgLabor = _orgLaborLocalService.createOrgLabor(orgLaborId);

		orgLabor.setTypeId(typeId);
		orgLabor.setSunOpen(sunOpen);
		orgLabor.setSunClose(sunClose);
		orgLabor.setMonOpen(monOpen);
		orgLabor.setMonClose(monClose);
		orgLabor.setTueOpen(tueOpen);
		orgLabor.setTueClose(tueClose);
		orgLabor.setWedOpen(wedOpen);
		orgLabor.setWedClose(wedClose);
		orgLabor.setThuOpen(thuOpen);
		orgLabor.setThuClose(thuClose);
		orgLabor.setFriOpen(friOpen);
		orgLabor.setFriClose(friClose);
		orgLabor.setSatOpen(satOpen);
		orgLabor.setSatClose(satClose);

		return orgLabor;
	}

	@Override
	protected void deleteEntry(long orgLaborId) throws Exception {
		_orgLaborService.deleteOrgLabor(orgLaborId);
	}

	@Override
	protected List<OrgLabor> getEntries() throws Exception {
		return _orgLaborService.getOrgLabors(_entityClassPK);
	}

	@Override
	protected OrgLabor getEntry(long orgLaborId) throws Exception {
		return _orgLaborService.getOrgLabor(orgLaborId);
	}

	@Override
	protected long getEntryId(OrgLabor orgLabor) {
		return orgLabor.getOrgLaborId();
	}

	@Override
	protected boolean isPrimaryEntry(OrgLabor orgLabor) {
		return false;
	}

	@Override
	protected void setEntryPrimary(OrgLabor orgLabor, boolean primary) {
	}

	@Override
	protected void updateEntry(OrgLabor orgLabor) throws Exception {
		_orgLaborService.updateOrgLabor(
			orgLabor.getOrgLaborId(), orgLabor.getTypeId(),
			orgLabor.getSunOpen(), orgLabor.getSunClose(),
			orgLabor.getMonOpen(), orgLabor.getMonClose(),
			orgLabor.getTueOpen(), orgLabor.getTueClose(),
			orgLabor.getWedOpen(), orgLabor.getWedClose(),
			orgLabor.getThuOpen(), orgLabor.getThuClose(),
			orgLabor.getFriOpen(), orgLabor.getFriClose(),
			orgLabor.getSatOpen(), orgLabor.getSatClose());
	}

	private final long _entityClassPK;
	private final OrgLaborLocalService _orgLaborLocalService;
	private final OrgLaborService _orgLaborService;

}