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

package com.liferay.commerce.discount.web.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.commerce.discount.constants.CommerceDiscountPortletKeys;
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.exception.NoSuchDiscountRelException;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ListUtil;
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
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceDiscountPortletKeys.COMMERCE_DISCOUNT,
		"mvc.command.name=editCommerceDiscountRel"
	},
	service = MVCActionCommand.class
)
public class EditCommerceDiscountRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommerceDiscountRels(ActionRequest actionRequest)
		throws Exception {

		long commerceDiscountId = ParamUtil.getLong(
			actionRequest, "commerceDiscountId");
		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		List<Long> classPKs = new ArrayList<>();

		if (classPK > 0) {
			classPKs.add(classPK);
		}
		else {
			classPKs = ListUtil.toList(
				StringUtil.split(
					ParamUtil.getString(actionRequest, "classPKs"), 0L));
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceDiscountRel.class.getName(), actionRequest);

		if (className.equals(CPDefinition.class.getName()) ||
			className.equals(CommercePricingClass.class.getName())) {

			for (long addClassPK : classPKs) {
				_commerceDiscountRelService.addCommerceDiscountRel(
					commerceDiscountId, className, addClassPK, serviceContext);
			}
		}

		if (className.equals(AssetCategory.class.getName())) {
			Group companyGroup = _groupLocalService.getCompanyGroup(
				_portal.getCompanyId(actionRequest));

			List<AssetVocabulary> assetVocabularies =
				_assetVocabularyLocalService.getGroupVocabularies(
					companyGroup.getGroupId(), false);

			for (AssetVocabulary assetVocabulary : assetVocabularies) {
				long classPKParam = ParamUtil.getLong(
					actionRequest,
					"classPKs_" + assetVocabulary.getVocabularyId());

				if (classPKParam > 0) {
					classPKs.add(classPKParam);
				}
			}

			_updateAssetCategoryCommerceDiscountRels(
				commerceDiscountId, className,
				ListUtil.toLongArray(classPKs, Long::longValue),
				serviceContext);
		}
	}

	protected void deleteCommerceDiscountRels(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceDiscountRelIds = null;

		long commerceDiscountRelId = ParamUtil.getLong(
			actionRequest, "commerceDiscountRelId");

		if (commerceDiscountRelId > 0) {
			deleteCommerceDiscountRelIds = new long[] {commerceDiscountRelId};
		}
		else {
			deleteCommerceDiscountRelIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceDiscountRelIds"),
				0L);
		}

		for (long deleteCommerceDiscountRelId : deleteCommerceDiscountRelIds) {
			_commerceDiscountRelService.deleteCommerceDiscountRel(
				deleteCommerceDiscountRelId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				addCommerceDiscountRels(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceDiscountRels(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchDiscountException ||
				e instanceof NoSuchDiscountRelException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	private void _updateAssetCategoryCommerceDiscountRels(
			long commerceDiscountId, String className, long[] addClassPKs,
			ServiceContext serviceContext)
		throws PortalException {

		List<CommerceDiscountRel> commerceDiscountRels =
			_commerceDiscountRelService.getCommerceDiscountRels(
				commerceDiscountId, className);

		for (CommerceDiscountRel commerceDiscountRel : commerceDiscountRels) {
			if (ArrayUtil.contains(
					addClassPKs, commerceDiscountRel.getClassPK())) {

				continue;
			}

			_commerceDiscountRelService.deleteCommerceDiscountRel(
				commerceDiscountRel.getCommerceDiscountRelId());
		}

		long[] assetCategoryIds = _commerceDiscountRelService.getClassPKs(
			commerceDiscountId, className);

		for (long addClassPK : addClassPKs) {
			if (ArrayUtil.contains(assetCategoryIds, addClassPK)) {
				continue;
			}

			if (addClassPK > 0) {
				_commerceDiscountRelService.addCommerceDiscountRel(
					commerceDiscountId, className, addClassPK, serviceContext);
			}
		}
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private CommerceDiscountRelService _commerceDiscountRelService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}