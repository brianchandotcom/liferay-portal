/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.configuration;

/**
 * @author Jorge Avalos
 */
public interface PortalInstanceConfigurationExporter {

	/**
	 * Copies the scoped configurations owned by the company into the schema
	 * exported for it. Does nothing when database partitioning is enabled,
	 * because the exported partition already carries them.
	 *
	 * @param companyId the primary key of the exported company
	 */
	public void exportConfigurations(long companyId) throws Exception;

}