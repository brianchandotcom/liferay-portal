/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.mapping;

import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Cheryl Tang
 */
@ProviderType
public interface CTMappingTableInfo {

	public List<Map.Entry<Long, Long>> getAddedMappings();

	public String getLeftColumnName();

	public Class<?> getLeftModelClass();

	public List<Map.Entry<Long, Long>> getRemovedMappings();

	public String getRightColumnName();

	public Class<?> getRightModelClass();

	public String getTableName();

}