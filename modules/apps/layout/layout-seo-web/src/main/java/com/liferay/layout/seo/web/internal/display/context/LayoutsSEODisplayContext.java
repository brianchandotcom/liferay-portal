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

package com.liferay.layout.seo.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.seo.canonical.url.LayoutSEOCanonicalURLProvider;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.model.LayoutSEOSite;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalServiceUtil;
import com.liferay.layout.seo.service.LayoutSEOSiteLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.layoutsadmin.display.context.GroupDisplayContextHelper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.portlet.ActionRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia García
 */
public class LayoutsSEODisplayContext {

	public LayoutsSEODisplayContext(
		DLAppService dlAppService, DLURLHelper dlurlHelper,
		InfoItemServiceTracker infoItemServiceTracker,
		ItemSelector itemSelector,
		LayoutPageTemplateEntryLocalService layoutPageTemplateEntryLocalService,
		LayoutSEOCanonicalURLProvider layoutSEOCanonicalURLProvider,
		LayoutSEOLinkManager layoutSEOLinkManager,
		LayoutSEOSiteLocalService layoutSEOSiteLocalService,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		StorageEngine storageEngine) {

		_dlAppService = dlAppService;
		_dlurlHelper = dlurlHelper;
		_infoItemServiceTracker = infoItemServiceTracker;
		_itemSelector = itemSelector;
		_layoutPageTemplateEntryLocalService =
			layoutPageTemplateEntryLocalService;
		_layoutSEOCanonicalURLProvider = layoutSEOCanonicalURLProvider;
		_layoutSEOLinkManager = layoutSEOLinkManager;
		_layoutSEOSiteLocalService = layoutSEOSiteLocalService;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_storageEngine = storageEngine;

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_liferayPortletRequest);

		_groupDisplayContextHelper = new GroupDisplayContextHelper(
			httpServletRequest);

		_httpServletRequest = httpServletRequest;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public DDMFormValues getDDMFormValues() throws StorageException {
		LayoutSEOEntry selLayoutSEOEntry = getSelLayoutSEOEntry();

		if ((selLayoutSEOEntry == null) ||
			(selLayoutSEOEntry.getDDMStorageId() == 0)) {

			return null;
		}

		return _storageEngine.getDDMFormValues(
			selLayoutSEOEntry.getDDMStorageId());
	}

	public long getDDMStructurePrimaryKey() throws PortalException {
		if (_ddmStructure != null) {
			return _ddmStructure.getPrimaryKey();
		}

		Company company = _themeDisplay.getCompany();

		_ddmStructure = DDMStructureServiceUtil.getStructure(
			company.getGroupId(),
			ClassNameLocalServiceUtil.getClassNameId(
				LayoutSEOEntry.class.getName()),
			"custom-meta-tags");

		return _ddmStructure.getPrimaryKey();
	}

	public String getDefaultCanonicalURL() throws PortalException {
		return _layoutSEOCanonicalURLProvider.getDefaultCanonicalURL(
			_selLayout, _themeDisplay);
	}

	public Map<Locale, String> getDefaultCanonicalURLMap()
		throws PortalException {

		return _layoutSEOCanonicalURLProvider.getCanonicalURLMap(
			_selLayout, _themeDisplay);
	}

	public String getDefaultOpenGraphImageURL() throws Exception {
		LayoutSEOSite layoutSEOSite =
			_layoutSEOSiteLocalService.fetchLayoutSEOSiteByGroupId(
				getGroupId());

		if ((layoutSEOSite == null) ||
			(layoutSEOSite.getOpenGraphImageFileEntryId() == 0) ||
			!layoutSEOSite.isOpenGraphEnabled()) {

			return StringPool.BLANK;
		}

		try {
			FileEntry fileEntry = _dlAppService.getFileEntry(
				layoutSEOSite.getOpenGraphImageFileEntryId());

			if (fileEntry.isInTrash()) {
				return StringPool.BLANK;
			}

			return _dlurlHelper.getImagePreviewURL(fileEntry, _themeDisplay);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return StringPool.BLANK;
		}
	}

	public Map<Locale, String> getDefaultPageTitleMap() {
		return HashMapBuilder.putAll(
			_selLayout.getNameMap()
		).putAll(
			_selLayout.getTitleMap()
		).build();
	}

	public PortletURL getEditCustomMetaTagsURL() {
		return _getPortletURL("/layout/edit_custom_meta_tags");
	}

	public PortletURL getEditOpenGraphURL() {
		return _getPortletURL("/layout/edit_open_graph");
	}

	public long getGroupId() {
		LayoutSEOEntry selLayoutSEOEntry = getSelLayoutSEOEntry();

		if (selLayoutSEOEntry == null) {
			return _groupDisplayContextHelper.getGroupId();
		}

		return selLayoutSEOEntry.getGroupId();
	}

	public String getItemSelectorURL() {
		ItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType(),
			new URLItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
			_liferayPortletResponse.getNamespace() +
				"openGraphImageSelectedItem",
			imageItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public Long getLayoutId() {
		if (_layoutId != null) {
			return _layoutId;
		}

		_layoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

		Layout selLayout = getSelLayout();

		if (selLayout != null) {
			_layoutId = selLayout.getLayoutId();
		}

		return _layoutId;
	}

	public String getOpenGraphImageTitle() {
		LayoutSEOEntry layoutSEOEntry = getSelLayoutSEOEntry();

		if ((layoutSEOEntry == null) ||
			(layoutSEOEntry.getOpenGraphImageFileEntryId() == 0)) {

			return StringPool.BLANK;
		}

		try {
			FileEntry fileEntry = _dlAppService.getFileEntry(
				layoutSEOEntry.getOpenGraphImageFileEntryId());

			if (fileEntry.isInTrash()) {
				return StringPool.BLANK;
			}

			return fileEntry.getTitle();
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return StringPool.BLANK;
		}
	}

	public String getOpenGraphImageURL() {
		LayoutSEOEntry layoutSEOEntry = getSelLayoutSEOEntry();

		if ((layoutSEOEntry == null) ||
			(layoutSEOEntry.getOpenGraphImageFileEntryId() == 0)) {

			return StringPool.BLANK;
		}

		try {
			FileEntry fileEntry = _dlAppService.getFileEntry(
				layoutSEOEntry.getOpenGraphImageFileEntryId());

			if (fileEntry.isInTrash()) {
				return StringPool.BLANK;
			}

			return _dlurlHelper.getImagePreviewURL(fileEntry, _themeDisplay);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return StringPool.BLANK;
		}
	}

	public HashMap<String, Object> getOpenGraphMappingData()
		throws PortalException {

		return HashMapBuilder.<String, Object>putAll(
			_getBaseSEOMappingData(
				_getInfoForm(_getLayoutPageTemplateEntry()),
				_getLayoutPageTemplateEntry())
		).put(
			"openGraphDescription",
			_selLayout.getTypeSettingsProperty(
				"mapped-openGraphDescription", "description")
		).put(
			"openGraphImage",
			_selLayout.getTypeSettingsProperty("mapped-openGraphImage", null)
		).put(
			"openGraphImageAlt",
			_selLayout.getTypeSettingsProperty("mapped-openGraphImageAlt", null)
		).put(
			"openGraphTitle",
			_selLayout.getTypeSettingsProperty("mapped-openGraphTitle", "title")
		).build();
	}

	public String getPageTitleSuffix() throws PortalException {
		Company company = _themeDisplay.getCompany();

		return _layoutSEOLinkManager.getPageTitleSuffix(
			_selLayout, company.getName());
	}

	public PortletURL getRedirectURL() {
		LiferayPortletURL liferayPortletURL =
			_liferayPortletResponse.createLiferayPortletURL(
				_liferayPortletRequest.getPlid(),
				_liferayPortletRequest.getPortletName(),
				PortletRequest.RENDER_PHASE, MimeResponse.Copy.ALL);

		liferayPortletURL.setParameter(
			"mvcRenderCommandName",
			_liferayPortletRequest.getParameter("mvcRenderCommandName"));
		liferayPortletURL.setParameter(
			"tabs1", _liferayPortletRequest.getParameter("tabs1"));
		liferayPortletURL.setParameter(
			"screenNavigationCategoryKey",
			_liferayPortletRequest.getParameter("screenNavigationCategoryKey"));
		liferayPortletURL.setParameter(
			"screenNavigationEntryKey",
			_liferayPortletRequest.getParameter("screenNavigationEntryKey"));
		liferayPortletURL.setParameter(
			"selPlid", _liferayPortletRequest.getParameter("selPlid"));
		liferayPortletURL.setParameter(
			"privateLayout",
			_liferayPortletRequest.getParameter("privateLayout"));
		liferayPortletURL.setParameter(
			"displayStyle",
			_liferayPortletRequest.getParameter("displayStyle"));

		return liferayPortletURL;
	}

	public Group getSelGroup() {
		return _groupDisplayContextHelper.getSelGroup();
	}

	public Layout getSelLayout() {
		if (_selLayout != null) {
			return _selLayout;
		}

		if (_getSelPlid() != LayoutConstants.DEFAULT_PLID) {
			_selLayout = LayoutLocalServiceUtil.fetchLayout(_getSelPlid());
		}

		return _selLayout;
	}

	public LayoutSEOEntry getSelLayoutSEOEntry() {
		Layout layout = getSelLayout();

		if (layout == null) {
			return null;
		}

		return LayoutSEOEntryLocalServiceUtil.fetchLayoutSEOEntry(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId());
	}

	public HashMap<String, Object> getSEOMappingData() throws PortalException {
		return HashMapBuilder.<String, Object>putAll(
			_getBaseSEOMappingData(
				_getInfoForm(_getLayoutPageTemplateEntry()),
				_getLayoutPageTemplateEntry())
		).put(
			"description",
			_selLayout.getTypeSettingsProperty(
				"mapped-description", "description")
		).put(
			"title", _selLayout.getTypeSettingsProperty("mapped-title", "title")
		).build();
	}

	public boolean isPrivateLayout() {
		if (_privateLayout != null) {
			return _privateLayout;
		}

		Group selGroup = getSelGroup();

		if (selGroup.isLayoutSetPrototype()) {
			_privateLayout = true;

			return _privateLayout;
		}

		if (getSelLayout() != null) {
			Layout selLayout = getSelLayout();

			_privateLayout = selLayout.isPrivateLayout();

			return _privateLayout;
		}

		Layout layout = _themeDisplay.getLayout();

		if (!layout.isTypeControlPanel()) {
			_privateLayout = layout.isPrivateLayout();

			return _privateLayout;
		}

		_privateLayout = ParamUtil.getBoolean(
			_liferayPortletRequest, "privateLayout");

		return _privateLayout;
	}

	private HashMap<String, Object> _getBaseSEOMappingData(
			InfoForm infoForm, LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		return HashMapBuilder.<String, Object>put(
			"defaultLanguageId", _selLayout.getDefaultLanguageId()
		).put(
			"fields",
			infoForm.getAllInfoFields(
			).stream(
			).map(
				infoField -> JSONUtil.put(
					"key", infoField.getName()
				).put(
					"label", infoField.getLabel(_themeDisplay.getLocale())
				).put(
					"type",
					infoField.getInfoFieldType(
					).getName()
				)
			).collect(
				Collectors.toList()
			)
		).put(
			"selectedSource",
			JSONUtil.put(
				"className", layoutPageTemplateEntry.getClassName()
			).put(
				"classNameLabel",
				_getTypeLabel(layoutPageTemplateEntry.getClassName())
			).put(
				"classTypeId", layoutPageTemplateEntry.getClassTypeId()
			).put(
				"classTypeLabel",
				_getSubtypeLabel(
					layoutPageTemplateEntry.getClassName(),
					layoutPageTemplateEntry.getClassTypeId())
			)
		).build();
	}

	private InfoForm _getInfoForm(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws NoSuchFormVariationException {

		InfoItemFormProvider<?> infoItemFormProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFormProvider.class,
				layoutPageTemplateEntry.getClassName());

		return infoItemFormProvider.getInfoForm(
			String.valueOf(layoutPageTemplateEntry.getClassTypeId()));
	}

	private LayoutPageTemplateEntry _getLayoutPageTemplateEntry() {
		return _layoutPageTemplateEntryLocalService.
			fetchLayoutPageTemplateEntryByPlid(_selPlid);
	}

	private PortletURL _getPortletURL(String actionName) {
		LiferayPortletURL liferayPortletURL =
			_liferayPortletResponse.createLiferayPortletURL(
				_liferayPortletRequest.getPlid(),
				_liferayPortletRequest.getPortletName(),
				PortletRequest.ACTION_PHASE, MimeResponse.Copy.ALL);

		liferayPortletURL.setParameter(ActionRequest.ACTION_NAME, actionName);

		liferayPortletURL.setParameter(
			"mvcRenderCommandName",
			_liferayPortletRequest.getParameter("mvcRenderCommandName"));
		liferayPortletURL.setParameter(
			"tabs1", _liferayPortletRequest.getParameter("tabs1"));
		liferayPortletURL.setParameter(
			"screenNavigationCategoryKey",
			_liferayPortletRequest.getParameter("screenNavigationCategoryKey"));
		liferayPortletURL.setParameter(
			"screenNavigationEntryKey",
			_liferayPortletRequest.getParameter("screenNavigationEntryKey"));
		liferayPortletURL.setParameter(
			"selPlid", _liferayPortletRequest.getParameter("selPlid"));
		liferayPortletURL.setParameter(
			"privateLayout",
			_liferayPortletRequest.getParameter("privateLayout"));
		liferayPortletURL.setParameter(
			"displayStyle",
			_liferayPortletRequest.getParameter("displayStyle"));

		return liferayPortletURL;
	}

	private Long _getSelPlid() {
		if (_selPlid != null) {
			return _selPlid;
		}

		_selPlid = ParamUtil.getLong(
			_liferayPortletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);

		return _selPlid;
	}

	private String _getSubtypeLabel(String className, long classTypeId)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if ((assetRendererFactory == null) || (classTypeId <= 0)) {
			return StringPool.BLANK;
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(
			classTypeId, _themeDisplay.getLocale());

		return classType.getName();
	}

	private String _getTypeLabel(String className) {
		InfoItemDetailsProvider infoItemDetailsProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemDetailsProvider.class, className);

		if (infoItemDetailsProvider == null) {
			return StringPool.BLANK;
		}

		InfoItemClassDetails infoItemClassDetails =
			infoItemDetailsProvider.getInfoItemClassDetails();

		return infoItemClassDetails.getLabel(_themeDisplay.getLocale());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutsSEODisplayContext.class);

	private DDMStructure _ddmStructure;
	private final DLAppService _dlAppService;
	private final DLURLHelper _dlurlHelper;
	private final GroupDisplayContextHelper _groupDisplayContextHelper;
	private final HttpServletRequest _httpServletRequest;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final ItemSelector _itemSelector;
	private Long _layoutId;
	private final LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;
	private final LayoutSEOCanonicalURLProvider _layoutSEOCanonicalURLProvider;
	private final LayoutSEOLinkManager _layoutSEOLinkManager;
	private final LayoutSEOSiteLocalService _layoutSEOSiteLocalService;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private Boolean _privateLayout;
	private Layout _selLayout;
	private Long _selPlid;
	private final StorageEngine _storageEngine;
	private final ThemeDisplay _themeDisplay;

}