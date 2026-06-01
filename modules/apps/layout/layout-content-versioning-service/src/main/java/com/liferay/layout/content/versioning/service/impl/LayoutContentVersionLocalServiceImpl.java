/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.versioning.service.impl;

import com.liferay.layout.content.versioning.model.LayoutContentVersion;
import com.liferay.layout.content.versioning.service.base.LayoutContentVersionLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "model.class.name=com.liferay.layout.content.versioning.model.LayoutContentVersion",
	service = AopService.class
)
public class LayoutContentVersionLocalServiceImpl
	extends LayoutContentVersionLocalServiceBaseImpl {

	@Override
	public LayoutContentVersion addLayoutContentVersion(
			String externalReferenceCode, long userId, long plid,
			Map<Locale, String> nameMap, String data, int status,
			boolean skipIfUnchanged)
		throws PortalException {

		Layout layout = _layoutLocalService.getLayout(plid);

		FeatureFlagManagerUtil.checkEnabled(layout.getCompanyId(), "LPD-10622");

		String dataHash = DigesterUtil.digestHex(
			"SHA-256", GetterUtil.getString(data));

		if (skipIfUnchanged) {
			LayoutContentVersion lastLayoutContentVersion =
				layoutContentVersionPersistence.fetchByPlid_First(plid, null);

			if ((lastLayoutContentVersion != null) &&
				dataHash.equals(lastLayoutContentVersion.getDataHash())) {

				return lastLayoutContentVersion;
			}
		}

		User user = _userLocalService.getUser(userId);

		long layoutContentVersionId = counterLocalService.increment();

		LayoutContentVersion layoutContentVersion =
			layoutContentVersionPersistence.create(layoutContentVersionId);

		int version = _generateVersion(plid);

		layoutContentVersion.setExternalReferenceCode(externalReferenceCode);

		layoutContentVersion.setGroupId(layout.getGroupId());
		layoutContentVersion.setCompanyId(layout.getCompanyId());
		layoutContentVersion.setUserId(userId);
		layoutContentVersion.setUserName(user.getFullName());

		Date date = new Date();

		layoutContentVersion.setCreateDate(date);
		layoutContentVersion.setModifiedDate(date);

		layoutContentVersion.setPlid(plid);

		layoutContentVersion.setNameMap(nameMap);
		layoutContentVersion.setVersion(version);
		layoutContentVersion.setSpecSchemaVersion("v1.0");
		layoutContentVersion.setData(data);
		layoutContentVersion.setDataHash(dataHash);
		layoutContentVersion.setStatus(status);
		layoutContentVersion.setStatusByUserId(userId);
		layoutContentVersion.setStatusByUserName(user.getFullName());
		layoutContentVersion.setStatusDate(date);

		return layoutContentVersionPersistence.update(layoutContentVersion);
	}

	@Override
	public LayoutContentVersion deleteLayoutContentVersion(
			long layoutContentVersionId)
		throws PortalException {

		LayoutContentVersion layoutContentVersion =
			layoutContentVersionPersistence.findByPrimaryKey(
				layoutContentVersionId);

		FeatureFlagManagerUtil.checkEnabled(
			layoutContentVersion.getCompanyId(), "LPD-10622");

		return layoutContentVersionPersistence.remove(layoutContentVersionId);
	}

	@Override
	public List<LayoutContentVersion> getLayoutContentVersions(long plid)
		throws PortalException {

		Layout layout = _layoutLocalService.getLayout(plid);

		FeatureFlagManagerUtil.checkEnabled(layout.getCompanyId(), "LPD-10622");

		return layoutContentVersionPersistence.findByPlid(plid);
	}

	@Override
	public LayoutContentVersion updateLayoutContentVersion(
			long layoutContentVersionId, Map<Locale, String> nameMap)
		throws PortalException {

		LayoutContentVersion layoutContentVersion =
			layoutContentVersionPersistence.findByPrimaryKey(
				layoutContentVersionId);

		FeatureFlagManagerUtil.checkEnabled(
			layoutContentVersion.getCompanyId(), "LPD-10622");

		layoutContentVersion.setModifiedDate(new Date());
		layoutContentVersion.setNameMap(nameMap);

		return layoutContentVersionPersistence.update(layoutContentVersion);
	}

	private int _generateVersion(long plid) {
		return layoutContentVersionPersistence.countByPlid(plid) + 1;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private UserLocalService _userLocalService;

}