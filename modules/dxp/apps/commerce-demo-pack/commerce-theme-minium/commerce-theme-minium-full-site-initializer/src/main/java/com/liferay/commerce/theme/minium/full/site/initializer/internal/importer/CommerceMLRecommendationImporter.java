/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.theme.minium.full.site.initializer.internal.importer;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.commerce.machine.learning.recommendation.ProductContentCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.ProductContentCommerceMLRecommendationManager;
import com.liferay.commerce.machine.learning.recommendation.ProductInteractionCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.ProductInteractionCommerceMLRecommendationManager;
import com.liferay.commerce.machine.learning.recommendation.UserCommerceMLRecommendation;
import com.liferay.commerce.machine.learning.recommendation.UserCommerceMLRecommendationManager;
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
@Component(service = CommerceMLRecommendationImporter.class)
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
					_importProductContentCommerceMLRecommendation(
						jsonObject, externalReferenceCodePrefix,
						serviceContext);
				}
				else if (type.equals("product-interaction-recommendation")) {
					_importProductInteractionCommerceMLRecommendation(
						jsonObject, externalReferenceCodePrefix,
						serviceContext);
				}
				else {
					_importUserCommerceMLRecommendation(
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

	private void _importProductContentCommerceMLRecommendation(
			JSONObject jsonObject, String externalReferenceCodePrefix,
			ServiceContext serviceContext)
		throws PortalException {

		String productExternalReferenceCode =
			externalReferenceCodePrefix +
				jsonObject.getString("productExternalReferenceCode");

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					productExternalReferenceCode,
					serviceContext.getCompanyId());

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

		CPDefinition recommendedCPDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					recommendedProductExternalReferenceCode,
					serviceContext.getCompanyId());

		if (recommendedCPDefinition == null) {
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
				_productContentCommerceMLRecommendationManager.create();

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
			recommendedCPDefinition.getCPDefinitionId());
		productContentCommerceMLRecommendation.setScore(
			(float)jsonObject.getDouble("score"));

		_productContentCommerceMLRecommendationManager.
			addProductContentCommerceMLRecommendation(
				productContentCommerceMLRecommendation);
	}

	private void _importProductInteractionCommerceMLRecommendation(
			JSONObject jsonObject, String externalReferenceCodePrefix,
			ServiceContext serviceContext)
		throws PortalException {

		String productExternalReferenceCode =
			externalReferenceCodePrefix +
				jsonObject.getString("productExternalReferenceCode");

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					productExternalReferenceCode,
					serviceContext.getCompanyId());

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

		CPDefinition recommendedCPDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					recommendedProductExternalReferenceCode,
					serviceContext.getCompanyId());

		if (recommendedCPDefinition == null) {
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
				_productInteractionCommerceMLRecommendationManager.create();

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
			recommendedCPDefinition.getCPDefinitionId());
		productInteractionCommerceMLRecommendation.setScore(
			(float)jsonObject.getDouble("score"));

		_productInteractionCommerceMLRecommendationManager.
			addProductInteractionCommerceMLRecommendation(
				productInteractionCommerceMLRecommendation);
	}

	private void _importUserCommerceMLRecommendation(
			JSONObject jsonObject, String externalReferenceCodePrefix,
			ServiceContext serviceContext)
		throws PortalException {

		String accountExternalReferenceCode = jsonObject.getString(
			"accountExternalReferenceCode");

		AccountEntry accountEntry =
			_accountEntryLocalService.fetchAccountEntryByExternalReferenceCode(
				accountExternalReferenceCode, serviceContext.getCompanyId());

		if (accountEntry == null) {
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

		CPDefinition recommendedCPDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					recommendedProductExternalReferenceCode,
					serviceContext.getCompanyId());

		if (recommendedCPDefinition == null) {
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
			recommendedCPDefinition.getCPDefinitionId());

		long[] categoryIds = assetEntry.getCategoryIds();

		UserCommerceMLRecommendation userCommerceMLRecommendation =
			_userCommerceMLRecommendationManager.create();

		userCommerceMLRecommendation.setAssetCategoryIds(categoryIds);
		userCommerceMLRecommendation.setCompanyId(
			serviceContext.getCompanyId());
		userCommerceMLRecommendation.setCreateDate(new Date());
		userCommerceMLRecommendation.setEntryClassPK(
			accountEntry.getAccountEntryId());
		userCommerceMLRecommendation.setJobId(
			"commerce-ml-recommendation-importer");
		userCommerceMLRecommendation.setRecommendedEntryClassPK(
			recommendedCPDefinition.getCPDefinitionId());
		userCommerceMLRecommendation.setScore(
			(float)jsonObject.getDouble("score"));

		_userCommerceMLRecommendationManager.addUserCommerceMLRecommendation(
			userCommerceMLRecommendation);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMLRecommendationImporter.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private ProductContentCommerceMLRecommendationManager
		_productContentCommerceMLRecommendationManager;

	@Reference
	private ProductInteractionCommerceMLRecommendationManager
		_productInteractionCommerceMLRecommendationManager;

	@Reference
	private UserCommerceMLRecommendationManager
		_userCommerceMLRecommendationManager;

	@Reference
	private UserLocalService _userLocalService;

}