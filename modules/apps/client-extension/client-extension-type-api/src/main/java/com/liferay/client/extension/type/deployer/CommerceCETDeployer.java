/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.deployer;

import com.liferay.client.extension.type.CET;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Andrea Sbarra
 */
@ProviderType
public interface CommerceCETDeployer {

	public List<ServiceRegistration<?>> deploy(CET cet);

}