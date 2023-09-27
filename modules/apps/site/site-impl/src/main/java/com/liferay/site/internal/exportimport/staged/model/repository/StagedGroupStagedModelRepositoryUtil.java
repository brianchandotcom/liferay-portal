/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.site.model.adapter.StagedGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joao Victor Alves
 */
public class StagedGroupStagedModelRepositoryUtil {

	public static List<StagedModel> fetchChildrenStagedModels(
		PortletDataContext portletDataContext, StagedGroup stagedGroup) {

		List<StagedModel> childrenStagedModels = new ArrayList<>();

		Group group = stagedGroup.getGroup();

		long groupId = group.getGroupId();

		try {
			LayoutSetLocalService layoutSetLocalService =
				_layoutSetLocalServiceSnapshot.get();

			childrenStagedModels.add(
				ModelAdapterUtil.adapt(
					layoutSetLocalService.getLayoutSet(
						groupId, portletDataContext.isPrivateLayout()),
					LayoutSet.class, StagedLayoutSet.class));
		}
		catch (PortalException portalException) {
			_log.error(
				StringBundler.concat(
					"Unable to fetch Layout Set with groupId ", groupId,
					" and private layout ",
					portletDataContext.isPrivateLayout()),
				portalException);
		}

		return childrenStagedModels;
	}

	public static Group fetchExistingGroup(
		PortletDataContext portletDataContext, Element referenceElement) {

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));
		long liveGroupId = GetterUtil.getLong(
			referenceElement.attributeValue("live-group-id"));

		if ((groupId == 0) || (liveGroupId == 0)) {
			return null;
		}

		String groupKey = GetterUtil.getString(
			referenceElement.attributeValue("group-key"));

		return fetchExistingGroup(
			portletDataContext, groupId, liveGroupId, groupKey);
	}

	public static Group fetchExistingGroup(
		PortletDataContext portletDataContext, long groupId, long liveGroupId) {

		return fetchExistingGroup(
			portletDataContext, groupId, liveGroupId, null);
	}

	public static Group fetchExistingGroup(
		PortletDataContext portletDataContext, long groupId, long liveGroupId,
		String groupKey) {

		GroupLocalService groupLocalService = _groupLocalServiceSnapshot.get();

		Group liveGroup = groupLocalService.fetchGroup(liveGroupId);

		if ((liveGroup != null) &&
			(liveGroup.getCompanyId() == portletDataContext.getCompanyId())) {

			return liveGroup;
		}

		long existingGroupId = 0;

		if (groupId == portletDataContext.getSourceCompanyGroupId()) {
			existingGroupId = portletDataContext.getCompanyGroupId();
		}
		else if (groupId == portletDataContext.getSourceGroupId()) {
			existingGroupId = portletDataContext.getGroupId();
		}
		else if (Validator.isNotNull(groupKey)) {
			Group groupKeyGroup = groupLocalService.fetchGroup(
				portletDataContext.getCompanyId(), groupKey);

			if (groupKeyGroup != null) {
				existingGroupId = groupKeyGroup.getGroupId();
			}
		}

		// During remote staging, valid mappings are found when the reference's
		// group is properly staged. During local staging, valid mappings are
		// found when the references do not change between staging and live.

		Group group = groupLocalService.fetchGroup(existingGroupId);

		if ((group != null) &&
			(group.getCompanyId() == portletDataContext.getCompanyId())) {

			return group;
		}

		return groupLocalService.fetchGroup(
			portletDataContext.getScopeGroupId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedGroupStagedModelRepositoryUtil.class);

	private static final Snapshot<GroupLocalService>
		_groupLocalServiceSnapshot = new Snapshot<>(
			StagedGroupStagedModelRepositoryUtil.class,
			GroupLocalService.class);
	private static final Snapshot<LayoutSetLocalService>
		_layoutSetLocalServiceSnapshot = new Snapshot<>(
			StagedGroupStagedModelRepositoryUtil.class,
			LayoutSetLocalService.class);

}