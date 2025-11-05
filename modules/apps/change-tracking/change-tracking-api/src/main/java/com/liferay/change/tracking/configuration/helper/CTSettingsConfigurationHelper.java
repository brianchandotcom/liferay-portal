/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2025-06
 */

package com.liferay.change.tracking.configuration.helper;

import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * @author Brooke Dalton
 */
public interface CTSettingsConfigurationHelper {

	public CTSettingsConfiguration getCTSettingsConfiguration(long companyId);

	public long getDefaultCTCollectionTemplateId(long companyId);

	public long getDefaultSandboxCTCollectionTemplateId(long companyId);

	public boolean isDefaultCTCollectionTemplate(
		long companyId, long ctCollectionTemplateId);

	public boolean isDefaultSandboxCTCollectionTemplate(
		long companyId, long ctCollectionTemplateId);

	public boolean isEnabled(long companyId);

	public boolean isSandboxEnabled(long companyId);

	public boolean isUnapprovedChangesAllowed(long companyId);

	public void save(long companyId, Map<String, Object> properties)
		throws PortalException;

}