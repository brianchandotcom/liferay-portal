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

package com.liferay.commerce.product.internal.util;

import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.media.CommerceMediaProvider;
import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.product.availability.CPAvailabilityChecker;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.exception.CPDefinitionIgnoreSKUCombinationsException;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.internal.catalog.CPSkuImpl;
import com.liferay.commerce.product.internal.util.comparator.CPDefinitionOptionRelComparator;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPAttachmentFileEntryLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceOptionValueRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.DDMFormValuesHelper;
import com.liferay.commerce.product.util.JsonHelper;
import com.liferay.commerce.product.util.comparator.CPDefinitionOptionValueRelPriorityComparator;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
@Component(enabled = false, immediate = true, service = CPInstanceHelper.class)
public class CPInstanceHelperImpl implements CPInstanceHelper {

	@Override
	public CPInstance fetchCPInstance(
			long cpDefinitionId, String serializedDDMFormValues)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		if (cpDefinition.isIgnoreSKUCombinations()) {
			return getDefaultCPInstance(cpDefinitionId);
		}

		if (_jsonHelper.isEmpty(serializedDDMFormValues)) {
			return null;
		}

		return _fetchCPInstanceBySKUContributors(
			cpDefinitionId, serializedDDMFormValues);
	}

	@Override
	public CPInstance fetchFirstAvailableReplacementCPInstance(
			long commerceChannelGroupId, long cpInstanceId)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if ((cpInstance == null) || !cpInstance.isDiscontinued()) {
			return null;
		}

		return _fetchFirstAvailableReplacementCPInstance(
			commerceChannelGroupId,
			_cpInstanceLocalService.fetchCProductInstance(
				cpInstance.getReplacementCProductId(),
				cpInstance.getReplacementCPInstanceUuid()));
	}

	@Override
	public CPInstance fetchReplacementCPInstance(
			long cProductId, String cpInstanceUuid)
		throws PortalException {

		return _cpInstanceLocalService.fetchCProductInstance(
			cProductId, cpInstanceUuid);
	}

	@Override
	public List<CPDefinitionOptionValueRel> filterCPDefinitionOptionValueRels(
			long cpDefinitionOptionRelId,
			List<Long> skuCombinationCPDefinitionOptionValueRelIds)
		throws PortalException {

		List<CPInstanceOptionValueRel> cpInstanceOptionValueRels =
			_cpInstanceOptionValueRelLocalService.
				getCPDefinitionOptionRelCPInstanceOptionValueRels(
					cpDefinitionOptionRelId);

		List<CPDefinitionOptionValueRel> filtered = new ArrayList<>();

		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				cpInstanceOptionValueRels) {

			if (!_hasCPInstanceCPDefinitionOptionValueRelIds(
					cpInstanceOptionValueRel.getCPInstanceId(),
					skuCombinationCPDefinitionOptionValueRelIds)) {

				continue;
			}

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelLocalService.
					getCPInstanceCPDefinitionOptionValueRel(
						cpDefinitionOptionRelId,
						cpInstanceOptionValueRel.getCPInstanceId());

			if (filtered.contains(cpDefinitionOptionValueRel)) {
				continue;
			}

			filtered.add(cpDefinitionOptionValueRel);
		}

		Collections.sort(filtered);

		return filtered;
	}

	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long commerceAccountId, long commerceChannelGroupId,
			long cpDefinitionId, String serializedDDMFormValues, int type)
		throws Exception {

		return getCPAttachmentFileEntries(
			commerceAccountId, commerceChannelGroupId, cpDefinitionId,
			serializedDDMFormValues, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	@Override
	public List<CPAttachmentFileEntry> getCPAttachmentFileEntries(
			long commerceAccountId, long commerceChannelGroupId,
			long cpDefinitionId, String serializedDDMFormValues, int type,
			int start, int end)
		throws Exception {

		_commerceProductViewPermission.check(
			PermissionThreadLocal.getPermissionChecker(), commerceAccountId,
			commerceChannelGroupId, cpDefinitionId);

		return _cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
			cpDefinitionId, serializedDDMFormValues, type, start, end);
	}

	@Override
	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
		getCPDefinitionOptionRelsMap(
			long cpDefinitionId, boolean skuContributor, boolean publicStore) {

		List<CPDefinitionOptionRel> cpDefinitionOptionRels;

		if (skuContributor) {
			cpDefinitionOptionRels =
				_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
					cpDefinitionId, true);
		}
		else {
			cpDefinitionOptionRels =
				_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
					cpDefinitionId);
		}

		if (cpDefinitionOptionRels.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelsMap = new TreeMap<>(
				new CPDefinitionOptionRelComparator());

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			if (cpDefinitionOptionRel.isSkuContributor() && publicStore) {
				cpDefinitionOptionRelsMap.put(
					cpDefinitionOptionRel,
					getCPInstanceCPDefinitionOptionValueRels(
						cpDefinitionId,
						cpDefinitionOptionRel.getCPDefinitionOptionRelId()));

				continue;
			}

			cpDefinitionOptionRelsMap.put(
				cpDefinitionOptionRel,
				cpDefinitionOptionRel.getCPDefinitionOptionValueRels());
		}

		return cpDefinitionOptionRelsMap;
	}

	@Override
	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			getCPDefinitionOptionRelsMap(long cpDefinitionId, String json)
		throws PortalException {

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelListMap = new HashMap<>();

		if (Validator.isNull(json)) {
			return cpDefinitionOptionRelListMap;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelLocalService.
					fetchCPDefinitionOptionRelByKey(
						cpDefinitionId, jsonObject.getString("key"));

			if (cpDefinitionOptionRel == null) {
				continue;
			}

			JSONArray valueJSONArray = jsonObject.getJSONArray("value");

			for (int j = 0; j < valueJSONArray.length(); j++) {
				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					_cpDefinitionOptionValueRelLocalService.
						fetchCPDefinitionOptionValueRel(
							cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
							valueJSONArray.getString(j));

				if (cpDefinitionOptionValueRel == null) {
					continue;
				}

				List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
					cpDefinitionOptionRelListMap.get(cpDefinitionOptionRel);

				if (cpDefinitionOptionValueRels == null) {
					cpDefinitionOptionValueRels = new ArrayList<>();

					cpDefinitionOptionRelListMap.put(
						cpDefinitionOptionRel, cpDefinitionOptionValueRels);
				}

				cpDefinitionOptionValueRels.add(cpDefinitionOptionValueRel);
			}
		}

		return cpDefinitionOptionRelListMap;
	}

	@Override
	public String getCPInstanceAdaptiveMediaImageHTMLTag(
			long commerceAccountId, long companyId, long cpInstanceId)
		throws Exception {

		FileVersion fileVersion = getCPInstanceImageFileVersion(
			commerceAccountId, companyId, cpInstanceId);

		String originalImgTag = StringBundler.concat(
			"<img class=\"aspect-ratio-bg-cover aspect-ratio-item ",
			"aspect-ratio-item-center-middle aspect-ratio-item-fluid ",
			"card-type-asset-icon\" src=\"",
			getCPInstanceThumbnailSrc(commerceAccountId, cpInstanceId),
			"\" />");

		return _amImageHTMLTagFactory.create(
			originalImgTag, fileVersion.getFileEntry());
	}

	@Override
	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			getCPInstanceCPDefinitionOptionRelsMap(long cpInstanceId)
		throws PortalException {

		List<CPInstanceOptionValueRel> cpInstanceCPInstanceOptionValueRels =
			_cpInstanceOptionValueRelLocalService.
				getCPInstanceCPInstanceOptionValueRels(cpInstanceId);

		if (cpInstanceCPInstanceOptionValueRels.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelsMap = new HashMap<>();

		for (CPInstanceOptionValueRel cpInstanceCPInstanceOptionValueRel :
				cpInstanceCPInstanceOptionValueRels) {

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRel(
					cpInstanceCPInstanceOptionValueRel.
						getCPDefinitionOptionRelId());

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRelsMap.get(cpDefinitionOptionRel);

			if (cpDefinitionOptionValueRels == null) {
				cpDefinitionOptionValueRels = new ArrayList<>();

				cpDefinitionOptionRelsMap.put(
					cpDefinitionOptionRel, cpDefinitionOptionValueRels);
			}

			cpDefinitionOptionValueRels.add(
				_cpDefinitionOptionValueRelLocalService.
					getCPDefinitionOptionValueRel(
						cpInstanceCPInstanceOptionValueRel.
							getCPDefinitionOptionValueRelId()));
		}

		return cpDefinitionOptionRelsMap;
	}

	@Override
	public List<CPDefinitionOptionValueRel>
		getCPInstanceCPDefinitionOptionValueRels(
			long cpDefinitionId, long cpDefinitionOptionRelId) {

		List<CPInstanceOptionValueRel> cpDefinitionCPInstanceOptionValueRels =
			_cpInstanceOptionValueRelLocalService.
				getCPDefinitionCPInstanceOptionValueRels(cpDefinitionId);

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			new ArrayList<>();

		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				cpDefinitionCPInstanceOptionValueRels) {

			if (cpDefinitionOptionRelId !=
					cpInstanceOptionValueRel.getCPDefinitionOptionRelId()) {

				continue;
			}

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelLocalService.
					fetchCPDefinitionOptionValueRel(
						cpInstanceOptionValueRel.
							getCPDefinitionOptionValueRelId());

			if ((cpDefinitionOptionValueRel != null) &&
				!cpDefinitionOptionValueRels.contains(
					cpDefinitionOptionValueRel)) {

				cpDefinitionOptionValueRels.add(cpDefinitionOptionValueRel);
			}
		}

		Collections.sort(
			cpDefinitionOptionValueRels,
			new CPDefinitionOptionValueRelPriorityComparator(true));

		return cpDefinitionOptionValueRels;
	}

	@Override
	public List<CPInstanceOptionValueRel>
		getCPInstanceCPInstanceOptionValueRels(long cpInstanceId) {

		return _cpInstanceOptionValueRelLocalService.
			getCPInstanceCPInstanceOptionValueRels(cpInstanceId);
	}

	@Override
	public FileVersion getCPInstanceImageFileVersion(
			long commerceAccountId, long companyId, long cpInstanceId)
		throws Exception {

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance == null) {
			return null;
		}

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpInstance.getCPDefinitionId());

		if (!_commerceProductViewPermission.contains(
				PermissionThreadLocal.getPermissionChecker(), commerceAccountId,
				cpDefinition.getCPDefinitionId())) {

			return null;
		}

		Map<String, List<String>>
			cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys =
				_cpDefinitionOptionRelLocalService.
					getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
						cpInstanceId);

		JSONArray keyValuesJSONArray = _jsonHelper.toJSONArray(
			cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys);

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
				cpInstance.getCPDefinitionId(), keyValuesJSONArray.toString(),
				CPAttachmentFileEntryConstants.TYPE_IMAGE, 0, 1);

		if (cpAttachmentFileEntries.isEmpty()) {
			CPAttachmentFileEntry cpAttachmentFileEntry =
				_cpDefinitionLocalService.getDefaultImageCPAttachmentFileEntry(
					cpInstance.getCPDefinitionId());

			if (cpAttachmentFileEntry != null) {
				FileEntry fileEntry = cpAttachmentFileEntry.fetchFileEntry();

				if (fileEntry != null) {
					return fileEntry.getFileVersion();
				}
			}

			FileEntry fileEntry =
				_commerceMediaProvider.getDefaultImageFileEntry(
					companyId, cpDefinition.getGroupId());

			return fileEntry.getFileVersion();
		}

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpAttachmentFileEntries.get(0);

		FileEntry fileEntry = cpAttachmentFileEntry.fetchFileEntry();

		return fileEntry.getFileVersion();
	}

	@Override
	public String getCPInstanceThumbnailSrc(
			long commerceAccountId, long cpInstanceId)
		throws Exception {

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance == null) {
			return StringPool.BLANK;
		}

		Map<String, List<String>>
			cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys =
				_cpDefinitionOptionRelLocalService.
					getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
						cpInstanceId);

		JSONArray keyValuesJSONArray = _jsonHelper.toJSONArray(
			cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys);

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
				cpInstance.getCPDefinitionId(), keyValuesJSONArray.toString(),
				CPAttachmentFileEntryConstants.TYPE_IMAGE, 0, 1);

		if (cpAttachmentFileEntries.isEmpty()) {
			CPDefinition cpDefinition = cpInstance.getCPDefinition();

			return cpDefinition.getDefaultImageThumbnailSrc(commerceAccountId);
		}

		CPAttachmentFileEntry cpAttachmentFileEntry =
			cpAttachmentFileEntries.get(0);

		return _commerceMediaResolver.getThumbnailURL(
			commerceAccountId,
			cpAttachmentFileEntry.getCPAttachmentFileEntryId());
	}

	@Override
	public CPInstance getDefaultCPInstance(long cpDefinitionId)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		if (!cpDefinition.isIgnoreSKUCombinations()) {
			throw new CPDefinitionIgnoreSKUCombinationsException(
				"Unable to get default CP instance if SKU combination present");
		}

		List<CPInstance> approvedCPInstances =
			_cpInstanceLocalService.getCPDefinitionApprovedCPInstances(
				cpDefinitionId);

		if (approvedCPInstances.isEmpty()) {
			return null;
		}

		if (approvedCPInstances.size() > 1) {
			throw new NoSuchCPInstanceException(
				"Unable to find default CP instance for CP definition ID " +
					cpDefinitionId);
		}

		return approvedCPInstances.get(0);
	}

	@Override
	public CPSku getDefaultCPSku(CPCatalogEntry cpCatalogEntry)
		throws Exception {

		if (!cpCatalogEntry.isIgnoreSKUCombinations()) {
			return null;
		}

		CPInstance cpInstance = getDefaultCPInstance(
			cpCatalogEntry.getCPDefinitionId());

		if (cpInstance == null) {
			return null;
		}

		return new CPSkuImpl(cpInstance);
	}

	@Override
	public List<KeyValuePair> getKeyValuePairs(
			long cpDefinitionId, String json, Locale locale)
		throws PortalException {

		List<KeyValuePair> values = new ArrayList<>();

		if (Validator.isNull(json)) {
			return values;
		}

		JSONArray jsonArray = _jsonHelper.getJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String key = jsonObject.getString("key");

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelLocalService.
					fetchCPDefinitionOptionRelByKey(cpDefinitionId, key);

			if (cpDefinitionOptionRel == null) {
				continue;
			}

			JSONArray valueJSONArray = _jsonHelper.getValueAsJSONArray(
				"value", jsonObject);

			for (int j = 0; j < valueJSONArray.length(); j++) {
				String value = valueJSONArray.getString(j);

				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					_cpDefinitionOptionValueRelLocalService.
						fetchCPDefinitionOptionValueRel(
							cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
							value);

				if (cpDefinitionOptionValueRel != null) {
					value = cpDefinitionOptionValueRel.getName(locale);
				}
				else {
					value = valueJSONArray.getString(j);
				}

				KeyValuePair keyValuePair = new KeyValuePair();

				keyValuePair.setKey(cpDefinitionOptionRel.getName(locale));
				keyValuePair.setValue(value);

				values.add(keyValuePair);
			}
		}

		return values;
	}

	private CPInstance _fetchCPInstanceBySKUContributors(
			long cpDefinitionId, String json)
		throws PortalException {

		int skuContributorCPDefinitionOptionRelsCount =
			_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsCount(
				cpDefinitionId, true);

		if (skuContributorCPDefinitionOptionRelsCount == 0) {
			return null;
		}

		Map<Long, List<Long>>
			cpDefinitionOptionRelCPDefinitionOptionValueRelIds =
				_cpDefinitionOptionRelLocalService.
					getCPDefinitionOptionRelCPDefinitionOptionValueRelIds(
						cpDefinitionId, true, json);

		if (cpDefinitionOptionRelCPDefinitionOptionValueRelIds.isEmpty() ||
			(skuContributorCPDefinitionOptionRelsCount !=
				cpDefinitionOptionRelCPDefinitionOptionValueRelIds.size())) {

			return null;
		}

		List<CPInstanceOptionValueRel> cpDefinitionCPInstanceOptionValueRels =
			_cpInstanceOptionValueRelLocalService.
				getCPDefinitionCPInstanceOptionValueRels(cpDefinitionId);

		Map<Long, Integer> cpInstanceCPInstanceOptionValueHits =
			new HashMap<>();

		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				cpDefinitionCPInstanceOptionValueRels) {

			if (!cpDefinitionOptionRelCPDefinitionOptionValueRelIds.containsKey(
					cpInstanceOptionValueRel.getCPDefinitionOptionRelId())) {

				continue;
			}

			List<Long> cpDefinitionOptionValueIds =
				cpDefinitionOptionRelCPDefinitionOptionValueRelIds.get(
					cpInstanceOptionValueRel.getCPDefinitionOptionRelId());

			if (!cpDefinitionOptionValueIds.contains(
					cpInstanceOptionValueRel.
						getCPDefinitionOptionValueRelId())) {

				continue;
			}

			if (cpInstanceCPInstanceOptionValueHits.containsKey(
					cpInstanceOptionValueRel.getCPInstanceId())) {

				int value = cpInstanceCPInstanceOptionValueHits.get(
					cpInstanceOptionValueRel.getCPInstanceId());

				cpInstanceCPInstanceOptionValueHits.put(
					cpInstanceOptionValueRel.getCPInstanceId(), value + 1);

				continue;
			}

			cpInstanceCPInstanceOptionValueHits.put(
				cpInstanceOptionValueRel.getCPInstanceId(), 1);
		}

		if (cpInstanceCPInstanceOptionValueHits.isEmpty()) {
			return null;
		}

		long cpInstanceId = _getTopId(cpInstanceCPInstanceOptionValueHits);

		if (skuContributorCPDefinitionOptionRelsCount !=
				cpInstanceCPInstanceOptionValueHits.get(cpInstanceId)) {

			return null;
		}

		return _cpInstanceLocalService.getCPInstance(cpInstanceId);
	}

	private CPInstance _fetchFirstAvailableReplacementCPInstance(
			long commerceChannelGroupId, CPInstance cpInstance)
		throws PortalException {

		if (cpInstance == null) {
			return null;
		}

		if (cpInstance.isDiscontinued() &&
			_cpAvailabilityChecker.check(
				commerceChannelGroupId, cpInstance,
				_cpDefinitionInventoryEngine.getMinOrderQuantity(cpInstance))) {

			return cpInstance;
		}

		return _fetchFirstAvailableReplacementCPInstance(
			commerceChannelGroupId,
			_cpInstanceLocalService.fetchCProductInstance(
				cpInstance.getReplacementCProductId(),
				cpInstance.getReplacementCPInstanceUuid()));
	}

	private long _getTopId(Map<Long, Integer> idIdHits) {
		long topId = 0;
		int topIdHits = 0;

		for (Map.Entry<Long, Integer> idIdHitsEntry : idIdHits.entrySet()) {
			if (topIdHits > idIdHitsEntry.getValue()) {
				continue;
			}

			topId = idIdHitsEntry.getKey();
			topIdHits = idIdHitsEntry.getValue();
		}

		return topId;
	}

	private boolean _hasCPInstanceCPDefinitionOptionValueRelIds(
		long cpInstanceId,
		List<Long> skuCombinationCPDefinitionOptionValueRelIds) {

		for (Long skuCombinationCPDefinitionOptionValueRelId :
				skuCombinationCPDefinitionOptionValueRelIds) {

			if (!_cpInstanceOptionValueRelLocalService.
					hasCPInstanceCPDefinitionOptionValueRel(
						skuCombinationCPDefinitionOptionValueRelId,
						cpInstanceId)) {

				return false;
			}
		}

		return true;
	}

	@Reference
	private AMImageHTMLTagFactory _amImageHTMLTagFactory;

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceMediaProvider _commerceMediaProvider;

	@Reference
	private CommerceMediaResolver _commerceMediaResolver;

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CPAttachmentFileEntryLocalService
		_cpAttachmentFileEntryLocalService;

	@Reference
	private CPAvailabilityChecker _cpAvailabilityChecker;

	@Reference
	private CPDefinitionInventoryEngine _cpDefinitionInventoryEngine;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private CPInstanceOptionValueRelLocalService
		_cpInstanceOptionValueRelLocalService;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private DDMFormValuesHelper _ddmFormValuesHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private JsonHelper _jsonHelper;

	@Reference
	private Portal _portal;

}