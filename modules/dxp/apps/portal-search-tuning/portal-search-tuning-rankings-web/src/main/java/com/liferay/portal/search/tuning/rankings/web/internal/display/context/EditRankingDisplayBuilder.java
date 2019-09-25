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

package com.liferay.portal.search.tuning.rankings.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kevin Tan
 */
public class EditRankingDisplayBuilder {

	public EditRankingDisplayBuilder(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public EditRankingDisplayContext build() {
		EditRankingDisplayContext editRankingDisplayContext =
			new EditRankingDisplayContext();

		_setBackURL(editRankingDisplayContext);
		_setCompanyId(editRankingDisplayContext);
		_setData(editRankingDisplayContext);
		_setKeywords(editRankingDisplayContext);
		_setRedirect(editRankingDisplayContext);
		_setResultsRankingUid(editRankingDisplayContext);

		return editRankingDisplayContext;
	}

	private String[] _getAliases() {
		return StringUtil.split(
			ParamUtil.getString(_httpServletRequest, "aliases"),
			StringPool.COMMA_AND_SPACE);
	}

	private Map<String, Object> _getConstants() {
		Map<String, Object> constants = new HashMap<>();

		constants.put(
			"WORKFLOW_ACTION_PUBLISH", WorkflowConstants.ACTION_PUBLISH);
		constants.put(
			"WORKFLOW_ACTION_SAVE_DRAFT", WorkflowConstants.ACTION_SAVE_DRAFT);

		return constants;
	}

	private Map<String, Object> _getContext() {
		Map<String, Object> context = new HashMap<>();

		context.put("companyId", String.valueOf(_themeDisplay.getCompanyId()));
		context.put("constants", _getConstants());
		context.put("namespace", _renderResponse.getNamespace());
		context.put(
			"spritemap",
			_themeDisplay.getPathThemeImages() + "/lexicon/icons.svg");

		return context;
	}

	private String _getHiddenResultRankingsResourceURL() {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setParameter(
			"companyId", String.valueOf(_themeDisplay.getCompanyId()));
		resourceURL.setParameter(Constants.CMD, "getHiddenResults");
		resourceURL.setParameter("resultsRankingUid", _getResultsRankingUid());
		resourceURL.setResourceID("/results_ranking/get_results");

		return resourceURL.toString();
	}

	private String _getKeywords() {
		return ParamUtil.getString(_httpServletRequest, "keywords");
	}

	private Map<String, Object> _getProps() {
		Map<String, Object> props = new HashMap<>();

		props.put("cancelUrl", HtmlUtil.escape(_getRedirect()));
		props.put(
			"fetchDocumentsHiddenUrl", _getHiddenResultRankingsResourceURL());
		props.put(
			"fetchDocumentsSearchUrl", _getSearchResultRankingsResourceURL());
		props.put(
			"fetchDocumentsVisibleUrl", _getVisibleResultRankingsResourceURL());
		props.put(
			"formName",
			_renderResponse.getNamespace() + "editResultsRankingsFm");
		props.put("initialAliases", _getAliases());
		props.put("searchQuery", _getKeywords());

		return props;
	}

	private String _getRedirect() {
		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		if (Validator.isNull(redirect)) {
			PortletURL portletURL = _renderResponse.createRenderURL();

			redirect = portletURL.toString();
		}

		return redirect;
	}

	private String _getResultsRankingUid() {
		return ParamUtil.getString(_httpServletRequest, "resultsRankingUid");
	}

	private String _getSearchResultRankingsResourceURL() {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setParameter(
			"companyId", String.valueOf(_themeDisplay.getCompanyId()));
		resourceURL.setParameter(Constants.CMD, "getSearchResults");
		resourceURL.setResourceID("/results_ranking/get_results");

		return resourceURL.toString();
	}

	private String _getVisibleResultRankingsResourceURL() {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setParameter(
			"companyId", String.valueOf(_themeDisplay.getCompanyId()));
		resourceURL.setParameter(Constants.CMD, "getVisibleResults");
		resourceURL.setParameter("resultsRankingUid", _getResultsRankingUid());
		resourceURL.setResourceID("/results_ranking/get_results");

		return resourceURL.toString();
	}

	private void _setBackURL(
		EditRankingDisplayContext editRankingDisplayContext) {

		String backURL = ParamUtil.getString(
			_httpServletRequest, "backURL", _getRedirect());

		editRankingDisplayContext.setBackURL(backURL);
	}

	private void _setCompanyId(
		EditRankingDisplayContext editRankingDisplayContext) {

		editRankingDisplayContext.setCompanyId(_themeDisplay.getCompanyId());
	}

	private void _setData(EditRankingDisplayContext editRankingDisplayContext) {
		Map<String, Object> data = new HashMap<>();

		data.put("context", _getContext());
		data.put("props", _getProps());

		editRankingDisplayContext.setData(data);
	}

	private void _setKeywords(
		EditRankingDisplayContext editRankingDisplayContext) {

		editRankingDisplayContext.setKeywords(_getKeywords());
	}

	private void _setRedirect(
		EditRankingDisplayContext editRankingDisplayContext) {

		editRankingDisplayContext.setRedirect(_getRedirect());
	}

	private void _setResultsRankingUid(
		EditRankingDisplayContext editRankingDisplayContext) {

		editRankingDisplayContext.setResultsRankingUid(_getResultsRankingUid());
	}

	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}