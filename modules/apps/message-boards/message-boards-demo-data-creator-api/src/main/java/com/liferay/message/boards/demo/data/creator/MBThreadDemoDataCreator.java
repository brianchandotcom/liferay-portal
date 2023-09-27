/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.demo.data.creator;

import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Sergio González
 */
@ProviderType
public interface MBThreadDemoDataCreator {

	public MBThread create(List<Long> userIds, long categoryId)
		throws IOException, PortalException;

	public default MBThread create(long userId, long categoryId)
		throws IOException, PortalException {

		return create(Arrays.asList(userId), categoryId);
	}

	public void delete() throws PortalException;

}