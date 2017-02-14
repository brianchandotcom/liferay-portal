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

package com.liferay.friendly.url.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.friendly.url.model.FriendlyURL;
import com.liferay.friendly.url.service.FriendlyURLLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.friendly.url.model.FriendlyURL"},
	service = StagedModelRepository.class
)
public class FriendlyURLStagedModelRepository
	extends BaseStagedModelRepository<FriendlyURL> {

	@Override
	public FriendlyURL addStagedModel(
			PortletDataContext portletDataContext, FriendlyURL friendlyURL)
		throws PortalException {

		return _friendlyURLLocalService.addFriendlyURL(
			friendlyURL.getGroupId(), friendlyURL.getCompanyId(),
			friendlyURL.getClassNameId(), friendlyURL.getClassPK(),
			friendlyURL.getUrlTitle());
	}

	@Override
	public void deleteStagedModel(FriendlyURL friendlyURL)
		throws PortalException {

		_friendlyURLLocalService.deleteFriendlyURL(
			friendlyURL.getGroupId(), friendlyURL.getCompanyId(),
			friendlyURL.getClassNameId(), friendlyURL.getClassPK(),
			friendlyURL.getUrlTitle());
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		FriendlyURL friendlyURL = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (friendlyURL != null) {
			deleteStagedModel(friendlyURL);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		long classNameId = MapUtil.getLong(
			parameterMap, "parentStagedModelClassNameId");

		_friendlyURLLocalService.deleteGroupFriendlyURLs(
			portletDataContext.getGroupId(), classNameId);
	}

	@Override
	public List<FriendlyURL> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _friendlyURLLocalService.getFriendlyURLsByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _friendlyURLLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public FriendlyURL saveStagedModel(FriendlyURL friendlyURL)
		throws PortalException {

		if (friendlyURL.isMain()) {
			FriendlyURL mainFriendlyURL =
				_friendlyURLLocalService.getMainFriendlyURL(
					friendlyURL.getGroupId(), friendlyURL.getCompanyId(),
					friendlyURL.getClassNameId(), friendlyURL.getClassPK());

			if (!mainFriendlyURL.equals(friendlyURL)) {
				mainFriendlyURL.setMain(false);

				_friendlyURLLocalService.updateFriendlyURL(mainFriendlyURL);
			}
		}

		friendlyURL.setUrlTitle(
			_friendlyURLLocalService.getUniqueUrlTitle(
				friendlyURL.getGroupId(), friendlyURL.getCompanyId(),
				friendlyURL.getClassNameId(), friendlyURL.getClassPK(),
				friendlyURL.getUrlTitle()));

		return _friendlyURLLocalService.updateFriendlyURL(friendlyURL);
	}

	@Override
	public FriendlyURL updateStagedModel(
			PortletDataContext portletDataContext, FriendlyURL friendlyURL)
		throws PortalException {

		return saveStagedModel(friendlyURL);
	}

	@Reference(unbind = "-")
	protected void setFriendlyURLLocalService(
		FriendlyURLLocalService friendlyURLLocalService) {

		_friendlyURLLocalService = friendlyURLLocalService;
	}

	private FriendlyURLLocalService _friendlyURLLocalService;

}