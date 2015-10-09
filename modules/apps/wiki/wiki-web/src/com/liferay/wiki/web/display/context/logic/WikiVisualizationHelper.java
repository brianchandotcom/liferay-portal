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

package com.liferay.wiki.web.display.context.logic;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.web.display.context.util.WikiRequestHelper;

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

		String strutsAction = _wikiRequestHelper.getStrutsAction();

		String frontPageName = _wikiGroupServiceConfiguration.frontPageName();

		if (Validator.isNull(strutsAction) ||
			(wikiPage != null) && frontPageName.equals(wikiPage.getTitle())) {

			return true;
		}

		return false;
	}

	public boolean isNodeNameVisible() {
		String portletId = _wikiRequestHelper.getPortletId();

		return portletId.equals(WikiPortletKeys.WIKI_ADMIN);
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
		String strutsAction = _wikiRequestHelper.getStrutsAction();

		if (strutsAction.endsWith("view_page_activities") ||
			strutsAction.endsWith("view_page_attachments")) {

			return false;
		}

		return true;
	}

	public boolean isViewAllPagesNavItemSelected() {
		return isNavItemSelected("/view_all_pages");
	}

	public boolean isViewDraftPagesNavItemSelected() {
		return isNavItemSelected("/view_draft_pages");
	}

	public boolean isViewOrphanPagesNavItemSelected() {
		return isNavItemSelected("/view_orphan_pages");
	}

	public boolean isViewRecentChangesNavItemSelected() {
		return isNavItemSelected("/view_recent_changes");
	}

	protected String getStrutsPath() {
		if (_strutsPath == null) {
			String strutsAction = _wikiRequestHelper.getStrutsAction();

			if (Validator.isNotNull(strutsAction)) {
				int pos = strutsAction.indexOf(StringPool.SLASH, 1);

				if (pos != -1) {
					_strutsPath = strutsAction.substring(0, pos);
				}
			}
		}

		return _strutsPath;
	}

	protected boolean isNavItemSelected(String navItemStrutsAction) {
		String strutsAction = _wikiRequestHelper.getStrutsAction();

		return strutsAction.equals(getStrutsPath() + navItemStrutsAction);
	}

	private String _strutsPath;
	private final WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;
	private final WikiPortletInstanceSettingsHelper
		_wikiPortletInstanceSettingsHelper;
	private final WikiRequestHelper _wikiRequestHelper;

}