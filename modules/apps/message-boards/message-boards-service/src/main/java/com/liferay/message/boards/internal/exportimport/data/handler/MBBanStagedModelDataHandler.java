/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.message.boards.model.MBBan;
import com.liferay.message.boards.service.MBBanLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(service = StagedModelDataHandler.class)
public class MBBanStagedModelDataHandler
	extends BaseStagedModelDataHandler<MBBan> {

	public static final String[] CLASS_NAMES = {MBBan.class.getName()};

	@Override
	public void deleteStagedModel(MBBan ban) {
		_mbBanLocalService.deleteBan(ban);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		MBBan ban = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (ban != null) {
			deleteStagedModel(ban);
		}
	}

	@Override
	public MBBan fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		return _mbBanLocalService.fetchMBBanByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<MBBan> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _mbBanLocalService.getMBBansByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<MBBan>());
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

		User bannedUser = _userLocalService.getUser(ban.getUserId());

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

		User user = _userLocalService.fetchUserByUuidAndCompanyId(
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

		_mbBanLocalService.addBan(userId, user.getUserId(), serviceContext);
	}

	@Override
	protected boolean isSkipImportReferenceStagedModels() {
		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBBanStagedModelDataHandler.class);

	@Reference
	private MBBanLocalService _mbBanLocalService;

	@Reference
	private UserLocalService _userLocalService;

}