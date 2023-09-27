/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		UnicodeProperties typeSettingsUnicodeProperties);

	public void setUserLocalService(UserLocalService userLocalService);

}