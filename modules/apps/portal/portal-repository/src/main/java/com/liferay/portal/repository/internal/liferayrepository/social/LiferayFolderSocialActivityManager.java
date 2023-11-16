/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.internal.liferayrepository.social;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.social.BaseSocialActivityManager;
import com.liferay.portal.kernel.social.SocialActivityManager;
import com.liferay.social.kernel.service.SocialActivityLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.portal.repository.liferayrepository.model.LiferayFolder",
	service = SocialActivityManager.class
)
public class LiferayFolderSocialActivityManager
	extends BaseSocialActivityManager<Folder> {

	@Override
	protected String getClassName(Folder folder) {
		return DLFolderConstants.getClassName();
	}

	@Override
	protected SocialActivityLocalService getSocialActivityLocalService() {
		return _socialActivityLocalService;
	}

	@Reference
	private SocialActivityLocalService _socialActivityLocalService;

}