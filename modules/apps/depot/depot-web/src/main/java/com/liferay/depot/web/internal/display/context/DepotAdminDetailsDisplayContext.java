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

package com.liferay.depot.web.internal.display.context;

import com.liferay.depot.application.DepotApplication;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryServiceUtil;
import com.liferay.depot.web.internal.application.controller.DepotApplicationController;
import com.liferay.depot.web.internal.constants.DepotAdminWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;

import java.util.Collection;
import java.util.Locale;

import javax.portlet.PortletRequest;

/**
 * @author Cristina González
 */
public class DepotAdminDetailsDisplayContext {

	public DepotAdminDetailsDisplayContext(
		DepotApplicationController depotApplicationController,
		PortletRequest portletRequest) {

		_depotApplicationController = depotApplicationController;
		_portletRequest = portletRequest;
	}

	public Collection<DepotApplication> getDepotApplications() {
		return _depotApplicationController.getCustomizableDepotApplications();
	}

	public long getDepotEntryId() {
		DepotEntry depotEntry = (DepotEntry)_portletRequest.getAttribute(
			DepotAdminWebKeys.DEPOT_ENTRY);

		return depotEntry.getDepotEntryId();
	}

	public String getDepotName(Locale locale) throws PortalException {
		Group group = getGroup();

		return group.getName(locale);
	}

	public Group getGroup() throws PortalException {
		if (_group == null) {
			DepotEntry depotEntry = DepotEntryServiceUtil.getDepotEntry(
				getDepotEntryId());

			_group = depotEntry.getGroup();
		}

		return _group;
	}

	public boolean isEnabled(String portletId) throws PortalException {
		return _depotApplicationController.isEnabled(portletId, _getGroupId());
	}

	private long _getGroupId() throws PortalException {
		Group group = getGroup();

		return group.getGroupId();
	}

	private final DepotApplicationController _depotApplicationController;
	private Group _group;
	private final PortletRequest _portletRequest;

}