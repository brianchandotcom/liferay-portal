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

package com.liferay.layout.utility.page.service.impl;

import com.liferay.layout.utility.page.exception.DuplicateLayoutUtilityPageEntryExternalReferenceCodeException;
import com.liferay.layout.utility.page.exception.LayoutUtilityPageEntryNameException;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.base.LayoutUtilityPageEntryLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.ColorScheme;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.layout.utility.page.model.LayoutUtilityPageEntry",
	service = AopService.class
)
public class LayoutUtilityPageEntryLocalServiceImpl
	extends LayoutUtilityPageEntryLocalServiceBaseImpl {

	@Override
	public LayoutUtilityPageEntry addLayoutUtilityPageEntry(
			String externalReferenceCode, long userId, long groupId,
			String name, int type, long masterLayoutPlid)
		throws PortalException {

		_validateExternalReferenceCode(externalReferenceCode, groupId);
		_validateName(groupId, name);

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			layoutUtilityPageEntryPersistence.create(
				counterLocalService.increment());

		layoutUtilityPageEntry.setGroupId(groupId);

		User user = _userLocalService.getUser(userId);

		layoutUtilityPageEntry.setCompanyId(user.getCompanyId());
		layoutUtilityPageEntry.setUserId(user.getUserId());
		layoutUtilityPageEntry.setUserName(user.getFullName());

		layoutUtilityPageEntry.setExternalReferenceCode(externalReferenceCode);

		long plid = 0;

		Layout layout = _addLayout(
			userId, groupId, name, type, masterLayoutPlid,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextThreadLocal.getServiceContext());

		if (layout != null) {
			plid = layout.getPlid();
		}

		layoutUtilityPageEntry.setPlid(plid);

		layoutUtilityPageEntry.setName(name);
		layoutUtilityPageEntry.setType(type);

		return layoutUtilityPageEntryPersistence.update(layoutUtilityPageEntry);
	}

	@Override
	public LayoutUtilityPageEntry copyLayoutUtilityPageEntry(
			long userId, long groupId, long layoutUtilityPageEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutUtilityPageEntry sourceLayoutUtilityPageEntry =
			layoutUtilityPageEntryPersistence.findByPrimaryKey(
				layoutUtilityPageEntryId);

		String name = _getUniqueCopyName(
			groupId, sourceLayoutUtilityPageEntry.getName(),
			sourceLayoutUtilityPageEntry.getType(), serviceContext.getLocale());

		return addLayoutUtilityPageEntry(
			null, userId, serviceContext.getScopeGroupId(), name,
			sourceLayoutUtilityPageEntry.getType(), 0);
	}

	@Override
	public LayoutUtilityPageEntry fetchDefaultLayoutUtilityPageEntry(
		long groupId, int type) {

		return layoutUtilityPageEntryPersistence.fetchByG_D_T_First(
			groupId, true, type, null);
	}

	@Override
	public LayoutUtilityPageEntry getDefaultLayoutUtilityPageEntry(
			long groupId, int type)
		throws PortalException {

		return layoutUtilityPageEntryPersistence.findByG_D_T_First(
			groupId, true, type, null);
	}

	@Override
	public List<LayoutUtilityPageEntry> getLayoutUtilityPageEntries(
		long groupId) {

		return layoutUtilityPageEntryPersistence.findByGroupId(groupId);
	}

	@Override
	public List<LayoutUtilityPageEntry> getLayoutUtilityPageEntries(
		long groupId, int type, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return layoutUtilityPageEntryPersistence.findByG_T(
			groupId, type, start, end, orderByComparator);
	}

	@Override
	public List<LayoutUtilityPageEntry> getLayoutUtilityPageEntries(
		long groupId, int start, int end,
		OrderByComparator<LayoutUtilityPageEntry> orderByComparator) {

		return layoutUtilityPageEntryPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getLayoutUtilityPageEntriesCount(long groupId) {
		return layoutUtilityPageEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public LayoutUtilityPageEntry setDefaultLayoutUtilityPageEntry(
			long layoutUtilityPageEntryId)
		throws PortalException {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			layoutUtilityPageEntryPersistence.findByPrimaryKey(
				layoutUtilityPageEntryId);

		LayoutUtilityPageEntry defaultLayoutUtilityPageEntry =
			layoutUtilityPageEntryPersistence.fetchByG_D_T_First(
				layoutUtilityPageEntry.getGroupId(), true,
				layoutUtilityPageEntry.getType(), null);

		if (defaultLayoutUtilityPageEntry != null) {
			defaultLayoutUtilityPageEntry.setDefaultLayoutUtilityPageEntry(
				false);

			layoutUtilityPageEntryPersistence.update(
				defaultLayoutUtilityPageEntry);
		}

		layoutUtilityPageEntry.setDefaultLayoutUtilityPageEntry(true);

		return layoutUtilityPageEntryPersistence.update(layoutUtilityPageEntry);
	}

	@Override
	public LayoutUtilityPageEntry updateLayoutUtilityPageEntry(
			long layoutUtilityPageEntryId, String name)
		throws PortalException {

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			layoutUtilityPageEntryPersistence.fetchByPrimaryKey(
				layoutUtilityPageEntryId);

		_validateName(layoutUtilityPageEntry.getGroupId(), name);

		layoutUtilityPageEntry.setName(name);

		return layoutUtilityPageEntryPersistence.update(layoutUtilityPageEntry);
	}

	private Layout _addLayout(
			long userId, long groupId, String name, int type,
			long masterLayoutPlid, int status, ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> titleMap = Collections.singletonMap(
			LocaleUtil.getSiteDefault(), name);

		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		if (status == WorkflowConstants.STATUS_APPROVED) {
			typeSettingsUnicodeProperties.put("published", "true");
		}

		if (masterLayoutPlid > 0) {
			typeSettingsUnicodeProperties.setProperty(
				"lfr-theme:regular:show-footer", Boolean.FALSE.toString());
			typeSettingsUnicodeProperties.setProperty(
				"lfr-theme:regular:show-header", Boolean.FALSE.toString());
			typeSettingsUnicodeProperties.setProperty(
				"lfr-theme:regular:show-header-search",
				Boolean.FALSE.toString());
			typeSettingsUnicodeProperties.setProperty(
				"lfr-theme:regular:wrap-widget-page-content",
				Boolean.FALSE.toString());
		}

		String typeSettings = typeSettingsUnicodeProperties.toString();

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);
		serviceContext.setAttribute("layout.page.template.entry.type", type);

		Layout layout = _layoutLocalService.addLayout(
			userId, groupId, true, 0, 0, 0, titleMap, titleMap, null, null,
			null, LayoutConstants.TYPE_CONTENT, typeSettings, true, true,
			new HashMap<>(), masterLayoutPlid, serviceContext);

		Layout draftLayout = layout.fetchDraftLayout();

		if (masterLayoutPlid > 0) {
			LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
				groupId, false);

			String themeId = layoutSet.getThemeId();

			String colorSchemeId = _getColorSchemeId(
				layout.getCompanyId(), themeId);

			draftLayout = _layoutLocalService.updateLookAndFeel(
				groupId, true, draftLayout.getLayoutId(), themeId,
				colorSchemeId, StringPool.BLANK);

			layout = _layoutLocalService.updateLookAndFeel(
				groupId, true, layout.getLayoutId(), themeId, colorSchemeId,
				StringPool.BLANK);
		}

		if (status == WorkflowConstants.STATUS_DRAFT) {
			_layoutLocalService.updateStatus(
				userId, draftLayout.getPlid(), status, serviceContext);

			layout = _layoutLocalService.updateStatus(
				userId, layout.getPlid(), status, serviceContext);
		}

		return layout;
	}

	private String _getColorSchemeId(long companyId, String themeId) {
		ColorScheme colorScheme = _themeLocalService.getColorScheme(
			companyId, themeId, StringPool.BLANK);

		return colorScheme.getColorSchemeId();
	}

	private String _getUniqueCopyName(
		long groupId, String sourceName, int type, Locale locale) {

		String copy = _language.get(locale, "copy");

		String name = StringUtil.appendParentheticalSuffix(sourceName, copy);

		for (int i = 1;; i++) {
			LayoutUtilityPageEntry layoutUtilityPageEntry =
				layoutUtilityPageEntryPersistence.fetchByG_N_T(
					groupId, name, type);

			if (layoutUtilityPageEntry == null) {
				break;
			}

			name = StringUtil.appendParentheticalSuffix(
				sourceName, copy + StringPool.SPACE + i);
		}

		return name;
	}

	private void _validateExternalReferenceCode(
			String externalReferenceCode, long groupId)
		throws PortalException {

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			layoutUtilityPageEntryPersistence.fetchByG_ERC(
				groupId, externalReferenceCode);

		if (layoutUtilityPageEntry != null) {
			throw new DuplicateLayoutUtilityPageEntryExternalReferenceCodeException(
				StringBundler.concat(
					"Duplicate layout utility page entry external reference ",
					"code ", externalReferenceCode, " in group ", groupId));
		}
	}

	private void _validateName(long groupId, String name)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new LayoutUtilityPageEntryNameException.MustNotBeNull(
				groupId);
		}

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			LayoutUtilityPageEntry.class.getName(), "name");

		if (name.length() > nameMaxLength) {
			throw new LayoutUtilityPageEntryNameException.
				MustNotExceedMaximumSize(nameMaxLength);
		}
	}

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private ThemeLocalService _themeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}