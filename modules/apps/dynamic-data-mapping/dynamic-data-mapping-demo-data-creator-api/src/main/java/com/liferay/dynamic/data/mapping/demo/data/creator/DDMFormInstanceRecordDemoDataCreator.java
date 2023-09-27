/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.demo.data.creator;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Inácio Nery
 */
@ProviderType
public interface DDMFormInstanceRecordDemoDataCreator {

	public DDMFormInstanceRecord create(
			long userId, long groupId, Date createDate, long ddmFormInstanceId)
		throws PortalException;

	public void delete() throws PortalException;

}