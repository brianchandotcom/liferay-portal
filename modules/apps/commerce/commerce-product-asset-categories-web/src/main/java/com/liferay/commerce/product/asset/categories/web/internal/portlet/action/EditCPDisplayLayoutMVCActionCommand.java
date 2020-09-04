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

package com.liferay.commerce.product.asset.categories.web.internal.portlet.action;

import com.liferay.asset.kernel.exception.NoSuchCategoryException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.CPDisplayLayoutEntryException;
import com.liferay.commerce.product.exception.CPDisplayLayoutLayoutUuidException;
import com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDisplayLayoutService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_CHANNELS,
		"mvc.command.name=editCategoryDisplayLayout"
	},
	service = MVCActionCommand.class
)
public class EditCPDisplayLayoutMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCPDisplayLayouts(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPDisplayLayoutIds = null;

		long cpDisplayLayoutId = ParamUtil.getLong(
			actionRequest, "cpDisplayLayoutId");

		if (cpDisplayLayoutId > 0) {
			deleteCPDisplayLayoutIds = new long[] {cpDisplayLayoutId};
		}
		else {
			deleteCPDisplayLayoutIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPDisplayLayoutIds"),
				0L);
		}

		for (long deleteCPDisplayLayoutId : deleteCPDisplayLayoutIds) {
			_cpDisplayLayoutService.deleteCPDisplayLayout(
				deleteCPDisplayLayoutId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCPDisplayLayout(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCPDisplayLayouts(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchCategoryException ||
				exception instanceof NoSuchCPDisplayLayoutException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof CPDisplayLayoutEntryException ||
					 exception instanceof CPDisplayLayoutLayoutUuidException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editCategoryDisplayLayout");
			}
		}
	}

	protected void updateCPDisplayLayout(ActionRequest actionRequest)
		throws PortalException {

		long cpDisplayLayoutId = ParamUtil.getLong(
			actionRequest, "cpDisplayLayoutId");

		List<Long> classPKs = new ArrayList<>();

		Group companyGroup = _groupLocalService.getCompanyGroup(
			_portal.getCompanyId(actionRequest));

		List<AssetVocabulary> assetVocabularies =
			_assetVocabularyLocalService.getGroupVocabularies(
				companyGroup.getGroupId(), false);

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			long classPKParam = ParamUtil.getLong(
				actionRequest, "classPK_" + assetVocabulary.getVocabularyId());

			if (classPKParam > 0) {
				classPKs.add(classPKParam);
			}
		}

		String layoutUuid = ParamUtil.getString(actionRequest, "layoutUuid");

		if (cpDisplayLayoutId > 0) {
			_cpDisplayLayoutService.updateCPDisplayLayout(
				cpDisplayLayoutId, layoutUuid);
		}
		else {
			long commerceChannelId = ParamUtil.getLong(
				actionRequest, "commerceChannelId");

			CommerceChannel commerceChannel =
				_commerceChannelService.getCommerceChannel(commerceChannelId);

			if (classPKs.isEmpty()) {
				throw new CPDisplayLayoutEntryException();
			}

			for (long classPK : classPKs) {
				_cpDisplayLayoutService.addCPDisplayLayout(
					_portal.getUserId(actionRequest),
					commerceChannel.getSiteGroupId(), AssetCategory.class,
					classPK, layoutUuid);
			}
		}
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CPDisplayLayoutService _cpDisplayLayoutService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}