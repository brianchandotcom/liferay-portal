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

package com.liferay.user.associated.data.web.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.constants.UADConstants;
import com.liferay.user.associated.data.web.internal.display.UADApplicationSummaryDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = UADApplicationSummaryHelper.class)
public class UADApplicationSummaryHelper {

	public List<UADAnonymizer> getApplicationUADAnonymizers(
		String applicationKey) {

		Stream<UADDisplay> uadDisplayStream =
			_uadRegistry.getApplicationUADDisplayStream(applicationKey);

		return uadDisplayStream.map(
			UADDisplay::getTypeClass
		).map(
			Class::getName
		).map(
			key -> _uadRegistry.getUADAnonymizer(key)
		).collect(
			Collectors.toList()
		);
	}

	public String getDefaultUADRegistryKey(String applicationKey) {
		List<UADDisplay> uadDisplays;

		if (applicationKey.equals("all-applications")) {
			uadDisplays = ListUtil.fromCollection(
				_uadRegistry.getUADDisplays());
		}
		else {
			uadDisplays = _uadRegistry.getApplicationUADDisplays(
				applicationKey);
		}

		UADDisplay uadDisplay = uadDisplays.get(0);

		if (uadDisplay == null) {
			return null;
		}

		Class<?> typeClass = uadDisplay.getTypeClass();

		return typeClass.getName();
	}

	public int getTotalNonreviewableUADEntitiesCount(long userId) {
		return _getNonreviewableUADEntitiesCount(
			_uadRegistry.getNonreviewableUADAnonymizerStream(), userId);
	}

	public int getTotalReviewableUADEntitiesCount(long userId) {
		return _getReviewableUADEntitiesCount(
			_uadRegistry.getUADDisplayStream(), userId);
	}

	public UADApplicationSummaryDisplay getUADApplicationSummaryDisplay(
		String applicationKey, List<UADDisplay> uadDisplayStream, long userId,
		long[] groupIds) {

		UADApplicationSummaryDisplay uadApplicationSummaryDisplay =
			new UADApplicationSummaryDisplay();

		int count = _getReviewableUADEntitiesCount(
			uadDisplayStream.stream(), userId, groupIds);

		uadApplicationSummaryDisplay.setCount(count);

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

		Set<String> applicationUADDisplayKeySet =
			_uadRegistry.getApplicationUADDisplaysKeySet();

		int count = 0;

		Iterator<String> iterator = applicationUADDisplayKeySet.iterator();

		while (iterator.hasNext()) {
			String applicationKey = iterator.next();

			Stream<UADDisplay> uadDisplayStream =
				_uadRegistry.getApplicationUADDisplayStream(applicationKey);

			List<UADDisplay> applicationUADDisplays = uadDisplayStream.filter(
				uadDisplay ->
					ArrayUtil.isNotEmpty(groupIds) == uadDisplay.isSiteScoped()
			).collect(
				Collectors.toList()
			);

			if (!ListUtil.isEmpty(applicationUADDisplays)) {
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
		Stream<UADAnonymizer> uadAnonymizerStream, long userId) {

		return uadAnonymizerStream.mapToInt(
			uadAnonymizer -> {
				try {
					return (int)uadAnonymizer.count(userId);
				}
				catch (PortalException pe) {
					throw new SystemException(pe);
				}
			}
		).sum();
	}

	private int _getReviewableUADEntitiesCount(
		Stream<UADDisplay> uadDisplayStream, long userId) {

		return uadDisplayStream.mapToInt(
			uadDisplay -> (int)uadDisplay.count(userId)
		).sum();
	}

	private int _getReviewableUADEntitiesCount(
		Stream<UADDisplay> uadDisplayStream, long userId, long[] groupIds) {

		return uadDisplayStream.mapToInt(
			uadDisplay -> (int)uadDisplay.searchCount(userId, groupIds, null)
		).sum();
	}

	@Reference
	private Portal _portal;

	@Reference
	private UADRegistry _uadRegistry;

}