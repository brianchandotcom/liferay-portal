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

package com.liferay.layout.reports.web.internal.data.provider;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.pagespeedonline.v5.PagespeedInsights;
import com.google.api.services.pagespeedonline.v5.model.LighthouseAuditResultV5;
import com.google.api.services.pagespeedonline.v5.model.LighthouseResultV5;
import com.google.api.services.pagespeedonline.v5.model.PagespeedApiPagespeedResponseV5;

import com.liferay.layout.reports.web.internal.model.LayoutReportsIssue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Cristina González
 */
public class LayoutReportsDataProvider {

	public LayoutReportsDataProvider(String apiKey) {
		_apiKey = apiKey;
	}

	public List<LayoutReportsIssue> getLayoutReportsIssues(String url)
		throws LayoutReportsDataProviderException {

		try {
			return _getLayoutReportsIssues(url);
		}
		catch (LayoutReportsDataProviderException
					layoutReportsDataProviderException) {

			throw layoutReportsDataProviderException;
		}
		catch (Exception exception) {
			throw new LayoutReportsDataProviderException(exception);
		}
	}

	public boolean isValidConnection() {
		return Validator.isNotNull(_apiKey);
	}

	public static class LayoutReportsDataProviderException
		extends PortalException {

		public LayoutReportsDataProviderException(Exception exception) {
			super(exception);
		}

		public LayoutReportsDataProviderException(String message) {
			super(message);
		}

	}

	private int _getCount(LighthouseAuditResultV5 lighthouseAuditResultV5) {
		Map<String, Object> details = lighthouseAuditResultV5.getDetails();

		if (details != null) {
			Object items = details.get("items");

			if (items instanceof List) {
				List<?> itemsList = (List)items;

				return itemsList.size();
			}
		}

		float score = GetterUtil.getFloat(lighthouseAuditResultV5.getScore());

		if (score == 0) {
			return 1;
		}

		return 0;
	}

	private LayoutReportsIssue.Detail _getDetail(
		LayoutReportsIssue.Detail.Key key,
		Map<String, LighthouseAuditResultV5> lighthouseAuditResultV5s,
		String pageSpeedKey) {

		return new LayoutReportsIssue.Detail(
			key, _getCount(lighthouseAuditResultV5s.get(pageSpeedKey)));
	}

	private List<LayoutReportsIssue> _getLayoutReportsIssues(String url)
		throws Exception {

		if (!isValidConnection()) {
			throw new LayoutReportsDataProviderException("Invalid Connection");
		}

		PagespeedInsights pagespeedInsights = new PagespeedInsights.Builder(
			GoogleNetHttpTransport.newTrustedTransport(),
			JacksonFactory.getDefaultInstance(),
			request -> {
				request.setConnectTimeout(_CONNECT_TIMEOUT);
				request.setReadTimeout(_READ_TIMEOUT);
			}
		).build();

		PagespeedInsights.Pagespeedapi pagespeedapi =
			pagespeedInsights.pagespeedapi();

		PagespeedInsights.Pagespeedapi.Runpagespeed runpagespeed =
			pagespeedapi.runpagespeed(url);

		runpagespeed.setCategory(
			Arrays.asList("accessibility", "best-practices", "seo"));
		runpagespeed.setKey(_apiKey);

		PagespeedApiPagespeedResponseV5 pagespeedApiPagespeedResponseV5 =
			runpagespeed.execute();

		LighthouseResultV5 lighthouseResultV5 =
			pagespeedApiPagespeedResponseV5.getLighthouseResult();

		Map<String, LighthouseAuditResultV5> lighthouseAuditResultV5s =
			lighthouseResultV5.getAudits();

		return Arrays.asList(
			new LayoutReportsIssue(
				Arrays.asList(
					_getDetail(
						LayoutReportsIssue.Detail.Key.LOW_CONTRAST_RATIO,
						lighthouseAuditResultV5s, "color-contrast"),
					_getDetail(
						LayoutReportsIssue.Detail.Key.
							MISSING_IMG_ALT_ATTRIBUTES,
						lighthouseAuditResultV5s, "image-alt"),
					_getDetail(
						LayoutReportsIssue.Detail.Key.
							MISSING_INPUT_ALT_ATTRIBUTES,
						lighthouseAuditResultV5s, "input-image-alt"),
					_getDetail(
						LayoutReportsIssue.Detail.Key.MISSING_VIDEO_CAPTION,
						lighthouseAuditResultV5s, "video-caption")),
				LayoutReportsIssue.Key.ACCESSIBILITY),
			new LayoutReportsIssue(
				Arrays.asList(
					_getDetail(
						LayoutReportsIssue.Detail.Key.CANONICAL_LINK,
						lighthouseAuditResultV5s, "canonical"),
					_getDetail(
						LayoutReportsIssue.Detail.Key.CRAWLABLE_ANCHORS,
						lighthouseAuditResultV5s, "crawlable-anchors"),
					_getDetail(
						LayoutReportsIssue.Detail.Key.INDEXING,
						lighthouseAuditResultV5s, "is-crawlable"),
					_getDetail(
						LayoutReportsIssue.Detail.Key.FONT_SIZES,
						lighthouseAuditResultV5s, "font-size"),
					_getDetail(
						LayoutReportsIssue.Detail.Key.HREFLANG,
						lighthouseAuditResultV5s, "hreflang"),
					_getDetail(
						LayoutReportsIssue.Detail.Key.
							INCORRECT_IMAGE_ASPECT_RATIOS,
						lighthouseAuditResultV5s, "image-aspect-ratio"),
					_getDetail(
						LayoutReportsIssue.Detail.Key.
							LINKS_DO_NOT_HAVE_DESCRIPTIVE_TEXT,
						lighthouseAuditResultV5s, "link-text"),
					_getDetail(
						LayoutReportsIssue.Detail.Key.
							META_DESCRIPTION_IS_MISSING,
						lighthouseAuditResultV5s, "meta-description"),
					_getDetail(
						LayoutReportsIssue.Detail.Key.SMALL_TAP_TARGETS,
						lighthouseAuditResultV5s, "tap-targets"),
					_getDetail(
						LayoutReportsIssue.Detail.Key.TITLE,
						lighthouseAuditResultV5s, "document-title")),
				LayoutReportsIssue.Key.SEO));
	}

	private static final int _CONNECT_TIMEOUT = 30000;

	private static final int _READ_TIMEOUT = 120000;

	private final String _apiKey;

}