/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.configuration;

import java.util.Locale;
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

	public String[] getCopyToFailInfo(
		long companyId, long groupId, long size, Locale locale);

	public long getGroupFileMaxSize(long groupId);

	public long getGroupMaxSizeToCopy(long groupId);

	public Map<String, Long> getGroupMimeTypeSizeLimit(long groupId);

	public long getSystemFileMaxSize();

	public long getSystemMaxSizeToCopy();

	public Map<String, Long> getSystemMimeTypeSizeLimit();

	public boolean isCopyToAllowed(long companyId, long groupId, long size);

	public void updateCompanySizeLimit(
			long companyId, long fileMaxSize, long maxSizeToCopy,
			Map<String, Long> mimeTypeSizeLimit)
		throws Exception;

	public void updateGroupSizeLimit(
			long groupId, long fileMaxSize, long maxSizeToCopy,
			Map<String, Long> mimeTypeSizeLimit)
		throws Exception;

	public void updateSystemSizeLimit(
			long fileMaxSize, long maxSizeToCopy,
			Map<String, Long> mimeTypeSizeLimit)
		throws Exception;

}