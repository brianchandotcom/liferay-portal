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

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.handler.LayoutExceptionRequestHandler;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout_admin/add_collection_layout"
	},
	service = MVCActionCommand.class
)
public class AddCollectionLayoutMVCActionCommand
	extends BaseAddLayoutMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			Layout layout = _addCollectionLayout(actionRequest);

			MultiSessionMessages.add(
				actionRequest, "collectionLayoutAdded", layout);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put(
					"redirectURL",
					getContentRedirectURL(actionRequest, layout)));
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, "layoutNameInvalid");

			hideDefaultErrorMessage(actionRequest);

			_layoutExceptionRequestHandler.handleException(
				actionRequest, actionResponse, exception);
		}
	}

	private Layout _addCollectionLayout(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");
		boolean privateLayout = ParamUtil.getBoolean(
			actionRequest, "privateLayout");
		long parentLayoutId = ParamUtil.getLong(
			actionRequest, "parentLayoutId");
		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(),
			ParamUtil.getString(actionRequest, "name")
		).build();

		UnicodeProperties typeSettingsUnicodeProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		String collectionPK = ParamUtil.getString(
			actionRequest, "collectionPK");

		typeSettingsUnicodeProperties.setProperty("collectionPK", collectionPK);

		String collectionType = ParamUtil.getString(
			actionRequest, "collectionType");

		typeSettingsUnicodeProperties.setProperty(
			"collectionType", collectionType);

		long masterLayoutPlid = ParamUtil.getLong(
			actionRequest, "masterLayoutPlid");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		Layout layout = _layoutService.addLayout(
			groupId, privateLayout, parentLayoutId, nameMap, new HashMap<>(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(),
			LayoutConstants.TYPE_COLLECTION,
			typeSettingsUnicodeProperties.toString(), false, new HashMap<>(),
			masterLayoutPlid, serviceContext);

		ActionUtil.updateLookAndFeel(
			actionRequest, themeDisplay.getCompanyId(), liveGroupId,
			stagingGroupId, privateLayout, layout.getLayoutId(),
			layout.getTypeSettingsProperties());

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		if (draftLayout != null) {
			layout = _layoutLocalService.updateLayout(
				groupId, privateLayout, layout.getLayoutId(),
				draftLayout.getModifiedDate());
		}

		_updateLayoutPageTemplateData(
			draftLayout, collectionType, collectionPK);

		return layout;
	}

	private String _getCollectionPageElementJSON(
		String className, String classPK) {

		if (Validator.isNull(classPK)) {
			return null;
		}

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(
				Long.valueOf(classPK));

		if (assetListEntry == null) {
			return null;
		}

		Map<String, String> values = HashMapBuilder.put(
			"CLASS_NAME", className
		).put(
			"CLASS_PK", classPK
		).put(
			"COLLECTION_NAME", HtmlUtil.escape(assetListEntry.getTitle())
		).build();

		String collectionPageElementJSON = StringUtil.read(
			AddCollectionLayoutMVCActionCommand.class,
			"collection-page-element.json");

		return StringUtil.replace(collectionPageElementJSON, "${", "}", values);
	}

	private String _getCollectionProviderPageElementJSON(String className) {
		InfoCollectionProvider<?> infoCollectionProvider =
			_infoItemServiceTracker.getInfoItemService(
				InfoCollectionProvider.class, className);

		if (infoCollectionProvider == null) {
			return null;
		}

		Map<String, String> values = HashMapBuilder.put(
			"CLASS_NAME", className
		).put(
			"COLLECTION_PROVIDER_NAME",
			infoCollectionProvider.getLabel(LocaleUtil.getDefault())
		).build();

		String collectionProviderPageElementJSON = StringUtil.read(
			AddCollectionLayoutMVCActionCommand.class,
			"collection-provider-page-element.json");

		return StringUtil.replace(
			collectionProviderPageElementJSON, "${", "}", values);
	}

	private void _updateLayoutPageTemplateData(
			Layout layout, String collectionType, String collectionPK)
		throws Exception {

		String pageElementJSON = StringPool.BLANK;

		if (Objects.equals(
				collectionType,
				InfoListItemSelectorReturnType.class.getName())) {

			pageElementJSON = _getCollectionPageElementJSON(
				collectionType, collectionPK);
		}
		else if (Objects.equals(
					collectionType,
					InfoListProviderItemSelectorReturnType.class.getName())) {

			pageElementJSON = _getCollectionProviderPageElementJSON(
				collectionPK);
		}

		if (Validator.isNotNull(pageElementJSON)) {
			LayoutPageTemplateStructure layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						layout.getGroupId(), layout.getPlid(), true);

			LayoutStructure layoutStructure = LayoutStructure.of(
				layoutPageTemplateStructure.getDefaultSegmentsExperienceData());

			_layoutPageTemplatesImporter.importPageElement(
				layout, layoutStructure, layoutStructure.getMainItemId(),
				pageElementJSON, 0);
		}
	}

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private LayoutExceptionRequestHandler _layoutExceptionRequestHandler;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

}