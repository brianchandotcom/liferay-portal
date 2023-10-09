/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.configuration;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marco Galluzzi
 */
@ProviderType
public interface DLFileEntryConfigurationProvider {

	public int getCompanyMaxNumberOfPages(long companyId);

	public long getCompanyPreviewableProcessorMaxSize(long companyId);

	public int getGroupMaxNumberOfPages(long groupId);

	public long getGroupPreviewableProcessorMaxSize(long groupId);

	public Map<Long, Long> getGroupPreviewableProcessorMaxSizeMap();

	public int getMaxNumberOfPages(
		ExtendedObjectClassDefinition.Scope scope, long scopePK);

	public int getMaxNumberOfPagesLimit(
		ExtendedObjectClassDefinition.Scope scope, long scopePK);

	public long getPreviewableProcessorMaxSize(
		ExtendedObjectClassDefinition.Scope scope, long scopePK);

	public long getPreviewableProcessorMaxSizeLimit(
		ExtendedObjectClassDefinition.Scope scope, long scopePK);

	public int getSystemMaxNumberOfPages();

	public long getSystemPreviewableProcessorMaxSize();

	public void update(
			long maxFileSize, int maxNumberOfPages,
			ExtendedObjectClassDefinition.Scope scope, long scopePK)
		throws Exception;

}