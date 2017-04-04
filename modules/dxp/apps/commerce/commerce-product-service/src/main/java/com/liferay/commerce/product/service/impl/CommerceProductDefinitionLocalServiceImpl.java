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

package com.liferay.commerce.product.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.commerce.product.exception.CommerceProductDefinitionDisplayDateException;
import com.liferay.commerce.product.exception.CommerceProductDefinitionExpirationDateException;
import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.service.base.CommerceProductDefinitionLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
@ProviderType
public class CommerceProductDefinitionLocalServiceImpl
	extends CommerceProductDefinitionLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceProductDefinition addCommerceProductDefinition(
			String baseSKU, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String productTypeName,
			String ddmStructureKey, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		// Product definition

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		Date displayDate = null;
		Date expirationDate = null;
		Date now = new Date();

		displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CommerceProductDefinitionDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommerceProductDefinitionExpirationDateException.class);
		}

		validateReferences(groupId, ddmStructureKey);

		long commerceProductDefinitionId = counterLocalService.increment();

		CommerceProductDefinition commerceProductDefinition =
			commerceProductDefinitionPersistence.create(
				commerceProductDefinitionId);

		commerceProductDefinition.setUuid(serviceContext.getUuid());
		commerceProductDefinition.setGroupId(groupId);
		commerceProductDefinition.setCompanyId(user.getCompanyId());
		commerceProductDefinition.setUserId(user.getUserId());
		commerceProductDefinition.setUserName(user.getFullName());
		commerceProductDefinition.setTitleMap(titleMap);
		commerceProductDefinition.setDescriptionMap(descriptionMap);
		commerceProductDefinition.setBaseSKU(baseSKU);
		commerceProductDefinition.setProductTypeName(productTypeName);
		commerceProductDefinition.setDDMStructureKey(ddmStructureKey);
		commerceProductDefinition.setDisplayDate(displayDate);
		commerceProductDefinition.setExpirationDate(expirationDate);

		if ((expirationDate == null) || expirationDate.after(now)) {
			commerceProductDefinition.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commerceProductDefinition.setStatus(
				WorkflowConstants.STATUS_EXPIRED);
		}

		commerceProductDefinition.setStatusByUserId(user.getUserId());
		commerceProductDefinition.setStatusDate(
			serviceContext.getModifiedDate(now));
		commerceProductDefinition.setExpandoBridgeAttributes(serviceContext);

		commerceProductDefinitionPersistence.update(commerceProductDefinition);

		// Resources

		resourceLocalService.addModelResources(
			commerceProductDefinition, serviceContext);

		// Asset

		updateAsset(
			user.getUserId(), commerceProductDefinition,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		// Workflow

		return startWorkflowInstance(
			user.getUserId(), commerceProductDefinition, serviceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceProductDefinition deleteCommerceProductDefinition(
			CommerceProductDefinition commerceProductDefinition)
		throws PortalException {

		// Product definition

		commerceProductDefinitionPersistence.remove(commerceProductDefinition);

		// Resources

		resourceLocalService.deleteResource(
			commerceProductDefinition.getCompanyId(),
			CommerceProductDefinition.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			commerceProductDefinition.getCommerceProductDefinitionId());

		// Asset

		assetEntryLocalService.deleteEntry(
			CommerceProductDefinition.class.getName(),
			commerceProductDefinition.getCommerceProductDefinitionId());

		// Expando

		expandoRowLocalService.deleteRows(
			commerceProductDefinition.getCommerceProductDefinitionId());

		// Trash

		trashEntryLocalService.deleteEntry(
			CommerceProductDefinition.class.getName(),
			commerceProductDefinition.getCommerceProductDefinitionId());

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			commerceProductDefinition.getCompanyId(),
			commerceProductDefinition.getGroupId(),
			CommerceProductDefinition.class.getName(),
			commerceProductDefinition.getCommerceProductDefinitionId());

		return commerceProductDefinition;
	}

	@Override
	public CommerceProductDefinition deleteCommerceProductDefinition(
			long commerceProductDefinitionId)
		throws PortalException {

		CommerceProductDefinition commerceProductDefinition =
			commerceProductDefinitionPersistence.findByPrimaryKey(
				commerceProductDefinitionId);

		return commerceProductDefinitionLocalService.
			deleteCommerceProductDefinition(commerceProductDefinition);
	}

	@Override
	public List<CommerceProductDefinition> getCommerceProductDefinitions(
		long groupId, int start, int end) {

		return commerceProductDefinitionPersistence.findByGroupId(
			groupId, start, end);
	}

	@Override
	public List<CommerceProductDefinition> getCommerceProductDefinitions(
		long groupId, int start, int end,
		OrderByComparator<CommerceProductDefinition> orderByComparator) {

		return commerceProductDefinitionPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceProductDefinitionsCount(long groupId) {
		return commerceProductDefinitionPersistence.countByGroupId(groupId);
	}

	@Override
	public void updateAsset(
			long userId, CommerceProductDefinition commerceProductDefinition,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds, Double priority)
		throws PortalException {

		AssetEntry assetEntry = assetEntryLocalService.updateEntry(
			userId, commerceProductDefinition.getGroupId(),
			commerceProductDefinition.getCreateDate(),
			commerceProductDefinition.getModifiedDate(),
			CommerceProductDefinition.class.getName(),
			commerceProductDefinition.getCommerceProductDefinitionId(),
			commerceProductDefinition.getUuid(), 0, assetCategoryIds,
			assetTagNames, true, true, null, null,
			commerceProductDefinition.getCreateDate(), null,
			ContentTypes.TEXT_PLAIN, commerceProductDefinition.getTitle(),
			commerceProductDefinition.getDescription(), null,
			commerceProductDefinition.getUrlTitle(), null, 0, 0, priority);

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceProductDefinition updateCommerceProductDefinition(
			long commerceProductDefinitionId, String baseSKU,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String productTypeName, String ddmStructureKey,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		// Product definition

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();
		CommerceProductDefinition commerceProductDefinition =
			commerceProductDefinitionPersistence.create(
				commerceProductDefinitionId);

		Date displayDate = null;
		Date expirationDate = null;
		Date now = new Date();

		displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CommerceProductDefinitionDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommerceProductDefinitionExpirationDateException.class);
		}

		validateReferences(groupId, ddmStructureKey);

		commerceProductDefinition.setTitleMap(titleMap);
		commerceProductDefinition.setDescriptionMap(descriptionMap);
		commerceProductDefinition.setBaseSKU(baseSKU);
		commerceProductDefinition.setProductTypeName(productTypeName);
		commerceProductDefinition.setDDMStructureKey(ddmStructureKey);
		commerceProductDefinition.setDisplayDate(displayDate);
		commerceProductDefinition.setExpirationDate(expirationDate);

		if ((expirationDate == null) || expirationDate.after(now)) {
			commerceProductDefinition.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commerceProductDefinition.setStatus(
				WorkflowConstants.STATUS_EXPIRED);
		}

		commerceProductDefinition.setStatusByUserId(user.getUserId());
		commerceProductDefinition.setStatusDate(
			serviceContext.getModifiedDate(now));
		commerceProductDefinition.setExpandoBridgeAttributes(serviceContext);

		commerceProductDefinitionPersistence.update(commerceProductDefinition);

		// Asset

		updateAsset(
			user.getUserId(), commerceProductDefinition,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		// Workflow

		return startWorkflowInstance(
			user.getUserId(), commerceProductDefinition, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceProductDefinition updateStatus(
			long userId, long commerceProductDefinitionId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		// CommerceProductDefinition

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		CommerceProductDefinition commerceProductDefinition =
			commerceProductDefinitionPersistence.findByPrimaryKey(
				commerceProductDefinitionId);

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(commerceProductDefinition.getDisplayDate() != null) &&
			now.before(commerceProductDefinition.getDisplayDate())) {

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		int oldStatus = commerceProductDefinition.getStatus();

		Date modifiedDate = serviceContext.getModifiedDate(now);

		commerceProductDefinition.setModifiedDate(modifiedDate);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			Date expirationDate = commerceProductDefinition.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(now)) {
				commerceProductDefinition.setExpirationDate(null);
			}
		}

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			commerceProductDefinition.setExpirationDate(now);
		}

		commerceProductDefinition.setStatus(status);
		commerceProductDefinition.setStatusByUserId(user.getUserId());
		commerceProductDefinition.setStatusByUserName(user.getFullName());
		commerceProductDefinition.setStatusDate(modifiedDate);

		commerceProductDefinitionPersistence.update(commerceProductDefinition);

		if (status == WorkflowConstants.STATUS_APPROVED) {

			// Asset

			assetEntryLocalService.updateEntry(
				CommerceProductDefinition.class.getName(),
				commerceProductDefinition.getCommerceProductDefinitionId(),
				commerceProductDefinition.getDisplayDate(),
				commerceProductDefinition.getExpirationDate(), true, true);
		}

		return commerceProductDefinition;
	}

	protected CommerceProductDefinition startWorkflowInstance(
			long userId, CommerceProductDefinition commerceProductDefinition,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			commerceProductDefinition.getCompanyId(),
			commerceProductDefinition.getGroupId(), userId,
			CommerceProductDefinition.class.getName(),
			commerceProductDefinition.getCommerceProductDefinitionId(),
			commerceProductDefinition, serviceContext, workflowContext);
	}

	protected void validateReferences(long groupId, String ddmStructureKey)
		throws PortalException {

		if (Validator.isNotNull(ddmStructureKey)) {
			long classNameId = classNameLocalService.getClassNameId(
				CommerceProductDefinition.class.getName());

			DDMStructure ddmStructure = ddmStructureLocalService.fetchStructure(
				groupId, classNameId, ddmStructureKey, true);

			if (ddmStructure == null) {
				throw new NoSuchStructureException();
			}
		}
	}

	@ServiceReference(type = DDMStructureLocalService.class)
	protected DDMStructureLocalService ddmStructureLocalService;

}