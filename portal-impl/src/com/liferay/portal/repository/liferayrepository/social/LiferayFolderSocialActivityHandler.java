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

package com.liferay.portal.repository.liferayrepository.social;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.social.handler.BaseSocialActivityHandler;
import com.liferay.portlet.social.handler.SocialActivityHandler;
import com.liferay.portlet.social.service.SocialActivityLocalService;

/**
 * @author Adolfo Pérez
 */
@OSGiBeanProperties(
	property = "model.className=com.liferay.portal.repository.liferayrepository.model.LiferayFolder",
	service = SocialActivityHandler.class
)
public class LiferayFolderSocialActivityHandler
	extends BaseSocialActivityHandler<Folder> {

	@Override
	protected String getClassName(Folder folder) {
		return DLFolderConstants.getClassName();
	}

	@Override
	protected SocialActivityLocalService getSocialActivityLocalService() {
		return socialActivityLocalService;
	}

	@BeanReference(type = SocialActivityLocalService.class)
	protected SocialActivityLocalService socialActivityLocalService;

}