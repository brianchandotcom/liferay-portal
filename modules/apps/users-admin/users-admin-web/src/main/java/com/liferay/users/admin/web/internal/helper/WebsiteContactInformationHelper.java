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

package com.liferay.users.admin.web.internal.helper;

import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WebsiteLocalService;
import com.liferay.portal.kernel.service.WebsiteService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.users.admin.kernel.util.UsersAdmin;

import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Drew Brokke
 */
public class WebsiteContactInformationHelper
	extends BaseContactInformationHelper<Website> {

	public WebsiteContactInformationHelper(
		Class entityClass, long entityClassPK, WebsiteService websiteService,
		WebsiteLocalService websiteLocalService, UsersAdmin usersAdmin) {

		_entityClass = entityClass;
		_entityClassPK = entityClassPK;
		_websiteService = websiteService;
		_websiteLocalService = websiteLocalService;
		_usersAdmin = usersAdmin;
	}

	@Override
	protected Website addEntry(Website website) throws Exception {
		return _websiteService.addWebsite(
			_entityClass.getName(), _entityClassPK, website.getUrl(),
			website.getTypeId(), website.isPrimary(), new ServiceContext());
	}

	@Override
	protected Website constructEntry(ActionRequest actionRequest)
		throws Exception {

		long websiteId = ParamUtil.getLong(actionRequest, "primaryKey");

		boolean primary = ParamUtil.getBoolean(actionRequest, "websitePrimary");
		long typeId = ParamUtil.getLong(actionRequest, "websiteTypeId");
		String url = ParamUtil.getString(actionRequest, "websiteUrl");

		Website website = _websiteLocalService.createWebsite(websiteId);

		website.setUrl(url);
		website.setTypeId(typeId);
		website.setPrimary(primary);

		return website;
	}

	@Override
	protected void deleteEntry(long websiteId) throws Exception {
		_websiteService.deleteWebsite(websiteId);
	}

	@Override
	protected List<Website> getEntries() throws Exception {
		return _websiteService.getWebsites(
			_entityClass.getName(), _entityClassPK);
	}

	@Override
	protected Website getEntry(long websiteId) throws Exception {
		return _websiteService.getWebsite(websiteId);
	}

	@Override
	protected long getEntryId(Website website) {
		return website.getWebsiteId();
	}

	@Override
	protected boolean isPrimaryEntry(Website website) {
		return website.isPrimary();
	}

	@Override
	protected void setEntryPrimary(Website website, boolean primary) {
		website.setPrimary(primary);
	}

	@Override
	protected void updateEntry(Website website) throws Exception {
		_websiteService.updateWebsite(
			website.getWebsiteId(), website.getUrl(), website.getTypeId(),
			website.isPrimary());
	}

	private final Class _entityClass;
	private final long _entityClassPK;
	private final UsersAdmin _usersAdmin;
	private final WebsiteLocalService _websiteLocalService;
	private final WebsiteService _websiteService;

}