/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.configuration;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.redirect.model.RedirectPatternEntry;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Alicia García
 */
@ProviderType
public interface RedirectPatternConfigurationProvider {

	public List<RedirectPatternEntry> getRedirectPatternEntries(long groupId)
		throws ConfigurationException;

	public void updatePatternStrings(
			long groupId, List<RedirectPatternEntry> redirectPatternEntries)
		throws Exception;

}