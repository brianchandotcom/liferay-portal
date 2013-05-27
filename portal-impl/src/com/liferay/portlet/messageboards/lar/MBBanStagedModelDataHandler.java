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

package com.liferay.portlet.messageboards.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;

/**
 * @author Daniel Kocsis
 */
public class MBBanStagedModelDataHandler
	extends BaseStagedModelDataHandler<MBBan> {

	public static final String[] CLASS_NAMES = {MBBan.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, MBBan ban)
		throws Exception {

		Element userBanElement = portletDataContext.getExportDataElement(ban);

		ban.setBanUserUuid(ban.getBanUserUuid());

		User bannedUser = UserLocalServiceUtil.getUser(ban.getUserId());

		portletDataContext.addReferenceElement(
			ban, userBanElement, bannedUser, User.class,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY_DISPOSABLE, true);

		portletDataContext.addClassedModel(
			userBanElement, ExportImportPathUtil.getModelPath(ban), ban,
			MBPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, MBBan ban)
		throws Exception {

		User user = UserUtil.fetchByUuid_C_First(
			ban.getBanUserUuid(), portletDataContext.getCompanyId(), null);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find banned user with uuid " +
						ban.getBanUserUuid());
			}

			return;
		}

		long userId = portletDataContext.getUserId(ban.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			ban, MBPortletDataHandler.NAMESPACE);

		MBBanLocalServiceUtil.addBan(userId, user.getUserId(), serviceContext);
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBBanStagedModelDataHandler.class);

}