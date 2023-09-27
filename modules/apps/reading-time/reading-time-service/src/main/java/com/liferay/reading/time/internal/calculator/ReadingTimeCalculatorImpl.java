/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = ReadingTimeCalculator.class)
public class ReadingTimeCalculatorImpl implements ReadingTimeCalculator {

	@Override
	public Duration calculate(GroupedModel groupedModel) {
		ReadingTimeModelInfo<GroupedModel> readingTimeModelInfo =
			(ReadingTimeModelInfo<GroupedModel>)_serviceTrackerMap.getService(
				groupedModel.getModelClassName());

		if (readingTimeModelInfo == null) {
			return null;
		}

		return calculate(
			readingTimeModelInfo.getContent(groupedModel),
			readingTimeModelInfo.getContentType(groupedModel),
			readingTimeModelInfo.getLocale(groupedModel));
	}

	@Override
	public Duration calculate(
		String content, String contentType, Locale locale) {

		if (!_supportedContentTypes.contains(contentType)) {
			return null;
		}

		if (Validator.isNull(content)) {
			return Duration.ZERO;
		}

		Document document = Jsoup.parseBodyFragment(content);

		String text = document.text();

		String[] words = text.split("(?:\\h|\\v)+");

		Duration readingTimeDuration = Duration.ofSeconds(
			Math.round(60 * words.length / 265F));

		List<Element> images = document.getElementsByTag("img");

		return readingTimeDuration.plus(Duration.ofSeconds(3 * images.size()));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<ReadingTimeModelInfo<?>>)
				(Class<?>)ReadingTimeModelInfo.class,
			"model.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final List<String> _supportedContentTypes = Arrays.asList(
		ContentTypes.TEXT_HTML, ContentTypes.TEXT_HTML_UTF8, ContentTypes.TEXT,
		ContentTypes.TEXT_PLAIN, ContentTypes.TEXT_PLAIN_UTF8);

	private ServiceTrackerMap<String, ReadingTimeModelInfo<?>>
		_serviceTrackerMap;

}