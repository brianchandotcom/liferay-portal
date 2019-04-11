/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.repository;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Mika Koivisto
 */
public interface BaseRepository extends Repository {

	public LocalRepository getLocalRepository();

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             com.liferay.portal.kernel.repository.registry.RepositoryDefiner#getSupportedParameters(
	 *             )}
	 */
	@Deprecated
	public String[][] getSupportedParameters();

	public void initRepository() throws PortalException;

	public void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService);

	public void setCompanyId(long companyId);

	public void setCompanyLocalService(CompanyLocalService companyLocalService);

	public void setDLAppHelperLocalService(
		DLAppHelperLocalService dlAppHelperLocalService);

	public void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService);

	public void setGroupId(long groupId);

	public void setRepositoryEntryLocalService(
		RepositoryEntryLocalService repositoryEntryLocalService);

	public void setRepositoryId(long repositoryId);

	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties);

	public void setUserLocalService(UserLocalService userLocalService);

}