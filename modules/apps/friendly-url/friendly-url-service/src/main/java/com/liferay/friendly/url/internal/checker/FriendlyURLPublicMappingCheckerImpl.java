/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.checker;

import com.liferay.friendly.url.checker.FriendlyURLPublicMappingChecker;
import com.liferay.friendly.url.checker.FriendlyURLPublicMappingConflict;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
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
@Component(service = FriendlyURLPublicMappingChecker.class)
public class FriendlyURLPublicMappingCheckerImpl
	implements FriendlyURLPublicMappingChecker {

	@Override
	public List<FriendlyURLPublicMappingConflict>
		getFriendlyURLMappingConflicts(long companyId) {

		Group defaultGroup = _groupLocalService.fetchGroup(
			companyId, PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

		if (defaultGroup == null) {
			return Collections.emptyList();
		}

		Map<String, Group> friendlyURLGroupMap = new HashMap<>();

		for (Group group :
				_groupLocalService.getGroups(
					companyId, GroupConstants.ANY_PARENT_GROUP_ID, true)) {

			friendlyURLGroupMap.put(group.getFriendlyURL(), group);

			for (SiteFriendlyURL siteFriendlyURL :
					_siteFriendlyURLLocalService.getSiteFriendlyURLs(
						companyId, group.getGroupId())) {

				friendlyURLGroupMap.put(
					siteFriendlyURL.getFriendlyURL(), group);
			}
		}

		List<FriendlyURLPublicMappingConflict> conflicts = new ArrayList<>();

		_collectGroupConflicts(conflicts, friendlyURLGroupMap);

		_collectLayoutConflicts(conflicts, defaultGroup, friendlyURLGroupMap);

		conflicts.sort(_comparator);

		return conflicts;
	}

	private void _collectGroupConflicts(
		List<FriendlyURLPublicMappingConflict> conflicts,
		Map<String, Group> friendlyURLGroupMap) {

		for (Map.Entry<String, Group> entry : friendlyURLGroupMap.entrySet()) {
			String friendlyURL = entry.getKey();

			if (!FriendlyURLKeywordsUtil.hasFriendlyURLKeyword(friendlyURL)) {
				continue;
			}

			Group group = entry.getValue();

			conflicts.add(
				new FriendlyURLPublicMappingConflict(
					Group.class.getName(), group.getGroupId(), null, null,
					friendlyURL, group.getName(LocaleUtil.getSiteDefault()),
					FriendlyURLPublicMappingConflict.TYPE_RESERVED_KEYWORD));
		}
	}

	private void _collectLayoutConflict(
		List<FriendlyURLPublicMappingConflict> conflicts, String friendlyURL,
		Map<String, Group> friendlyURLGroupMap, long groupId, String name,
		long plid) {

		if (Validator.isNull(friendlyURL)) {
			return;
		}

		if (FriendlyURLKeywordsUtil.hasFriendlyURLKeyword(friendlyURL)) {
			conflicts.add(
				new FriendlyURLPublicMappingConflict(
					Layout.class.getName(), plid, null, null, friendlyURL, name,
					FriendlyURLPublicMappingConflict.TYPE_RESERVED_KEYWORD));
		}

		Group group = friendlyURLGroupMap.get(friendlyURL);

		if (group == null) {
			return;
		}

		if (group.getGroupId() == groupId) {
			conflicts.add(
				new FriendlyURLPublicMappingConflict(
					Layout.class.getName(), plid, null, null, friendlyURL, name,
					FriendlyURLPublicMappingConflict.TYPE_SELF));
		}
		else {
			conflicts.add(
				new FriendlyURLPublicMappingConflict(
					Layout.class.getName(), plid, group.getGroupId(),
					group.getName(LocaleUtil.getSiteDefault()), friendlyURL,
					name, FriendlyURLPublicMappingConflict.TYPE_CROSS_SITE));
		}
	}

	private void _collectLayoutConflicts(
		List<FriendlyURLPublicMappingConflict> conflicts, Group group,
		Map<String, Group> friendlyURLGroupMap) {

		List<Layout> layouts = _layoutLocalService.getLayouts(
			group.getGroupId(), false);

		if (layouts.isEmpty()) {
			return;
		}

		long classNameId = _layoutFriendlyURLEntryHelper.getClassNameId(false);
		long groupId = group.getGroupId();

		for (Layout layout : layouts) {
			String name = layout.getName(LocaleUtil.getSiteDefault());

			long plid = layout.getPlid();

			FriendlyURLEntry friendlyURLEntry =
				_friendlyURLEntryLocalService.fetchMainFriendlyURLEntry(
					classNameId, plid);

			if (friendlyURLEntry == null) {
				_collectLayoutConflict(
					conflicts, layout.getFriendlyURL(), friendlyURLGroupMap,
					groupId, name, plid);

				continue;
			}

			List<FriendlyURLEntryLocalization> friendlyURLEntryLocalizations =
				_friendlyURLEntryLocalService.getFriendlyURLEntryLocalizations(
					friendlyURLEntry.getFriendlyURLEntryId());

			for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
					friendlyURLEntryLocalizations) {

				_collectLayoutConflict(
					conflicts, friendlyURLEntryLocalization.getUrlTitle(),
					friendlyURLGroupMap, groupId, name, plid);
			}
		}
	}

	private static final Comparator<FriendlyURLPublicMappingConflict>
		_comparator = Comparator.comparing(
			FriendlyURLPublicMappingConflict::getType
		).thenComparing(
			FriendlyURLPublicMappingConflict::getFriendlyURL
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