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

package com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.display.context;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.MisspellingSet;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexName;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexNameBuilder;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.constants.MisspellingsMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.constants.MisspellingsWebKeys;
import com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index.MisspellingsIndexReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Petteri Karttunen
 */
public class EditMisspellingSetDisplayBuilder {

	public EditMisspellingSetDisplayBuilder(
		HttpServletRequest httpServletRequest, Language language, Portal portal,
		RenderRequest renderRequest, RenderResponse renderResponse,
		MisspellingsIndexNameBuilder misspellingsIndexNameBuilder,
		MisspellingsIndexReader misspellingsIndexReader) {

		_httpServletRequest = httpServletRequest;
		_language = language;
		_portal = portal;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_misspellingsIndexNameBuilder = misspellingsIndexNameBuilder;
		_misspellingsIndexReader = misspellingsIndexReader;

		_misspellingSetId = ParamUtil.getString(
			renderRequest, MisspellingsWebKeys.MISSPELLING_SET_ID);

		_misspellingSetOptional = _getMisspellingSetOptional();

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public EditMisspellingSetDisplayContext build() {
		EditMisspellingSetDisplayContext editMisspellingSetDisplayContext =
			new EditMisspellingSetDisplayContext();

		_setData(editMisspellingSetDisplayContext);
		_setMisspellingSetId(editMisspellingSetDisplayContext);
		_setPageTitle(editMisspellingSetDisplayContext);
		_setRedirect(editMisspellingSetDisplayContext);

		return editMisspellingSetDisplayContext;
	}

	private long _getCompanyId() {
		return _portal.getCompanyId(_renderRequest);
	}

	private Map<String, Object> _getContext() {
		return HashMapBuilder.<String, Object>put(
			"locale", _themeDisplay.getLanguageId()
		).put(
			"namespace", _renderResponse.getNamespace()
		).build();
	}

	private String _getCreated() {
		return _misspellingSetOptional.map(
			misspellingSet -> misspellingSet.getCreated(
			).toString()
		).orElse(
			StringPool.BLANK
		);
	}

	private Long _getGroupId() {
		return _misspellingSetOptional.map(
			MisspellingSet::getGroupId
		).orElse(
			null
		);
	}

	private String _getLanguageId() {
		return _misspellingSetOptional.map(
			MisspellingSet::getLanguageId
		).orElse(
			StringPool.BLANK
		);
	}

	private List<String> _getMisspellings() {
		return _misspellingSetOptional.map(
			MisspellingSet::getMisspellings
		).orElse(
			new ArrayList<String>()
		);
	}

	private Optional<MisspellingSet> _getMisspellingSetOptional() {
		MisspellingsIndexName misspellingsIndexName =
			_misspellingsIndexNameBuilder.getMisspellingsIndexName(
				_getCompanyId());

		return _misspellingsIndexReader.fetchMisspellingSetOptional(
			misspellingsIndexName, _misspellingSetId);
	}

	private String _getModified() {
		return _misspellingSetOptional.map(
			misspellingSet -> misspellingSet.getModified(
			).toString()
		).orElse(
			StringPool.BLANK
		);
	}

	private String _getPhrase() {
		return _misspellingSetOptional.map(
			MisspellingSet::getPhrase
		).orElse(
			StringPool.BLANK
		);
	}

	private Map<String, Object> _getProps() {
		return HashMapBuilder.<String, Object>put(
			"created", _getCreated()
		).put(
			"groupId", _getGroupId()
		).put(
			"languageId", _getLanguageId()
		).put(
			"misspellings", _getMisspellings()
		).put(
			"misspellingSetId", _misspellingSetId
		).put(
			"modified", _getModified()
		).put(
			"phrase", _getPhrase()
		).put(
			"redirectURL", _getRedirect()
		).put(
			"submitFormURL", _getSubmitFormURL()
		).put(
			"userId", _getUserId()
		).build();
	}

	private String _getRedirect() {
		return ParamUtil.getString(_httpServletRequest, "redirect");
	}

	private String _getSubmitFormURL() {
		ActionURL actionURL = _renderResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME,
			MisspellingsMVCCommandNames.EDIT_MISSPELLING_SET);
		actionURL.setParameter(
			Constants.CMD,
			_misspellingSetOptional.isPresent() ? Constants.EDIT :
				Constants.ADD);
		actionURL.setParameter("redirect", _getRedirect());

		return actionURL.toString();
	}

	private Long _getUserId() {
		return _misspellingSetOptional.map(
			MisspellingSet::getUserId
		).orElse(
			null
		);
	}

	private void _setData(
		EditMisspellingSetDisplayContext editMisspellingSetDisplayContext) {

		editMisspellingSetDisplayContext.setData(
			HashMapBuilder.<String, Object>put(
				"context", _getContext()
			).put(
				"props", _getProps()
			).build());
	}

	private void _setMisspellingSetId(
		EditMisspellingSetDisplayContext editMisspellingSetDisplayContext) {

		_misspellingSetOptional.ifPresent(
			misspellingSet ->
				editMisspellingSetDisplayContext.setMisspellingSetId(
					misspellingSet.getMisspellingSetId()));
	}

	private void _setPageTitle(
		EditMisspellingSetDisplayContext editMisspellingSetDisplayContext) {

		StringBundler sb = new StringBundler(2);

		sb.append(_misspellingSetOptional.isPresent() ? "edit-" : "add-");

		sb.append("misspelling-set");

		editMisspellingSetDisplayContext.setPageTitle(
			_language.get(_httpServletRequest, sb.toString()));
	}

	private void _setRedirect(
		EditMisspellingSetDisplayContext editMisspellingSetDisplayContext) {

		editMisspellingSetDisplayContext.setRedirect(_getRedirect());
	}

	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final String _misspellingSetId;
	private final Optional<MisspellingSet> _misspellingSetOptional;
	private final MisspellingsIndexNameBuilder _misspellingsIndexNameBuilder;
	private final MisspellingsIndexReader _misspellingsIndexReader;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}