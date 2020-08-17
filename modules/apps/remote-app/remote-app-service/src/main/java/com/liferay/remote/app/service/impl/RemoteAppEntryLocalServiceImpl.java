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

package com.liferay.remote.app.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.remote.app.exception.DuplicateRemoteAppEntryURLException;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.base.RemoteAppEntryLocalServiceBaseImpl;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.remote.app.model.RemoteAppEntry",
	service = AopService.class
)
public class RemoteAppEntryLocalServiceImpl
	extends RemoteAppEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteAppEntry addRemoteAppEntry(
			long userId, Map<Locale, String> nameMap, String url,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long companyId = user.getCompanyId();

		validate(companyId, 0, url);

		long remoteAppEntryId = counterLocalService.increment();

		RemoteAppEntry remoteAppEntry = remoteAppEntryPersistence.create(
			remoteAppEntryId);

		remoteAppEntry.setUuid(serviceContext.getUuid());
		remoteAppEntry.setCompanyId(companyId);
		remoteAppEntry.setUserId(user.getUserId());
		remoteAppEntry.setUserName(user.getFullName());
		remoteAppEntry.setNameMap(nameMap);
		remoteAppEntry.setUrl(url);

		return remoteAppEntryPersistence.update(remoteAppEntry);
	}

	@Override
	public RemoteAppEntry updateRemoteAppEntry(
			long remoteAppEntryId, Map<Locale, String> nameMap, String url,
			ServiceContext serviceContext)
		throws PortalException {

		validate(serviceContext.getCompanyId(), remoteAppEntryId, url);

		RemoteAppEntry remoteAppEntry =
			remoteAppEntryPersistence.findByPrimaryKey(remoteAppEntryId);

		remoteAppEntry.setNameMap(nameMap);
		remoteAppEntry.setUrl(url);

		return remoteAppEntryPersistence.update(remoteAppEntry);
	}

	protected void validate(long companyId, long remoteAppEntryId, String url)
		throws PortalException {

		RemoteAppEntry remoteAppEntry = remoteAppEntryPersistence.fetchByC_U(
			companyId, StringUtil.trim(url));

		if ((remoteAppEntry != null) &&
			(remoteAppEntry.getRemoteAppEntryId() != remoteAppEntryId)) {

			throw new DuplicateRemoteAppEntryURLException(
				"{remoteAppEntryId=" + remoteAppEntryId + "}");
		}
	}

}