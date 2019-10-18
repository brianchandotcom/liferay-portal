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

package com.liferay.portal.search.similar.results.web.internal.display.context;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.similar.results.web.internal.configuration.SimilarResultsPortletInstanceConfiguration;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kevin Tan
 */
public class SimilarResultsDisplayContext {

	public SimilarResultsDisplayContext(HttpServletRequest httpServletRequest)
		throws ConfigurationException {

		_httpServletRequest = httpServletRequest;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_similarResultsPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				SimilarResultsPortletInstanceConfiguration.class);
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_similarResultsPortletInstanceConfiguration.displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	public List<Document> getDocuments() {
		return _documents;
	}

	public List<SimilarResultsDocumentDisplayContext>
		getSimilarResultsDocumentDisplayContexts() {

		if (_similarResultsDocumentDisplayContexts != null) {
			return _similarResultsDocumentDisplayContexts;
		}

		return new ArrayList<>();
	}

	public SimilarResultsPortletInstanceConfiguration
		getSimilarResultsPortletInstanceConfiguration() {

		return _similarResultsPortletInstanceConfiguration;
	}

	public int getTotalHits() {
		return _totalHits;
	}

	public void setDocuments(List<Document> documents) {
		_documents = documents;
	}

	public void setSimilarResultsDocumentDisplayContexts(
		List<SimilarResultsDocumentDisplayContext>
			similarResultsDocumentDisplayContexts) {

		_similarResultsDocumentDisplayContexts =
			similarResultsDocumentDisplayContexts;
	}

	public void setTotalHits(int totalHits) {
		_totalHits = totalHits;
	}

	private long _displayStyleGroupId;
	private List<Document> _documents;
	private final HttpServletRequest _httpServletRequest;
	private List<SimilarResultsDocumentDisplayContext>
		_similarResultsDocumentDisplayContexts;
	private final SimilarResultsPortletInstanceConfiguration
		_similarResultsPortletInstanceConfiguration;
	private int _totalHits;

}