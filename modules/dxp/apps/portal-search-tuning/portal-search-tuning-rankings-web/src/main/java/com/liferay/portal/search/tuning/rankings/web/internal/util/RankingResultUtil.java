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

package com.liferay.portal.search.tuning.rankings.web.internal.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.tuning.rankings.web.internal.constants.ResultRankingsPortletKeys;
import com.liferay.portal.search.web.interpreter.SearchResultInterpreter;
import com.liferay.portal.search.web.interpreter.SearchResultInterpreterProvider;

import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 */
@Component(immediate = true, service = {})
public class RankingResultUtil {

	public static AssetRenderer<?> getAssetRenderer(
		String entryClassName, long entryClassPK) {

		Document document = _documentBuilderFactory.builder(
		).setString(
			Field.ENTRY_CLASS_NAME, entryClassName
		).setLong(
			Field.ENTRY_CLASS_PK, Long.valueOf(entryClassPK)
		).build();

		SearchResultInterpreter searchResultInterpreter =
			_getSearchResultInterpreter();

		try {
			return searchResultInterpreter.getAssetRenderer(document);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to get asset renderer for class ",
						entryClassName, " with primary key ", entryClassPK),
					exception);
			}

			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public static String getRankingResultViewURL(
		Document document, ResourceRequest resourceRequest,
		ResourceResponse resourceResponse, boolean viewInContext) {

		SearchResultInterpreter searchResultInterpreter =
			_getSearchResultInterpreter();

		PortletURL viewContentURL = resourceResponse.createRenderURL();
		String currentURL = _portal.getCurrentURL(resourceRequest);

		try {
			viewContentURL.setParameter("mvcPath", "/view_content.jsp");
			viewContentURL.setParameter("redirect", currentURL);
			viewContentURL.setPortletMode(PortletMode.VIEW);
			viewContentURL.setWindowState(WindowState.MAXIMIZED);

			AssetEntry assetEntry = searchResultInterpreter.getAssetEntry(
				document);

			if (assetEntry == null) {
				return viewContentURL.toString();
			}

			viewContentURL.setParameter(
				"assetEntryId", String.valueOf(assetEntry.getEntryId()));
			viewContentURL.setParameter(
				"entryClassName", document.getString(Field.ENTRY_CLASS_NAME));
			viewContentURL.setParameter(
				"entryClassPK", document.getString(Field.ENTRY_CLASS_PK));

			if (!viewInContext) {
				return viewContentURL.toString();
			}

			String viewURL = searchResultInterpreter.getAssetURLViewInContext(
				document, PortalUtil.getLiferayPortletRequest(resourceRequest),
				PortalUtil.getLiferayPortletResponse(resourceResponse),
				viewContentURL.toString());

			if (Validator.isNull(viewURL)) {
				return viewContentURL.toString();
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			viewURL = HttpUtil.setParameter(
				viewURL, "inheritRedirect", viewInContext);

			Layout layout = themeDisplay.getLayout();

			String assetEntryLayoutUuid = assetEntry.getLayoutUuid();

			if (Validator.isNotNull(assetEntryLayoutUuid) &&
				!assetEntryLayoutUuid.equals(layout.getUuid())) {

				viewURL = HttpUtil.setParameter(
					viewURL, "redirect", currentURL);
			}

			return viewURL;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to get ranking result view URL for class ",
						document.getString(Field.ENTRY_CLASS_NAME),
						" with primary key ",
						document.getString(Field.ENTRY_CLASS_PK)),
					exception);
			}

			return StringPool.BLANK;
		}
	}

	public static boolean isAssetDeleted(Document document) {
		SearchResultInterpreter searchResultInterpreter =
			_getSearchResultInterpreter();

		try {
			return searchResultInterpreter.isAssetDeleted(document);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to get asset deletion status for class ",
						document.getString(Field.ENTRY_CLASS_NAME),
						" with primary key ",
						document.getString(Field.ENTRY_CLASS_PK)),
					exception);
			}

			return false;
		}
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	@Reference(unbind = "-")
	protected void setSearchDocumentBuilderFactory(
		DocumentBuilderFactory documentBuilderFactory) {

		_documentBuilderFactory = documentBuilderFactory;
	}

	@Reference(unbind = "-")
	protected void setSearchResultInterpreterProvider(
		SearchResultInterpreterProvider searchResultInterpreterProvider) {

		_searchResultInterpreterProvider = searchResultInterpreterProvider;
	}

	private static SearchResultInterpreter _getSearchResultInterpreter() {
		return _searchResultInterpreterProvider.getSearchResultInterpreter(
			ResultRankingsPortletKeys.RESULT_RANKINGS);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RankingResultUtil.class);

	private static DocumentBuilderFactory _documentBuilderFactory;
	private static Portal _portal;
	private static SearchResultInterpreterProvider
		_searchResultInterpreterProvider;

}