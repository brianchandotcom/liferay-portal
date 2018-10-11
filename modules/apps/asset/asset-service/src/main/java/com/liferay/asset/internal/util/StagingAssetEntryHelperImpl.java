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

package com.liferay.asset.internal.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.util.StagingAssetEntryHelper;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = StagingAssetEntryHelper.class)
public class StagingAssetEntryHelperImpl implements StagingAssetEntryHelper {

	@Override
	public void addAssetReference(
		PortletDataContext portletDataContext, ClassedModel classedModel,
		Element stagedElement, AssetEntry assetEntry) {

		AssetRenderer<? extends StagedModel> assetRenderer = null;
		StagedModel stagedModel = null;

		try {
			assetRenderer =
				(AssetRenderer<? extends StagedModel>)
					assetEntry.getAssetRenderer();

			stagedModel = assetRenderer.getAssetObject();
		}
		catch (Exception e) {
			return;
		}

		if (stagedModel == null) {
			return;
		}

		portletDataContext.addReferenceElement(
			classedModel, stagedElement, stagedModel,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY_DISPOSABLE, true);
	}

	@Override
	public AssetEntry fetchAssetEntry(long groupId, String uuid)
		throws PortalException {

		DynamicQuery dynamicQuery = _assetEntryLocalService.dynamicQuery();

		Property classUuidProperty = PropertyFactoryUtil.forName("classUuid");

		dynamicQuery.add(classUuidProperty.eq(uuid));

		List<AssetEntry> assetEntries = _assetEntryLocalService.dynamicQuery(
			dynamicQuery);

		if (ListUtil.isEmpty(assetEntries)) {
			return null;
		}

		Map<Long, AssetEntry> assetEntryMap = new HashMap<>();

		for (AssetEntry assetEntry : assetEntries) {
			assetEntryMap.put(assetEntry.getGroupId(), assetEntry);
		}

		// Try to fetch the existing staged model from the importing group

		if (assetEntryMap.containsKey(groupId)) {
			return assetEntryMap.get(groupId);
		}

		// Try to fetch the existing staged model from parent sites

		Group group = _groupLocalService.getGroup(groupId);

		Group parentGroup = group.getParentGroup();

		while (parentGroup != null) {
			if (assetEntryMap.containsKey(parentGroup.getGroupId())) {
				AssetEntry assetEntry = assetEntryMap.get(
					parentGroup.getGroupId());

				if (isAssetEntryApplicable(assetEntry)) {
					return assetEntry;
				}
			}

			parentGroup = parentGroup.getParentGroup();
		}

		// Try to fetch the existing staged model from the global site

		Group companyGroup = _groupLocalService.fetchCompanyGroup(
			group.getCompanyId());

		if (assetEntryMap.containsKey(companyGroup.getGroupId())) {
			return assetEntryMap.get(companyGroup.getGroupId());
		}

		// Try to fetch the existing staged model from the company

		Stream<AssetEntry> assetEntryStream = assetEntries.stream();

		List<AssetEntry> companyAssetEntries = assetEntryStream.filter(
			entry -> entry.getCompanyId() == group.getCompanyId()
		).collect(
			Collectors.toList()
		);

		if (ListUtil.isEmpty(companyAssetEntries)) {
			return null;
		}

		for (AssetEntry assetEntry : companyAssetEntries) {
			try {
				if (isAssetEntryApplicable(assetEntry)) {
					return assetEntry;
				}
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}
		}

		return null;
	}

	@Override
	public boolean isAssetEntryApplicable(AssetEntry assetEntry)
		throws PortalException {

		AssetRenderer<? extends StagedModel> assetRenderer = null;

		StagedModel stagedModel = null;

		try {
			assetRenderer =
				(AssetRenderer<? extends StagedModel>)
					assetEntry.getAssetRenderer();

			stagedModel = assetRenderer.getAssetObject();
		}
		catch (Exception e) {
			return false;
		}

		if (stagedModel instanceof TrashedModel) {
			TrashedModel trashedModel = (TrashedModel)stagedModel;

			if (trashedModel.isInTrash()) {
				return false;
			}
		}

		if (stagedModel instanceof StagedGroupedModel) {
			StagedGroupedModel stagedGroupedModel =
				(StagedGroupedModel)stagedModel;

			Group group = _groupLocalService.getGroup(
				stagedGroupedModel.getGroupId());

			if (group.isStagingGroup()) {
				return false;
			}
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingAssetEntryHelperImpl.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}