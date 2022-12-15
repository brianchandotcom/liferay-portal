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

package com.liferay.user.associated.data.web.internal.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.constants.UADConstants;
import com.liferay.user.associated.data.web.internal.display.UADApplicationSummaryDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = UADApplicationSummaryHelper.class)
public class UADApplicationSummaryHelper {

	public List<UADAnonymizer<?>> getApplicationUADAnonymizers(
		String applicationKey) {

		List<UADAnonymizer<?>> uadAnonymizerList = new ArrayList<>();

		for (UADDisplay<?> uadDisplay :
				_uadRegistry.getApplicationUADDisplays(applicationKey)) {

			Class<?> typeClass = uadDisplay.getTypeClass();

			String entityName = typeClass.getName();

			uadAnonymizerList.add(_uadRegistry.getUADAnonymizer(entityName));
		}

		return uadAnonymizerList;
	}

	public String getDefaultUADRegistryKey(String applicationKey) {
		List<UADDisplay<?>> uadDisplays;

		if (applicationKey.equals("all-applications")) {
			uadDisplays = ListUtil.fromCollection(
				_uadRegistry.getUADDisplays());
		}
		else {
			uadDisplays = _uadRegistry.getApplicationUADDisplays(
				applicationKey);
		}

		UADDisplay<?> uadDisplay = uadDisplays.get(0);

		if (uadDisplay == null) {
			return null;
		}

		Class<?> typeClass = uadDisplay.getTypeClass();

		return typeClass.getName();
	}

	public int getTotalNonreviewableUADEntitiesCount(long userId) {
		return _getNonreviewableUADEntitiesCount(
			_uadRegistry.getNonreviewableUADAnonymizerList(), userId);
	}

	public int getTotalReviewableUADEntitiesCount(long userId) {
		return _getReviewableUADEntitiesCount(
			_uadRegistry.getUADDisplayList(), userId);
	}

	public UADApplicationSummaryDisplay getUADApplicationSummaryDisplay(
		String applicationKey, List<UADDisplay<?>> uadDisplayList, long userId,
		long[] groupIds) {

		UADApplicationSummaryDisplay uadApplicationSummaryDisplay =
			new UADApplicationSummaryDisplay();

		uadApplicationSummaryDisplay.setCount(
			_getReviewableUADEntitiesCount(uadDisplayList, userId, groupIds));
		uadApplicationSummaryDisplay.setApplicationKey(applicationKey);

		return uadApplicationSummaryDisplay;
	}

	public List<UADApplicationSummaryDisplay> getUADApplicationSummaryDisplays(
		long userId, long[] groupIds) {

		List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays =
			new ArrayList<>();

		UADApplicationSummaryDisplay
			allApplicationsUADApplicationSummaryDisplay =
				new UADApplicationSummaryDisplay();

		allApplicationsUADApplicationSummaryDisplay.setApplicationKey(
			UADConstants.ALL_APPLICATIONS);

		List<UADApplicationSummaryDisplay>
			generatedUADApplicationSummaryDisplays = new ArrayList<>();

		Set<String> applicationUADDisplaysKeySet =
			_uadRegistry.getApplicationUADDisplaysKeySet();

		int count = 0;

		for (String applicationKey : applicationUADDisplaysKeySet) {
			List<UADDisplay<?>> applicationUADDisplays = new ArrayList<>();

			for (UADDisplay<?> uadDisplay :
					_uadRegistry.getApplicationUADDisplays(applicationKey)) {

				if (ArrayUtil.isNotEmpty(groupIds) ==
						uadDisplay.isSiteScoped()) {

					applicationUADDisplays.add(uadDisplay);
				}
			}

			if (ListUtil.isNotEmpty(applicationUADDisplays)) {
				UADApplicationSummaryDisplay uadApplicationSummaryDisplay =
					getUADApplicationSummaryDisplay(
						applicationKey, applicationUADDisplays, userId,
						groupIds);

				generatedUADApplicationSummaryDisplays.add(
					uadApplicationSummaryDisplay);

				count += uadApplicationSummaryDisplay.getCount();
			}
		}

		allApplicationsUADApplicationSummaryDisplay.setCount(count);

		uadApplicationSummaryDisplays.add(
			allApplicationsUADApplicationSummaryDisplay);

		generatedUADApplicationSummaryDisplays.sort(
			(uadApplicationSummaryDisplay, uadApplicationSummaryDisplay2) -> {
				String applicationKey1 =
					uadApplicationSummaryDisplay.getApplicationKey();

				return applicationKey1.compareTo(
					uadApplicationSummaryDisplay2.getApplicationKey());
			});

		uadApplicationSummaryDisplays.addAll(
			generatedUADApplicationSummaryDisplays);

		return uadApplicationSummaryDisplays;
	}

	private int _getNonreviewableUADEntitiesCount(
		List<UADAnonymizer<?>> uadAnonymizerList, long userId) {

		int sum = 0;

		for (UADAnonymizer<?> uadAnonymizer : uadAnonymizerList) {
			try {
				int userIds = (int)uadAnonymizer.count(userId);

				sum += userIds;
			}
			catch (PortalException portalException) {
				throw new SystemException(portalException);
			}
		}

		return sum;
	}

	private int _getReviewableUADEntitiesCount(
		List<UADDisplay<?>> uadDisplayList, long userId) {

		int sum = 0;

		for (UADDisplay<?> uadDisplay : uadDisplayList) {
			int userIds = (int)uadDisplay.count(userId);

			sum += userIds;
		}

		return sum;
	}

	private int _getReviewableUADEntitiesCount(
		List<UADDisplay<?>> uadDisplayList, long userId, long[] groupIds) {

		int sum = 0;

		for (UADDisplay<?> uadDisplay : uadDisplayList) {
			int userIds = (int)uadDisplay.searchCount(userId, groupIds, null);

			sum += userIds;
		}

		return sum;
	}

	@Reference
	private UADRegistry _uadRegistry;

}