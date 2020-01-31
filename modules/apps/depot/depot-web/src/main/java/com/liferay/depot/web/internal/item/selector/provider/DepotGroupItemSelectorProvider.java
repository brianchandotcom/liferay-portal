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

package com.liferay.depot.web.internal.item.selector.provider;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.web.internal.util.DepotSupportChecker;
import com.liferay.item.selector.provider.GroupItemSelectorProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupService;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = GroupItemSelectorProvider.class)
public class DepotGroupItemSelectorProvider
	implements GroupItemSelectorProvider {

	@Override
	public String getEmptyResultsMessage() {
		return "no-repositories-were-found";
	}

	@Override
	public List<Group> getGroups(
		long companyId, long groupId, String keywords, int start, int end) {

		try {
			List<DepotEntryGroupRel> depotEntryGroupRels =
				_depotEntryGroupRelService.getDepotEntryGroupRels(
					_getLiveGroupId(groupId), start, end);

			Stream<DepotEntryGroupRel> stream = depotEntryGroupRels.stream();

			return stream.map(
				this::_toGroupOptional
			).filter(
				Optional::isPresent
			).map(
				Optional::get
			).collect(
				Collectors.toList()
			);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return Collections.emptyList();
		}
	}

	@Override
	public int getGroupsCount(long companyId, long groupId, String keywords) {
		try {
			return _depotEntryGroupRelService.getDepotEntryGroupRelsCount(
				_getLiveGroupId(groupId));
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return 0;
		}
	}

	@Override
	public String getGroupType() {
		return "depot";
	}

	@Override
	public String getIcon() {
		return "repository";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "repository");
	}

	@Override
	public boolean isEnabled() {
		return _depotSupportChecker.isEnabled();
	}

	private long _getLiveGroupId(long groupId) throws PortalException {
		Group group = _groupService.getGroup(groupId);

		if (group.isStagingGroup()) {
			return group.getLiveGroupId();
		}

		return groupId;
	}

	private Optional<Group> _toGroupOptional(
		DepotEntryGroupRel depotEntryGroupRel) {

		try {
			DepotEntry depotEntry = _depotEntryLocalService.getDepotEntry(
				depotEntryGroupRel.getDepotEntryId());

			return Optional.of(_groupService.getGroup(depotEntry.getGroupId()));
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return Optional.empty();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotGroupItemSelectorProvider.class);

	@Reference
	private DepotEntryGroupRelService _depotEntryGroupRelService;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private DepotSupportChecker _depotSupportChecker;

	@Reference
	private GroupService _groupService;

	@Reference
	private Language _language;

}