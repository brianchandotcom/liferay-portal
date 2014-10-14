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

package com.liferay.portlet.messageboards.lar;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;

import java.util.List;

/**
 * @author Daniel Kocsis
 */
public class MBBanStagedModelDataHandler
	extends BaseStagedModelDataHandler<MBBan> {

	public static final String[] CLASS_NAMES = {MBBan.class.getName()};

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		MBBan ban = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (ban != null) {
			MBBanLocalServiceUtil.deleteBan(ban);
		}
	}

	@Override
	public MBBan fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<MBBan> bans = MBBanLocalServiceUtil.getMBBansByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<MBBan>());

		if (ListUtil.isEmpty(bans)) {
			return null;
		}

		return bans.get(0);
	}

	@Override
	public MBBan fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		return MBBanLocalServiceUtil.fetchMBBanByUuidAndGroupId(uuid, groupId);
	}

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
			ban, userBanElement, bannedUser,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY_DISPOSABLE, true);

		portletDataContext.addClassedModel(
			userBanElement, ExportImportPathUtil.getModelPath(ban), ban);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, MBBan ban)
		throws Exception {

		User user = UserLocalServiceUtil.fetchUserByUuidAndCompanyId(
			ban.getBanUserUuid(), portletDataContext.getCompanyId());

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
			ban);

		serviceContext.setUuid(ban.getUuid());

		MBBanLocalServiceUtil.addBan(userId, user.getUserId(), serviceContext);
	}

	@Override
	protected void importReferenceStagedModels(
		PortletDataContext portletDataContext, MBBan ban) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBBanStagedModelDataHandler.class);

}