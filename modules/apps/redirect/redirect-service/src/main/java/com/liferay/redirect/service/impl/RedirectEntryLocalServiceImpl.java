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

package com.liferay.redirect.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.base.RedirectEntryLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.redirect.model.RedirectEntry",
	service = AopService.class
)
public class RedirectEntryLocalServiceImpl
	extends RedirectEntryLocalServiceBaseImpl {

	@Override
	public RedirectEntry addRedirectEntry(
		long groupId, String destinationURL, String sourceURL,
		ServiceContext serviceContext) {

		RedirectEntry redirectEntry = redirectEntryPersistence.create(
			counterLocalService.increment());

		redirectEntry.setUuid(serviceContext.getUuid());

		redirectEntry.setGroupId(groupId);

		redirectEntry.setCompanyId(serviceContext.getCompanyId());
		redirectEntry.setUserId(serviceContext.getUserId());

		redirectEntry.setDestinationURL(destinationURL);
		redirectEntry.setSourceURL(sourceURL);

		return redirectEntryPersistence.update(redirectEntry);
	}

}