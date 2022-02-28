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

package com.liferay.commerce.term.service.impl;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifierTable;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifierTable;
import com.liferay.commerce.term.constants.CommerceTermEntryConstants;
import com.liferay.commerce.term.exception.CommerceTermEntryDisplayDateException;
import com.liferay.commerce.term.exception.CommerceTermEntryExpirationDateException;
import com.liferay.commerce.term.exception.CommerceTermEntryNameException;
import com.liferay.commerce.term.exception.CommerceTermEntryPriorityException;
import com.liferay.commerce.term.exception.CommerceTermEntryTypeException;
import com.liferay.commerce.term.model.CTermEntryLocalization;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.model.CommerceTermEntryRelTable;
import com.liferay.commerce.term.model.CommerceTermEntryTable;
import com.liferay.commerce.term.service.CommerceTermEntryRelLocalService;
import com.liferay.commerce.term.service.base.CommerceTermEntryLocalServiceBaseImpl;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.term.model.CommerceTermEntry",
	service = AopService.class
)
public class CommerceTermEntryLocalServiceImpl
	extends CommerceTermEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTermEntry addCommerceTermEntry(
			String externalReferenceCode, long userId, boolean active,
			Map<Locale, String> descriptionMap, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, Map<Locale, String> labelMap, String name,
			double priority, String type, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		name = FriendlyURLNormalizerUtil.normalize(name);

		_validate(null, serviceContext.getCompanyId(), name, priority, type);

		CommerceTermEntry commerceTermEntry =
			commerceTermEntryPersistence.create(
				counterLocalService.increment());

		commerceTermEntry.setExternalReferenceCode(externalReferenceCode);

		User user = userLocalService.getUser(userId);

		commerceTermEntry.setCompanyId(user.getCompanyId());
		commerceTermEntry.setUserId(user.getUserId());
		commerceTermEntry.setUserName(user.getFullName());

		commerceTermEntry.setActive(active);
		commerceTermEntry.setDisplayDate(
			_portal.getDate(
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, user.getTimeZone(),
				CommerceTermEntryDisplayDateException.class));

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = _portal.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommerceTermEntryExpirationDateException.class);
		}

		commerceTermEntry.setExpirationDate(expirationDate);

		commerceTermEntry.setName(name);
		commerceTermEntry.setPriority(priority);
		commerceTermEntry.setType(type);

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				typeSettings
			).build();

		commerceTermEntry.setTypeSettingsUnicodeProperties(
			typeSettingsUnicodeProperties);

		Date date = new Date();

		if ((expirationDate == null) || expirationDate.after(date)) {
			commerceTermEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commerceTermEntry.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		commerceTermEntry.setStatusByUserId(user.getUserId());
		commerceTermEntry.setStatusDate(serviceContext.getModifiedDate(date));

		commerceTermEntry = commerceTermEntryPersistence.update(
			commerceTermEntry);

		// Commerce term entry localization

		_addCommerceTermEntryLocalizedFields(
			user.getCompanyId(), commerceTermEntry.getCommerceTermEntryId(),
			descriptionMap, labelMap);

		// Resource

		resourceLocalService.addModelResources(
			commerceTermEntry, serviceContext);

		// Workflow

		return _startWorkflowInstance(
			user.getUserId(), commerceTermEntry, serviceContext);
	}

	@Override
	public void checkCommerceTermEntries() throws PortalException {
		_checkCommerceTermEntriesByDisplayDate();
		_checkCommerceTermEntriesByExpirationDate();
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceTermEntry deleteCommerceTermEntry(
			CommerceTermEntry commerceTermEntry)
		throws PortalException {

		commerceTermEntryPersistence.remove(commerceTermEntry);

		resourceLocalService.deleteResource(
			commerceTermEntry.getCompanyId(), CommerceTermEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			commerceTermEntry.getCommerceTermEntryId());

		_commerceTermEntryRelLocalService.deleteCommerceTermEntryRels(
			commerceTermEntry.getCommerceTermEntryId());

		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			commerceTermEntry.getCompanyId(), 0L,
			CommerceTermEntry.class.getName(),
			commerceTermEntry.getCommerceTermEntryId());

		return commerceTermEntry;
	}

	@Override
	public CommerceTermEntry deleteCommerceTermEntry(long commerceTermEntryId)
		throws PortalException {

		CommerceTermEntry commerceTermEntry =
			commerceTermEntryPersistence.findByPrimaryKey(commerceTermEntryId);

		return commerceTermEntryLocalService.deleteCommerceTermEntry(
			commerceTermEntry);
	}

	@Override
	public List<CommerceTermEntry> getCommerceTermEntries(
		long companyId, String type) {

		return commerceTermEntryPersistence.filterFindByC_A_LikeType(
			companyId, true, type);
	}

	@Override
	public List<String> getCTermEntryLocalizationLanguageIds(
		long commerceTermEntryId) {

		List<CTermEntryLocalization> cTermEntryLocalizations =
			cTermEntryLocalizationPersistence.findByCommerceTermEntryId(
				commerceTermEntryId);

		List<String> availableLanguageIds = new ArrayList<>();

		for (CTermEntryLocalization cTermEntryLocalization :
				cTermEntryLocalizations) {

			availableLanguageIds.add(cTermEntryLocalization.getLanguageId());
		}

		return availableLanguageIds;
	}

	@Override
	public List<CommerceTermEntry> getDeliveryCommerceTermEntries(
		long companyId, long commerceOrderTypeId,
		long commerceShippingOptionId) {

		List<CommerceTermEntry> commerceTermEntries = new LinkedList<>();

		commerceTermEntries.addAll(
			dslQuery(
				_getDeliveryTermsEntryGroupByStep(
					companyId,
					(commerceOrderTypeId > 0) ? commerceOrderTypeId : null,
					commerceShippingOptionId,
					DSLQueryFactoryUtil.selectDistinct(
						CommerceTermEntryTable.INSTANCE)
				).orderBy(
					CommerceTermEntryTable.INSTANCE.priority.descending()
				)));

		if ((commerceOrderTypeId > 0) && commerceTermEntries.isEmpty()) {
			commerceTermEntries.addAll(
				dslQuery(
					_getDeliveryTermsEntryGroupByStep(
						companyId, null, commerceShippingOptionId,
						DSLQueryFactoryUtil.selectDistinct(
							CommerceTermEntryTable.INSTANCE)
					).orderBy(
						CommerceTermEntryTable.INSTANCE.priority.descending()
					)));
		}

		return commerceTermEntries;
	}

	@Override
	public List<CommerceTermEntry> getPaymentCommerceTermEntries(
		long companyId, long commerceOrderTypeId,
		long commercePaymentMethodGroupRelId) {

		List<CommerceTermEntry> commerceTermEntries = new LinkedList<>();

		commerceTermEntries.addAll(
			dslQuery(
				_getPaymentTermsEntryGroupByStep(
					companyId,
					(commerceOrderTypeId > 0) ? commerceOrderTypeId : null,
					commercePaymentMethodGroupRelId,
					DSLQueryFactoryUtil.selectDistinct(
						CommerceTermEntryTable.INSTANCE)
				).orderBy(
					CommerceTermEntryTable.INSTANCE.priority.descending()
				)));

		if ((commerceOrderTypeId > 0) && commerceTermEntries.isEmpty()) {
			commerceTermEntries.addAll(
				dslQuery(
					_getPaymentTermsEntryGroupByStep(
						companyId, null, commercePaymentMethodGroupRelId,
						DSLQueryFactoryUtil.selectDistinct(
							CommerceTermEntryTable.INSTANCE)
					).orderBy(
						CommerceTermEntryTable.INSTANCE.priority.descending()
					)));
		}

		return commerceTermEntries;
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CommerceTermEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(CommerceTermEntry.class);

			return indexer.search(searchContext);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Override
	public BaseModelSearchResult<CommerceTermEntry> searchCommerceTermEntries(
			long companyId, long accountEntryId, String type, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, accountEntryId, type, keywords, start, end, sort);

		return searchCommerceTermEntries(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTermEntry updateCommerceTermEntry(
			long userId, long commerceTermEntryId, boolean active,
			Map<Locale, String> descriptionMap, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, Map<Locale, String> labelMap, String name,
			double priority, String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		CommerceTermEntry commerceTermEntry =
			commerceTermEntryLocalService.getCommerceTermEntry(
				commerceTermEntryId);

		_validate(
			commerceTermEntry, serviceContext.getCompanyId(), name, priority,
			commerceTermEntry.getType());

		commerceTermEntry.setActive(active);

		User user = userLocalService.getUser(userId);

		commerceTermEntry.setDisplayDate(
			_portal.getDate(
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, user.getTimeZone(),
				CommerceTermEntryDisplayDateException.class));

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = _portal.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommerceTermEntryExpirationDateException.class);
		}

		commerceTermEntry.setExpirationDate(expirationDate);

		commerceTermEntry.setName(name);
		commerceTermEntry.setPriority(priority);

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.fastLoad(
				typeSettings
			).build();

		commerceTermEntry.setTypeSettingsUnicodeProperties(
			typeSettingsUnicodeProperties);

		Date date = new Date();

		if ((expirationDate == null) || expirationDate.after(date)) {
			commerceTermEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commerceTermEntry.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		commerceTermEntry.setStatusByUserId(user.getUserId());
		commerceTermEntry.setStatusDate(serviceContext.getModifiedDate(date));
		commerceTermEntry.setExpandoBridgeAttributes(serviceContext);

		commerceTermEntry = commerceTermEntryPersistence.update(
			commerceTermEntry);

		// Commerce term entry localization

		_updateCommerceTermEntryLocalizedFields(
			user.getCompanyId(), commerceTermEntryId, descriptionMap, labelMap);

		// Workflow

		return _startWorkflowInstance(
			user.getUserId(), commerceTermEntry, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTermEntry updateCommerceTermEntryExternalReferenceCode(
			String externalReferenceCode, long commerceTermEntryId)
		throws PortalException {

		CommerceTermEntry commerceTermEntry =
			commerceTermEntryPersistence.findByPrimaryKey(commerceTermEntryId);

		commerceTermEntry.setExternalReferenceCode(externalReferenceCode);

		return commerceTermEntryPersistence.update(commerceTermEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTermEntry updateStatus(
			long userId, long commerceTermEntryId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceTermEntry commerceTermEntry =
			commerceTermEntryPersistence.findByPrimaryKey(commerceTermEntryId);

		Date date = new Date();

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(commerceTermEntry.getDisplayDate() != null) &&
			date.before(commerceTermEntry.getDisplayDate())) {

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		if (status == WorkflowConstants.STATUS_APPROVED) {
			Date expirationDate = commerceTermEntry.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(date)) {
				commerceTermEntry.setExpirationDate(null);
			}
		}

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			commerceTermEntry.setActive(false);
			commerceTermEntry.setExpirationDate(date);
		}

		commerceTermEntry.setStatus(status);

		User user = userLocalService.getUser(userId);

		commerceTermEntry.setStatusByUserId(user.getUserId());
		commerceTermEntry.setStatusByUserName(user.getFullName());

		commerceTermEntry.setStatusDate(serviceContext.getModifiedDate(date));

		return commerceTermEntryPersistence.update(commerceTermEntry);
	}

	protected SearchContext buildSearchContext(
		long companyId, long accountEntryId, String type, String keywords,
		int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.NAME, keywords
			).put(
				Field.TYPE, type
			).put(
				"accountEntryId", accountEntryId
			).build());

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected List<CommerceTermEntry> getCommerceTermEntries(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommerceTermEntry> commerceTermEntries = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long commerceTermEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommerceTermEntry commerceTermEntry = fetchCommerceTermEntry(
				commerceTermEntryId);

			if (commerceTermEntry == null) {
				commerceTermEntries = null;

				Indexer<CommerceTermEntry> indexer =
					IndexerRegistryUtil.getIndexer(CommerceTermEntry.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commerceTermEntries != null) {
				commerceTermEntries.add(commerceTermEntry);
			}
		}

		return commerceTermEntries;
	}

	protected BaseModelSearchResult<CommerceTermEntry>
			searchCommerceTermEntries(SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceTermEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceTermEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CommerceTermEntry> commerceTermEntries =
				getCommerceTermEntries(hits);

			if (commerceTermEntries != null) {
				return new BaseModelSearchResult<>(
					commerceTermEntries, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	private List<CTermEntryLocalization> _addCommerceTermEntryLocalizedFields(
		long companyId, long commerceTermEntryId,
		Map<Locale, String> descriptionMap, Map<Locale, String> labelMap) {

		Set<Locale> localeSet = new HashSet<>();

		if (descriptionMap != null) {
			localeSet.addAll(descriptionMap.keySet());
		}

		if (labelMap != null) {
			localeSet.addAll(labelMap.keySet());
		}

		List<CTermEntryLocalization> cTermEntryLocalizations =
			new ArrayList<>();

		for (Locale locale : localeSet) {
			String description = null;
			String label = null;

			if (descriptionMap != null) {
				description = descriptionMap.get(locale);
			}

			if (labelMap != null) {
				label = labelMap.get(locale);
			}

			if (Validator.isNull(description) && Validator.isNull(label)) {
				continue;
			}

			CTermEntryLocalization cTermEntryLocalization =
				_addCommerceTermEntryLocalizedFields(
					companyId, commerceTermEntryId, description, label,
					LocaleUtil.toLanguageId(locale));

			cTermEntryLocalizations.add(cTermEntryLocalization);
		}

		return cTermEntryLocalizations;
	}

	private CTermEntryLocalization _addCommerceTermEntryLocalizedFields(
		long companyId, long commerceTermEntryId, String description,
		String label, String languageId) {

		CTermEntryLocalization cTermEntryLocalization =
			cTermEntryLocalizationPersistence.
				fetchByCommerceTermEntryId_LanguageId(
					commerceTermEntryId, languageId);

		if (cTermEntryLocalization == null) {
			cTermEntryLocalization = cTermEntryLocalizationPersistence.create(
				counterLocalService.increment());

			cTermEntryLocalization.setCompanyId(companyId);
			cTermEntryLocalization.setCommerceTermEntryId(commerceTermEntryId);
			cTermEntryLocalization.setDescription(description);
			cTermEntryLocalization.setLabel(label);
			cTermEntryLocalization.setLanguageId(languageId);
		}
		else {
			cTermEntryLocalization.setDescription(description);
			cTermEntryLocalization.setLabel(label);
		}

		return cTermEntryLocalizationPersistence.update(cTermEntryLocalization);
	}

	private void _checkCommerceTermEntriesByDisplayDate()
		throws PortalException {

		List<CommerceTermEntry> commerceTermEntries =
			commerceTermEntryPersistence.findByLtD_S(
				new Date(), WorkflowConstants.STATUS_SCHEDULED);

		for (CommerceTermEntry commerceTermEntry : commerceTermEntries) {
			long userId = _portal.getValidUserId(
				commerceTermEntry.getCompanyId(),
				commerceTermEntry.getUserId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCommand(Constants.UPDATE);

			commerceTermEntryLocalService.updateStatus(
				userId, commerceTermEntry.getCommerceTermEntryId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext);
		}
	}

	private void _checkCommerceTermEntriesByExpirationDate()
		throws PortalException {

		List<CommerceTermEntry> commerceTermEntries =
			commerceTermEntryPersistence.findByLtE_S(
				new Date(), WorkflowConstants.STATUS_APPROVED);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Expiring " + commerceTermEntries.size() +
					" commerce term entries");
		}

		for (CommerceTermEntry commerceTermEntry : commerceTermEntries) {
			long userId = _portal.getValidUserId(
				commerceTermEntry.getCompanyId(),
				commerceTermEntry.getUserId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCommand(Constants.UPDATE);

			commerceTermEntryLocalService.updateStatus(
				userId, commerceTermEntry.getCommerceTermEntryId(),
				WorkflowConstants.STATUS_EXPIRED, serviceContext);
		}
	}

	private GroupByStep _getDeliveryTermsEntryGroupByStep(
		Long companyId, Long commerceOrderTypeId,
		Long commerceShippingFixedOptionId, FromStep fromStep) {

		CommerceTermEntryRelTable commerceOrderTypeCommerceTermEntryRel =
			CommerceTermEntryRelTable.INSTANCE.as(
				"commerceOrderTypeCommerceTermEntryRel");

		Column<CommerceTermEntryRelTable, Long>
			commerceTermEntryRelTableClassNameIdColumn =
				commerceOrderTypeCommerceTermEntryRel.classNameId;

		Column<CommerceShippingFixedOptionQualifierTable, Long>
			commerceShippingFixedOptionQualifierTableClassNameIdColumn =
				CommerceShippingFixedOptionQualifierTable.INSTANCE.classNameId;

		JoinStep joinStep = fromStep.from(
			CommerceTermEntryTable.INSTANCE
		).innerJoinON(
			CommerceShippingFixedOptionQualifierTable.INSTANCE,
			commerceShippingFixedOptionQualifierTableClassNameIdColumn.eq(
				classNameLocalService.getClassNameId(
					CommerceTermEntry.class.getName())
			).and(
				CommerceShippingFixedOptionQualifierTable.INSTANCE.classPK.eq(
					CommerceTermEntryTable.INSTANCE.commerceTermEntryId)
			)
		).leftJoinOn(
			commerceOrderTypeCommerceTermEntryRel,
			commerceTermEntryRelTableClassNameIdColumn.eq(
				classNameLocalService.getClassNameId(
					CommerceOrderType.class.getName())
			).and(
				commerceOrderTypeCommerceTermEntryRel.commerceTermEntryId.eq(
					CommerceTermEntryTable.INSTANCE.commerceTermEntryId)
			)
		);

		return joinStep.where(
			CommerceShippingFixedOptionQualifierTable.INSTANCE.
				commerceShippingFixedOptionId.eq(
					commerceShippingFixedOptionId
				).and(
					() -> {
						if (commerceOrderTypeId != null) {
							return commerceOrderTypeCommerceTermEntryRel.
								classPK.eq(commerceOrderTypeId);
						}

						return commerceOrderTypeCommerceTermEntryRel.
							commerceTermEntryId.isNull();
					}
				).and(
					CommerceTermEntryTable.INSTANCE.status.eq(
						WorkflowConstants.STATUS_APPROVED)
				).and(
					CommerceTermEntryTable.INSTANCE.companyId.eq(companyId)
				).and(
					CommerceTermEntryTable.INSTANCE.active.eq(true)
				).and(
					CommerceTermEntryTable.INSTANCE.type.eq(
						CommerceTermEntryConstants.TYPE_DELIVERY_TERMS)
				));
	}

	private GroupByStep _getPaymentTermsEntryGroupByStep(
		Long companyId, Long commerceOrderTypeId,
		Long commercePaymentMethodGroupRelId, FromStep fromStep) {

		CommerceTermEntryRelTable commerceOrderTypeCommerceTermEntryRel =
			CommerceTermEntryRelTable.INSTANCE.as(
				"commerceOrderTypeCommerceTermEntryRel");

		Column<CommerceTermEntryRelTable, Long>
			commerceTermEntryRelTableClassNameIdColumn =
				commerceOrderTypeCommerceTermEntryRel.classNameId;

		Column<CommercePaymentMethodGroupRelQualifierTable, Long>
			commercePaymentMethodGroupRelQualifierTableClassNameIdColumn =
				CommercePaymentMethodGroupRelQualifierTable.INSTANCE.
					classNameId;

		JoinStep joinStep = fromStep.from(
			CommerceTermEntryTable.INSTANCE
		).innerJoinON(
			CommercePaymentMethodGroupRelQualifierTable.INSTANCE,
			commercePaymentMethodGroupRelQualifierTableClassNameIdColumn.eq(
				classNameLocalService.getClassNameId(
					CommerceTermEntry.class.getName())
			).and(
				CommercePaymentMethodGroupRelQualifierTable.INSTANCE.classPK.eq(
					CommerceTermEntryTable.INSTANCE.commerceTermEntryId)
			)
		).leftJoinOn(
			commerceOrderTypeCommerceTermEntryRel,
			commerceTermEntryRelTableClassNameIdColumn.eq(
				classNameLocalService.getClassNameId(
					CommerceOrderType.class.getName())
			).and(
				commerceOrderTypeCommerceTermEntryRel.commerceTermEntryId.eq(
					CommerceTermEntryTable.INSTANCE.commerceTermEntryId)
			)
		);

		return joinStep.where(
			CommercePaymentMethodGroupRelQualifierTable.INSTANCE.
				CommercePaymentMethodGroupRelId.eq(
					commercePaymentMethodGroupRelId
				).and(
					() -> {
						if (commerceOrderTypeId != null) {
							return commerceOrderTypeCommerceTermEntryRel.
								classPK.eq(commerceOrderTypeId);
						}

						return commerceOrderTypeCommerceTermEntryRel.
							commerceTermEntryId.isNull();
					}
				).and(
					CommerceTermEntryTable.INSTANCE.status.eq(
						WorkflowConstants.STATUS_APPROVED)
				).and(
					CommerceTermEntryTable.INSTANCE.companyId.eq(companyId)
				).and(
					CommerceTermEntryTable.INSTANCE.active.eq(true)
				).and(
					CommerceTermEntryTable.INSTANCE.type.eq(
						CommerceTermEntryConstants.TYPE_PAYMENT_TERMS)
				));
	}

	private CommerceTermEntry _startWorkflowInstance(
			long userId, CommerceTermEntry commerceTermEntry,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			commerceTermEntry.getCompanyId(), 0L, userId,
			CommerceTermEntry.class.getName(),
			commerceTermEntry.getCommerceTermEntryId(), commerceTermEntry,
			serviceContext, workflowContext);
	}

	private List<CTermEntryLocalization>
		_updateCommerceTermEntryLocalizedFields(
			long companyId, long commerceTermEntryId,
			Map<Locale, String> descriptionMap, Map<Locale, String> labelMap) {

		List<CTermEntryLocalization> oldCTermEntryLocalizations =
			new ArrayList<>(
				cTermEntryLocalizationPersistence.findByCommerceTermEntryId(
					commerceTermEntryId));

		List<CTermEntryLocalization> newCTermEntryLocalizations =
			_addCommerceTermEntryLocalizedFields(
				companyId, commerceTermEntryId, descriptionMap, labelMap);

		oldCTermEntryLocalizations.removeAll(newCTermEntryLocalizations);

		for (CTermEntryLocalization oldCTermEntryLocalization :
				oldCTermEntryLocalizations) {

			cTermEntryLocalizationPersistence.remove(oldCTermEntryLocalization);
		}

		return newCTermEntryLocalizations;
	}

	private void _validate(
			CommerceTermEntry commerceTermEntry, long companyId, String name,
			double priority, String type)
		throws PortalException {

		CommerceTermEntry oldCommerceTermEntry =
			commerceTermEntryPersistence.fetchByC_N(companyId, name);

		if (((commerceTermEntry == null) ||
			 !name.equals(commerceTermEntry.getName())) &&
			(Validator.isNull(name) || (oldCommerceTermEntry != null))) {

			throw new CommerceTermEntryNameException();
		}

		if (Validator.isNull(type)) {
			throw new CommerceTermEntryTypeException();
		}

		oldCommerceTermEntry = commerceTermEntryPersistence.fetchByC_P_T(
			companyId, priority, type);

		if (((commerceTermEntry == null) ||
			 (priority != commerceTermEntry.getPriority())) &&
			(oldCommerceTermEntry != null)) {

			throw new CommerceTermEntryPriorityException();
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTermEntryLocalServiceImpl.class);

	@Reference
	private CommerceTermEntryRelLocalService _commerceTermEntryRelLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}