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

package com.liferay.site.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.site.model.adapter.StagedGroup;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.site.model.adapter.StagedGroup"},
	service =
		{StagedGroupStagedModelRepository.class, StagedModelRepository.class}
)
public class StagedGroupStagedModelRepository
	extends BaseStagedModelRepository<StagedGroup> {

	@Override
	public StagedGroup addStagedModel(
			PortletDataContext portletDataContext, StagedGroup stagedGroup)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(StagedGroup stagedGroup)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	public List<StagedModel> fetchChildrenStagedModels(
		PortletDataContext portletDataContext, StagedGroup stagedGroup) {

		List<StagedModel> childrenStagedModels = new ArrayList<>();

		LayoutSet layoutSet = null;

		try {
			layoutSet = _layoutSetLocalService.getLayoutSet(
				stagedGroup.getGroupId(), portletDataContext.isPrivateLayout());

			childrenStagedModels.add(
				ModelAdapterUtil.adapt(
					layoutSet, LayoutSet.class, StagedLayoutSet.class));
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to fetch Layout Set with groupId " +
					stagedGroup.getGroupId() + " and private layout " +
						portletDataContext.isPrivateLayout(),
				pe);
		}

		return childrenStagedModels;
	}

	public Group fetchExistingGroup(
		PortletDataContext portletDataContext, Element referenceElement) {

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));
		long liveGroupId = GetterUtil.getLong(
			referenceElement.attributeValue("live-group-id"));

		if ((groupId == 0) || (liveGroupId == 0)) {
			return null;
		}

		return fetchExistingGroup(portletDataContext, groupId, liveGroupId);
	}

	public Group fetchExistingGroup(
		PortletDataContext portletDataContext, long groupId, long liveGroupId) {

		Group liveGroup = _groupLocalService.fetchGroup(liveGroupId);

		if (liveGroup != null) {
			return liveGroup;
		}

		long existingGroupId = portletDataContext.getScopeGroupId();

		if (groupId == portletDataContext.getSourceCompanyGroupId()) {
			existingGroupId = portletDataContext.getCompanyGroupId();
		}
		else if (groupId == portletDataContext.getSourceGroupId()) {
			existingGroupId = portletDataContext.getGroupId();
		}

		// During remote staging, valid mappings are found when the reference's
		// group is properly staged. During local staging, valid mappings are
		// found when the references do not change between staging and live.

		return _groupLocalService.fetchGroup(existingGroupId);
	}

	@Override
	public StagedGroup fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		Group group = _groupLocalService.fetchGroup(groupId);

		return ModelAdapterUtil.adapt(group, Group.class, StagedGroup.class);
	}

	@Override
	public List<StagedGroup> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public StagedGroup saveStagedModel(StagedGroup stagedGroup)
		throws PortalException {

		Group group = _groupLocalService.updateGroup(stagedGroup);

		return ModelAdapterUtil.adapt(group, Group.class, StagedGroup.class);
	}

	@Override
	public StagedGroup updateStagedModel(
			PortletDataContext portletDataContext, StagedGroup stagedGroup)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			stagedGroup);

		Group group = _groupLocalService.updateGroup(
			stagedGroup.getGroupId(), stagedGroup.getParentGroupId(),
			stagedGroup.getNameMap(), stagedGroup.getDescriptionMap(),
			stagedGroup.getType(), stagedGroup.getManualMembership(),
			stagedGroup.getMembershipRestriction(),
			stagedGroup.getFriendlyURL(), stagedGroup.isInheritContent(),
			stagedGroup.isActive(), serviceContext);

		return ModelAdapterUtil.adapt(group, Group.class, StagedGroup.class);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedGroupStagedModelRepository.class);

	@Reference(unbind = "-")
	private GroupLocalService _groupLocalService;

	@Reference(unbind = "-")
	private LayoutSetLocalService _layoutSetLocalService;

}