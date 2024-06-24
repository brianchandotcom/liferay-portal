/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.contributor;

import com.liferay.commerce.constants.CommerceReturnConstants;
import com.liferay.commerce.model.CommerceOrder;
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

			ObjectEntry originalCommerceReturn =
				_objectEntryLocalService.fetchObjectEntry(
					GetterUtil.getLong(
						commerceReturnValues.get(
							CommerceReturnConstants.
								RETURN_FIELD_COMMERCE_RETURN_ID)));

			if (originalCommerceReturn == null) {
				originalCommerceReturn =
					_objectEntryLocalService.fetchObjectEntry(
						GetterUtil.getString(
							commerceReturnValues.get(
								CommerceReturnConstants.
									RETURN_FIELD_EXTERNAL_REFERENCE_CODE)),
						objectDefinition.getObjectDefinitionId());
			}

			Map<String, Serializable> originalCommerceReturnValues =
				originalCommerceReturn.getValues();

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

			List<ObjectEntry> commerceReturnItems =
				_objectEntryLocalService.getOneToManyObjectEntries(
					originalCommerceReturn.getGroupId(),
					_objectRelationshipLocalService.getObjectRelationship(
						originalCommerceReturn.getObjectDefinitionId(),
						"commerceReturnToCommerceReturnItems"
					).getObjectRelationshipId(),
					originalCommerceReturn.getObjectEntryId(), true, null,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			Map<String, List<ObjectEntry>> returnItemStatusMap =
				_toReturnItemStatusMap(commerceReturnItems);

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
					objectEntryValues.put(
						"skipCommerceReturnItemContributor", true);

					_objectEntryLocalService.updateObjectEntry(
						objectEntry.getUserId(), objectEntry.getObjectEntryId(),
						objectEntryValues, new ServiceContext());
				}

				commerceReturnValues.put(
					CommerceReturnConstants.RETURN_FIELD_RETURN_STATUS,
					CommerceReturnConstants.RETURN_STATUS_COMPLETED);

				return;
			}

			if (GetterUtil.getBoolean(
					commerceReturnValues.get("mark-as-processed"))) {

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
					objectEntryValues.put(
						"skipCommerceReturnItemContributor", true);

					_objectEntryLocalService.updateObjectEntry(
						objectEntry.getUserId(), objectEntry.getObjectEntryId(),
						objectEntryValues, new ServiceContext());
				}

				return;
			}

			String nextReturnStatus = _getNextReturnStatus(
				commerceReturnItems.size(), currentReturnStatus,
				returnItemStatusMap);

			if (StringUtil.equals(currentReturnStatus, nextReturnStatus)) {
				return;
			}

			if (StringUtil.equals(
					nextReturnStatus,
					CommerceReturnConstants.RETURN_STATUS_AUTHORIZED)) {

				for (ObjectEntry authorizedReturnItem :
						returnItemStatusMap.getOrDefault(
							"authorizedReturnItems", Collections.emptyList())) {

					Map<String, Serializable> authorizedReturnItemValues =
						authorizedReturnItem.getValues();

					authorizedReturnItemValues.put(
						CommerceReturnConstants.
							RETURN_ITEM_FIELD_RETURN_ITEM_STATUS,
						CommerceReturnConstants.
							RETURN_ITEM_STATUS_AWAITING_RECEIPT);
					authorizedReturnItemValues.put(
						"skipCommerceReturnItemContributor", true);

					_objectEntryLocalService.updateObjectEntry(
						authorizedReturnItem.getUserId(),
						authorizedReturnItem.getObjectEntryId(),
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

		List<ObjectEntry> notAuthorizedReturnItems =
			returnItemStatusMap.getOrDefault(
				"notAuthorizedReturnItems", Collections.emptyList());

		int notAuthorizedReturnItemsSize = notAuthorizedReturnItems.size();

		List<ObjectEntry> toBeProcessedReturnItems =
			returnItemStatusMap.getOrDefault(
				"toBeProcessedReturnItems", Collections.emptyList());

		List<ObjectEntry> receivedReturnItems =
			returnItemStatusMap.getOrDefault(
				"receivedReturnItems", Collections.emptyList());

		List<ObjectEntry> receiptRejectedReturnItems =
			returnItemStatusMap.getOrDefault(
				"receiptRejectedReturnItems", Collections.emptyList());

		int receiptRejectedReturnItemsSize = receiptRejectedReturnItems.size();

		if (StringUtil.equalsIgnoreCase(
				currentReturnStatus,
				CommerceReturnConstants.RETURN_STATUS_AUTHORIZED)) {

			if (commerceReturnItemsSize ==
					(notAuthorizedReturnItemsSize +
						receiptRejectedReturnItemsSize)) {

				return CommerceReturnConstants.RETURN_STATUS_REJECTED;
			}

			int returnResolutionMethod = ListUtil.count(
				receivedReturnItems,
				receivedReturnItem -> {
					Map<String, Serializable> values =
						receivedReturnItem.getValues();

					return Validator.isNotNull(
						values.get(
							CommerceReturnConstants.
								RETURN_ITEM_FIELD_RETURN_RESOLUTION_METHOD));
				});

			if ((commerceReturnItemsSize == returnResolutionMethod) ||
				ListUtil.isNotEmpty(toBeProcessedReturnItems)) {

				return CommerceReturnConstants.RETURN_STATUS_PROCESSING;
			}
		}

		if (StringUtil.equalsIgnoreCase(
				currentReturnStatus,
				CommerceReturnConstants.RETURN_STATUS_PENDING)) {

			int toBeProcessedReturnItemsSize = toBeProcessedReturnItems.size();

			if ((toBeProcessedReturnItemsSize > 0) &&
				(commerceReturnItemsSize ==
					(notAuthorizedReturnItemsSize +
						toBeProcessedReturnItemsSize))) {

				return CommerceReturnConstants.RETURN_STATUS_PROCESSING;
			}

			if (commerceReturnItemsSize == notAuthorizedReturnItemsSize) {
				return CommerceReturnConstants.RETURN_STATUS_REJECTED;
			}

			List<ObjectEntry> authorizedReturnItems =
				returnItemStatusMap.getOrDefault(
					"authorizedReturnItems", Collections.emptyList());

			int authorizedReturnItemsSize = authorizedReturnItems.size();

			int receivedReturnItemsSize = receivedReturnItems.size();

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
		List<ObjectEntry> commerceReturnItems) {

		Map<String, List<ObjectEntry>> commerceReturnItemMap = new HashMap<>();

		for (ObjectEntry commerceReturnItem : commerceReturnItems) {
			Map<String, Serializable> commerceReturnItemValues =
				commerceReturnItem.getValues();

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
				List<ObjectEntry> items = commerceReturnItemMap.get(key);

				if (ListUtil.isEmpty(items)) {
					items = new ArrayList<>();
				}

				items.add(commerceReturnItem);

				commerceReturnItemMap.put(key, items);
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