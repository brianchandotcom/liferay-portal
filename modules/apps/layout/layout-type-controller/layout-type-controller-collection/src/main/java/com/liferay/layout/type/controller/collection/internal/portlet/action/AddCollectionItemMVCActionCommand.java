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

package com.liferay.layout.type.controller.collection.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.product.navigation.control.menu.constants.ProductNavigationControlMenuPortletKeys;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.context.RequestContextMapper;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = {
		"javax.portlet.name=" + ProductNavigationControlMenuPortletKeys.PRODUCT_NAVIGATION_CONTROL_MENU,
		"mvc.command.name=/control_menu/add_collection_item"
	},
	service = MVCActionCommand.class
)
public class AddCollectionItemMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long assetListEntryId = ParamUtil.getLong(
			actionRequest, "assetListEntryId");

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		AssetListEntry assetListEntry =
			_assetListEntryService.getAssetListEntry(assetListEntryId);

		hideDefaultSuccessMessage(actionRequest);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		if ((assetListEntry.getType() ==
				AssetListEntryTypeConstants.TYPE_MANUAL) &&
			Validator.isNotNull(className) && (classPK > 0)) {

			AssetEntry assetEntry = _assetEntryService.getEntry(
				className, classPK);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			long[] segmentsEntryIds = GetterUtil.getLongValues(
				_getSegmentsEntryIds(httpServletRequest),
				new long[] {SegmentsEntryConstants.ID_DEFAULT});

			for (long segmentsEntryId : segmentsEntryIds) {
				_assetListEntryService.addAssetEntrySelection(
					assetListEntryId, assetEntry.getEntryId(), segmentsEntryId,
					serviceContext);
			}
		}

		SessionMessages.add(httpServletRequest, "collectionItemAdded");

		sendRedirect(actionRequest, actionResponse);
	}

	private long[] _getSegmentsEntryIds(HttpServletRequest httpServletRequest)
		throws Exception {

		return _segmentsEntryRetriever.getSegmentsEntryIds(
			_portal.getScopeGroupId(httpServletRequest),
			_portal.getUserId(httpServletRequest),
			_requestContextMapper.map(httpServletRequest));
	}

	@Reference
	private AssetEntryService _assetEntryService;

	@Reference
	private AssetListEntryService _assetListEntryService;

	@Reference
	private Portal _portal;

	@Reference
	private RequestContextMapper _requestContextMapper;

	@Reference
	private SegmentsEntryRetriever _segmentsEntryRetriever;

}