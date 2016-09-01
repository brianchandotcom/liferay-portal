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

package com.liferay.layout.set.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.layout.set.model.adapter.StagedLayoutSet;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.model.adapter.StagedGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.layout.set.model.adapter.StagedLayoutSet"
	},
	service = {
		StagedLayoutSetStagedModelRepository.class, StagedModelRepository.class
	}
)
public class StagedLayoutSetStagedModelRepository
	extends BaseStagedModelRepository<StagedLayoutSet> {

	public StagedLayoutSet addStagedModel(
			PortletDataContext portletDataContext,
			StagedLayoutSet stagedLayoutSet)
		throws PortalException {

		// Not supported for layout sets

		return null;
	}

	public void deleteStagedModel(StagedLayoutSet stagedLayoutSet)
		throws PortalException {

		// Not supported for layout sets

	}

	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		// Not supported for layout sets

	}

	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		// Not supported for layout sets

	}

	public List<StagedModel> fetchChildrenStagedModels(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		List<Layout> layouts = _layoutLocalService.getLayouts(
			stagedLayoutSet.getGroupId(), stagedLayoutSet.isPrivateLayout());

		long[] layoutIds = portletDataContext.getLayoutIds();

		Stream<Layout> layoutsStream = layouts.stream();

		layoutsStream = layoutsStream.filter(
			(layout) -> ArrayUtil.contains(layoutIds, layout.getLayoutId()));

		return layoutsStream.collect(Collectors.toList());
	}

	public List<StagedModel> fetchDependencyStagedModels(
		PortletDataContext portletDataContext,
		StagedLayoutSet stagedLayoutSet) {

		List<StagedModel> dependencyStagedModels = new ArrayList<>();

		try {
			Group group = stagedLayoutSet.getGroup();

			dependencyStagedModels.add(
				ModelAdapterUtil.adapt(group, Group.class, StagedGroup.class));
		}
		catch (PortalException pe) {
		}

		return dependencyStagedModels;
	}

	public Optional<StagedLayoutSet> fetchExistingLayoutSet(
		long groupId, boolean privateLayout) {

		StagedLayoutSet stagedLayoutSet = null;

		try {
			LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
				groupId, privateLayout);

			stagedLayoutSet = ModelAdapterUtil.adapt(
				layoutSet, LayoutSet.class, StagedLayoutSet.class);
		}
		catch (PortalException pe) {
		}

		return Optional.ofNullable(stagedLayoutSet);
	}

	public StagedLayoutSet fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		boolean privateLayout = GetterUtil.getBoolean(uuid);

		try {
			LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
				groupId, privateLayout);

			return ModelAdapterUtil.adapt(
				layoutSet, LayoutSet.class, StagedLayoutSet.class);
		}
		catch (PortalException pe) {
			return null;
		}
	}

	public List<StagedLayoutSet> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		boolean privateLayout = GetterUtil.getBoolean(uuid);

		DynamicQuery dynamicQuery = _layoutSetLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		Property privateLayoutProperty = PropertyFactoryUtil.forName(
			"privateLayout");

		dynamicQuery.add(privateLayoutProperty.eq(privateLayout));

		List<LayoutSet> layoutSets = dynamicQuery.list();

		Stream<LayoutSet> layoutSetsStream = layoutSets.stream();

		Stream<StagedLayoutSet> stagedLayoutSetsStream = layoutSetsStream.map(
			(layoutSet) ->
				ModelAdapterUtil.adapt(
					layoutSet, LayoutSet.class, StagedLayoutSet.class));

		return stagedLayoutSetsStream.collect(Collectors.toList());
	}

	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return null;
	}

	public StagedLayoutSet saveStagedModel(StagedLayoutSet stagedLayoutSet)
		throws PortalException {

		LayoutSet layoutSet = _layoutSetLocalService.updateLayoutSet(
			stagedLayoutSet);

		return ModelAdapterUtil.adapt(
			layoutSet, LayoutSet.class, StagedLayoutSet.class);
	}

	public StagedLayoutSet updateStagedModel(
			PortletDataContext portletDataContext,
			StagedLayoutSet stagedLayoutSet)
		throws PortalException {

		LayoutSet existingLayoutSet = _layoutSetLocalService.fetchLayoutSet(
			stagedLayoutSet.getLayoutSetId());

		// Layout set prototype settings

		boolean layoutSetPrototypeSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS);

		if (!layoutSetPrototypeSettings ||
			Validator.isNull(stagedLayoutSet.getLayoutSetPrototypeUuid())) {

			existingLayoutSet.setLayoutSetPrototypeUuid(null);
			existingLayoutSet.setLayoutSetPrototypeLinkEnabled(false);

			existingLayoutSet = _layoutSetLocalService.updateLayoutSet(
				existingLayoutSet);
		}

		// Layout set settings

		boolean layoutSetSettings = MapUtil.getBoolean(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS);

		if (layoutSetSettings) {
			existingLayoutSet = _layoutSetLocalService.updateSettings(
				existingLayoutSet.getGroupId(),
				existingLayoutSet.isPrivateLayout(),
				stagedLayoutSet.getSettings());
		}

		return ModelAdapterUtil.adapt(
			existingLayoutSet, LayoutSet.class, StagedLayoutSet.class);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}