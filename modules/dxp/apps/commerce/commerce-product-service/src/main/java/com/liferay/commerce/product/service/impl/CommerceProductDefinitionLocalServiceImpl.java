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
import com.liferay.commerce.product.model.CommerceProductDefinitionLocalization;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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

		// Commerce product definition

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

		// Commerce product definition localization

		_addCommerceProductDefinitionLocalizedFields(
			user.getCompanyId(), commerceProductDefinitionId, titleMap,
			descriptionMap);

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

		// Commerce product definition

		commerceProductDefinitionPersistence.remove(commerceProductDefinition);

		// Commerce product definition localization

		commerceProductDefinitionLocalizationPersistence.
			removeByCommerceProductDefinitionPK(
				commerceProductDefinition.getCommerceProductDefinitionId());

		// Commerce product definition option rels

		commerceProductDefinitionOptionRelLocalService.
			deleteCommerceProductDefinitionOptionRels(
				commerceProductDefinition.getCommerceProductDefinitionId());

		// Commerce product instances

		commerceProductInstanceLocalService.deleteCommerceProductInstances(
			commerceProductDefinition.getCommerceProductDefinitionId());

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
	public List<String> getCommerceProductDefinitionLocalizationLanguageIds(
		long commerceProductDefinitionId) {

			List<CommerceProductDefinitionLocalization>
				commerceProductDefinitionLocalizationList =
					commerceProductDefinitionLocalizationPersistence.
						findByCommerceProductDefinitionPK (
							commerceProductDefinitionId);

		List<String> availableLanguageIds = new ArrayList<>();

		for (CommerceProductDefinitionLocalization
				commerceProductDefinitionLocalization :
					commerceProductDefinitionLocalizationList) {

			availableLanguageIds.add(
				commerceProductDefinitionLocalization.getLanguageId());
		}

		return availableLanguageIds;
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
			ContentTypes.TEXT_PLAIN,
			commerceProductDefinition.getTitleMapAsXML(),
			commerceProductDefinition.getDescriptionMapAsXML(), null, null,
			null, 0, 0, priority);

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

		// Commerce product definition

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();
		CommerceProductDefinition commerceProductDefinition =
			commerceProductDefinitionPersistence.findByPrimaryKey(
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

		// Commerce product definition localization

		_updateCommerceProductDefinitionLocalizedFields(
			commerceProductDefinition.getCompanyId(),
			commerceProductDefinition.getCommerceProductDefinitionId(),
			titleMap, descriptionMap);

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

		// Commerce product definition

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

	private List<CommerceProductDefinitionLocalization>
			_addCommerceProductDefinitionLocalizedFields(
				long companyId, long commerceProductDefinitionId,
				Map<Locale, String> titleMap,
				Map<Locale, String> descriptionMap)
		throws PortalException {

		Set<Locale> localeSet = new HashSet<>();

		localeSet.addAll(titleMap.keySet());

		if (descriptionMap != null) {
			localeSet.addAll(descriptionMap.keySet());
		}

		List<CommerceProductDefinitionLocalization>
			commerceProductDefinitionLocalizations = new ArrayList<>();

		for (Locale locale : localeSet) {
			String title = titleMap.get(locale);
			String description = null;

			if (descriptionMap != null) {
				description = descriptionMap.get(locale);
			}

			if (Validator.isNull(title) && Validator.isNull(description)) {
				continue;
			}

			CommerceProductDefinitionLocalization
				commerceProductDefinitionLocalization =
					_addCommerceProductDefinitionLocalizedFields(
						companyId, commerceProductDefinitionId, title,
						description, LocaleUtil.toLanguageId(locale));

			commerceProductDefinitionLocalizations.add(
				commerceProductDefinitionLocalization);
		}

		return commerceProductDefinitionLocalizations;
	}

	private CommerceProductDefinitionLocalization
			_addCommerceProductDefinitionLocalizedFields(
				long companyId, long commerceProductDefinitionId, String title,
				String description, String languageId)
		throws PortalException {

		CommerceProductDefinitionLocalization
			commerceProductDefinitionLocalization =
				commerceProductDefinitionLocalizationPersistence.fetchByCPD_L(
					commerceProductDefinitionId, languageId);

		if (commerceProductDefinitionLocalization == null) {
			long commerceProductDefinitionLocalizationId =
				counterLocalService.increment();

			commerceProductDefinitionLocalization =
				commerceProductDefinitionLocalizationPersistence.create(
					commerceProductDefinitionLocalizationId);

			commerceProductDefinitionLocalization.setCompanyId(companyId);
			commerceProductDefinitionLocalization.
				setCommerceProductDefinitionPK(commerceProductDefinitionId);
			commerceProductDefinitionLocalization.setTitle(title);
			commerceProductDefinitionLocalization.setDescription(description);
			commerceProductDefinitionLocalization.setLanguageId(languageId);
		}
		else {
			commerceProductDefinitionLocalization.setTitle(title);
			commerceProductDefinitionLocalization.setDescription(description);
		}

		return commerceProductDefinitionLocalizationPersistence.update(
			commerceProductDefinitionLocalization);
	}

	private List<CommerceProductDefinitionLocalization>
			_updateCommerceProductDefinitionLocalizedFields(
				long companyId, long commerceProductDefinitionId,
				Map<Locale, String> titleMap,
				Map<Locale, String> descriptionMap)
		throws PortalException {

		List<CommerceProductDefinitionLocalization>
			oldCommerceProductDefinitionLocalizations = new ArrayList<>(
				commerceProductDefinitionLocalizationPersistence.
					findByCommerceProductDefinitionPK(
						commerceProductDefinitionId));

		List<CommerceProductDefinitionLocalization>
			newCommerceProductDefinitionLocalizations =
				_addCommerceProductDefinitionLocalizedFields(
					companyId, commerceProductDefinitionId, titleMap,
					descriptionMap);

		oldCommerceProductDefinitionLocalizations.removeAll(
			newCommerceProductDefinitionLocalizations);

		for (CommerceProductDefinitionLocalization
				oldCommerceProductDefinitionLocalization :
					oldCommerceProductDefinitionLocalizations) {

			commerceProductDefinitionLocalizationPersistence.remove(
				oldCommerceProductDefinitionLocalization);
		}

		return newCommerceProductDefinitionLocalizations;
	}

}