/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.crud;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import javax.ws.rs.core.UriInfo;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Correa
 */
@ProviderType
public interface VulcanCRUDItemDelegateBuilder {

	public UriInfoStepVulcanCRUDItemDelegateBuilder acceptLanguage(
		AcceptLanguage acceptLanguage);

	@ProviderType
	public interface BuildStepVulcanCRUDItemDelegateBuilder {

		public VulcanCRUDItemDelegate build() throws Exception;

	}

	@ProviderType
	public interface UriInfoStepVulcanCRUDItemDelegateBuilder {

		public UserStepVulcanCRUDItemDelegateBuilder uriInfo(UriInfo uriInfo);

	}

	@ProviderType
	public interface UserStepVulcanCRUDItemDelegateBuilder {

		public BuildStepVulcanCRUDItemDelegateBuilder user(User user);

	}

}