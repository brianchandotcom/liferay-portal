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

package com.liferay.remote.web.app.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.remote.web.app.exception.DuplicateEntryURLException;
import com.liferay.remote.web.app.model.RemoteWebAppEntry;
import com.liferay.remote.web.app.service.base.RemoteWebAppEntryLocalServiceBaseImpl;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the remote web app entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.remote.web.app.service.RemoteWebAppEntryLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RemoteWebAppEntryLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.remote.web.app.model.RemoteWebAppEntry",
	service = AopService.class
)
public class RemoteWebAppEntryLocalServiceImpl
	extends RemoteWebAppEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RemoteWebAppEntry addEntry(
			long userId, Map<Locale, String> nameMap, String url,
			ServiceContext serviceContext)
		throws PortalException {

		// Entry

		User user = userLocalService.getUser(userId);

		long companyId = user.getCompanyId();

		validate(companyId, 0, url);

		long entryId = counterLocalService.increment();

		RemoteWebAppEntry remoteWebAppEntry =
			remoteWebAppEntryPersistence.create(entryId);

		remoteWebAppEntry.setUuid(serviceContext.getUuid());
		remoteWebAppEntry.setCompanyId(companyId);
		remoteWebAppEntry.setUserId(user.getUserId());
		remoteWebAppEntry.setUserName(user.getFullName());
		remoteWebAppEntry.setNameMap(nameMap);
		remoteWebAppEntry.setUrl(url);

		try {
			remoteWebAppEntry = remoteWebAppEntryPersistence.update(
				remoteWebAppEntry);
		}
		catch (SystemException systemException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Add failed, fetch {companyId=",
						String.valueOf(user.getCompanyId()), "url=", url, "}"));
			}

			remoteWebAppEntry = remoteWebAppEntryPersistence.fetchByC_U(
				user.getCompanyId(), url);

			if (remoteWebAppEntry == null) {
				throw systemException;
			}
		}

		return remoteWebAppEntry;
	}

	@Override
	public RemoteWebAppEntry updateEntry(
			long entryId, Map<Locale, String> nameMap, String url,
			ServiceContext serviceContext)
		throws PortalException {

		validate(serviceContext.getCompanyId(), entryId, url);

		RemoteWebAppEntry remoteWebAppEntry =
			remoteWebAppEntryPersistence.findByPrimaryKey(entryId);

		remoteWebAppEntry.setNameMap(nameMap);
		remoteWebAppEntry.setUrl(url);

		return remoteWebAppEntryPersistence.update(remoteWebAppEntry);
	}

	protected void validate(long companyId, long entryId, String url)
		throws PortalException {

		RemoteWebAppEntry remoteWebAppEntry =
			remoteWebAppEntryPersistence.fetchByC_U(
				companyId, StringUtil.trim(url));

		if ((remoteWebAppEntry != null) &&
			(remoteWebAppEntry.getEntryId() != entryId)) {

			throw new DuplicateEntryURLException("{entryId=" + entryId + "}");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteWebAppEntryLocalServiceImpl.class);

}