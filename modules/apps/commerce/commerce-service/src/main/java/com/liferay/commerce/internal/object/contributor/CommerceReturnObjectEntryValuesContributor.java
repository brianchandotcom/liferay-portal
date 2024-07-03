/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.contributor;

import com.liferay.commerce.constants.CommerceReturnConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceReturnThreadLocal;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.object.entry.ObjectEntryContext;
import com.liferay.object.entry.contributor.ObjectEntryValuesContributor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = ObjectEntryValuesContributor.class)
public class CommerceReturnObjectEntryValuesContributor
	implements ObjectEntryValuesContributor {

	@Override
	public void contribute(ObjectEntryContext objectEntryContext) {
		Map<String, Serializable> commerceReturnValues =
			objectEntryContext.getValues();

		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntryContext.getObjectDefinitionId());

			if (!StringUtil.equals(
					objectDefinition.getName(), "CommerceReturn")) {

				return;
			}

			CommerceOrder commerceOrder =
				_commerceOrderLocalService.getCommerceOrder(
					GetterUtil.getLong(
						commerceReturnValues.get(
							CommerceReturnConstants.
								RETURN_FIELD_COMMERCE_ORDER_ID)));

			long groupId = commerceOrder.getGroupId();

			commerceReturnValues.put(
				CommerceReturnConstants.RETURN_FIELD_CHANNEL_GROUP_ID, groupId);

			CommerceChannel commerceChannel =
				_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
					groupId);

			commerceReturnValues.put(
				CommerceReturnConstants.RETURN_FIELD_CHANNEL_ID,
				commerceChannel.getCommerceChannelId());
			commerceReturnValues.put(
				CommerceReturnConstants.RETURN_FIELD_CHANNEL_NAME,
				commerceChannel.getName());

			if (!commerceReturnValues.containsKey(
					CommerceReturnConstants.RETURN_FIELD_COMMERCE_RETURN_ID) &&
				!commerceReturnValues.containsKey(
					CommerceReturnConstants.
						RETURN_FIELD_EXTERNAL_REFERENCE_CODE)) {

				return;
			}

			ObjectEntry originalCommerceReturnObjectEntry =
				_objectEntryLocalService.fetchObjectEntry(
					GetterUtil.getLong(
						commerceReturnValues.get(
							CommerceReturnConstants.
								RETURN_FIELD_COMMERCE_RETURN_ID)));

			if (originalCommerceReturnObjectEntry == null) {
				originalCommerceReturnObjectEntry =
					_objectEntryLocalService.fetchObjectEntry(
						GetterUtil.getString(
							commerceReturnValues.get(
								CommerceReturnConstants.
									RETURN_FIELD_EXTERNAL_REFERENCE_CODE)),
						objectDefinition.getObjectDefinitionId());
			}

			Map<String, Serializable> originalCommerceReturnValues =
				originalCommerceReturnObjectEntry.getValues();

			String currentReturnStatus = GetterUtil.getString(
				originalCommerceReturnValues.get(
					CommerceReturnConstants.RETURN_FIELD_RETURN_STATUS));

			String newReturnStatus = GetterUtil.getString(
				commerceReturnValues.get(
					CommerceReturnConstants.RETURN_FIELD_RETURN_STATUS));

			if (StringUtil.equalsIgnoreCase(
					currentReturnStatus,
					CommerceReturnConstants.RETURN_STATUS_DRAFT) &&
				StringUtil.equalsIgnoreCase(
					newReturnStatus,
					CommerceReturnConstants.RETURN_STATUS_PENDING)) {

				return;
			}

			List<ObjectEntry> commerceReturnObjectEntries =
				_objectEntryLocalService.getOneToManyObjectEntries(
					originalCommerceReturnObjectEntry.getGroupId(),
					_objectRelationshipLocalService.getObjectRelationship(
						originalCommerceReturnObjectEntry.
							getObjectDefinitionId(),
						"commerceReturnToCommerceReturnItems"
					).getObjectRelationshipId(),
					originalCommerceReturnObjectEntry.getObjectEntryId(), true,
					null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			Map<String, List<ObjectEntry>> returnItemStatusMap =
				_toReturnItemStatusMap(commerceReturnObjectEntries);

			if (GetterUtil.getBoolean(
					commerceReturnValues.get("mark-as-completed"))) {

				for (ObjectEntry objectEntry :
						returnItemStatusMap.getOrDefault(
							"processedReturnItems", Collections.emptyList())) {

					Map<String, Serializable> objectEntryValues =
						objectEntry.getValues();

					objectEntryValues.put(
						CommerceReturnConstants.
							RETURN_ITEM_FIELD_RETURN_ITEM_STATUS,
						CommerceReturnConstants.RETURN_ITEM_STATUS_COMPLETED);

					CommerceReturnThreadLocal.
						setSkipCommerceReturnItemContributor(true);

					_objectEntryLocalService.updateObjectEntry(
						objectEntry.getUserId(), objectEntry.getObjectEntryId(),
						objectEntryValues, new ServiceContext());
				}

				commerceReturnValues.put(
					CommerceReturnConstants.RETURN_FIELD_RETURN_STATUS,
					CommerceReturnConstants.RETURN_STATUS_COMPLETED);

				return;
			}

			if (CommerceReturnThreadLocal.isMarkAsProcessed()) {
				CommerceReturnThreadLocal.setMarkAsProcessed(false);

				for (ObjectEntry objectEntry :
						returnItemStatusMap.getOrDefault(
							"toBeProcessedReturnItems",
							Collections.emptyList())) {

					Map<String, Serializable> objectEntryValues =
						objectEntry.getValues();

					objectEntryValues.put(
						CommerceReturnConstants.
							RETURN_ITEM_FIELD_RETURN_ITEM_STATUS,
						CommerceReturnConstants.RETURN_ITEM_STATUS_PROCESSED);

					CommerceReturnThreadLocal.
						setSkipCommerceReturnItemContributor(true);

					_objectEntryLocalService.updateObjectEntry(
						objectEntry.getUserId(), objectEntry.getObjectEntryId(),
						objectEntryValues, new ServiceContext());
				}

				return;
			}

			String nextReturnStatus = _getNextReturnStatus(
				commerceReturnObjectEntries.size(), currentReturnStatus,
				returnItemStatusMap);

			if (StringUtil.equals(currentReturnStatus, nextReturnStatus)) {
				return;
			}

			if (StringUtil.equals(
					nextReturnStatus,
					CommerceReturnConstants.RETURN_STATUS_AUTHORIZED)) {

				for (ObjectEntry objectEntry :
						returnItemStatusMap.getOrDefault(
							"authorizedReturnItems", Collections.emptyList())) {

					Map<String, Serializable> authorizedReturnItemValues =
						objectEntry.getValues();

					authorizedReturnItemValues.put(
						CommerceReturnConstants.
							RETURN_ITEM_FIELD_RETURN_ITEM_STATUS,
						CommerceReturnConstants.
							RETURN_ITEM_STATUS_AWAITING_RECEIPT);

					CommerceReturnThreadLocal.
						setSkipCommerceReturnItemContributor(true);

					_objectEntryLocalService.updateObjectEntry(
						objectEntry.getUserId(), objectEntry.getObjectEntryId(),
						authorizedReturnItemValues, new ServiceContext());
				}
			}

			commerceReturnValues.put(
				CommerceReturnConstants.RETURN_FIELD_RETURN_STATUS,
				nextReturnStatus);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private String _getNextReturnStatus(
		int commerceReturnItemsSize, String currentReturnStatus,
		Map<String, List<ObjectEntry>> returnItemStatusMap) {

		List<ObjectEntry> notAuthorizedReturnItemObjectEntries =
			returnItemStatusMap.getOrDefault(
				"notAuthorizedReturnItems", Collections.emptyList());

		int notAuthorizedReturnItemsSize =
			notAuthorizedReturnItemObjectEntries.size();

		List<ObjectEntry> toBeProcessedReturnItemObjectEntries =
			returnItemStatusMap.getOrDefault(
				"toBeProcessedReturnItems", Collections.emptyList());

		List<ObjectEntry> receivedReturnItemObjectEntries =
			returnItemStatusMap.getOrDefault(
				"receivedReturnItems", Collections.emptyList());

		List<ObjectEntry> receiptRejectedReturnItemObjectEntries =
			returnItemStatusMap.getOrDefault(
				"receiptRejectedReturnItems", Collections.emptyList());

		int receiptRejectedReturnItemsSize =
			receiptRejectedReturnItemObjectEntries.size();

		if (StringUtil.equalsIgnoreCase(
				currentReturnStatus,
				CommerceReturnConstants.RETURN_STATUS_AUTHORIZED)) {

			if (commerceReturnItemsSize ==
					(notAuthorizedReturnItemsSize +
						receiptRejectedReturnItemsSize)) {

				return CommerceReturnConstants.RETURN_STATUS_REJECTED;
			}

			int returnResolutionMethod = ListUtil.count(
				receivedReturnItemObjectEntries,
				receivedReturnItem -> {
					Map<String, Serializable> values =
						receivedReturnItem.getValues();

					return Validator.isNotNull(
						values.get(
							CommerceReturnConstants.
								RETURN_ITEM_FIELD_RETURN_RESOLUTION_METHOD));
				});

			if ((commerceReturnItemsSize == returnResolutionMethod) ||
				ListUtil.isNotEmpty(toBeProcessedReturnItemObjectEntries)) {

				return CommerceReturnConstants.RETURN_STATUS_PROCESSING;
			}
		}

		if (StringUtil.equalsIgnoreCase(
				currentReturnStatus,
				CommerceReturnConstants.RETURN_STATUS_PENDING)) {

			int toBeProcessedReturnItemsSize =
				toBeProcessedReturnItemObjectEntries.size();

			if ((toBeProcessedReturnItemsSize > 0) &&
				(commerceReturnItemsSize ==
					(notAuthorizedReturnItemsSize +
						toBeProcessedReturnItemsSize))) {

				return CommerceReturnConstants.RETURN_STATUS_PROCESSING;
			}

			if (commerceReturnItemsSize == notAuthorizedReturnItemsSize) {
				return CommerceReturnConstants.RETURN_STATUS_REJECTED;
			}

			List<ObjectEntry> authorizedReturnItemObjectEntries =
				returnItemStatusMap.getOrDefault(
					"authorizedReturnItems", Collections.emptyList());

			int authorizedReturnItemsSize =
				authorizedReturnItemObjectEntries.size();

			int receivedReturnItemsSize =
				receivedReturnItemObjectEntries.size();

			if (commerceReturnItemsSize ==
					(authorizedReturnItemsSize + notAuthorizedReturnItemsSize +
						receivedReturnItemsSize +
							toBeProcessedReturnItemsSize)) {

				return CommerceReturnConstants.RETURN_STATUS_AUTHORIZED;
			}
		}

		if (StringUtil.equalsIgnoreCase(
				currentReturnStatus,
				CommerceReturnConstants.RETURN_STATUS_PROCESSING) &&
			(commerceReturnItemsSize ==
				(notAuthorizedReturnItemsSize +
					receiptRejectedReturnItemsSize))) {

			return CommerceReturnConstants.RETURN_STATUS_REJECTED;
		}

		return currentReturnStatus;
	}

	private Map<String, List<ObjectEntry>> _toReturnItemStatusMap(
		List<ObjectEntry> commerceReturnObjectEntries) {

		Map<String, List<ObjectEntry>> commerceReturnItemMap = new HashMap<>();

		for (ObjectEntry objectEntry : commerceReturnObjectEntries) {
			Map<String, Serializable> commerceReturnItemValues =
				objectEntry.getValues();

			String returnItemStatus = GetterUtil.getString(
				commerceReturnItemValues.get(
					CommerceReturnConstants.
						RETURN_ITEM_FIELD_RETURN_ITEM_STATUS));

			String key = null;

			if (Arrays.asList(
					CommerceReturnConstants.RETURN_ITEM_STATUSES_AUTHORIZED
				).contains(
					returnItemStatus
				)) {

				key = "authorizedReturnItems";
			}
			else if (StringUtil.equals(
						returnItemStatus,
						CommerceReturnConstants.
							RETURN_ITEM_STATUS_NOT_AUTHORIZED)) {

				key = "notAuthorizedReturnItems";
			}
			else if (StringUtil.equals(
						returnItemStatus,
						CommerceReturnConstants.RETURN_ITEM_STATUS_PROCESSED)) {

				key = "processedReturnItems";
			}
			else if (StringUtil.equals(
						returnItemStatus,
						CommerceReturnConstants.
							RETURN_ITEM_STATUS_RECEIPT_REJECTED)) {

				key = "receiptRejectedReturnItems";
			}
			else if (Arrays.asList(
						CommerceReturnConstants.RETURN_ITEM_STATUSES_RECEIVED
					).contains(
						returnItemStatus
					)) {

				key = "receivedReturnItems";
			}
			else if (StringUtil.equals(
						returnItemStatus,
						CommerceReturnConstants.
							RETURN_ITEM_STATUS_TO_BE_PROCESSED)) {

				key = "toBeProcessedReturnItems";
			}

			if (Validator.isNotNull(key)) {
				List<ObjectEntry> objectEntries = commerceReturnItemMap.get(
					key);

				if (ListUtil.isEmpty(objectEntries)) {
					objectEntries = new ArrayList<>();
				}

				objectEntries.add(objectEntry);

				commerceReturnItemMap.put(key, objectEntries);
			}
		}

		return commerceReturnItemMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceReturnObjectEntryValuesContributor.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}