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
import com.liferay.portal.kernel.model.Group;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.base.RedirectNotFoundEntryLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the redirect not found entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.redirect.service.RedirectNotFoundEntryLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RedirectNotFoundEntryLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.redirect.model.RedirectNotFoundEntry",
	service = AopService.class
)
public class RedirectNotFoundEntryLocalServiceImpl
	extends RedirectNotFoundEntryLocalServiceBaseImpl {

	@Override
	public RedirectNotFoundEntry addOrUpdateRedirectNotFoundEntry(
		Group group, String url) {

		RedirectNotFoundEntry redirectNotFoundEntry =
			redirectNotFoundEntryPersistence.fetchByG_U(
				group.getGroupId(), url);

		if (redirectNotFoundEntry == null) {
			redirectNotFoundEntry = redirectNotFoundEntryPersistence.create(
				counterLocalService.increment());

			redirectNotFoundEntry.setGroupId(group.getGroupId());

			redirectNotFoundEntry.setCompanyId(group.getCompanyId());
			redirectNotFoundEntry.setUrl(url);

			redirectNotFoundEntry = redirectNotFoundEntryPersistence.update(
				redirectNotFoundEntry);
		}

		redirectNotFoundEntry.setHits(redirectNotFoundEntry.getHits() + 1);

		return redirectNotFoundEntryPersistence.update(redirectNotFoundEntry);
	}

}