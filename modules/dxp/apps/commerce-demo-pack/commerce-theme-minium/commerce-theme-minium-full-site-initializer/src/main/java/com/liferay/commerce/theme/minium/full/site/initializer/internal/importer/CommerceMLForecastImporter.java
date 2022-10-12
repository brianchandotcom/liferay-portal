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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountLocalService;
import com.liferay.commerce.machine.learning.forecast.AssetCategoryCommerceMLForecast;
import com.liferay.commerce.machine.learning.forecast.AssetCategoryCommerceMLForecastManager;
import com.liferay.commerce.machine.learning.forecast.CommerceAccountCommerceMLForecast;
import com.liferay.commerce.machine.learning.forecast.CommerceAccountCommerceMLForecastManager;
import com.liferay.commerce.machine.learning.forecast.CommerceMLForecast;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.GetterUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import java.util.Calendar;
import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = CommerceMLForecastImporter.class)
public class CommerceMLForecastImporter {

	public void importCommerceMLForecasts(
			JSONArray jsonArray, long scopeGroupId, long userId)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String scope = jsonObject.getString("scope");

			if (scope.equals("asset-category")) {
				_importAssetCategoryCommerceMLForecast(
					jsonObject, serviceContext);
			}
			else {
				_importCommerceAccountCommerceMLForecast(
					jsonObject, serviceContext);
			}
		}
	}

	private Date _getCurrentDate(String period) {
		LocalDateTime localDateTime = LocalDateTime.now(_DEFAULT_ZONE_OFFSET);

		localDateTime = localDateTime.truncatedTo(ChronoUnit.DAYS);

		if (period.equals("month")) {
			localDateTime = localDateTime.with(ChronoField.DAY_OF_MONTH, 1);
		}
		else {
			localDateTime = localDateTime.with(ChronoField.DAY_OF_WEEK, 1);
		}

		ZonedDateTime zonedDateTime = localDateTime.atZone(
			_DEFAULT_ZONE_OFFSET);

		return Date.from(zonedDateTime.toInstant());
	}

	private Date _getTimestamp(Date currentDate, String period, int step) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(currentDate);

		if (period.equals("month")) {
			calendar.add(Calendar.MONTH, step);
		}
		else {
			calendar.add(Calendar.WEEK_OF_YEAR, step);
		}

		return calendar.getTime();
	}

	private void _importAssetCategoryCommerceMLForecast(
			JSONObject jsonObject, ServiceContext serviceContext)
		throws PortalException {

		AssetCategoryCommerceMLForecast assetCategoryCommerceMLForecast =
			_setFields(
				serviceContext.getCompanyId(),
				_getCurrentDate(jsonObject.getString("period")),
				_assetCategoryCommerceMLForecastManager.create(), jsonObject);

		Company company = _companyLocalService.getCompany(
			serviceContext.getCompanyId());

		Group group = serviceContext.getScopeGroup();

		String vocabularyName = group.getName(serviceContext.getLocale());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.fetchGroupVocabulary(
				company.getGroupId(), vocabularyName);

		if (assetVocabulary == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No asset vocabulary with name: " + vocabularyName);
			}

			return;
		}

		String category = jsonObject.getString("category");

		AssetCategory assetCategory = _assetCategoryLocalService.fetchCategory(
			company.getGroupId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, category,
			assetVocabulary.getVocabularyId());

		if (assetCategory == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No asset category with name: " + category);
			}

			return;
		}

		assetCategoryCommerceMLForecast.setAssetCategoryId(
			assetCategory.getCategoryId());

		String accountName = jsonObject.getString("account");

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.fetchByExternalReferenceCode(
				serviceContext.getCompanyId(),
				_friendlyURLNormalizer.normalize(accountName));

		if (commerceAccount == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No commerce account with name: " + accountName);
			}

			return;
		}

		assetCategoryCommerceMLForecast.setCommerceAccountId(
			commerceAccount.getCommerceAccountId());

		_assetCategoryCommerceMLForecastManager.
			addAssetCategoryCommerceMLForecast(assetCategoryCommerceMLForecast);
	}

	private void _importCommerceAccountCommerceMLForecast(
			JSONObject jsonObject, ServiceContext serviceContext)
		throws PortalException {

		CommerceAccountCommerceMLForecast commerceAccountCommerceMLForecast =
			_setFields(
				serviceContext.getCompanyId(),
				_getCurrentDate(jsonObject.getString("period")),
				_commerceAccountCommerceMLForecastManager.create(), jsonObject);

		String accountName = jsonObject.getString("account");

		CommerceAccount commerceAccount =
			_commerceAccountLocalService.fetchByExternalReferenceCode(
				serviceContext.getCompanyId(),
				_friendlyURLNormalizer.normalize(accountName));

		if (commerceAccount == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No commerce account with name: " + accountName);
			}

			return;
		}

		commerceAccountCommerceMLForecast.setCommerceAccountId(
			commerceAccount.getCommerceAccountId());

		_commerceAccountCommerceMLForecastManager.
			addCommerceAccountCommerceMLForecast(
				commerceAccountCommerceMLForecast);
	}

	private <T extends CommerceMLForecast> T _setFields(
		long companyId, Date currentDate, T commerceMLForecast,
		JSONObject jsonObject) {

		commerceMLForecast.setActual(
			GetterUtil.getFloat(jsonObject.get("actual"), Float.MIN_VALUE));
		commerceMLForecast.setCompanyId(companyId);
		commerceMLForecast.setForecast(
			GetterUtil.getFloat(jsonObject.get("forecast"), Float.MIN_VALUE));
		commerceMLForecast.setForecastLowerBound(
			GetterUtil.getFloat(
				jsonObject.get("forecastLowerBound"), Float.MIN_VALUE));
		commerceMLForecast.setForecastUpperBound(
			GetterUtil.getFloat(
				jsonObject.get("forecastUpperBound"), Float.MIN_VALUE));
		commerceMLForecast.setPeriod(jsonObject.getString("period"));
		commerceMLForecast.setScope(jsonObject.getString("scope"));
		commerceMLForecast.setTarget(jsonObject.getString("target"));
		commerceMLForecast.setTimestamp(
			_getTimestamp(
				currentDate, jsonObject.getString("period"),
				jsonObject.getInt("timestamp")));

		return commerceMLForecast;
	}

	private static final ZoneId _DEFAULT_ZONE_OFFSET =
		ZoneOffset.systemDefault();

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMLForecastImporter.class);

	@Reference
	private AssetCategoryCommerceMLForecastManager
		_assetCategoryCommerceMLForecastManager;

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private CommerceAccountCommerceMLForecastManager
		_commerceAccountCommerceMLForecastManager;

	@Reference
	private CommerceAccountLocalService _commerceAccountLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private FriendlyURLNormalizer _friendlyURLNormalizer;

	@Reference
	private UserLocalService _userLocalService;

}