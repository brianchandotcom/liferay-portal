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

package com.liferay.style.book.web.internal.display.context;

import com.liferay.fragment.collection.item.selector.FragmentCollectionItemSelectorReturnType;
import com.liferay.fragment.collection.item.selector.criterion.FragmentCollectionItemSelectorCriterion;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.service.FragmentCollectionServiceUtil;
import com.liferay.fragment.util.comparator.FragmentCollectionContributorNameComparator;
import com.liferay.fragment.util.comparator.FragmentCollectionCreateDateComparator;
import com.liferay.frontend.token.definition.FrontendTokenDefinition;
import com.liferay.frontend.token.definition.FrontendTokenDefinitionRegistry;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.item.selector.LayoutItemSelectorReturnType;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.item.selector.LayoutPageTemplateEntryItemSelectorReturnType;
import com.liferay.layout.page.template.item.selector.criterion.LayoutPageTemplateEntryItemSelectorCriterion;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.layout.page.template.util.comparator.LayoutPageTemplateEntryModifiedDateComparator;
import com.liferay.layout.util.comparator.LayoutModifiedDateComparator;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.portlet.url.builder.ResourceURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.segments.service.SegmentsExperienceLocalServiceUtil;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;
import com.liferay.style.book.web.internal.configuration.FFStyleBookConfigurationUtil;
import com.liferay.style.book.web.internal.constants.StyleBookWebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class EditStyleBookEntryDisplayContext {

	public EditStyleBookEntryDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_fragmentCollectionContributorTracker =
			(FragmentCollectionContributorTracker)renderRequest.getAttribute(
				StyleBookWebKeys.FRAGMENT_COLLECTION_CONTRIBUTOR_TRACKER);
		_frontendTokenDefinitionRegistry =
			(FrontendTokenDefinitionRegistry)_renderRequest.getAttribute(
				FrontendTokenDefinitionRegistry.class.getName());
		_itemSelector = (ItemSelector)_renderRequest.getAttribute(
			ItemSelector.class.getName());
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_setViewAttributes();
	}

	public Map<String, Object> getStyleBookEditorData() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"fragmentCollectionPreviewURL",
			ResourceURLBuilder.createResourceURL(
				_renderResponse
			).setResourceID(
				"/style_book/preview_fragment_collection"
			).buildString()
		).put(
			"frontendTokenDefinition", _getFrontendTokenDefinitionJSONObject()
		).put(
			"frontendTokensValues",
			() -> {
				StyleBookEntry styleBookEntry = _getStyleBookEntry();

				return JSONFactoryUtil.createJSONObject(
					styleBookEntry.getFrontendTokensValues());
			}
		).put(
			"isPrivateLayoutsEnabled",
			() -> {
				Group group = _themeDisplay.getScopeGroup();

				return group.isPrivateLayoutsEnabled();
			}
		).put(
			"layoutsTreeURL",
			() -> {
				ResourceURL resourceURL = _renderResponse.createResourceURL();

				resourceURL.setResourceID("/style_book/layouts_tree");

				return resourceURL.toString();
			}
		).put(
			"namespace", _renderResponse.getNamespace()
		).put(
			"previewOptions",
			JSONUtil.putAll(
				JSONUtil.put(
					"data",
					_getOptionJSONObject(
						LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE)
				).put(
					"type", "displayPageTemplate"
				),
				JSONUtil.put(
					"data", _getFragmentCollectionOptionJSONObject()
				).put(
					"type", "fragmentCollection"
				),
				JSONUtil.put(
					"data",
					_getOptionJSONObject(
						LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT)
				).put(
					"type", "master"
				),
				JSONUtil.put(
					"data", _getPageOptionJSONObject()
				).put(
					"type", "page"
				),
				JSONUtil.put(
					"data",
					_getOptionJSONObject(
						LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
						LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE)
				).put(
					"type", "pageTemplate"
				))
		).put(
			"publishURL", _getActionURL("/style_book/publish_style_book_entry")
		).put(
			"redirectURL", _getRedirect()
		).put(
			"saveDraftURL", _getActionURL("/style_book/edit_style_book_entry")
		).put(
			"styleBookEntryId", _getStyleBookEntryId()
		).put(
			"themeName", _getThemeName()
		).put(
			"tokenReuseEnabled",
			FFStyleBookConfigurationUtil.tokenReuseEnabled()
		).build();
	}

	private String _getActionURL(String actionName) {
		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			actionName
		).buildString();
	}

	private String _getFragmentCollectionItemSelectorURL() {
		FragmentCollectionItemSelectorCriterion
			fragmentCollectionItemSelectorCriterion =
				new FragmentCollectionItemSelectorCriterion();

		fragmentCollectionItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				new FragmentCollectionItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
			_renderResponse.getNamespace() + "selectPreviewItem",
			fragmentCollectionItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	private JSONObject _getFragmentCollectionOptionJSONObject() {
		int fragmentCollectionsCount = _getFragmentCollectionsCount();

		return JSONUtil.put(
			"itemSelectorURL", _getFragmentCollectionItemSelectorURL()
		).put(
			"recentLayouts",
			() -> {
				List<FragmentCollection> fragmentCollections =
					FragmentCollectionServiceUtil.getFragmentCollections(
						new long[] {
							_themeDisplay.getSiteGroupId(),
							_themeDisplay.getCompanyGroupId()
						},
						0, Math.min(fragmentCollectionsCount, 4),
						new FragmentCollectionCreateDateComparator(false));

				JSONObject[] fragmentCollectionContributorJSONObjects =
					new JSONObject[0];

				if (fragmentCollections.size() < 4) {
					List<FragmentCollectionContributor>
						fragmentCollectionContributors =
							_fragmentCollectionContributorTracker.
								getFragmentCollectionContributors();

					Collections.sort(
						fragmentCollectionContributors,
						new FragmentCollectionContributorNameComparator(
							_themeDisplay.getLocale()));

					List<FragmentCollectionContributor>
						filteredFragmentCollectionContributors =
							ListUtil.subList(
								fragmentCollectionContributors, 0,
								4 - fragmentCollections.size());

					Stream<FragmentCollectionContributor>
						fragmentCollectionContributorsStream =
							filteredFragmentCollectionContributors.stream();

					fragmentCollectionContributorJSONObjects =
						fragmentCollectionContributorsStream.map(
							fragmentCollectionContributor -> JSONUtil.put(
								"name", fragmentCollectionContributor.getName()
							).put(
								"url",
								_getPreviewFragmentCollectionURL(
									fragmentCollectionContributor.
										getFragmentCollectionKey(),
									CompanyConstants.SYSTEM)
							)
						).toArray(
							JSONObject[]::new
						);
				}

				Stream<FragmentCollection> fragmentCollectionsStream =
					fragmentCollections.stream();

				return JSONUtil.putAll(
					ArrayUtil.append(
						fragmentCollectionsStream.map(
							fragmentCollection -> JSONUtil.put(
								"name", fragmentCollection.getName()
							).put(
								"url",
								_getPreviewFragmentCollectionURL(
									fragmentCollection.
										getFragmentCollectionKey(),
									fragmentCollection.getGroupId())
							)
						).toArray(
							JSONObject[]::new
						),
						fragmentCollectionContributorJSONObjects));
			}
		).put(
			"totalLayouts", fragmentCollectionsCount
		);
	}

	private int _getFragmentCollectionsCount() {
		int fragmentCollectionsCount =
			FragmentCollectionServiceUtil.getFragmentCollectionsCount(
				new long[] {
					_themeDisplay.getSiteGroupId(),
					_themeDisplay.getCompanyGroupId()
				});

		if (_fragmentCollectionContributorTracker == null) {
			return fragmentCollectionsCount;
		}

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributors();

		return fragmentCollectionsCount + fragmentCollectionContributors.size();
	}

	private JSONObject _getFrontendTokenDefinitionJSONObject()
		throws Exception {

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(
			_themeDisplay.getSiteGroupId(), false);

		FrontendTokenDefinition frontendTokenDefinition =
			_frontendTokenDefinitionRegistry.getFrontendTokenDefinition(
				layoutSet.getThemeId());

		if (frontendTokenDefinition != null) {
			return JSONFactoryUtil.createJSONObject(
				frontendTokenDefinition.getJSON(_themeDisplay.getLocale()));
		}

		return JSONFactoryUtil.createJSONObject();
	}

	private JSONObject _getOptionJSONObject(int... layoutTypes) {
		int total =
			LayoutPageTemplateEntryServiceUtil.
				getLayoutPageTemplateEntriesCount(
					_getPreviewItemsGroupId(), layoutTypes,
					WorkflowConstants.STATUS_APPROVED);

		return JSONUtil.put(
			"itemSelectorURL",
			() -> {
				LayoutPageTemplateEntryItemSelectorCriterion
					layoutPageTemplateEntryItemSelectorCriterion =
						new LayoutPageTemplateEntryItemSelectorCriterion();

				layoutPageTemplateEntryItemSelectorCriterion.setGroupId(
					_getPreviewItemsGroupId());
				layoutPageTemplateEntryItemSelectorCriterion.setLayoutTypes(
					layoutTypes);

				layoutPageTemplateEntryItemSelectorCriterion.
					setDesiredItemSelectorReturnTypes(
						new LayoutPageTemplateEntryItemSelectorReturnType());

				PortletURL entryItemSelectorURL =
					_itemSelector.getItemSelectorURL(
						RequestBackedPortletURLFactoryUtil.create(
							_httpServletRequest),
						_renderResponse.getNamespace() + "selectPreviewItem",
						layoutPageTemplateEntryItemSelectorCriterion);

				return entryItemSelectorURL.toString();
			}
		).put(
			"recentLayouts",
			() -> {
				List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
					LayoutPageTemplateEntryServiceUtil.
						getLayoutPageTemplateEntries(
							_getPreviewItemsGroupId(), layoutTypes,
							WorkflowConstants.STATUS_APPROVED, 0,
							Math.min(total, 4),
							new LayoutPageTemplateEntryModifiedDateComparator(
								false));

				Stream<LayoutPageTemplateEntry>
					layoutPageTemplateEntriesStream =
						layoutPageTemplateEntries.stream();

				return JSONUtil.putAll(
					layoutPageTemplateEntriesStream.map(
						layoutPageTemplateEntry -> JSONUtil.put(
							"name", layoutPageTemplateEntry.getName()
						).put(
							"private", false
						).put(
							"url", _getPreviewURL(layoutPageTemplateEntry)
						)
					).toArray(
						JSONObject[]::new
					));
			}
		).put(
			"totalLayouts", total
		);
	}

	private JSONObject _getPageOptionJSONObject() {
		int total = LayoutLocalServiceUtil.getPublishedLayoutsCount(
			_getPreviewItemsGroupId());

		return JSONUtil.put(
			"itemSelectorURL",
			() -> {
				LayoutItemSelectorCriterion layoutItemSelectorCriterion =
					new LayoutItemSelectorCriterion();

				layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
					new LayoutItemSelectorReturnType());
				layoutItemSelectorCriterion.setShowHiddenPages(true);

				Group group = _themeDisplay.getScopeGroup();

				layoutItemSelectorCriterion.setShowPrivatePages(
					group.isPrivateLayoutsEnabled());

				PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
					RequestBackedPortletURLFactoryUtil.create(
						_httpServletRequest),
					_renderResponse.getNamespace() + "selectPreviewItem",
					layoutItemSelectorCriterion);

				return itemSelectorURL.toString();
			}
		).put(
			"recentLayouts",
			() -> {
				List<Layout> layouts =
					LayoutLocalServiceUtil.getPublishedLayouts(
						_getPreviewItemsGroupId(), 0, Math.min(total, 4),
						new LayoutModifiedDateComparator(false));

				Stream<Layout> layoutsStream = layouts.stream();

				return JSONUtil.putAll(
					layoutsStream.map(
						layout -> JSONUtil.put(
							"name", layout.getName(_themeDisplay.getLocale())
						).put(
							"private", layout.isPrivateLayout()
						).put(
							"url", _getPreviewURL(layout)
						)
					).toArray(
						JSONObject[]::new
					));
			}
		).put(
			"totalLayouts", total
		);
	}

	private String _getPreviewFragmentCollectionURL(
		String fragmentCollectionKey, long groupId) {

		String url = ResourceURLBuilder.createResourceURL(
			_renderResponse
		).setResourceID(
			"/style_book/preview_fragment_collection"
		).buildString();

		String portletNamespace = PortalUtil.getPortletNamespace(
			StyleBookPortletKeys.STYLE_BOOK);

		url = HttpUtil.addParameter(url, portletNamespace + "groupId", groupId);

		return HttpUtil.addParameter(
			url, portletNamespace + "fragmentCollectionKey",
			fragmentCollectionKey);
	}

	private long _getPreviewItemsGroupId() {
		if (_previewItemsGroupId != null) {
			return _previewItemsGroupId;
		}

		Layout layout = _themeDisplay.getLayout();

		_previewItemsGroupId = layout.getGroupId();

		return _previewItemsGroupId;
	}

	private String _getPreviewURL(Layout layout) {
		try {
			String layoutURL = HttpUtil.addParameter(
				PortalUtil.getLayoutFullURL(layout, _themeDisplay), "p_l_mode",
				Constants.PREVIEW);

			layoutURL = HttpUtil.addParameter(
				layoutURL, "p_p_auth",
				AuthTokenUtil.getToken(_httpServletRequest));

			return HttpUtil.addParameter(
				layoutURL, "styleBookEntryPreview", true);
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);
		}

		return null;
	}

	private String _getPreviewURL(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		try {
			Layout layout = LayoutLocalServiceUtil.getLayout(
				layoutPageTemplateEntry.getPlid());

			if (layoutPageTemplateEntry.getType() ==
					LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE) {

				ResourceURL getPagePreviewURL = PortletURLFactoryUtil.create(
					_httpServletRequest,
					ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
					layout, PortletRequest.RESOURCE_PHASE);

				getPagePreviewURL.setParameter(
					"segmentsExperienceId",
					String.valueOf(
						SegmentsExperienceLocalServiceUtil.
							fetchDefaultSegmentsExperienceId(
								layoutPageTemplateEntry.getPlid())));
				getPagePreviewURL.setResourceID(
					"/layout_content_page_editor/get_page_preview");

				String url = HttpUtil.addParameter(
					getPagePreviewURL.toString(), "doAsUserId",
					_themeDisplay.getDefaultUserId());

				url = HttpUtil.addParameter(url, "p_l_mode", Constants.PREVIEW);

				return HttpUtil.addParameter(
					url, "styleBookEntryPreview", true);
			}

			return _getPreviewURL(layout);
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);
		}

		return null;
	}

	private String _getRedirect() {
		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			return redirect;
		}

		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).buildString();
	}

	private StyleBookEntry _getStyleBookEntry() {
		if (_styleBookEntry != null) {
			return _styleBookEntry;
		}

		_styleBookEntry = StyleBookEntryLocalServiceUtil.fetchStyleBookEntry(
			_getStyleBookEntryId());

		if (_styleBookEntry.isHead()) {
			StyleBookEntry draftStyleBookEntry =
				StyleBookEntryLocalServiceUtil.fetchDraft(_styleBookEntry);

			if (draftStyleBookEntry != null) {
				_styleBookEntry = draftStyleBookEntry;
			}
		}

		return _styleBookEntry;
	}

	private long _getStyleBookEntryId() {
		if (_styleBookEntryId != null) {
			return _styleBookEntryId;
		}

		_styleBookEntryId = ParamUtil.getLong(
			_httpServletRequest, "styleBookEntryId");

		return _styleBookEntryId;
	}

	private String _getStyleBookEntryTitle() {
		StyleBookEntry styleBookEntry = _getStyleBookEntry();

		return styleBookEntry.getName();
	}

	private String _getThemeName() {
		LayoutSet layoutSet = LayoutSetLocalServiceUtil.fetchLayoutSet(
			_themeDisplay.getSiteGroupId(), false);

		Theme theme = layoutSet.getTheme();

		return theme.getName();
	}

	private void _setViewAttributes() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(_getRedirect());

		_renderResponse.setTitle(_getStyleBookEntryTitle());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditStyleBookEntryDisplayContext.class.getName());

	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private final FrontendTokenDefinitionRegistry
		_frontendTokenDefinitionRegistry;
	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private Long _previewItemsGroupId;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private StyleBookEntry _styleBookEntry;
	private Long _styleBookEntryId;
	private final ThemeDisplay _themeDisplay;

}