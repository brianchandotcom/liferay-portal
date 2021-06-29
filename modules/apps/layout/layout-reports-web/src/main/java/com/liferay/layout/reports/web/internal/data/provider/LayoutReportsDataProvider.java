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

import com.liferay.layout.reports.web.internal.model.LayoutReportsIssue;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.HttpURLConnection;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author Cristina González
 */
public class LayoutReportsDataProvider {

	public LayoutReportsDataProvider(String apiKey, String strategy) {
		_apiKey = apiKey;
		_strategy = strategy;
	}

	public List<LayoutReportsIssue> getLayoutReportsIssues(
			Locale locale, String url)
		throws LayoutReportsDataProviderException {

		try {
			return _getLayoutReportsIssues(locale, url);
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

		public LayoutReportsDataProviderException(
			JSONObject googlePageSpeedErrorJSONObject, String message) {

			super(message);

			_googlePageSpeedErrorJSONObject = googlePageSpeedErrorJSONObject;
		}

		public LayoutReportsDataProviderException(String message) {
			super(message);
		}

		public JSONObject getGooglePageSpeedErrorJSONObject() {
			return _googlePageSpeedErrorJSONObject;
		}

		private JSONObject _googlePageSpeedErrorJSONObject;

	}

	private LayoutReportsIssue.Detail _getDetail(
		LayoutReportsIssue.Detail.Key key,
		JSONObject lighthouseAuditJSONObject) {

		return new LayoutReportsIssue.Detail(key, lighthouseAuditJSONObject);
	}

	private List<LayoutReportsIssue> _getLayoutReportsIssues(
			Locale locale, String url)
		throws Exception {

		if (!isValidConnection()) {
			throw new LayoutReportsDataProviderException("Invalid Connection");
		}

		Http.Options options = new Http.Options();

		String googlePageSpeedURL =
			"https://content-pagespeedonline.googleapis.com/pagespeedonline" +
				"/v5/runPagespeed";

		googlePageSpeedURL = HttpUtil.addParameter(
			googlePageSpeedURL, "category", "ACCESSIBILITY");
		googlePageSpeedURL = HttpUtil.addParameter(
			googlePageSpeedURL, "category", "BEST_PRACTICES");
		googlePageSpeedURL = HttpUtil.addParameter(
			googlePageSpeedURL, "category", "SEO");
		googlePageSpeedURL = HttpUtil.addParameter(
			googlePageSpeedURL, "key", _apiKey);
		googlePageSpeedURL = HttpUtil.addParameter(
			googlePageSpeedURL, "locale", LanguageUtil.getLanguageId(locale));
		googlePageSpeedURL = HttpUtil.addParameter(
			googlePageSpeedURL, "strategy", _strategy);
		googlePageSpeedURL = HttpUtil.addParameter(
			googlePageSpeedURL, "url", url);

		options.setLocation(googlePageSpeedURL);

		options.setTimeout(120000);

		String responseJSON = HttpUtil.URLtoString(options);

		Http.Response response = options.getResponse();

		if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new LayoutReportsDataProviderException(
				JSONFactoryUtil.createJSONObject(responseJSON),
				StringBundler.concat(
					"Response code ", response.getResponseCode(), ": ",
					responseJSON));
		}

		JSONObject auditResultJSONObject = JSONFactoryUtil.createJSONObject(
			responseJSON);

		JSONObject lighthouseResultJSONObject =
			auditResultJSONObject.getJSONObject("lighthouseResult");

		JSONObject auditsJSONObject = lighthouseResultJSONObject.getJSONObject(
			"audits");

		return Arrays.asList(
			new LayoutReportsIssue(
				Arrays.asList(
					_getDetail(
						LayoutReportsIssue.Detail.Key.LOW_CONTRAST_RATIO,
						auditsJSONObject.getJSONObject("color-contrast")),
					_getDetail(
						LayoutReportsIssue.Detail.Key.
							MISSING_IMG_ALT_ATTRIBUTES,
						auditsJSONObject.getJSONObject("image-alt")),
					_getDetail(
						LayoutReportsIssue.Detail.Key.
							MISSING_INPUT_ALT_ATTRIBUTES,
						auditsJSONObject.getJSONObject("input-image-alt"))),
				LayoutReportsIssue.Key.ACCESSIBILITY),
			new LayoutReportsIssue(
				Arrays.asList(
					_getDetail(
						LayoutReportsIssue.Detail.Key.INVALID_CANONICAL_URL,
						auditsJSONObject.getJSONObject("canonical")),
					_getDetail(
						LayoutReportsIssue.Detail.Key.
							NOT_ALL_LINKS_ARE_CRAWLABLE,
						auditsJSONObject.getJSONObject("crawlable-anchors")),
					_getDetail(
						LayoutReportsIssue.Detail.Key.
							PAGE_BLOCKED_FROM_INDEXING,
						auditsJSONObject.getJSONObject("is-crawlable")),
					_getDetail(
						LayoutReportsIssue.Detail.Key.ILLEGIBLE_FONT_SIZES,
						auditsJSONObject.getJSONObject("font-size")),
					_getDetail(
						LayoutReportsIssue.Detail.Key.INVALID_HREFLANG,
						auditsJSONObject.getJSONObject("hreflang")),
					_getDetail(
						LayoutReportsIssue.Detail.Key.
							INCORRECT_IMAGE_ASPECT_RATIOS,
						auditsJSONObject.getJSONObject("image-aspect-ratio")),
					_getDetail(
						LayoutReportsIssue.Detail.Key.LINK_TEXTS,
						auditsJSONObject.getJSONObject("link-text")),
					_getDetail(
						LayoutReportsIssue.Detail.Key.MISSING_META_DESCRIPTION,
						auditsJSONObject.getJSONObject("meta-description")),
					_getDetail(
						LayoutReportsIssue.Detail.Key.SMALL_TAP_TARGETS,
						auditsJSONObject.getJSONObject("tap-targets")),
					_getDetail(
						LayoutReportsIssue.Detail.Key.MISSING_TITLE_ELEMENT,
						auditsJSONObject.getJSONObject("document-title"))),
				LayoutReportsIssue.Key.SEO));
	}

	private final String _apiKey;
	private final String _strategy;

}