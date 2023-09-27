/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.executor;

import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Matija Petanjek
 */
@ProviderType
public interface DispatchTaskExecutorRegistry {

	public DispatchTaskExecutor fetchDispatchTaskExecutor(String type);

	public String fetchDispatchTaskExecutorName(String type);

	public Set<String> getDispatchTaskExecutorTypes();

	public boolean isClusterModeSingle(String type);

	public boolean isHiddenInUI(String type);

}