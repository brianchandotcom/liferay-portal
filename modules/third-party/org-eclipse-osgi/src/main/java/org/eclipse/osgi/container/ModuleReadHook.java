/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package org.eclipse.osgi.container;

/**
 *
 * @author Matthew Tambara
 */
public interface ModuleReadHook {

	public void process(long bundleId, String location);
	
}
/* @generated */