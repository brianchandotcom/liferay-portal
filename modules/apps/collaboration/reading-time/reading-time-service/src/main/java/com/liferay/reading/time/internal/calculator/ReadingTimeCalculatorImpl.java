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

package com.liferay.reading.time.internal.calculator;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.reading.time.calculator.ReadingTimeCalculator;
import com.liferay.reading.time.calculator.ReadingTimeModelInfo;

import java.time.Duration;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = ReadingTimeCalculator.class)
public class ReadingTimeCalculatorImpl implements ReadingTimeCalculator {

	@Override
	public Optional<Duration> calculate(GroupedModel groupedModel) {
		ReadingTimeModelInfo readingTimeModelInfo =
			_serviceTrackerMap.getService(groupedModel.getModelClassName());

		if (readingTimeModelInfo == null) {
			return Optional.empty();
		}

		return calculate(
			readingTimeModelInfo.getContent(groupedModel),
			readingTimeModelInfo.getContentType(groupedModel),
			readingTimeModelInfo.getLocale(groupedModel));
	}

	@Override
	public Optional<Duration> calculate(
		String content, String contentType, Locale locale) {

		if (!_supportedContentTypes.contains(contentType)) {
			return Optional.empty();
		}

		if (Validator.isNull(content)) {
			return Optional.of(Duration.ZERO);
		}

		Document document = Jsoup.parseBodyFragment(content);

		String text = document.text();

		String[] words = text.split("(?:\\h|\\v)+");

		Duration readingTimeDuration = Duration.ofSeconds(
			Math.round(60 * words.length / 265F));

		List<Element> images = document.getElementsByTag("img");

		readingTimeDuration = readingTimeDuration.plus(
			Duration.ofSeconds(3 * images.size()));

		return Optional.of(readingTimeDuration);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ReadingTimeModelInfo.class, "model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final List<String> _supportedContentTypes = Arrays.asList(
		ContentTypes.TEXT_HTML, ContentTypes.TEXT_HTML_UTF8, ContentTypes.TEXT,
		ContentTypes.TEXT_PLAIN, ContentTypes.TEXT_PLAIN_UTF8);

	private ServiceTrackerMap<String, ReadingTimeModelInfo> _serviceTrackerMap;

}