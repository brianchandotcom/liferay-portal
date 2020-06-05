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
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderTracker;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.handler.LayoutExceptionRequestHandler;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureService;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.CollectionLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

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
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout/add_collection_layout"
	},
	service = MVCActionCommand.class
)
public class AddCollectionLayoutMVCActionCommand
	extends BaseAddLayoutMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
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
		String name = ParamUtil.getString(actionRequest, "name");
		String type = ParamUtil.getString(actionRequest, "type");

		String collectionPK = ParamUtil.getString(
			actionRequest, "collectionPK");
		String collectionType = ParamUtil.getString(
			actionRequest, "collectionType");

		long masterLayoutPlid = ParamUtil.getLong(
			actionRequest, "masterLayoutPlid");

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(), name
		).build();

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Layout.class.getName(), actionRequest);

		UnicodeProperties typeSettingsUnicodeProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		typeSettingsUnicodeProperties.setProperty(
			"collectionType", collectionType);
		typeSettingsUnicodeProperties.setProperty("collectionPK", collectionPK);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			Layout layout = _layoutService.addLayout(
				groupId, privateLayout, parentLayoutId, nameMap,
				new HashMap<>(), new HashMap<>(), new HashMap<>(),
				new HashMap<>(), type, typeSettingsUnicodeProperties.toString(),
				false, masterLayoutPlid, new HashMap<>(), serviceContext);

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			layoutTypePortlet.setLayoutTemplateId(
				themeDisplay.getUserId(),
				PropsValues.DEFAULT_LAYOUT_TEMPLATE_ID);

			layout = _layoutService.updateLayout(
				groupId, privateLayout, layout.getLayoutId(),
				layout.getTypeSettings());

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

			MultiSessionMessages.add(actionRequest, "layoutAdded", layout);

			String redirectURL = getContentRedirectURL(actionRequest, layout);

			jsonObject.put("redirectURL", redirectURL);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			SessionErrors.add(actionRequest, "layoutNameInvalid");

			hideDefaultErrorMessage(actionRequest);

			_layoutExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, portalException);
		}
	}

	private void _addLayoutStructureItems(
		LayoutStructure layoutStructure, String collectionType,
		String collectionPK) {

		LayoutStructureItem mainLayoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		ContainerLayoutStructureItem containerLayoutStructureItem =
			(ContainerLayoutStructureItem)
				layoutStructure.addLayoutStructureItem(
					LayoutDataItemTypeConstants.TYPE_CONTAINER,
					mainLayoutStructureItem.getItemId(), 0);

		CollectionLayoutStructureItem collectionLayoutStructureItem =
			(CollectionLayoutStructureItem)
				layoutStructure.addLayoutStructureItem(
					LayoutDataItemTypeConstants.TYPE_COLLECTION,
					containerLayoutStructureItem.getItemId(), 0);

		if (Objects.equals(
				collectionType,
				"com.liferay.item.selector.criteria." +
					"InfoListItemSelectorReturnType")) {

			collectionLayoutStructureItem.setCollectionJSONObject(
				_getCollectionJSONObject(collectionPK));
		}
		else if (Objects.equals(
					collectionType,
					"com.liferay.info.list.provider.item.selector.criterion." +
						"InfoListProviderItemSelectorReturnType")) {

			collectionLayoutStructureItem.setCollectionJSONObject(
				_getCollectionProviderJSONObject(collectionPK));
		}

		layoutStructure.addLayoutStructureItem(
			LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM,
			collectionLayoutStructureItem.getItemId(), 0);
	}

	private JSONObject _getCollectionJSONObject(String collectionPK) {
		if (Validator.isNull(collectionPK)) {
			return null;
		}

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(
				Long.valueOf(collectionPK));

		if (assetListEntry == null) {
			return null;
		}

		return JSONUtil.put(
			"classNameId",
			_portal.getClassNameId(AssetListEntry.class.getName())
		).put(
			"classPK", collectionPK
		).put(
			"itemSubtype", assetListEntry.getAssetEntrySubtype()
		).put(
			"itemType", assetListEntry.getAssetEntryType()
		).put(
			"title", assetListEntry.getTitle()
		).put(
			"type", InfoListItemSelectorReturnType.class.getName()
		);
	}

	private JSONObject _getCollectionProviderJSONObject(String collectionPK) {
		InfoListProvider<?> infoListProvider =
			_infoListProviderTracker.getInfoListProvider(collectionPK);

		if (infoListProvider == null) {
			return null;
		}

		return JSONUtil.put(
			"itemType", GenericUtil.getGenericClassName(infoListProvider)
		).put(
			"key", collectionPK
		).put(
			"title", infoListProvider.getLabel(LocaleUtil.getDefault())
		).put(
			"type", InfoListProviderItemSelectorReturnType.class.getName()
		);
	}

	private void _updateLayoutPageTemplateData(
			Layout layout, String collectionType, String collectionPK)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(),
					_portal.getClassNameId(Layout.class.getName()),
					layout.getPlid(), true);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(0));

		_addLayoutStructureItems(layoutStructure, collectionType, collectionPK);

		JSONObject dataJSONObject = layoutStructure.toJSONObject();

		_layoutPageTemplateStructureService.updateLayoutPageTemplateStructure(
			layout.getGroupId(), _portal.getClassNameId(Layout.class.getName()),
			layout.getPlid(), 0, dataJSONObject.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddCollectionLayoutMVCActionCommand.class);

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference
	private InfoListProviderTracker _infoListProviderTracker;

	@Reference
	private LayoutExceptionRequestHandler _layoutExceptionRequestHandler;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutPageTemplateStructureService
		_layoutPageTemplateStructureService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

}