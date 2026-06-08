/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.versioning.service.impl;

import com.liferay.layout.content.versioning.exception.DuplicateLayoutContentVersionExternalReferenceCodeException;
import com.liferay.layout.content.versioning.exception.LayoutContentVersionExternalReferenceCodeException;
import com.liferay.layout.content.versioning.model.LayoutContentVersion;
import com.liferay.layout.content.versioning.service.base.LayoutContentVersionLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

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

	public LayoutContentVersion addLayoutContentVersion(
			String externalReferenceCode, long userId, long plid,
			Map<Locale, String> nameMap, String data, int status,
			boolean skipIfUnchanged)
		throws PortalException {

		Layout layout = _layoutLocalService.getLayout(plid);

		FeatureFlagManagerUtil.checkEnabled(layout.getCompanyId(), "LPD-10622");

		_validateExternalReferenceCode(
			externalReferenceCode, layout.getGroupId());

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
	public LayoutContentVersion getLayoutContentVersion(
			long layoutContentVersionId)
		throws PortalException {

		LayoutContentVersion layoutContentVersion =
			layoutContentVersionPersistence.findByPrimaryKey(
				layoutContentVersionId);

		FeatureFlagManagerUtil.checkEnabled(
			layoutContentVersion.getCompanyId(), "LPD-10622");

		return layoutContentVersion;
	}

	@Override
	public LayoutContentVersion getLayoutContentVersionByExternalReferenceCode(
			String externalReferenceCode, long groupId)
		throws PortalException {

		LayoutContentVersion layoutContentVersion =
			layoutContentVersionPersistence.findByERC_G(
				externalReferenceCode, groupId);

		FeatureFlagManagerUtil.checkEnabled(
			layoutContentVersion.getCompanyId(), "LPD-10622");

		return layoutContentVersion;
	}

	public List<LayoutContentVersion> getLayoutContentVersions(long plid)
		throws PortalException {

		Layout layout = _layoutLocalService.getLayout(plid);

		FeatureFlagManagerUtil.checkEnabled(layout.getCompanyId(), "LPD-10622");

		return layoutContentVersionPersistence.findByPlid(plid);
	}

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

	private void _validateExternalReferenceCode(
			String externalReferenceCode, long groupId)
		throws PortalException {

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		LayoutContentVersion layoutContentVersion =
			layoutContentVersionPersistence.fetchByERC_G(
				externalReferenceCode, groupId);

		if (layoutContentVersion != null) {
			throw new DuplicateLayoutContentVersionExternalReferenceCodeException(
				StringBundler.concat(
					"Duplicate layout content version external reference code ",
					externalReferenceCode, " in group ", groupId));
		}

		int maxLength = ModelHintsUtil.getMaxLength(
			LayoutContentVersion.class.getName(), "externalReferenceCode");

		if (externalReferenceCode.length() > maxLength) {
			throw new LayoutContentVersionExternalReferenceCodeException(
				"External reference code must be less than " + maxLength +
					" characters");
		}
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private UserLocalService _userLocalService;

}