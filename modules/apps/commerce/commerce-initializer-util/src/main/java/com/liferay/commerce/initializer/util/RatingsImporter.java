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

package com.liferay.commerce.initializer.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserIdMapper;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserIdMapperLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.model.RatingsStats;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import java.io.File;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = RatingsImporter.class)
public class RatingsImporter {

	public void importRatings(File ratingsFile, long scopeGroupId, long userId)
		throws Exception {

		ServiceContext serviceContext = getServiceContext(scopeGroupId, userId);

		MappingJsonFactory mappingJsonFactory = new MappingJsonFactory();

		JsonParser jsonFactoryParser = mappingJsonFactory.createParser(
			ratingsFile);

		JsonToken jsonToken = jsonFactoryParser.nextToken();

		if (jsonToken != JsonToken.START_ARRAY) {
			throw new Exception("JSON Array Expected");
		}

		while (jsonFactoryParser.nextToken() != JsonToken.END_ARRAY) {
			TreeNode treeNode = jsonFactoryParser.readValueAsTree();

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				treeNode.toString());

			if (_log.isDebugEnabled()) {
				_log.debug(jsonObject);
			}

			_importRating(jsonObject, serviceContext);
		}

		jsonFactoryParser.close();
	}

	protected ServiceContext getServiceContext(long scopeGroupId, long userId)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);
		serviceContext.setCompanyId(user.getCompanyId());

		return serviceContext;
	}

	private void _importRating(
			JSONObject jsonObject, ServiceContext serviceContext)
		throws Exception {

		// Prepare data

		String externalUserId = jsonObject.getString("externalUserId");

		String externalSystemType = jsonObject.getString("externalSystemType");

		// Retrieve Liferay User ID

		UserIdMapper userIdMapper = null;

		try {
			userIdMapper =
				_userIdMapperLocalService.getUserIdMapperByExternalUserId(
					externalSystemType, externalUserId);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Can not find an user Id mapping for: " + externalUserId);
			}
		}

		if (userIdMapper == null) {
			return;
		}

		String externalReferenceId = jsonObject.getString("externalProductId");

		// Retrieve CPDefinition

		CProduct cProduct = _cProductLocalService.fetchCProductByReferenceCode(
			serviceContext.getCompanyId(), externalReferenceId);

		if (cProduct == null) {
			return;
		}

		CPDefinition cpDefinition = _cpDefinitionLocalService.fetchCPDefinition(
			cProduct.getPublishedCPDefinitionId());

		if (cpDefinition == null) {
			return;
		}

		long userId = userIdMapper.getUserId();

		double rating = jsonObject.getDouble("rating");

		long timestamp = jsonObject.getLong("timestamp");

		Date createDate = new Date(timestamp * 1000);

		long classNameId = _classNameLocalService.getClassNameId(
			CPDefinition.class);

		// Create Rating

		long ratingEntryId = _counterLocalService.increment();

		RatingsEntry ratingsEntry =
			_ratingsEntryLocalService.createRatingsEntry(ratingEntryId);

		ratingsEntry.setCompanyId(serviceContext.getCompanyId());
		ratingsEntry.setUserId(userId);
		ratingsEntry.setUserName("");
		ratingsEntry.setClassNameId(classNameId);
		ratingsEntry.setClassPK(cpDefinition.getCPDefinitionId());
		ratingsEntry.setScore(rating);

		// We create ratings with exact createDate

		ratingsEntry.setCreateDate(createDate);
		ratingsEntry.setModifiedDate(new Date());

		_ratingsEntryLocalService.updateRatingsEntry(ratingsEntry);

		RatingsStats ratingsStats = _ratingsStatsLocalService.fetchStats(
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		if (ratingsStats == null) {
			ratingsStats = _ratingsStatsLocalService.addStats(
				classNameId, cpDefinition.getCPDefinitionId());
		}

		ratingsStats.setTotalEntries(ratingsStats.getTotalEntries() + 1);
		ratingsStats.setTotalScore(ratingsStats.getTotalScore() + rating);
		ratingsStats.setAverageScore(
			ratingsStats.getTotalScore() / ratingsStats.getTotalEntries());

		_ratingsStatsLocalService.updateRatingsStats(ratingsStats);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RatingsImporter.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private RatingsEntryLocalService _ratingsEntryLocalService;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private UserIdMapperLocalService _userIdMapperLocalService;

	@Reference
	private UserLocalService _userLocalService;

}