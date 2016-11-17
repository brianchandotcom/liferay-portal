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

package com.liferay.screens.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.screens.service.base.ScreensRatingsEntryServiceBaseImpl;

import java.util.List;

/**
 * @author Alejandro Hernández Malillos
 */
@ProviderType
public class ScreensRatingsEntryServiceImpl
	extends ScreensRatingsEntryServiceBaseImpl {

	@Override
	public JSONObject deleteRatingsEntry(
			long classPK, String className, int ratingsLength)
		throws PortalException {

		ratingsEntryLocalService.deleteEntry(getUserId(), className, classPK);

		return getRatingsEntries(classPK, className, ratingsLength);
	}

	@Override
	public JSONObject getRatingsEntries(long assetEntryId, int ratingsLength)
		throws PortalException {

		AssetEntry assetEntry = assetEntryLocalService.fetchEntry(assetEntryId);

		return getRatingsEntries(
			assetEntry.getClassPK(), assetEntry.getClassName(), ratingsLength);
	}

	@Override
	public JSONObject getRatingsEntries(
			long classPK, String className, int ratingsLength)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		List<RatingsEntry> ratingsEntries = ratingsEntryLocalService.getEntries(
			className, classPK);

		int[] ratings = new int[ratingsLength];
		double totalScore = 0;
		double userScore = -1;

		for (RatingsEntry ratingsEntry : ratingsEntries) {
			int index = (int)(ratingsEntry.getScore() * ratingsLength);

			if (index == ratingsLength) {
				index--;
			}

			ratings[index]++;
			totalScore += ratingsEntry.getScore();

			if (ratingsEntry.getUserId() == getUserId()) {
				userScore = ratingsEntry.getScore();
			}
		}

		if (!ratingsEntries.isEmpty()) {
			jsonObject.put("average", totalScore / ratingsEntries.size());
		}
		else {
			jsonObject.put("average", 0);
		}

		jsonObject.put("className", className);
		jsonObject.put("classPK", classPK);
		jsonObject.put("ratings", ratings);
		jsonObject.put("totalCount", ratingsEntries.size());
		jsonObject.put("totalScore", totalScore);
		jsonObject.put("userScore", userScore);

		return jsonObject;
	}

	@Override
	public JSONObject updateRatingsEntry(
			long classPK, String className, double score, int ratingsLength)
		throws PortalException {

		ratingsEntryLocalService.updateEntry(
			getUserId(), className, classPK, score, new ServiceContext());

		return getRatingsEntries(classPK, className, ratingsLength);
	}

}