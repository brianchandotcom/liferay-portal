/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.friendly.url.verifier;

import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.layout.friendly.url.verifier.LayoutFriendlyURLPublicMappingConflict;
import com.liferay.layout.friendly.url.verifier.LayoutFriendlyURLPublicMappingVerifier;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.FriendlyURLKeywordsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.model.SiteFriendlyURL;
import com.liferay.site.service.SiteFriendlyURLLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = LayoutFriendlyURLPublicMappingVerifier.class)
public class LayoutFriendlyURLPublicMappingVerifierImpl
	implements LayoutFriendlyURLPublicMappingVerifier {

	@Override
	public List<LayoutFriendlyURLPublicMappingConflict> getConflicts(
		long companyId) {

		Group defaultGroup = _groupLocalService.fetchGroup(
			companyId, PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

		if (defaultGroup == null) {
			return Collections.emptyList();
		}

		List<Layout> layouts = _layoutLocalService.getLayouts(
			defaultGroup.getGroupId(), false);

		if (layouts.isEmpty()) {
			return Collections.emptyList();
		}

		Map<String, Group> siteFriendlyURLsMap = _getSiteFriendlyURLsMap(
			companyId);

		List<LayoutFriendlyURLPublicMappingConflict> conflicts =
			new ArrayList<>();

		long layoutClassNameId = _layoutFriendlyURLEntryHelper.getClassNameId(
			false);
		long defaultGroupId = defaultGroup.getGroupId();

		for (Layout layout : layouts) {
			String layoutName = layout.getName(LocaleUtil.getSiteDefault());

			long layoutPlid = layout.getPlid();

			FriendlyURLEntry friendlyURLEntry =
				_friendlyURLEntryLocalService.fetchMainFriendlyURLEntry(
					layoutClassNameId, layoutPlid);

			if (friendlyURLEntry == null) {
				_collectLayoutConflicts(
					conflicts, defaultGroupId, layoutName, layoutPlid,
					layout.getFriendlyURL(), siteFriendlyURLsMap);

				continue;
			}

			List<FriendlyURLEntryLocalization> friendlyURLEntryLocalizations =
				_friendlyURLEntryLocalService.getFriendlyURLEntryLocalizations(
					friendlyURLEntry.getFriendlyURLEntryId());

			for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
					friendlyURLEntryLocalizations) {

				_collectLayoutConflicts(
					conflicts, defaultGroupId, layoutName, layoutPlid,
					friendlyURLEntryLocalization.getUrlTitle(),
					siteFriendlyURLsMap);
			}
		}

		conflicts.sort(_comparator);

		return conflicts;
	}

	private void _collectLayoutConflicts(
		List<LayoutFriendlyURLPublicMappingConflict> conflicts,
		long defaultGroupId, String layoutName, long layoutPlid, String pageURL,
		Map<String, Group> siteFriendlyURLsMap) {

		if (Validator.isNull(pageURL)) {
			return;
		}

		Group conflictingGroup = siteFriendlyURLsMap.get(pageURL);

		if (conflictingGroup != null) {
			if (conflictingGroup.getGroupId() == defaultGroupId) {
				conflicts.add(
					new LayoutFriendlyURLPublicMappingConflict(
						layoutPlid, layoutName, pageURL,
						LayoutFriendlyURLPublicMappingConflict.Type.SELF, null,
						null));
			}
			else {
				conflicts.add(
					new LayoutFriendlyURLPublicMappingConflict(
						layoutPlid, layoutName, pageURL,
						LayoutFriendlyURLPublicMappingConflict.Type.CROSS_SITE,
						conflictingGroup.getGroupId(),
						conflictingGroup.getName(LocaleUtil.getSiteDefault())));
			}
		}

		if (FriendlyURLKeywordsUtil.hasFriendlyURLKeyword(pageURL)) {
			conflicts.add(
				new LayoutFriendlyURLPublicMappingConflict(
					layoutPlid, layoutName, pageURL,
					LayoutFriendlyURLPublicMappingConflict.Type.
						RESERVED_KEYWORD,
					null, null));
		}
	}

	private Map<String, Group> _getSiteFriendlyURLsMap(long companyId) {
		Map<String, Group> siteFriendlyURLsMap = new HashMap<>();

		for (Group siteGroup :
				_groupLocalService.getGroups(
					companyId, GroupConstants.ANY_PARENT_GROUP_ID, true)) {

			String siteGroupFriendlyURL = siteGroup.getFriendlyURL();

			if (Validator.isNotNull(siteGroupFriendlyURL)) {
				siteFriendlyURLsMap.put(siteGroupFriendlyURL, siteGroup);
			}

			for (SiteFriendlyURL siteFriendlyURL :
					_siteFriendlyURLLocalService.getSiteFriendlyURLs(
						companyId, siteGroup.getGroupId())) {

				siteFriendlyURLsMap.put(
					siteFriendlyURL.getFriendlyURL(), siteGroup);
			}
		}

		return siteFriendlyURLsMap;
	}

	private final Comparator<LayoutFriendlyURLPublicMappingConflict>
		_comparator = Comparator.comparing(
			LayoutFriendlyURLPublicMappingConflict::getType
		).thenComparing(
			LayoutFriendlyURLPublicMappingConflict::getPageURL
		);

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private SiteFriendlyURLLocalService _siteFriendlyURLLocalService;

}