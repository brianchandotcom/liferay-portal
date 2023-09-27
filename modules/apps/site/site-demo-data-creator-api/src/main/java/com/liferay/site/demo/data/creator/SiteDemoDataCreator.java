/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.demo.data.creator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;

/**
 * @author Pei-Jung Lan
 */
public interface SiteDemoDataCreator {

	public Group create(long companyId) throws PortalException;

	public Group create(long companyId, String name) throws PortalException;

	public void delete() throws PortalException;

}