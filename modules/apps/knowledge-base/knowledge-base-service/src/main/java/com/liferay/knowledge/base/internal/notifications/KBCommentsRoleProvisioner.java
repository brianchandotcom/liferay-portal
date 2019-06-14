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

package com.liferay.knowledge.base.internal.notifications;

import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Gregory Amerson
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class KBCommentsRoleProvisioner
	implements PortalInstanceLifecycleListener {

	public KBCommentsRoleProvisioner() {
		_logger = LoggerFactory.getLogger(KBCommentsRoleProvisioner.class);
	}

	@Override
	public void portalInstancePreregistered(long companyId) {
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		long companyId = company.getCompanyId();

		try {
			User defaultUser = _userLocalService.getDefaultUser(companyId);

			Role role = _roleLocalService.fetchRole(
				companyId, KBConstants.ROLE_NAME_KB_FEEDBACK);

			if (role == null) {
				Map<Locale, String> titleMap = new HashMap<>();

				titleMap.put(
					LocaleUtil.getDefault(), "Knowledge Base Feedback");

				Map<Locale, String> descriptionMap = new HashMap<>();

				descriptionMap.put(
					LocaleUtil.getDefault(),
					"This role is for those users who wish to be notified " +
					"when a suggestion is made on a Knowledge Base article.");

				_roleLocalService.addRole(
					defaultUser.getUserId(), null, 0,
					KBConstants.ROLE_NAME_KB_FEEDBACK, titleMap, descriptionMap,
					RoleConstants.TYPE_REGULAR, null, null);
			}
		}
		catch (PortalException pe) {
			_logger.error(
				"Unable to add necessary roles for Knowledge Base Feedback",
				pe);
		}
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
	}

	private final Logger _logger;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}