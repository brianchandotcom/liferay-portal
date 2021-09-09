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

package com.liferay.asset.list.web.internal.model.listener;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryUsage;
import com.liferay.asset.list.service.AssetListEntryUsageLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(service = ModelListener.class)
public class FragmentEntryLinkModelListener
	extends BaseModelListener<FragmentEntryLink> {

	@Override
	public void onAfterCreate(FragmentEntryLink fragmentEntryLink)
		throws ModelListenerException {

		_updateAssetListEntryUsages(fragmentEntryLink);
	}

	@Override
	public void onAfterRemove(FragmentEntryLink fragmentEntryLink)
		throws ModelListenerException {

		_assetListEntryUsageLocalService.deleteAssetListEntryUsages(
			String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
			_portal.getClassNameId(FragmentEntryLink.class),
			fragmentEntryLink.getPlid());
	}

	@Override
	public void onAfterUpdate(
			FragmentEntryLink originalFragmentEntryLink,
			FragmentEntryLink fragmentEntryLink)
		throws ModelListenerException {

		_updateAssetListEntryUsages(fragmentEntryLink);
	}

	private void _addAssetListEntryUsage(
		long classNameId, long fragmentEntryLinkId, long groupId, String key,
		long plid) {

		AssetListEntryUsage assetListEntryUsage =
			_assetListEntryUsageLocalService.fetchAssetListEntryUsage(
				groupId, classNameId, String.valueOf(fragmentEntryLinkId),
				_portal.getClassNameId(FragmentEntryLink.class), key, plid);

		if (assetListEntryUsage != null) {
			return;
		}

		ServiceContext serviceContext = Optional.ofNullable(
			ServiceContextThreadLocal.getServiceContext()
		).orElse(
			new ServiceContext()
		);

		try {
			_assetListEntryUsageLocalService.addAssetListEntryUsage(
				serviceContext.getUserId(), groupId, classNameId,
				String.valueOf(fragmentEntryLinkId),
				_portal.getClassNameId(FragmentEntryLink.class), key, plid,
				serviceContext);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private List<FragmentConfigurationField>
		_getCollectionSelectorFragmentConfigurationFields(
			FragmentEntryLink fragmentEntryLink) {

		List<FragmentConfigurationField> fragmentConfigurationFields =
			_fragmentEntryConfigurationParser.getFragmentConfigurationFields(
				fragmentEntryLink.getConfiguration());

		Stream<FragmentConfigurationField> stream =
			fragmentConfigurationFields.stream();

		return stream.filter(
			fragmentConfigurationField -> Objects.equals(
				fragmentConfigurationField.getType(), "collectionSelector")
		).collect(
			Collectors.toList()
		);
	}

	private void _updateAssetListEntryUsages(
		FragmentEntryLink fragmentEntryLink) {

		_assetListEntryUsageLocalService.deleteAssetListEntryUsages(
			String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
			_portal.getClassNameId(FragmentEntryLink.class),
			fragmentEntryLink.getPlid());

		List<FragmentConfigurationField> fragmentConfigurationFields =
			_getCollectionSelectorFragmentConfigurationFields(
				fragmentEntryLink);

		for (FragmentConfigurationField fragmentConfigurationField :
				fragmentConfigurationFields) {

			Object fieldValue = _fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getConfiguration(),
				fragmentEntryLink.getEditableValues(),
				LocaleUtil.getMostRelevantLocale(),
				fragmentConfigurationField.getName());

			if (!(fieldValue instanceof JSONObject)) {
				continue;
			}

			JSONObject fieldValueJSONObject = (JSONObject)fieldValue;

			if (fieldValueJSONObject.has("key")) {
				_addAssetListEntryUsage(
					_portal.getClassNameId(InfoCollectionProvider.class),
					fragmentEntryLink.getFragmentEntryLinkId(),
					fragmentEntryLink.getGroupId(),
					fieldValueJSONObject.getString("key"),
					fragmentEntryLink.getPlid());
			}

			if (fieldValueJSONObject.has("classPK")) {
				_addAssetListEntryUsage(
					_portal.getClassNameId(AssetListEntry.class),
					fragmentEntryLink.getFragmentEntryLinkId(),
					fragmentEntryLink.getGroupId(),
					fieldValueJSONObject.getString("classPK"),
					fragmentEntryLink.getPlid());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkModelListener.class);

	@Reference
	private AssetListEntryUsageLocalService _assetListEntryUsageLocalService;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private Portal _portal;

}