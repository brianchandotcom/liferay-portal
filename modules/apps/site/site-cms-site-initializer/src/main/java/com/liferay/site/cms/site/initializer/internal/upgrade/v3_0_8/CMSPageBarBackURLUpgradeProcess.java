/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.upgrade.v3_0_8;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Mikel Lorza
 */
public class CMSPageBarBackURLUpgradeProcess extends UpgradeProcess {

	public CMSPageBarBackURLUpgradeProcess(
		CompanyLocalService companyLocalService,
		FragmentEntryLinkLocalService fragmentEntryLinkLocalService,
		FragmentEntryLocalService fragmentEntryLocalService,
		GroupLocalService groupLocalService) {

		_companyLocalService = companyLocalService;
		_fragmentEntryLinkLocalService = fragmentEntryLinkLocalService;
		_fragmentEntryLocalService = fragmentEntryLocalService;
		_groupLocalService = groupLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(this::_upgradeCompany);
	}

	private void _upgradeCompany(long companyId) throws Exception {
		Group group = _groupLocalService.fetchGroup(
			companyId, GroupConstants.CMS);

		if (group == null) {
			return;
		}

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				group.getGroupId(), "page-bar");

		if (fragmentEntry == null) {
			return;
		}

		String html = fragmentEntry.getHtml();

		if (html.contains("p_l_back_url") ||
			!html.contains(_SIDEBAR_TRIGGER_HTML)) {

			return;
		}

		FragmentEntry draftFragmentEntry = _fragmentEntryLocalService.getDraft(
			fragmentEntry);

		draftFragmentEntry.setHtml(
			StringUtil.replace(
				html, _SIDEBAR_TRIGGER_HTML,
				StringBundler.concat(
					_SIDEBAR_TRIGGER_HTML, "\n\t\t\t",
					StringUtil.read(
						getClass(), "dependencies/page-bar-back-link.html"))));

		fragmentEntry = _fragmentEntryLocalService.publishDraft(
			_fragmentEntryLocalService.updateFragmentEntry(draftFragmentEntry));

		for (FragmentEntryLink fragmentEntryLink :
				_fragmentEntryLinkLocalService.
					getFragmentEntryLinksByFragmentEntry(
						group.getGroupId(), fragmentEntry)) {

			_fragmentEntryLinkLocalService.updateLatestChanges(
				fragmentEntry, fragmentEntryLink);
		}
	}

	private static final String _SIDEBAR_TRIGGER_HTML =
		"<lfr-drop-zone data-lfr-drop-zone-id=\"sidebarTrigger\">" +
			"</lfr-drop-zone>\n\t\t\t</li>";

	private final CompanyLocalService _companyLocalService;
	private final FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;
	private final FragmentEntryLocalService _fragmentEntryLocalService;
	private final GroupLocalService _groupLocalService;

}