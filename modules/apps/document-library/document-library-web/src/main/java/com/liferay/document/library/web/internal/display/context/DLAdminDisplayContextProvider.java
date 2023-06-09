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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfigurationFactory;
import com.liferay.document.library.configuration.DLFileOrderConfigurationProvider;
import com.liferay.document.library.kernel.versioning.VersioningStrategy;
import com.liferay.document.library.web.internal.display.context.helper.DLRequestHelper;
import com.liferay.document.library.web.internal.helper.DLTrashHelper;
import com.liferay.item.selector.ItemSelector;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.trash.TrashHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Adolfo Pérez
 */
@Component(service = DLAdminDisplayContextProvider.class)
public class DLAdminDisplayContextProvider {

	public DLAdminDisplayContext getDLAdminDisplayContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		httpServletRequest.setAttribute(
			ItemSelector.class.getName(), _itemSelector);

		DLRequestHelper dlRequestHelper = new DLRequestHelper(
			httpServletRequest);

		return new DLAdminDisplayContext(
			_getAssetAutoTaggerConfiguration(dlRequestHelper),
			_dlFileOrderConfigurationProvider, httpServletRequest,
			dlRequestHelper.getLiferayPortletRequest(),
			dlRequestHelper.getLiferayPortletResponse(), _trashHelper,
			_versioningStrategy);
	}

	public DLAdminManagementToolbarDisplayContext
		getDLAdminManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			DLAdminDisplayContext dlAdminDisplayContext) {

		DLRequestHelper dlRequestHelper = new DLRequestHelper(
			httpServletRequest);

		return new DLAdminManagementToolbarDisplayContext(
			dlAdminDisplayContext, _dlTrashHelper, httpServletRequest,
			dlRequestHelper.getLiferayPortletRequest(),
			dlRequestHelper.getLiferayPortletResponse());
	}

	private AssetAutoTaggerConfiguration _getAssetAutoTaggerConfiguration(
		DLRequestHelper dlRequestHelper) {

		try {
			return _assetAutoTaggerConfigurationFactory.
				getGroupAssetAutoTaggerConfiguration(
					_groupLocalService.getGroup(
						dlRequestHelper.getSiteGroupId()));
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Reference
	private AssetAutoTaggerConfigurationFactory
		_assetAutoTaggerConfigurationFactory;

	@Reference
	private DLFileOrderConfigurationProvider _dlFileOrderConfigurationProvider;

	@Reference
	private DLTrashHelper _dlTrashHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private TrashHelper _trashHelper;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile VersioningStrategy _versioningStrategy;

}