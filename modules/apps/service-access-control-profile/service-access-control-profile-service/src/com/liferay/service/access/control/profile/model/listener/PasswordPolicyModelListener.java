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

package com.liferay.service.access.control.profile.model.listener;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.service.access.control.profile.model.SACPEntry;
import com.liferay.service.access.control.profile.service.SACPEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(service = ModelListener.class)
public class PasswordPolicyModelListener
	extends BaseModelListener<PasswordPolicy> {

	@Override
	public void onAfterCreate(PasswordPolicy passwordPolicy)
		throws ModelListenerException {

		if (!passwordPolicy.isDefaultPolicy()) {
			return;
		}

		try {
			_sacpEntryLocalService.checkDefaultProfile(
				passwordPolicy.getCompanyId());
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Override
	public void onBeforeRemove(PasswordPolicy passwordPolicy)
		throws ModelListenerException {

		if (!passwordPolicy.isDefaultPolicy()) {
			return;
		}

		try {
			for (SACPEntry sacpEntry :
					_sacpEntryLocalService.getCompanySACPEntries(
						passwordPolicy.getCompanyId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS)) {

				_sacpEntryLocalService.deleteSACPEntry(sacpEntry);
			}
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Reference(unbind = "-")
	protected void setSACPEntryLocalService(
		SACPEntryLocalService sacpEntryLocalService) {

		_sacpEntryLocalService = sacpEntryLocalService;
	}

	protected SACPEntryLocalService _sacpEntryLocalService;

}