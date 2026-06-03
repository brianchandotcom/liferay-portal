/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.standalone.site.initializer.internal.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez Álvarez
 */
@Component(service = ServiceWrapper.class)
public class CMSGroupServiceWrapper extends GroupServiceWrapper {

	@Override
	public List<Group> getUserSitesGroups(
			long userId, String[] classNames, int max)
		throws PortalException {

		return Collections.emptyList();
	}

}