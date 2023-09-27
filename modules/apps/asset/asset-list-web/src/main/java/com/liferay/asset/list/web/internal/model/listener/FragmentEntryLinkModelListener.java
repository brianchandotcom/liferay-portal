/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.web.internal.model.listener;

import com.liferay.asset.list.model.AssetListEntry;
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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Objects;

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
			_getFragmentEntryLinkClassNameId(), fragmentEntryLink.getPlid());
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

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		try {
			_assetListEntryUsageLocalService.addAssetListEntryUsage(
				serviceContext.getUserId(), groupId, classNameId,
				String.valueOf(fragmentEntryLinkId),
				_getFragmentEntryLinkClassNameId(), key, plid, serviceContext);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private long _getAssetListEntryClassNameId() {
		if (_assetListEntryClassNameId != null) {
			return _assetListEntryClassNameId;
		}

		_assetListEntryClassNameId = _portal.getClassNameId(
			AssetListEntry.class.getName());

		return _assetListEntryClassNameId;
	}

	private List<FragmentConfigurationField>
		_getCollectionSelectorFragmentConfigurationFields(
			FragmentEntryLink fragmentEntryLink) {

		return ListUtil.filter(
			_fragmentEntryConfigurationParser.getFragmentConfigurationFields(
				fragmentEntryLink.getConfiguration()),
			fragmentConfigurationField -> Objects.equals(
				fragmentConfigurationField.getType(), "collectionSelector"));
	}

	private long _getFragmentEntryLinkClassNameId() {
		if (_fragmentEntryLinkClassNameId != null) {
			return _fragmentEntryLinkClassNameId;
		}

		_fragmentEntryLinkClassNameId = _portal.getClassNameId(
			FragmentEntryLink.class.getName());

		return _fragmentEntryLinkClassNameId;
	}

	private long _getInfoCollectionProviderClassNameId() {
		if (_infoCollectionProviderClassNameId != null) {
			return _infoCollectionProviderClassNameId;
		}

		_infoCollectionProviderClassNameId = _portal.getClassNameId(
			InfoCollectionProvider.class.getName());

		return _infoCollectionProviderClassNameId;
	}

	private void _updateAssetListEntryUsages(
		FragmentEntryLink fragmentEntryLink) {

		_assetListEntryUsageLocalService.deleteAssetListEntryUsages(
			String.valueOf(fragmentEntryLink.getFragmentEntryLinkId()),
			_getFragmentEntryLinkClassNameId(), fragmentEntryLink.getPlid());

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
					_getInfoCollectionProviderClassNameId(),
					fragmentEntryLink.getFragmentEntryLinkId(),
					fragmentEntryLink.getGroupId(),
					fieldValueJSONObject.getString("key"),
					fragmentEntryLink.getPlid());
			}

			if (fieldValueJSONObject.has("classPK")) {
				_addAssetListEntryUsage(
					_getAssetListEntryClassNameId(),
					fragmentEntryLink.getFragmentEntryLinkId(),
					fragmentEntryLink.getGroupId(),
					fieldValueJSONObject.getString("classPK"),
					fragmentEntryLink.getPlid());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkModelListener.class);

	private Long _assetListEntryClassNameId;

	@Reference
	private AssetListEntryUsageLocalService _assetListEntryUsageLocalService;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	private Long _fragmentEntryLinkClassNameId;
	private Long _infoCollectionProviderClassNameId;

	@Reference
	private Portal _portal;

}