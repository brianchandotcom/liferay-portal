/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.demo.data.creator;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Sergio González
 */
@ProviderType
public interface MBCategoryDemoDataCreator
	extends BaseMBCategoryDemoDataCreator {

	public MBCategory create(long userId, long parentCategoryId)
		throws IOException, PortalException;

}