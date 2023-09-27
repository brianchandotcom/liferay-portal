/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.admin.web.internal.display.context;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.page.template.admin.web.internal.constants.LayoutPageTemplateAdminWebKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class DisplayPageTemplateInfoPanelDisplayContext {

	public DisplayPageTemplateInfoPanelDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_infoItemServiceRegistry =
			(InfoItemServiceRegistry)httpServletRequest.getAttribute(
				InfoItemServiceRegistry.class.getName());
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public int getHomeItemsCount(long scopeGroupId) {
		return LayoutPageTemplateEntryServiceUtil.
			getLayoutPageCollectionsAndLayoutPageTemplateEntriesCount(
				scopeGroupId,
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE);
	}

	public LayoutPageTemplateCollection getLayoutPageTemplateCollection(
		long layoutPageTemplateCollectionId) {

		return LayoutPageTemplateCollectionLocalServiceUtil.
			fetchLayoutPageTemplateCollection(layoutPageTemplateCollectionId);
	}

	public int getLayoutPageTemplateCollectionItemsCount(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {

		return LayoutPageTemplateEntryServiceUtil.
			getLayoutPageCollectionsAndLayoutPageTemplateEntriesCount(
				layoutPageTemplateCollection.getGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				layoutPageTemplateCollection.getType());
	}

	public List<String> getLayoutPageTemplateCollectionPath(
		long layoutPageTemplateCollectionId) {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			getLayoutPageTemplateCollection(layoutPageTemplateCollectionId);

		List<String> paths = new ArrayList<>();

		if (layoutPageTemplateCollection != null) {
			DisplayPageDisplayContext displayPageDisplayContext =
				new DisplayPageDisplayContext(
					_httpServletRequest, _renderRequest, _renderResponse);

			paths = TransformUtil.transform(
				displayPageDisplayContext.
					getLayoutPageTemplateBreadcrumbEntries(),
				curLayoutPageTemplateCollection -> HtmlUtil.escape(
					curLayoutPageTemplateCollection.getTitle()));
		}

		paths.add(LanguageUtil.get(_httpServletRequest, "home"));

		Collections.reverse(paths);

		return paths;
	}

	public List<LayoutPageTemplateCollection>
		getLayoutPageTemplateCollections() {

		List<LayoutPageTemplateCollection> layoutPageTemplateCollections =
			(List<LayoutPageTemplateCollection>)
				_httpServletRequest.getAttribute(
					LayoutPageTemplateAdminWebKeys.
						LAYOUT_PAGE_TEMPLATE_COLLECTIONS);

		if (ListUtil.isEmpty(layoutPageTemplateCollections) &&
			ListUtil.isEmpty(getLayoutPageTemplateEntries())) {

			layoutPageTemplateCollections = new ArrayList<>();

			long layoutPageTemplateCollectionId = ParamUtil.getLong(
				_httpServletRequest, "layoutPageTemplateCollectionId");

			if (layoutPageTemplateCollectionId !=
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT) {

				layoutPageTemplateCollections.add(
					LayoutPageTemplateCollectionLocalServiceUtil.
						fetchLayoutPageTemplateCollection(
							layoutPageTemplateCollectionId));
			}
			else {
				layoutPageTemplateCollections.add(null);
			}
		}

		return layoutPageTemplateCollections;
	}

	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries() {
		if (_layoutPageTemplateEntries != null) {
			return _layoutPageTemplateEntries;
		}

		_layoutPageTemplateEntries =
			(List<LayoutPageTemplateEntry>)_httpServletRequest.getAttribute(
				LayoutPageTemplateAdminWebKeys.LAYOUT_PAGE_TEMPLATE_ENTRIES);

		return _layoutPageTemplateEntries;
	}

	public String getSubtypeLabel(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				layoutPageTemplateEntry.getClassName());

		if (infoItemFormVariationsProvider == null) {
			return StringPool.BLANK;
		}

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariationsProvider.getInfoItemFormVariation(
				layoutPageTemplateEntry.getGroupId(),
				String.valueOf(layoutPageTemplateEntry.getClassTypeId()));

		if (infoItemFormVariation != null) {
			return infoItemFormVariation.getLabel(_themeDisplay.getLocale());
		}

		return StringPool.BLANK;
	}

	public String getTypeLabel(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		InfoItemDetailsProvider<?> infoItemDetailsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemDetailsProvider.class,
				layoutPageTemplateEntry.getClassName());

		if (infoItemDetailsProvider == null) {
			return StringPool.BLANK;
		}

		InfoItemClassDetails infoItemClassDetails =
			infoItemDetailsProvider.getInfoItemClassDetails();

		return infoItemClassDetails.getLabel(_themeDisplay.getLocale());
	}

	private final HttpServletRequest _httpServletRequest;
	private final InfoItemServiceRegistry _infoItemServiceRegistry;
	private List<LayoutPageTemplateEntry> _layoutPageTemplateEntries;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}