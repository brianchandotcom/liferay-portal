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

import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.display.UADApplicationSummaryDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.ArrayList;
import java.util.Collection;
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
		List<UADDisplay> uadDisplays = _uadRegistry.getApplicationUADDisplays(
			applicationKey);

		UADDisplay uadDisplay = uadDisplays.get(0);

		if (uadDisplay == null) {
			return null;
		}

		Class<?> typeClass = uadDisplay.getTypeClass();

		return typeClass.getName();
	}

	public int getReviewableUADEntitiesCount(
		Stream<UADDisplay> uadDisplayStream, long userId) {

		return uadDisplayStream.mapToInt(
			uadDisplay -> (int)uadDisplay.count(userId)
		).sum();
	}

	public int getTotalReviewableUADEntitiesCount(long userId) {
		return getReviewableUADEntitiesCount(
			_uadRegistry.getUADDisplayStream(), userId);
	}

	public UADApplicationSummaryDisplay getUADApplicationSummaryDisplay(
		String applicationKey, long userId) {

		UADApplicationSummaryDisplay uadApplicationSummaryDisplay =
			new UADApplicationSummaryDisplay();

		Collection<UADDisplay> applicationUADDisplays =
			_uadRegistry.getApplicationUADDisplays(applicationKey);

		int count = getReviewableUADEntitiesCount(
			applicationUADDisplays.stream(), userId);

		uadApplicationSummaryDisplay.setCount(count);

		uadApplicationSummaryDisplay.setApplicationKey(applicationKey);

		return uadApplicationSummaryDisplay;
	}

	public List<UADApplicationSummaryDisplay> getUADApplicationSummaryDisplays(
		long userId) {

		List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays =
			new ArrayList<>();

		Set<String> applicationUADDisplayKeySet =
			_uadRegistry.getApplicationUADDisplaysKeySet();

		Iterator<String> iterator = applicationUADDisplayKeySet.iterator();

		while (iterator.hasNext()) {
			String applicationKey = iterator.next();

			uadApplicationSummaryDisplays.add(
				getUADApplicationSummaryDisplay(applicationKey, userId));
		}

		uadApplicationSummaryDisplays.sort(
			(uadApplicationSummaryDisplay, uadApplicationSummaryDisplay2) -> {
				String applicationKey1 =
					uadApplicationSummaryDisplay.getApplicationKey();

				return applicationKey1.compareTo(
					uadApplicationSummaryDisplay2.getApplicationKey());
			});

		return uadApplicationSummaryDisplays;
	}

	@Reference
	private Portal _portal;

	@Reference
	private UADRegistry _uadRegistry;

}