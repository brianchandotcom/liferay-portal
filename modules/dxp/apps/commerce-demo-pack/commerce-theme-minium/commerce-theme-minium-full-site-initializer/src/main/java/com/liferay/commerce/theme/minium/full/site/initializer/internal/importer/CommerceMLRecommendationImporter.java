/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.theme.minium.full.site.initializer.internal.importer;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.machine.learning.recommendation.model.ProductContentCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.model.ProductInteractionCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.model.UserCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.service.ProductContentCommerceMLRecommendationService;
import com.liferay.commerce.machine.learning.recommendation.service.ProductInteractionCommerceMLRecommendationService;
import com.liferay.commerce.machine.learning.recommendation.service.UserCommerceMLRecommendationService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(enabled = false, service = CommerceMLRecommendationImporter.class)
public class CommerceMLRecommendationImporter {

	public void importCommerceMLRecommendations(
			JSONArray jsonArray, String externalReferenceCodePrefix,
			long scopeGroupId, long userId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		User user = _userLocalService.getUser(userId);

		serviceContext.setCompanyId(user.getCompanyId());

		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String type = jsonObject.getString("type");

			try {
				if (type.equals("product-content-recommendation")) {
					_importProductContentRecommendation(
						jsonObject, externalReferenceCodePrefix,
						serviceContext);
				}
				else if (type.equals("product-interaction-recommendation")) {
					_importProductInteractionRecommendation(
						jsonObject, externalReferenceCodePrefix,
						serviceContext);
				}
				else {
					_importUserRecommendation(
						jsonObject, externalReferenceCodePrefix,
						serviceContext);
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						String.format(
							"Unable to import recommendation type: %s", type),
						exception);
				}
			}
		}
	}

	private void _importProductContentRecommendation(
			JSONObject jsonObject, String externalReferenceCodePrefix,
			ServiceContext serviceContext)
		throws PortalException {

		String productExternalReferenceCode =
			externalReferenceCodePrefix +
				jsonObject.getString("productExternalReferenceCode");

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					serviceContext.getCompanyId(),
					productExternalReferenceCode);

		if (cpDefinition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Unable to fetch commerce product definition with " +
							"external reference code: %s",
						productExternalReferenceCode));
			}

			return;
		}

		String recommendedProductExternalReferenceCode =
			externalReferenceCodePrefix +
				jsonObject.getString("recommendedProductExternalReferenceCode");

		CPDefinition recommendedCpDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					serviceContext.getCompanyId(),
					recommendedProductExternalReferenceCode);

		if (recommendedCpDefinition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Unable to fetch commerce product definition with " +
							"external reference code: %s",
						recommendedProductExternalReferenceCode));
			}

			return;
		}

		ProductContentCommerceMLRecommendation
			productContentCommerceMLRecommendation =
				_productContentCommerceMLRecommendationService.create();

		productContentCommerceMLRecommendation.setCompanyId(
			serviceContext.getCompanyId());
		productContentCommerceMLRecommendation.setCreateDate(new Date());
		productContentCommerceMLRecommendation.setEntryClassPK(
			cpDefinition.getCPDefinitionId());
		productContentCommerceMLRecommendation.setJobId(
			"commerce-ml-recommendation-importer");
		productContentCommerceMLRecommendation.setRank(
			jsonObject.getInt("rank"));
		productContentCommerceMLRecommendation.setRecommendedEntryClassPK(
			recommendedCpDefinition.getCPDefinitionId());
		productContentCommerceMLRecommendation.setScore(
			(float)jsonObject.getDouble("score"));

		_productContentCommerceMLRecommendationService.
			addProductContentCommerceMLRecommendation(
				productContentCommerceMLRecommendation);
	}

	private void _importProductInteractionRecommendation(
			JSONObject jsonObject, String externalReferenceCodePrefix,
			ServiceContext serviceContext)
		throws PortalException {

		String productExternalReferenceCode =
			externalReferenceCodePrefix +
				jsonObject.getString("productExternalReferenceCode");

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					serviceContext.getCompanyId(),
					productExternalReferenceCode);

		if (cpDefinition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Unable to fetch CPDefinition with external " +
							"reference code: %s",
						productExternalReferenceCode));
			}

			return;
		}

		String recommendedProductExternalReferenceCode =
			externalReferenceCodePrefix +
				jsonObject.getString("recommendedProductExternalReferenceCode");

		CPDefinition recommendedCpDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					serviceContext.getCompanyId(),
					recommendedProductExternalReferenceCode);

		if (recommendedCpDefinition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Unable to fetch commerce product definition with " +
							"external reference code: %s",
						recommendedProductExternalReferenceCode));
			}

			return;
		}

		ProductInteractionCommerceMLRecommendation
			productInteractionCommerceMLRecommendation =
				_productInteractionCommerceMLRecommendationService.create();

		productInteractionCommerceMLRecommendation.setCompanyId(
			serviceContext.getCompanyId());
		productInteractionCommerceMLRecommendation.setCreateDate(new Date());
		productInteractionCommerceMLRecommendation.setEntryClassPK(
			cpDefinition.getCPDefinitionId());
		productInteractionCommerceMLRecommendation.setJobId(
			"commerce-ml-recommendation-importer");
		productInteractionCommerceMLRecommendation.setRank(
			jsonObject.getInt("rank"));
		productInteractionCommerceMLRecommendation.setRecommendedEntryClassPK(
			recommendedCpDefinition.getCPDefinitionId());
		productInteractionCommerceMLRecommendation.setScore(
			(float)jsonObject.getDouble("score"));

		_productInteractionCommerceMLRecommendationService.
			addProductInteractionCommerceMLRecommendation(
				productInteractionCommerceMLRecommendation);
	}

	private void _importUserRecommendation(
			JSONObject jsonObject, String externalReferenceCodePrefix,
			ServiceContext serviceContext)
		throws PortalException {

		String accountExternalReferenceCode = jsonObject.getString(
			"accountExternalReferenceCode");

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.fetchByExternalReferenceCode(
				serviceContext.getCompanyId(), accountExternalReferenceCode);

		if (commerceAccount == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Unable to fetch account with external reference " +
							"code: %s",
						accountExternalReferenceCode));
			}

			return;
		}

		String recommendedProductExternalReferenceCode =
			externalReferenceCodePrefix +
				jsonObject.getString("recommendedProductExternalReferenceCode");

		CPDefinition recommendedCpDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					serviceContext.getCompanyId(),
					recommendedProductExternalReferenceCode);

		if (recommendedCpDefinition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Unable to fetch commerce product definition with " +
							"external reference code: %s",
						recommendedProductExternalReferenceCode));
			}

			return;
		}

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			CPDefinition.class.getName(),
			recommendedCpDefinition.getCPDefinitionId());

		long[] categoryIds = assetEntry.getCategoryIds();

		UserCommerceMLRecommendation userCommerceMLRecommendation =
			_userCommerceMLRecommendationService.create();

		userCommerceMLRecommendation.setAssetCategoryIds(categoryIds);
		userCommerceMLRecommendation.setCompanyId(
			serviceContext.getCompanyId());
		userCommerceMLRecommendation.setCreateDate(new Date());
		userCommerceMLRecommendation.setEntryClassPK(
			commerceAccount.getCommerceAccountId());
		userCommerceMLRecommendation.setJobId(
			"commerce-ml-recommendation-importer");
		userCommerceMLRecommendation.setRecommendedEntryClassPK(
			recommendedCpDefinition.getCPDefinitionId());
		userCommerceMLRecommendation.setScore(
			(float)jsonObject.getDouble("score"));

		_userCommerceMLRecommendationService.addUserCommerceMLRecommendation(
			userCommerceMLRecommendation);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMLRecommendationImporter.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private ProductContentCommerceMLRecommendationService
		_productContentCommerceMLRecommendationService;

	@Reference
	private ProductInteractionCommerceMLRecommendationService
		_productInteractionCommerceMLRecommendationService;

	@Reference
	private UserCommerceMLRecommendationService
		_userCommerceMLRecommendationService;

	@Reference
	private UserLocalService _userLocalService;

}