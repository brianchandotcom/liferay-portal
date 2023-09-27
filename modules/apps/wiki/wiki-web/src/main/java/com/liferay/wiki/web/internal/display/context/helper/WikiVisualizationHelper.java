/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.web.internal.display.context.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;

import java.util.Collection;

/**
 * @author Adolfo Pérez
 */
public class WikiVisualizationHelper {

	public WikiVisualizationHelper(
		WikiRequestHelper wikiRequestHelper,
		WikiPortletInstanceSettingsHelper wikiPortletInstanceSettingsHelper,
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiRequestHelper = wikiRequestHelper;
		_wikiPortletInstanceSettingsHelper = wikiPortletInstanceSettingsHelper;
		_wikiGroupServiceConfiguration = wikiGroupServiceConfiguration;
	}

	public boolean isFrontPageNavItemSelected() {
		WikiPage wikiPage = _wikiRequestHelper.getWikiPage();

		String mvcRenderCommandName =
			_wikiRequestHelper.getMVCRenderCommandName();

		String frontPageName = _wikiGroupServiceConfiguration.frontPageName();

		if (Validator.isNull(mvcRenderCommandName) ||
			mvcRenderCommandName.equals("/wiki/view") ||
			mvcRenderCommandName.equals("/wiki/view_page_activities") ||
			mvcRenderCommandName.equals("/wiki/view_page_attachments") ||
			mvcRenderCommandName.equals("/wiki/view_page_details") ||
			mvcRenderCommandName.equals("/wiki/view_page_incoming_links") ||
			mvcRenderCommandName.equals("/wiki/view_page_outgoing_links") ||
			((wikiPage != null) && frontPageName.equals(wikiPage.getTitle()))) {

			return true;
		}

		return false;
	}

	public boolean isNodeNavigationVisible() throws PortalException {
		Collection<WikiNode> nodes =
			_wikiPortletInstanceSettingsHelper.getAllPermittedNodes();

		String portletId = _wikiRequestHelper.getPortletId();

		if ((nodes.size() > 1) && portletId.equals(WikiPortletKeys.WIKI)) {
			return true;
		}

		return false;
	}

	public boolean isUndoTrashControlVisible() {
		String mvcRenderCommandName =
			_wikiRequestHelper.getMVCRenderCommandName();

		if (mvcRenderCommandName.equals("/wiki/view_page_activities") ||
			mvcRenderCommandName.equals("/wiki/view_page_attachments")) {

			return false;
		}

		return true;
	}

	public boolean isViewAllPagesNavItemSelected() {
		return _isNavItemSelected("/wiki/view_pages");
	}

	public boolean isViewDraftPagesNavItemSelected() {
		return _isNavItemSelected("/wiki/view_draft_pages");
	}

	public boolean isViewOrphanPagesNavItemSelected() {
		return _isNavItemSelected("/wiki/view_orphan_pages");
	}

	public boolean isViewRecentChangesNavItemSelected() {
		return _isNavItemSelected("/wiki/view_recent_changes");
	}

	private boolean _isNavItemSelected(String navItemMVCRenderCommandName) {
		String mvcRenderCommandName =
			_wikiRequestHelper.getMVCRenderCommandName();

		return mvcRenderCommandName.equals(navItemMVCRenderCommandName);
	}

	private final WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;
	private final WikiPortletInstanceSettingsHelper
		_wikiPortletInstanceSettingsHelper;
	private final WikiRequestHelper _wikiRequestHelper;

}