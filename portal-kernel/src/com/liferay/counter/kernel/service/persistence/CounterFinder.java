/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.counter.kernel.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface CounterFinder {

	public java.util.List<String> getNames();

	public String getRegistryName();

	public long increment();

	public long increment(String name);

	public long increment(String name, int size);

	public void invalidate();

	public void rename(String oldName, String newName);

	public void reset(String name);

	public void reset(String name, long size);

}