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
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;

/**
 * @author Daniel Kocsis
 */
public class MBBanStagedModelDataHandler
	extends BaseStagedModelDataHandler<MBBan> {

	@Override
	public String getClassName() {
		return MBBan.class.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Element[] elements,
			MBBan ban)
		throws Exception {

		Element userBansElement = elements[0];

		Element userBanElement = userBansElement.addElement("user-ban");

		ban.setBanUserUuid(ban.getBanUserUuid());

		portletDataContext.addClassedModel(
			userBanElement, StagedModelPathUtil.getPath(ban), ban,
			MBPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Element element, MBBan ban)
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
			element, ban, MBPortletDataHandler.NAMESPACE);

		MBBanLocalServiceUtil.addBan(userId, user.getUserId(), serviceContext);
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBBanStagedModelDataHandler.class);

}