/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.demo.data.creator;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alejandro Hernández
 */
@ProviderType
public interface BlogsEntryDemoDataCreator {

	public BlogsEntry create(long userId, long groupId)
		throws IOException, PortalException;

	public void delete() throws PortalException;

}