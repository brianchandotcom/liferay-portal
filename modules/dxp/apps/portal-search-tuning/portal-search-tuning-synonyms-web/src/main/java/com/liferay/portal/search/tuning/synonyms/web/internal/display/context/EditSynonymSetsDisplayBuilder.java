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

package com.liferay.portal.search.tuning.synonyms.web.internal.display.context;

import com.liferay.portal.kernel.util.ParamUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kevin Tan
 */
public class EditSynonymSetsDisplayBuilder {

	public EditSynonymSetsDisplayBuilder(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public EditSynonymSetsDisplayContext build() {
		EditSynonymSetsDisplayContext editSynonymSetsDisplayContext =
			new EditSynonymSetsDisplayContext();

		_setBackURL(editSynonymSetsDisplayContext);
		_setData(editSynonymSetsDisplayContext);
		_setFormName(editSynonymSetsDisplayContext);
		_setInputName(editSynonymSetsDisplayContext);
		_setOriginalInputName(editSynonymSetsDisplayContext);
		_setRedirect(editSynonymSetsDisplayContext);

		return editSynonymSetsDisplayContext;
	}

	private String _getBackURL() {
		return ParamUtil.getString(
			_httpServletRequest, "backURL", _getRedirect());
	}

	private String _getFormName() {
		return "synonymSetsForm";
	}

	private String _getInputName() {
		return "newSynonymSet";
	}

	private String _getOriginalInputName() {
		return "originalSynonymSet";
	}

	private String _getRedirect() {
		return ParamUtil.getString(_httpServletRequest, "redirect");
	}

	private String _getSynonymSets() {
		return ParamUtil.getString(_httpServletRequest, "synonymSets");
	}

	private void _setBackURL(
		EditSynonymSetsDisplayContext editSynonymSetsDisplayContext) {

		editSynonymSetsDisplayContext.setBackURL(_getBackURL());
	}

	private void _setData(
		EditSynonymSetsDisplayContext editSynonymSetsDisplayContext) {

		Map<String, Object> data = new HashMap<>();

		data.put("formName", _renderResponse.getNamespace() + _getFormName());
		data.put("inputName", _renderResponse.getNamespace() + _getInputName());
		data.put(
			"originalInputName",
			_renderResponse.getNamespace() + _getOriginalInputName());
		data.put("synonymSets", _getSynonymSets());

		editSynonymSetsDisplayContext.setData(data);
	}

	private void _setFormName(
		EditSynonymSetsDisplayContext editSynonymSetsDisplayContext) {

		editSynonymSetsDisplayContext.setFormName(_getFormName());
	}

	private void _setInputName(
		EditSynonymSetsDisplayContext editSynonymSetsDisplayContext) {

		editSynonymSetsDisplayContext.setInputName(_getInputName());
	}

	private void _setOriginalInputName(
		EditSynonymSetsDisplayContext editSynonymSetsDisplayContext) {

		editSynonymSetsDisplayContext.setOriginalInputName(
			_getOriginalInputName());
	}

	private void _setRedirect(
		EditSynonymSetsDisplayContext editSynonymSetsDisplayContext) {

		editSynonymSetsDisplayContext.setRedirect(_getRedirect());
	}

	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}