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

package com.liferay.portal.search.tuning.blueprints.result.web.internal.hit.contributor;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.journal.model.JournalArticle;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.result.web.internal.constants.ResultWebPortletKeys;
import com.liferay.portal.search.tuning.blueprints.result.web.internal.util.ResultUtil;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.constants.ResponseAttributeKeys;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.hit.HitContributor;
import com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.spi.result.ResultBuilder;
import com.liferay.wiki.model.WikiPage;

import java.util.Optional;
import java.util.ResourceBundle;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=view-url", service = HitContributor.class
)
public class ViewURLHitContributor implements HitContributor {

	@Override
	public void contribute(
		JSONObject hitJSONObject, Document document,
		ResultBuilder resultBuilder, BlueprintsAttributes blueprintsAttributes,
		ResourceBundle resourceBundle) {

		PortletRequest portletRequest = _getPortletRequest(
			blueprintsAttributes);
		PortletResponse portletResponse = _getPortletResponse(
			blueprintsAttributes);

		if ((portletRequest == null) || (portletResponse == null)) {
			return;
		}

		hitJSONObject.put(
			"viewURL",
			getViewURL(
				_portal.getLiferayPortletRequest(portletRequest),
				_portal.getLiferayPortletResponse(portletResponse),
				hitJSONObject, document,
				_isViewInContext(blueprintsAttributes)));
	}

	protected String getViewURL(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, JSONObject hitJSONObject,
		Document document, boolean viewInContext) {

		try {
			if (_isComment(document)) {
				return _getCommentViewURL(
					liferayPortletRequest, liferayPortletResponse, document,
					viewInContext);
			}

			PortletURL viewContentURL = _getViewContentURL(
				liferayPortletRequest, liferayPortletResponse, document,
				hitJSONObject);

			if (!viewInContext) {
				return viewContentURL.toString();
			}

			return _getURLViewInContext(
				liferayPortletRequest, liferayPortletResponse, document,
				viewContentURL.toString(), viewInContext);
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Unable to get search result view URL for class ",
					document.getString(Field.ENTRY_CLASS_NAME),
					" with primary key ",
					document.getString(Field.ENTRY_CLASS_PK)),
				exception);

			return StringPool.BLANK;
		}
	}

	private long _getClassNameId(String className) {
		return _portal.getClassNameId(className);
	}

	private String _getCommentViewURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, Document document,
			boolean viewInContext)
		throws Exception {

		long classPK = document.getLong(Field.CLASS_PK);

		String className = _portal.getClassName(
			document.getLong(Field.CLASS_NAME_ID));

		if (className.equals(JournalArticle.class.getName())) {
			return _getJournalArticleCommentViewURL(
				liferayPortletRequest, liferayPortletResponse, classPK,
				viewInContext);
		}
		else if (className.equals(WikiPage.class.getName())) {
			return _getWikiPageCommentViewURL(
				liferayPortletRequest, liferayPortletResponse, classPK,
				viewInContext);
		}

		return null;
	}

	private String _getJournalArticleCommentViewURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classPK,
			boolean viewResultsInContext)
		throws Exception {

		if (viewResultsInContext) {
			AssetRenderer<?> assetRenderer = ResultUtil.getAssetRenderer(
				JournalArticle.class.getName(), classPK);

			return assetRenderer.getURLViewInContext(
				liferayPortletRequest, liferayPortletResponse, null);
		}

		return null;
	}

	private PortletRequest _getPortletRequest(
			BlueprintsAttributes blueprintsAttributes)
		throws ClassCastException {

		Optional<Object> portletRequestOptional =
			blueprintsAttributes.getAttributeOptional(
				ResponseAttributeKeys.PORTLET_REQUEST);

		if (!portletRequestOptional.isPresent()) {
			return null;
		}

		return (PortletRequest)portletRequestOptional.get();
	}

	private PortletResponse _getPortletResponse(
			BlueprintsAttributes blueprintsAttributes)
		throws ClassCastException {

		Optional<Object> portletResponseOptional =
			blueprintsAttributes.getAttributeOptional(
				ResponseAttributeKeys.PORTLET_RESPONSE);

		if (!portletResponseOptional.isPresent()) {
			return null;
		}

		return (PortletResponse)portletResponseOptional.get();
	}

	private String _getURLViewInContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, Document document,
			String viewContentURL, boolean viewInContext)
		throws Exception {

		AssetRenderer<?> assetRenderer = ResultUtil.getAssetRenderer(
			document.getString(Field.ENTRY_CLASS_NAME),
			document.getLong(Field.ENTRY_CLASS_PK));

		String viewURL = assetRenderer.getURLViewInContext(
			liferayPortletRequest, liferayPortletResponse, "");

		if (Validator.isNull(viewURL)) {
			return viewContentURL;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		viewURL = _http.setParameter(viewURL, "inheritRedirect", viewInContext);

		Layout layout = themeDisplay.getLayout();

		String layoutUuid = document.getString(Field.LAYOUT_UUID);

		if (Validator.isNotNull(layoutUuid) &&
			!layoutUuid.equals(layout.getUuid())) {

			viewURL = _http.setParameter(
				viewURL, "redirect",
				_portal.getCurrentURL(liferayPortletRequest));
		}

		return viewURL;
	}

	@SuppressWarnings("deprecation")
	private PortletURL _getViewContentURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, Document document,
			JSONObject hitJSONObject)
		throws PortletModeException, WindowStateException {

		PortletURL viewContentURL = liferayPortletResponse.createRenderURL(
			ResultWebPortletKeys.RESULT_WEB);

		viewContentURL.setParameter("mvcPath", "/view_content.jsp");
		viewContentURL.setParameter(
			"redirect", _portal.getCurrentURL(liferayPortletRequest));
		viewContentURL.setPortletMode(PortletMode.VIEW);
		viewContentURL.setWindowState(WindowState.MAXIMIZED);

		long assetEntryId = hitJSONObject.getLong("assetEntryId");

		if (assetEntryId == 0) {
			return viewContentURL;
		}

		viewContentURL.setParameter(
			"assetEntryId", String.valueOf(assetEntryId));
		viewContentURL.setParameter(
			"entryClassName", document.getString(Field.ENTRY_CLASS_NAME));
		viewContentURL.setParameter(
			"entryClassPK", document.getString(Field.ENTRY_CLASS_PK));

		return viewContentURL;
	}

	private String _getWikiPageCommentViewURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classPK,
			boolean viewResultsInContext)
		throws Exception {

		AssetRenderer<?> assetRenderer = ResultUtil.getAssetRenderer(
			WikiPage.class.getName(), classPK);

		if (viewResultsInContext) {
			return assetRenderer.getURLViewInContext(
				liferayPortletRequest, liferayPortletResponse, "");
		}

		return assetRenderer.getURLView(
			liferayPortletResponse, WindowState.MAXIMIZED);
	}

	private boolean _isComment(Document document) {
		String entryClassName = document.getString(Field.ENTRY_CLASS_NAME);

		long classNameId = _getClassNameId(entryClassName);

		if (entryClassName.equals(MBMessage.class.getName()) &&
			(classNameId > 0)) {

			return true;
		}

		return false;
	}

	private boolean _isViewInContext(
		BlueprintsAttributes blueprintsAttributes) {

		Optional<Object> viewResultsInContextOptional =
			blueprintsAttributes.getAttributeOptional(
				ResponseAttributeKeys.VIEW_RESULT_IN_CONTEXT);

		return GetterUtil.getBoolean(viewResultsInContextOptional.orElse(true));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ViewURLHitContributor.class);

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

}