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

package com.liferay.users.admin.web.internal.manager;

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
public class WebsiteContactInfoManager extends BaseContactInfoManager<Website> {

	public WebsiteContactInfoManager(
		String className, long classPK, WebsiteLocalService websiteLocalService,
		WebsiteService websiteService, UsersAdmin usersAdmin) {

		_className = className;
		_classPK = classPK;
		_websiteLocalService = websiteLocalService;
		_websiteService = websiteService;
		_usersAdmin = usersAdmin;
	}

	@Override
	protected Website construct(ActionRequest actionRequest) throws Exception {
		long websiteId = ParamUtil.getLong(actionRequest, "primaryKey");

		String url = ParamUtil.getString(actionRequest, "websiteUrl");
		long typeId = ParamUtil.getLong(actionRequest, "websiteTypeId");
		boolean primary = ParamUtil.getBoolean(actionRequest, "websitePrimary");

		Website website = _websiteLocalService.createWebsite(websiteId);

		website.setUrl(url);
		website.setTypeId(typeId);
		website.setPrimary(primary);

		return website;
	}

	@Override
	protected Website doAdd(Website website) throws Exception {
		return _websiteService.addWebsite(
			_className, _classPK, website.getUrl(), website.getTypeId(),
			website.isPrimary(), new ServiceContext());
	}

	@Override
	protected void doDelete(long websiteId) throws Exception {
		_websiteService.deleteWebsite(websiteId);
	}

	@Override
	protected void doUpdate(Website website) throws Exception {
		_websiteService.updateWebsite(
			website.getWebsiteId(), website.getUrl(), website.getTypeId(),
			website.isPrimary());
	}

	@Override
	protected Website get(long websiteId) throws Exception {
		return _websiteService.getWebsite(websiteId);
	}

	@Override
	protected List<Website> getAll() throws Exception {
		return _websiteService.getWebsites(_className, _classPK);
	}

	@Override
	protected long getPrimaryKey(Website website) {
		return website.getWebsiteId();
	}

	@Override
	protected boolean isPrimary(Website website) {
		return website.isPrimary();
	}

	@Override
	protected void setPrimary(Website website, boolean primary) {
		website.setPrimary(primary);
	}

	private final String _className;
	private final long _classPK;
	private final UsersAdmin _usersAdmin;
	private final WebsiteLocalService _websiteLocalService;
	private final WebsiteService _websiteService;

}