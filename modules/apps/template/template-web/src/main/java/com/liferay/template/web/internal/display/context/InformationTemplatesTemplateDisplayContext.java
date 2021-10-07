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

package com.liferay.template.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalServiceUtil;
import com.liferay.template.web.internal.security.permissions.resource.TemplateEntryPermission;
import com.liferay.template.web.internal.util.TemplateEntryActionDropdownItemsProvider;

import java.util.List;
import java.util.Optional;

/**
 * @author Eudaldo Alonso
 * @author Lourdes Fernández Besada
 */
public class InformationTemplatesTemplateDisplayContext
	extends BaseTemplateDisplayContext {

	public InformationTemplatesTemplateDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletRequest, liferayPortletResponse);

		_infoItemServiceTracker =
			(InfoItemServiceTracker)liferayPortletRequest.getAttribute(
				InfoItemServiceTracker.class.getName());
	}

	public List<DropdownItem> getTemplateEntryActionDropdownItems(
		TemplateEntry templateEntry) {

		TemplateEntryActionDropdownItemsProvider
			templateEntryActionDropdownItemsProvider =
				new TemplateEntryActionDropdownItemsProvider(
					isAddButtonEnabled(),
					PortalUtil.getHttpServletRequest(liferayPortletRequest),
					liferayPortletResponse, getTabs1(), templateEntry);

		return templateEntryActionDropdownItemsProvider.
			getActionDropdownItems();
	}

	public String getTemplateEntryEditURL(TemplateEntry templateEntry)
		throws PortalException {

		if (!isStagingGroup() ||
			!TemplateEntryPermission.contains(
				themeDisplay.getPermissionChecker(), templateEntry,
				ActionKeys.UPDATE)) {

			return StringPool.BLANK;
		}

		return PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCRenderCommandName(
			"/template/edit_ddm_template"
		).setRedirect(
			themeDisplay.getURLCurrent()
		).setTabs1(
			getTabs1()
		).setParameter(
			"ddmTemplateId", templateEntry.getDDMTemplateId()
		).setParameter(
			"templateEntryId", templateEntry.getTemplateEntryId()
		).buildString();
	}

	public SearchContainer<TemplateEntry> getTemplateSearchContainer() {
		if (_templateEntrySearchContainer != null) {
			return _templateEntrySearchContainer;
		}

		SearchContainer<TemplateEntry> templateEntrySearchContainer =
			new SearchContainer<>(
				liferayPortletRequest, getPortletURL(), null,
				"there-are-no-templates");

		templateEntrySearchContainer.setOrderByCol(getOrderByCol());
		templateEntrySearchContainer.setOrderByComparator(null);
		templateEntrySearchContainer.setOrderByType(getOrderByType());
		templateEntrySearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(liferayPortletResponse));
		templateEntrySearchContainer.setResults(
			TemplateEntryLocalServiceUtil.getTemplateEntries(
				themeDisplay.getScopeGroupId(),
				templateEntrySearchContainer.getStart(),
				templateEntrySearchContainer.getEnd(),
				templateEntrySearchContainer.getOrderByComparator()));
		templateEntrySearchContainer.setTotal(
			TemplateEntryLocalServiceUtil.getTemplateEntriesCount(
				themeDisplay.getScopeGroupId()));

		_templateEntrySearchContainer = templateEntrySearchContainer;

		return _templateEntrySearchContainer;
	}

	public String getTemplateSubtypeLabel(TemplateEntry templateEntry) {
		if (Validator.isNull(templateEntry.getInfoItemFormVariationKey())) {
			return StringPool.BLANK;
		}

		return Optional.ofNullable(
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				templateEntry.getInfoItemClassName())
		).map(
			infoItemFormVariationsProvider ->
				infoItemFormVariationsProvider.getInfoItemFormVariation(
					themeDisplay.getScopeGroupId(),
					templateEntry.getInfoItemFormVariationKey())
		).map(
			infoItemFormVariation -> infoItemFormVariation.getLabel(
				themeDisplay.getLocale())
		).orElse(
			StringPool.BLANK
		);
	}

	public String getTemplateTypeLabel(TemplateEntry templateEntry) {
		return Optional.ofNullable(
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemDetailsProvider.class,
				templateEntry.getInfoItemClassName())
		).map(
			InfoItemDetailsProvider::getInfoItemClassDetails
		).map(
			infoItemDetails -> infoItemDetails.getLabel(
				themeDisplay.getLocale())
		).orElse(
			ResourceActionsUtil.getModelResource(
				themeDisplay.getLocale(), templateEntry.getInfoItemClassName())
		);
	}

	private final InfoItemServiceTracker _infoItemServiceTracker;
	private SearchContainer<TemplateEntry> _templateEntrySearchContainer;

}