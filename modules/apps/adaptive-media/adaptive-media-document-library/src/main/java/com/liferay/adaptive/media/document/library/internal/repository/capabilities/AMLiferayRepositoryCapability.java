/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.document.library.internal.repository.capabilities;

import com.liferay.portal.kernel.repository.capabilities.Capability;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "repository.class.name=com.liferay.portal.repository.liferayrepository.LiferayRepository",
	service = Capability.class
)
public class AMLiferayRepositoryCapability extends BaseCapability {
}