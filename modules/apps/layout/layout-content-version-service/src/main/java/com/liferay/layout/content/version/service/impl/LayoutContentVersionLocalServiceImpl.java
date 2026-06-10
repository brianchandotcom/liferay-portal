/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.version.service.impl;

import com.liferay.layout.content.version.exception.DuplicateLayoutContentVersionExternalReferenceCodeException;
import com.liferay.layout.content.version.exception.LayoutContentVersionExternalReferenceCodeException;
import com.liferay.layout.content.version.exception.LayoutContentVersionNameException;
import com.liferay.layout.content.version.exception.RequiredLayoutContentVersionException;
import com.liferay.layout.content.version.exception.UnsupportedLayoutLayoutContentVersionException;
import com.liferay.layout.content.version.model.LayoutContentVersion;
import com.liferay.layout.content.version.service.base.LayoutContentVersionLocalServiceBaseImpl;
import com.liferay.layout.content.version.util.comparator.LayoutContentVersionVersionComparator;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
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
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

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
	property = "model.class.name=com.liferay.layout.content.version.model.LayoutContentVersion",
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

		_validateLayout(layout);
		_validateExternalReferenceCode(
			externalReferenceCode, layout.getGroupId());

		String dataHash = DigesterUtil.digestHex(
			"SHA-256", GetterUtil.getString(data));

		if (skipIfUnchanged) {
			LayoutContentVersion lastLayoutContentVersion =
				layoutContentVersionPersistence.fetchByPlid_First(
					plid,
					LayoutContentVersionVersionComparator.getInstance(false));

			if ((lastLayoutContentVersion != null) &&
				dataHash.equals(lastLayoutContentVersion.getDataHash())) {

				return lastLayoutContentVersion;
			}
		}

		LayoutContentVersion layoutContentVersion =
			layoutContentVersionPersistence.create(
				counterLocalService.increment(
					LayoutContentVersion.class.getName()));

		int version = _generateVersion(plid);

		if (Validator.isNull(externalReferenceCode)) {
			String defaultExternalReferenceCode =
				layout.getExternalReferenceCode() + "_v_" + version;

			if (defaultExternalReferenceCode.length() <=
					ModelHintsUtil.getMaxLength(
						LayoutContentVersion.class.getName(),
						"externalReferenceCode")) {

				externalReferenceCode = defaultExternalReferenceCode;
			}
		}

		layoutContentVersion.setExternalReferenceCode(externalReferenceCode);

		layoutContentVersion.setGroupId(layout.getGroupId());
		layoutContentVersion.setCompanyId(layout.getCompanyId());
		layoutContentVersion.setUserId(userId);

		User user = _userLocalService.getUser(userId);

		layoutContentVersion.setUserName(user.getFullName());

		Date date = new Date();

		layoutContentVersion.setCreateDate(date);
		layoutContentVersion.setModifiedDate(date);

		layoutContentVersion.setPlid(plid);

		if (MapUtil.isEmpty(nameMap)) {
			nameMap = layout.getNameMap();
		}

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

		_validateLayout(
			_layoutLocalService.getLayout(layoutContentVersion.getPlid()));

		if (layoutContentVersion.getStatus() ==
				WorkflowConstants.STATUS_APPROVED) {

			LayoutContentVersion latestApprovedLayoutContentVersion =
				layoutContentVersionPersistence.fetchByP_S_First(
					layoutContentVersion.getPlid(),
					WorkflowConstants.STATUS_APPROVED,
					LayoutContentVersionVersionComparator.getInstance(false));

			if (layoutContentVersion.getLayoutContentVersionId() ==
					latestApprovedLayoutContentVersion.
						getLayoutContentVersionId()) {

				throw new RequiredLayoutContentVersionException();
			}
		}

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

		_validateLayout(
			_layoutLocalService.getLayout(layoutContentVersion.getPlid()));

		if (MapUtil.isEmpty(nameMap)) {
			throw new LayoutContentVersionNameException(
				"Name must not be null");
		}

		layoutContentVersion.setModifiedDate(new Date());
		layoutContentVersion.setNameMap(nameMap);

		return layoutContentVersionPersistence.update(layoutContentVersion);
	}

	private int _generateVersion(long plid) {
		LayoutContentVersion layoutContentVersion =
			layoutContentVersionPersistence.fetchByPlid_First(
				plid, LayoutContentVersionVersionComparator.getInstance(false));

		if (layoutContentVersion == null) {
			return 1;
		}

		return layoutContentVersion.getVersion() + 1;
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

	private void _validateLayout(Layout layout) throws PortalException {
		if (!layout.isDraftLayout() || layout.isTypeAssetDisplay() ||
			layout.isTypeUtility()) {

			throw new UnsupportedLayoutLayoutContentVersionException();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(layout.getPlid());

		if (layoutPageTemplateEntry != null) {
			throw new UnsupportedLayoutLayoutContentVersionException();
		}
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}