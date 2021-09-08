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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceSubscriptionEntryConstants;
import com.liferay.commerce.constants.CommerceSubscriptionNotificationConstants;
import com.liferay.commerce.exception.CommerceSubscriptionEntryNextIterationDateException;
import com.liferay.commerce.exception.CommerceSubscriptionEntrySubscriptionStatusException;
import com.liferay.commerce.internal.search.CommerceSubscriptionEntryIndexer;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.notification.util.CommerceNotificationHelper;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPSubscriptionType;
import com.liferay.commerce.product.util.CPSubscriptionTypeRegistry;
import com.liferay.commerce.service.base.CommerceSubscriptionEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
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
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Alessio Antonio Rendina
 * @author Luca Pellizzon
 */
public class CommerceSubscriptionEntryLocalServiceImpl
	extends CommerceSubscriptionEntryLocalServiceBaseImpl {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceSubscriptionEntry addCommerceSubscriptionEntry(
			long userId, long groupId, long commerceOrderItemId,
			int subscriptionLength, String subscriptionType,
			long maxSubscriptionCycles,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties)
		throws PortalException {

		return commerceSubscriptionEntryLocalService.
			addCommerceSubscriptionEntry(
				userId, groupId, commerceOrderItemId, subscriptionLength,
				subscriptionType, maxSubscriptionCycles,
				subscriptionTypeSettingsUnicodeProperties, 0, null, 0, null);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceSubscriptionEntry addCommerceSubscriptionEntry(
			long userId, long groupId, long commerceOrderItemId,
			int subscriptionLength, String subscriptionType,
			long maxSubscriptionCycles,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			int deliverySubscriptionLength, String deliverySubscriptionType,
			long deliveryMaxSubscriptionCycles,
			UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		CPSubscriptionType cpSubscriptionType =
			_cpSubscriptionTypeRegistry.getCPSubscriptionType(subscriptionType);

		CPSubscriptionType deliveryCPSubscriptionType =
			_cpSubscriptionTypeRegistry.getCPSubscriptionType(
				deliverySubscriptionType);

		long commerceSubscriptionEntryId = counterLocalService.increment();

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			commerceSubscriptionEntryPersistence.create(
				commerceSubscriptionEntryId);

		commerceSubscriptionEntry.setUuid(PortalUUIDUtil.generate());
		commerceSubscriptionEntry.setGroupId(groupId);
		commerceSubscriptionEntry.setCompanyId(user.getCompanyId());
		commerceSubscriptionEntry.setUserId(user.getUserId());
		commerceSubscriptionEntry.setUserName(user.getFullName());

		commerceSubscriptionEntry.setCommerceOrderItemId(commerceOrderItemId);
		commerceSubscriptionEntry.setSubscriptionLength(subscriptionLength);
		commerceSubscriptionEntry.setSubscriptionType(subscriptionType);
		commerceSubscriptionEntry.setCurrentCycle(1);
		commerceSubscriptionEntry.setMaxSubscriptionCycles(
			maxSubscriptionCycles);
		commerceSubscriptionEntry.setSubscriptionTypeSettingsProperties(
			subscriptionTypeSettingsUnicodeProperties);
		commerceSubscriptionEntry.setLastIterationDate(new Date());
		commerceSubscriptionEntry.setDeliverySubscriptionLength(
			deliverySubscriptionLength);
		commerceSubscriptionEntry.setDeliverySubscriptionType(
			deliverySubscriptionType);
		commerceSubscriptionEntry.setDeliveryCurrentCycle(1);
		commerceSubscriptionEntry.setDeliveryMaxSubscriptionCycles(
			deliveryMaxSubscriptionCycles);
		commerceSubscriptionEntry.setDeliverySubscriptionTypeSettingsProperties(
			deliverySubscriptionTypeSettingsUnicodeProperties);
		commerceSubscriptionEntry.setDeliveryLastIterationDate(new Date());

		if (cpSubscriptionType != null) {
			commerceSubscriptionEntry.setSubscriptionStatus(
				CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_ACTIVE);

			Date subscriptionNextIterationDate =
				cpSubscriptionType.getSubscriptionNextIterationDate(
					user.getTimeZone(), subscriptionLength,
					subscriptionTypeSettingsUnicodeProperties, null);

			commerceSubscriptionEntry.setNextIterationDate(
				subscriptionNextIterationDate);

			Date subscriptionStartDate =
				cpSubscriptionType.getSubscriptionStartDate(
					user.getTimeZone(),
					subscriptionTypeSettingsUnicodeProperties);

			commerceSubscriptionEntry.setStartDate(subscriptionStartDate);
		}
		else {
			commerceSubscriptionEntry.setSubscriptionStatus(
				CommerceSubscriptionEntryConstants.
					SUBSCRIPTION_STATUS_INACTIVE);
		}

		if (deliveryCPSubscriptionType != null) {
			commerceSubscriptionEntry.setDeliverySubscriptionStatus(
				CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_ACTIVE);

			Date subscriptionNextIterationDate =
				deliveryCPSubscriptionType.getSubscriptionNextIterationDate(
					user.getTimeZone(), deliverySubscriptionLength,
					deliverySubscriptionTypeSettingsUnicodeProperties, null);

			commerceSubscriptionEntry.setDeliveryNextIterationDate(
				subscriptionNextIterationDate);

			Date subscriptionStartDate =
				deliveryCPSubscriptionType.getSubscriptionStartDate(
					user.getTimeZone(),
					deliverySubscriptionTypeSettingsUnicodeProperties);

			commerceSubscriptionEntry.setDeliveryStartDate(
				subscriptionStartDate);
		}
		else {
			commerceSubscriptionEntry.setDeliverySubscriptionStatus(
				CommerceSubscriptionEntryConstants.
					SUBSCRIPTION_STATUS_INACTIVE);
		}

		return commerceSubscriptionEntryPersistence.update(
			commerceSubscriptionEntry);
	}

	@Override
	public void deleteCommerceSubscriptionEntries(long groupId) {
		List<CommerceSubscriptionEntry> commerceSubscriptionEntries =
			commerceSubscriptionEntryPersistence.findByGroupId(groupId);

		for (CommerceSubscriptionEntry commerceSubscriptionEntry :
				commerceSubscriptionEntries) {

			commerceSubscriptionEntryLocalService.
				deleteCommerceSubscriptionEntry(commerceSubscriptionEntry);
		}
	}

	@Override
	public CommerceSubscriptionEntry
		fetchCommerceSubscriptionEntryByCommerceOrderItemId(
			long commerceOrderItemId) {

		return commerceSubscriptionEntryPersistence.fetchByCommerceOrderItemId(
			commerceOrderItemId);
	}

	@Override
	public List<CommerceSubscriptionEntry>
		getActiveCommerceSubscriptionEntries() {

		return commerceSubscriptionEntryPersistence.findBySubscriptionStatus(
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_ACTIVE);
	}

	@Override
	public List<CommerceSubscriptionEntry> getActiveCommerceSubscriptionEntries(
		long commerceAccountId) {

		return commerceSubscriptionEntryFinder.findByA_S(
			commerceAccountId,
			CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_ACTIVE);
	}

	@Override
	public List<CommerceSubscriptionEntry>
		getCommerceDeliverySubscriptionEntriesToRenew() {

		return commerceSubscriptionEntryFinder.findByDeliveryNextIterationDate(
			new Date());
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public List<CommerceSubscriptionEntry> getCommerceSubscriptionEntries(
		long companyId, long userId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return commerceSubscriptionEntryPersistence.findByC_U(
			companyId, userId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceSubscriptionEntry> getCommerceSubscriptionEntries(
		long companyId, long groupId, long userId, int start, int end,
		OrderByComparator<CommerceSubscriptionEntry> orderByComparator) {

		return commerceSubscriptionEntryPersistence.findByG_C_U(
			groupId, companyId, userId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public int getCommerceSubscriptionEntriesCount(
		long companyId, long userId) {

		return commerceSubscriptionEntryPersistence.countByC_U(
			companyId, userId);
	}

	@Override
	public int getCommerceSubscriptionEntriesCount(
		long companyId, long groupId, long userId) {

		return commerceSubscriptionEntryPersistence.countByG_C_U(
			groupId, companyId, userId);
	}

	@Override
	public List<CommerceSubscriptionEntry>
		getCommerceSubscriptionEntriesToRenew() {

		return commerceSubscriptionEntryFinder.findByNextIterationDate(
			new Date());
	}

	@Override
	public CommerceSubscriptionEntry
			incrementCommerceDeliverySubscriptionEntryCycle(
				long commerceSubscriptionEntryId)
		throws PortalException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			commerceSubscriptionEntryPersistence.findByPrimaryKey(
				commerceSubscriptionEntryId);

		CPSubscriptionType cpSubscriptionType =
			_cpSubscriptionTypeRegistry.getCPSubscriptionType(
				commerceSubscriptionEntry.getDeliverySubscriptionType());

		if (cpSubscriptionType == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"No subscription type found for subscription entry " +
						commerceSubscriptionEntryId);
			}

			return commerceSubscriptionEntry;
		}

		long currentSubscriptionCycle =
			commerceSubscriptionEntry.getDeliveryCurrentCycle();

		commerceSubscriptionEntry.setDeliveryCurrentCycle(
			currentSubscriptionCycle + 1);

		User user = userLocalService.getUser(
			commerceSubscriptionEntry.getUserId());

		commerceSubscriptionEntry.setDeliveryLastIterationDate(
			commerceSubscriptionEntry.getDeliveryNextIterationDate());

		Date subscriptionNextIterationDate =
			cpSubscriptionType.getSubscriptionNextIterationDate(
				user.getTimeZone(),
				commerceSubscriptionEntry.getDeliverySubscriptionLength(),
				commerceSubscriptionEntry.
					getDeliverySubscriptionTypeSettingsProperties(),
				commerceSubscriptionEntry.getDeliveryNextIterationDate());

		commerceSubscriptionEntry.setDeliveryNextIterationDate(
			subscriptionNextIterationDate);

		CommerceSubscriptionEntry updatedSubscriptionEntry =
			commerceSubscriptionEntryPersistence.update(
				commerceSubscriptionEntry);

		// Send user notification

		CommerceOrderItem commerceOrderItem =
			commerceSubscriptionEntry.fetchCommerceOrderItem();

		if (commerceOrderItem != null) {
			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			_commerceNotificationHelper.sendNotifications(
				commerceOrder.getGroupId(), commerceOrder.getUserId(),
				CommerceSubscriptionNotificationConstants.SUBSCRIPTION_RENEWED,
				updatedSubscriptionEntry);
		}

		return updatedSubscriptionEntry;
	}

	@Override
	public CommerceSubscriptionEntry incrementCommerceSubscriptionEntryCycle(
			long commerceSubscriptionEntryId)
		throws PortalException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			commerceSubscriptionEntryPersistence.findByPrimaryKey(
				commerceSubscriptionEntryId);

		CPSubscriptionType cpSubscriptionType =
			_cpSubscriptionTypeRegistry.getCPSubscriptionType(
				commerceSubscriptionEntry.getSubscriptionType());

		if (cpSubscriptionType == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"No subscription type found for subscription entry " +
						commerceSubscriptionEntryId);
			}

			return commerceSubscriptionEntry;
		}

		long currentSubscriptionCycle =
			commerceSubscriptionEntry.getCurrentCycle();

		commerceSubscriptionEntry.setCurrentCycle(currentSubscriptionCycle + 1);

		User user = userLocalService.getUser(
			commerceSubscriptionEntry.getUserId());

		commerceSubscriptionEntry.setLastIterationDate(
			commerceSubscriptionEntry.getNextIterationDate());

		Date subscriptionNextIterationDate =
			cpSubscriptionType.getSubscriptionNextIterationDate(
				user.getTimeZone(),
				commerceSubscriptionEntry.getSubscriptionLength(),
				commerceSubscriptionEntry.
					getSubscriptionTypeSettingsProperties(),
				commerceSubscriptionEntry.getNextIterationDate());

		commerceSubscriptionEntry.setNextIterationDate(
			subscriptionNextIterationDate);

		CommerceSubscriptionEntry updatedSubscriptionEntry =
			commerceSubscriptionEntryPersistence.update(
				commerceSubscriptionEntry);

		// Send user notification

		CommerceOrderItem commerceOrderItem =
			commerceSubscriptionEntry.fetchCommerceOrderItem();

		if (commerceOrderItem != null) {
			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			_commerceNotificationHelper.sendNotifications(
				commerceOrder.getGroupId(), commerceOrder.getUserId(),
				CommerceSubscriptionNotificationConstants.SUBSCRIPTION_RENEWED,
				updatedSubscriptionEntry);
		}

		return updatedSubscriptionEntry;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public BaseModelSearchResult<CommerceSubscriptionEntry>
			searchCommerceSubscriptionEntries(
				long companyId, Long maxSubscriptionCycles,
				Integer subscriptionStatus, String keywords, int start, int end,
				Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, null, maxSubscriptionCycles, subscriptionStatus,
			keywords, start, end, sort);

		return searchCommerceSubscriptionEntries(searchContext);
	}

	@Override
	public BaseModelSearchResult<CommerceSubscriptionEntry>
			searchCommerceSubscriptionEntries(
				long companyId, long[] groupIds, Long maxSubscriptionCycles,
				Integer subscriptionStatus, String keywords, int start, int end,
				Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupIds, maxSubscriptionCycles, subscriptionStatus,
			keywords, start, end, sort);

		return searchCommerceSubscriptionEntries(searchContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceSubscriptionEntry updateCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId, int subscriptionLength,
			String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, int subscriptionStatus,
			int nextIterationDateMonth, int nextIterationDateDay,
			int nextIterationDateYear, int nextIterationDateHour,
			int nextIterationDateMinute)
		throws PortalException {

		return commerceSubscriptionEntryLocalService.
			updateCommerceSubscriptionEntry(
				commerceSubscriptionEntryId, subscriptionLength,
				subscriptionType, subscriptionTypeSettingsUnicodeProperties,
				maxSubscriptionCycles, subscriptionStatus,
				nextIterationDateMonth, nextIterationDateDay,
				nextIterationDateYear, nextIterationDateHour,
				nextIterationDateMinute, 0, null, null, 0,
				CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_INACTIVE,
				0, 0, 0, 0, 0);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceSubscriptionEntry updateCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId, int subscriptionLength,
			String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, int subscriptionStatus,
			int nextIterationDateMonth, int nextIterationDateDay,
			int nextIterationDateYear, int nextIterationDateHour,
			int nextIterationDateMinute, int deliverySubscriptionLength,
			String deliverySubscriptionType,
			UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties,
			long deliveryMaxSubscriptionCycles, int deliverySubscriptionStatus,
			int deliveryNextIterationDateMonth,
			int deliveryNextIterationDateDay, int deliveryNextIterationDateYear,
			int deliveryNextIterationDateHour,
			int deliveryNextIterationDateMinute)
		throws PortalException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			commerceSubscriptionEntryPersistence.findByPrimaryKey(
				commerceSubscriptionEntryId);

		User user = userLocalService.getUser(
			commerceSubscriptionEntry.getUserId());

		validateSubscriptionStatus(
			subscriptionStatus,
			commerceSubscriptionEntry.getSubscriptionStatus());

		validateSubscriptionStatus(
			deliverySubscriptionStatus,
			commerceSubscriptionEntry.getDeliverySubscriptionStatus());

		commerceSubscriptionEntry.setSubscriptionLength(subscriptionLength);
		commerceSubscriptionEntry.setSubscriptionType(subscriptionType);
		commerceSubscriptionEntry.setSubscriptionTypeSettingsProperties(
			subscriptionTypeSettingsUnicodeProperties);
		commerceSubscriptionEntry.setMaxSubscriptionCycles(
			maxSubscriptionCycles);
		commerceSubscriptionEntry.setSubscriptionStatus(subscriptionStatus);

		if (subscriptionStatus !=
				CommerceSubscriptionEntryConstants.
					SUBSCRIPTION_STATUS_INACTIVE) {

			Date nextIterationDate = PortalUtil.getDate(
				nextIterationDateMonth, nextIterationDateDay,
				nextIterationDateYear, nextIterationDateHour,
				nextIterationDateMinute, user.getTimeZone(),
				CommerceSubscriptionEntryNextIterationDateException.class);

			commerceSubscriptionEntry.setNextIterationDate(nextIterationDate);
		}

		commerceSubscriptionEntry.setDeliverySubscriptionLength(
			deliverySubscriptionLength);
		commerceSubscriptionEntry.setDeliverySubscriptionType(
			deliverySubscriptionType);
		commerceSubscriptionEntry.setDeliverySubscriptionTypeSettingsProperties(
			deliverySubscriptionTypeSettingsUnicodeProperties);
		commerceSubscriptionEntry.setDeliveryMaxSubscriptionCycles(
			deliveryMaxSubscriptionCycles);
		commerceSubscriptionEntry.setDeliverySubscriptionStatus(
			deliverySubscriptionStatus);

		if (deliverySubscriptionStatus !=
				CommerceSubscriptionEntryConstants.
					SUBSCRIPTION_STATUS_INACTIVE) {

			Date deliveryNextIterationDate = PortalUtil.getDate(
				deliveryNextIterationDateMonth, deliveryNextIterationDateDay,
				deliveryNextIterationDateYear, deliveryNextIterationDateHour,
				deliveryNextIterationDateMinute, user.getTimeZone(),
				CommerceSubscriptionEntryNextIterationDateException.class);

			commerceSubscriptionEntry.setDeliveryNextIterationDate(
				deliveryNextIterationDate);
		}

		return commerceSubscriptionEntryPersistence.update(
			commerceSubscriptionEntry);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceSubscriptionEntry
			updateCommerceSubscriptionEntryIterationDates(
				long commerceSubscriptionEntryId, Date lastIterationDate)
		throws PortalException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			commerceSubscriptionEntryPersistence.findByPrimaryKey(
				commerceSubscriptionEntryId);

		CPSubscriptionType cpSubscriptionType =
			_cpSubscriptionTypeRegistry.getCPSubscriptionType(
				commerceSubscriptionEntry.getSubscriptionType());

		User user = userLocalService.getUser(
			commerceSubscriptionEntry.getUserId());

		commerceSubscriptionEntry.setLastIterationDate(lastIterationDate);

		Date subscriptionNextIterationDate =
			cpSubscriptionType.getSubscriptionNextIterationDate(
				user.getTimeZone(),
				commerceSubscriptionEntry.getSubscriptionLength(),
				commerceSubscriptionEntry.
					getSubscriptionTypeSettingsProperties(),
				lastIterationDate);

		commerceSubscriptionEntry.setNextIterationDate(
			subscriptionNextIterationDate);

		return commerceSubscriptionEntryPersistence.update(
			commerceSubscriptionEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceSubscriptionEntry updateDeliverySubscriptionStatus(
			long commerceSubscriptionEntryId, int subscriptionStatus)
		throws PortalException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			commerceSubscriptionEntryPersistence.findByPrimaryKey(
				commerceSubscriptionEntryId);

		validateSubscriptionStatus(
			subscriptionStatus,
			commerceSubscriptionEntry.getSubscriptionStatus());

		commerceSubscriptionEntry.setDeliverySubscriptionStatus(
			subscriptionStatus);

		// Messaging

		sendSubscriptionStatusMessage(
			commerceSubscriptionEntryId, subscriptionStatus);

		return commerceSubscriptionEntryPersistence.update(
			commerceSubscriptionEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceSubscriptionEntry updateSubscriptionStatus(
			long commerceSubscriptionEntryId, int subscriptionStatus)
		throws PortalException {

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			commerceSubscriptionEntryPersistence.findByPrimaryKey(
				commerceSubscriptionEntryId);

		validateSubscriptionStatus(
			subscriptionStatus,
			commerceSubscriptionEntry.getSubscriptionStatus());

		commerceSubscriptionEntry.setSubscriptionStatus(subscriptionStatus);

		// Messaging

		sendSubscriptionStatusMessage(
			commerceSubscriptionEntryId, subscriptionStatus);

		return commerceSubscriptionEntryPersistence.update(
			commerceSubscriptionEntry);
	}

	protected SearchContext buildSearchContext(
		long companyId, long[] groupIds, Long maxSubscriptionCycles,
		Integer subscriptionStatus, String keywords, int start, int end,
		Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				CommerceSubscriptionEntryIndexer.FIELD_CP_INSTANCE_ID, keywords
			).put(
				CommerceSubscriptionEntryIndexer.FIELD_MAX_SUBSCRIPTION_CYCLES,
				() -> maxSubscriptionCycles
			).put(
				CommerceSubscriptionEntryIndexer.FIELD_SKU, keywords
			).put(
				CommerceSubscriptionEntryIndexer.FIELD_SUBSCRIPTION_STATUS,
				() -> subscriptionStatus
			).put(
				Field.ENTRY_CLASS_PK, keywords
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords", keywords
				).build()
			).build());

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		if ((groupIds != null) && (groupIds.length > 0)) {
			searchContext.setGroupIds(groupIds);
		}

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

	protected List<CommerceSubscriptionEntry> getCommerceSubscriptionEntries(
			Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommerceSubscriptionEntry> commerceSubscriptionEntries =
			new ArrayList<>(documents.size());

		for (Document document : documents) {
			long commerceSubscriptionEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommerceSubscriptionEntry commerceSubscriptionEntry =
				fetchCommerceSubscriptionEntry(commerceSubscriptionEntryId);

			if (commerceSubscriptionEntry == null) {
				commerceSubscriptionEntries = null;

				Indexer<CommerceSubscriptionEntry> indexer =
					IndexerRegistryUtil.getIndexer(
						CommerceSubscriptionEntry.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commerceSubscriptionEntries != null) {
				commerceSubscriptionEntries.add(commerceSubscriptionEntry);
			}
		}

		return commerceSubscriptionEntries;
	}

	protected BaseModelSearchResult<CommerceSubscriptionEntry>
			searchCommerceSubscriptionEntries(SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceSubscriptionEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CommerceSubscriptionEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CommerceSubscriptionEntry> commerceSubscriptionEntries =
				getCommerceSubscriptionEntries(hits);

			if (commerceSubscriptionEntries != null) {
				return new BaseModelSearchResult<>(
					commerceSubscriptionEntries, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void sendSubscriptionStatusMessage(
		long commerceSubscriptionEntryId, int subscriptionStatus) {

		TransactionCommitCallbackUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Message message = new Message();

					message.setPayload(
						JSONUtil.put(
							"commerceSubscriptionEntryId",
							commerceSubscriptionEntryId
						).put(
							"subscriptionStatus", subscriptionStatus
						));

					MessageBusUtil.sendMessage(
						DestinationNames.COMMERCE_SUBSCRIPTION_STATUS, message);

					return null;
				}

			});
	}

	protected void validateSubscriptionStatus(
			int subscriptionStatus, int oldSubscriptionStatus)
		throws PortalException {

		if ((oldSubscriptionStatus ==
				CommerceSubscriptionEntryConstants.
					SUBSCRIPTION_STATUS_SUSPENDED) ||
			((subscriptionStatus ==
				CommerceSubscriptionEntryConstants.
					SUBSCRIPTION_STATUS_INACTIVE) &&
			 (oldSubscriptionStatus !=
				 CommerceSubscriptionEntryConstants.
					 SUBSCRIPTION_STATUS_COMPLETED))) {

			return;
		}

		if (subscriptionStatus < oldSubscriptionStatus) {
			throw new CommerceSubscriptionEntrySubscriptionStatusException();
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceSubscriptionEntryLocalServiceImpl.class);

	@ServiceReference(type = CommerceChannelLocalService.class)
	private CommerceChannelLocalService _commerceChannelLocalService;

	@ServiceReference(type = CommerceNotificationHelper.class)
	private CommerceNotificationHelper _commerceNotificationHelper;

	@ServiceReference(type = CPDefinitionLocalService.class)
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@ServiceReference(type = CPInstanceLocalService.class)
	private CPInstanceLocalService _cpInstanceLocalService;

	@ServiceReference(type = CPSubscriptionTypeRegistry.class)
	private CPSubscriptionTypeRegistry _cpSubscriptionTypeRegistry;

}