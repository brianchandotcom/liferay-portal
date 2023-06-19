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

package com.liferay.document.library.configuration;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Adolfo Pérez
 */
@ProviderType
public interface DLSizeLimitConfigurationProvider {

	public long getCompanyFileMaxSize(long companyId);

	public long getCompanyMaxSizeToCopy(long companyId);

	public Map<String, Long> getCompanyMimeTypeSizeLimit(long companyId);

	public long getGroupFileMaxSize(long groupId);

	public long getGroupMaxSizeToCopy(long groupId);

	public Map<String, Long> getGroupMimeTypeSizeLimit(long groupId);

	public long getSystemFileMaxSize();

	public long getSystemMaxSizeToCopy();

	public Map<String, Long> getSystemMimeTypeSizeLimit();

	public void updateCompanySizeLimit(
			long companyId, long fileMaxSize,
			Map<String, Long> mimeTypeSizeLimit)
		throws Exception;

	public void updateGroupSizeLimit(
			long groupId, long fileMaxSize, Map<String, Long> mimeTypeSizeLimit)
		throws Exception;

	public void updateSystemSizeLimit(
			long fileMaxSize, Map<String, Long> mimeTypeSizeLimit)
		throws Exception;

}