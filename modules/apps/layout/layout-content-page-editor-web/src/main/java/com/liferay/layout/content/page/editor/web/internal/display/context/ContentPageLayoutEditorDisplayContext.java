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

package com.liferay.layout.content.page.editor.web.internal.display.context;

import com.liferay.fragment.constants.FragmentEntryTypeConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryServiceUtil;
import com.liferay.fragment.util.FragmentEntryRenderUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.PortletCategory;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.PortletCategoryComparator;
import com.liferay.portal.kernel.util.comparator.PortletTitleComparator;
import com.liferay.portal.template.soy.util.SoyContext;
import com.liferay.portal.template.soy.util.SoyContextFactoryUtil;
import com.liferay.portal.util.PortletCategoryUtil;
import com.liferay.portal.util.WebAppPool;

import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletConfig;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Eudaldo Alonso
 */
public class ContentPageLayoutEditorDisplayContext
	extends ContentPageEditorDisplayContext {

	public ContentPageLayoutEditorDisplayContext(
		HttpServletRequest request, RenderResponse renderResponse,
		String className, long classPK) {

		super(request, renderResponse, className, classPK);
	}

	public SoyContext getEditorContext() throws Exception {
		if (_editorSoyContext != null) {
			return _editorSoyContext;
		}

		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put(
			"addFragmentEntryLinkURL",
			getFragmentEntryActionURL(
				"/content_layout/add_fragment_entry_link"));
		soyContext.put(
			"addPortletURL",
			getFragmentEntryActionURL("/content_layout/add_portlet"));
		soyContext.put("availableLanguages", getAvailableLanguagesSoyContext());
		soyContext.put("classNameId", classNameId);
		soyContext.put("classPK", classPK);
		soyContext.put(
			"defaultEditorConfigurations", getDefaultConfigurations());
		soyContext.put("defaultLanguageId", themeDisplay.getLanguageId());
		soyContext.put(
			"deleteFragmentEntryLinkURL",
			getFragmentEntryActionURL(
				"/content_layout/delete_fragment_entry_link"));
		soyContext.put(
			"editFragmentEntryLinkURL",
			getFragmentEntryActionURL(
				"/content_layout/edit_fragment_entry_link"));
		soyContext.put(
			"elements",
			getSoyContextFragmentCollections(
				FragmentEntryTypeConstants.TYPE_ELEMENT));
		soyContext.put(
			"fragmentEntryLinks", _getSoyContextFragmentEntryLinks());
		soyContext.put("imageSelectorURL", getItemSelectorURL());
		soyContext.put("languageId", themeDisplay.getLanguageId());
		soyContext.put(
			"layoutData", JSONFactoryUtil.createJSONObject(getLayoutData()));
		soyContext.put("panels", getPanelSoyContexts(false, true));
		soyContext.put("portletNamespace", renderResponse.getNamespace());
		soyContext.put(
			"renderFragmentEntryURL",
			getFragmentEntryActionURL("/content_layout/render_fragment_entry"));
		soyContext.put("redirectURL", getRedirect());
		soyContext.put(
			"sections",
			getSoyContextFragmentCollections(
				FragmentEntryTypeConstants.TYPE_SECTION));
		soyContext.put(
			"spritemap",
			themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");
		soyContext.put("themeColors", getThemeColors());
		soyContext.put(
			"updateLayoutPageTemplateDataURL",
			getFragmentEntryActionURL(
				"/content_layout/update_layout_page_template_data"));
		soyContext.put("widgets", _getWidgetsSoyContexts());

		_editorSoyContext = soyContext;

		return _editorSoyContext;
	}

	public SoyContext getFragmentsEditorToolbarContext() {
		if (_fragmentsEditorToolbarSoyContext != null) {
			return _fragmentsEditorToolbarSoyContext;
		}

		SoyContext soyContext = SoyContextFactoryUtil.createSoyContext();

		soyContext.put("availableLanguages", getAvailableLanguagesSoyContext());
		soyContext.put("classPK", themeDisplay.getPlid());
		soyContext.put("defaultLanguageId", themeDisplay.getLanguageId());
		soyContext.put("lastSaveDate", StringPool.BLANK);
		soyContext.put("portletNamespace", renderResponse.getNamespace());
		soyContext.put(
			"spritemap",
			themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");

		_fragmentsEditorToolbarSoyContext = soyContext;

		return _fragmentsEditorToolbarSoyContext;
	}

	private String _getPortletCategoryTitle(PortletCategory portletCategory) {
		String title = LanguageUtil.get(request, portletCategory.getName());

		if (Validator.isNotNull(title)) {
			return title;
		}

		for (String portletId :
				PortletCategoryUtil.getFirstChildPortletIds(portletCategory)) {

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId);

			if (portlet == null) {
				continue;
			}

			PortletApp portletApp = portlet.getPortletApp();

			if (!portletApp.isWARFile()) {
				continue;
			}

			PortletConfig portletConfig = PortletConfigFactoryUtil.create(
				portlet, request.getServletContext());

			ResourceBundle portletResourceBundle =
				portletConfig.getResourceBundle(themeDisplay.getLocale());

			return ResourceBundleUtil.getString(
				portletResourceBundle, portletCategory.getName());
		}

		return StringPool.BLANK;
	}

	private String _getPortletId(String content) {
		Document document = Jsoup.parse(content);

		Elements elements = document.getElementsByAttributeValueStarting(
			"id", "portlet_");

		if (elements.size() != 1) {
			return StringPool.BLANK;
		}

		Element element = elements.get(0);

		String id = element.id();

		return PortletIdCodec.decodePortletName(id.substring(8));
	}

	private List<SoyContext> _getPortletsContexts(
		PortletCategory portletCategory) {

		Set<String> portletIds = portletCategory.getPortletIds();

		Stream<String> stream = portletIds.stream();

		HttpSession session = request.getSession();

		ServletContext servletContext = session.getServletContext();

		return stream.map(
			portletId -> PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId)
		).filter(
			portlet -> {
				if (portlet == null) {
					return false;
				}

				try {
					return PortletPermissionUtil.contains(
						themeDisplay.getPermissionChecker(),
						themeDisplay.getLayout(), portlet,
						ActionKeys.ADD_TO_PAGE);
				}
				catch (PortalException pe) {
					_log.error(
						"Unable to check portlet permissions for " +
							portlet.getPortletId(),
						pe);

					return false;
				}
			}
		).sorted(
			new PortletTitleComparator(servletContext, themeDisplay.getLocale())
		).map(
			portlet -> {
				SoyContext portletSoyContext =
					SoyContextFactoryUtil.createSoyContext();

				portletSoyContext.put("instanceable", portlet.isInstanceable());
				portletSoyContext.put("portletId", portlet.getPortletId());
				portletSoyContext.put(
					"title",
					PortalUtil.getPortletTitle(
						portlet, servletContext, themeDisplay.getLocale()));
				portletSoyContext.put(
					"used", _isUsed(portlet, themeDisplay.getPlid()));

				return portletSoyContext;
			}
		).collect(
			Collectors.toList()
		);
	}

	private SoyContext _getSoyContextFragmentEntryLinks()
		throws PortalException {

		if (_soyContextFragmentEntryLinksSoyContext != null) {
			return _soyContextFragmentEntryLinksSoyContext;
		}

		SoyContext soyContexts = SoyContextFactoryUtil.createSoyContext();

		List<FragmentEntryLink> fragmentEntryLinks =
			FragmentEntryLinkLocalServiceUtil.getFragmentEntryLinks(
				themeDisplay.getScopeGroupId(), classNameId, classPK);

		boolean isolated = themeDisplay.isIsolated();

		themeDisplay.setIsolated(true);

		try {
			for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
				FragmentEntry fragmentEntry =
					FragmentEntryServiceUtil.fetchFragmentEntry(
						fragmentEntryLink.getFragmentEntryId());

				SoyContext soyContext =
					SoyContextFactoryUtil.createSoyContext();

				String content =
					FragmentEntryRenderUtil.renderFragmentEntryLink(
						fragmentEntryLink, request,
						PortalUtil.getHttpServletResponse(renderResponse));

				soyContext.putHTML("content", content);

				soyContext.put(
					"editableValues",
					JSONFactoryUtil.createJSONObject(
						fragmentEntryLink.getEditableValues()));
				soyContext.put(
					"fragmentEntryLinkId",
					String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()));

				if (fragmentEntry != null) {
					soyContext.put(
						"fragmentEntryId", fragmentEntry.getFragmentEntryId());
					soyContext.put("name", fragmentEntry.getName());
				}
				else {
					String portletId = _getPortletId(content);

					soyContext.put("fragmentEntryId", 0);
					soyContext.put(
						"name",
						PortalUtil.getPortletTitle(
							portletId, themeDisplay.getLocale()));
					soyContext.put("portletId", portletId);
				}

				soyContexts.put(
					String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
					soyContext);
			}
		}
		finally {
			themeDisplay.setIsolated(isolated);
		}

		_soyContextFragmentEntryLinksSoyContext = soyContexts;

		return _soyContextFragmentEntryLinksSoyContext;
	}

	private List<SoyContext> _getWidgetCategoriesContexts(
		PortletCategory portletCategory) {

		Collection<PortletCategory> portletCategories =
			portletCategory.getCategories();

		Stream<PortletCategory> stream = portletCategories.stream();

		return stream.sorted(
			new PortletCategoryComparator(themeDisplay.getLocale())
		).filter(
			category -> !category.isHidden()
		).map(
			category -> {
				SoyContext categoryContext =
					SoyContextFactoryUtil.createSoyContext();

				categoryContext.put(
					"categories", _getWidgetCategoriesContexts(category));
				categoryContext.put(
					"path",
					StringUtil.replace(
						category.getPath(), new String[] {"/", "."},
						new String[] {"-", "-"}));
				categoryContext.put("portlets", _getPortletsContexts(category));
				categoryContext.put(
					"title", _getPortletCategoryTitle(category));

				return categoryContext;
			}
		).collect(
			Collectors.toList()
		);
	}

	private List<SoyContext> _getWidgetsSoyContexts() throws Exception {
		PortletCategory portletCategory = (PortletCategory)WebAppPool.get(
			themeDisplay.getCompanyId(), WebKeys.PORTLET_CATEGORY);

		portletCategory = PortletCategoryUtil.getRelevantPortletCategory(
			themeDisplay.getPermissionChecker(), themeDisplay.getCompanyId(),
			themeDisplay.getLayout(), portletCategory,
			themeDisplay.getLayoutTypePortlet());

		return _getWidgetCategoriesContexts(portletCategory);
	}

	private boolean _isUsed(Portlet portlet, long plid) {
		if (portlet.isInstanceable()) {
			return false;
		}

		long count =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid,
				portlet.getPortletId());

		if (count > 0) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentPageLayoutEditorDisplayContext.class);

	private SoyContext _editorSoyContext;
	private SoyContext _fragmentsEditorToolbarSoyContext;
	private SoyContext _soyContextFragmentEntryLinksSoyContext;

}