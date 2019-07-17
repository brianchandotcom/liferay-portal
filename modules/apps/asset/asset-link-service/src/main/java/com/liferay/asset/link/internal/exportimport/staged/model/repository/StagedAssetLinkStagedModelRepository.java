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

package com.liferay.asset.link.internal.exportimport.staged.model.repository;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.adapter.StagedAssetLink;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.asset.util.StagingAssetEntryHelper;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.asset.kernel.model.adapter.StagedAssetLink",
	service = {
		StagedAssetLinkStagedModelRepository.class, StagedModelRepository.class
	}
)
public class StagedAssetLinkStagedModelRepository
	implements StagedModelRepository<StagedAssetLink> {

	@Override
	public StagedAssetLink addStagedModel(
			PortletDataContext portletDataContext,
			StagedAssetLink stagedAssetLink)
		throws PortalException {

		AssetEntry assetEntry1 = _stagingAssetEntryHelper.fetchAssetEntry(
			portletDataContext.getScopeGroupId(),
			stagedAssetLink.getEntry1Uuid());
		AssetEntry assetEntry2 = _stagingAssetEntryHelper.fetchAssetEntry(
			portletDataContext.getScopeGroupId(),
			stagedAssetLink.getEntry2Uuid());

		if ((assetEntry1 == null) || (assetEntry2 == null)) {
			return null;
		}

		AssetLink assetLink = _assetLinkLocalService.addLink(
			portletDataContext.getUserId(stagedAssetLink.getUserUuid()),
			assetEntry1.getEntryId(), assetEntry2.getEntryId(),
			stagedAssetLink.getType(), stagedAssetLink.getWeight());

		return ModelAdapterUtil.adapt(
			assetLink, AssetLink.class, StagedAssetLink.class);
	}

	@Override
	public void deleteStagedModel(StagedAssetLink stagedAssetLink)
		throws PortalException {

		_assetLinkLocalService.deleteLink(stagedAssetLink);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		StagedAssetLink stagedAssetLink = fetchExistingAssetLink(
			groupId, parseAssetEntry1Uuid(uuid), parseAssetEntry2Uuid(uuid));

		if (stagedAssetLink != null) {
			deleteStagedModel(stagedAssetLink);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_assetLinkLocalService.deleteGroupLinks(
			portletDataContext.getScopeGroupId());
	}

	public StagedAssetLink fetchExistingAssetLink(
			long groupId, String assetEntry1Uuid, String assetEntry2Uuid)
		throws PortalException {

		AssetEntry assetEntry1 = _stagingAssetEntryHelper.fetchAssetEntry(
			groupId, assetEntry1Uuid);
		AssetEntry assetEntry2 = _stagingAssetEntryHelper.fetchAssetEntry(
			groupId, assetEntry2Uuid);

		if ((assetEntry1 == null) || (assetEntry2 == null)) {
			return null;
		}

		DynamicQuery dynamicQuery = getAssetLinkDynamicQuery(
			assetEntry1.getEntryId(), assetEntry2.getEntryId());

		List<AssetLink> assetLinks = _assetLinkLocalService.dynamicQuery(
			dynamicQuery);

		if (ListUtil.isNotEmpty(assetLinks)) {
			return ModelAdapterUtil.adapt(
				assetLinks.get(0), AssetLink.class, StagedAssetLink.class);
		}

		return null;
	}

	@Override
	public StagedAssetLink fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return null;
	}

	@Override
	public List<StagedAssetLink> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		DynamicQuery dynamicQuery = getAssetLinkDynamicQuery(
			companyId, 0, parseAssetEntry1Uuid(uuid),
			parseAssetEntry2Uuid(uuid));

		dynamicQuery.addOrder(OrderFactoryUtil.desc("linkId"));

		List<AssetLink> assetLinks = _assetLinkLocalService.dynamicQuery(
			dynamicQuery);

		if (ListUtil.isNotEmpty(assetLinks)) {
			return ModelAdapterUtil.adapt(
				assetLinks, AssetLink.class, StagedAssetLink.class);
		}

		return Collections.emptyList();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return new ExportActionableDynamicQuery();
	}

	@Override
	public StagedAssetLink getStagedModel(long assetLinkId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			_assetLinkLocalService.getAssetLink(assetLinkId), AssetLink.class,
			StagedAssetLink.class);
	}

	@Override
	public StagedAssetLink saveStagedModel(StagedAssetLink stagedAssetLink)
		throws PortalException {

		AssetLink assetLink = _assetLinkLocalService.updateAssetLink(
			stagedAssetLink);

		return ModelAdapterUtil.adapt(
			assetLink, AssetLink.class, StagedAssetLink.class);
	}

	@Override
	public StagedAssetLink updateStagedModel(
			PortletDataContext portletDataContext,
			StagedAssetLink stagedAssetLink)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			stagedAssetLink.getUserUuid());

		AssetLink assetLink = _assetLinkLocalService.updateLink(
			userId, stagedAssetLink.getEntryId1(),
			stagedAssetLink.getEntryId2(), stagedAssetLink.getType(),
			stagedAssetLink.getWeight());

		return ModelAdapterUtil.adapt(
			assetLink, AssetLink.class, StagedAssetLink.class);
	}

	protected DynamicQuery getAssetLinkDynamicQuery(
		long entryId1, long entryId2) {

		DynamicQuery dynamicQuery = _assetLinkLocalService.dynamicQuery();

		Property entryId1IdProperty = PropertyFactoryUtil.forName("entryId1");

		dynamicQuery.add(entryId1IdProperty.eq(entryId1));

		Property entryId2IdProperty = PropertyFactoryUtil.forName("entryId2");

		dynamicQuery.add(entryId2IdProperty.eq(entryId2));

		return dynamicQuery;
	}

	protected DynamicQuery getAssetLinkDynamicQuery(
		long companyId, long groupId, String assetEntry1Uuid,
		String assetEntry2Uuid) {

		// Asset entry 1 dynamic query

		Projection entryIdProjection = ProjectionFactoryUtil.property(
			"entryId");

		DynamicQuery assetEntry1DynamicQuery =
			_assetEntryLocalService.dynamicQuery();

		assetEntry1DynamicQuery.setProjection(entryIdProjection);

		Property classUuidProperty = PropertyFactoryUtil.forName("classUuid");

		assetEntry1DynamicQuery.add(classUuidProperty.eq(assetEntry1Uuid));

		// Asset entry 2 dynamic query

		DynamicQuery assetEntry2DynamicQuery =
			_assetEntryLocalService.dynamicQuery();

		assetEntry2DynamicQuery.setProjection(entryIdProjection);

		assetEntry2DynamicQuery.add(classUuidProperty.eq(assetEntry2Uuid));

		// Asset link dynamic query

		DynamicQuery dynamicQuery = _assetLinkLocalService.dynamicQuery();

		Property entryId1IdProperty = PropertyFactoryUtil.forName("entryId1");

		dynamicQuery.add(entryId1IdProperty.eq(assetEntry1DynamicQuery));

		Property entryId2IdProperty = PropertyFactoryUtil.forName("entryId2");

		dynamicQuery.add(entryId2IdProperty.eq(assetEntry2DynamicQuery));

		// Company ID

		if (companyId > 0) {
			Property companyIdProperty = PropertyFactoryUtil.forName(
				"companyId");

			Criterion companyIdCriterion = companyIdProperty.eq(companyId);

			assetEntry1DynamicQuery.add(companyIdCriterion);
			assetEntry2DynamicQuery.add(companyIdCriterion);
			dynamicQuery.add(companyIdCriterion);
		}

		// Group ID

		if (groupId > 0) {
			Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

			Criterion groupIdCriterion = groupIdProperty.eq(groupId);

			assetEntry1DynamicQuery.add(groupIdCriterion);
			assetEntry2DynamicQuery.add(groupIdCriterion);
		}

		return dynamicQuery;
	}

	protected String parseAssetEntry1Uuid(String uuid) {
		return uuid.substring(0, uuid.indexOf(StringPool.POUND));
	}

	protected String parseAssetEntry2Uuid(String uuid) {
		return uuid.substring(uuid.indexOf(StringPool.POUND) + 1);
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

	@Reference
	private StagingAssetEntryHelper _stagingAssetEntryHelper;

}