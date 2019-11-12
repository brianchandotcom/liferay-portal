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

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.repository.cmis.configuration.CMISRepositoryConfiguration;
import com.liferay.document.library.repository.cmis.internal.constants.CMISRepositoryConstants;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.document.library.repository.cmis.configuration.CMISRepositoryConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	property = "repository.target.class.name=" + CMISRepositoryConstants.CMIS_ATOMPUB_REPOSITORY_CLASSNAME,
	service = RepositoryFactory.class
)
public class CMISAtomPubRepositoryFactory
	extends BaseCMISRepositoryFactory<CMISAtomPubRepository> {

	@Activate
	protected void activate(Map<String, Object> properties) {
		CMISRepositoryConfiguration cmisRepositoryConfiguration =
			ConfigurableUtil.createConfigurable(
				CMISRepositoryConfiguration.class, properties);

		super.setCMISRepositoryConfiguration(cmisRepositoryConfiguration);
	}

	@Override
	protected CMISAtomPubRepository createBaseRepository() {
		return new CMISAtomPubRepository();
	}

	@Override
	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		super.setAssetEntryLocalService(assetEntryLocalService);
	}

	@Override
	@Reference(unbind = "-")
	protected void setCMISSessionCache(CMISSessionCache cmisSessionCache) {
		super.setCMISSessionCache(cmisSessionCache);
	}

	@Override
	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		super.setCompanyLocalService(companyLocalService);
	}

	@Override
	@Reference(unbind = "-")
	protected void setDLAppHelperLocalService(
		DLAppHelperLocalService dlAppHelperLocalService) {

		super.setDLAppHelperLocalService(dlAppHelperLocalService);
	}

	@Override
	@Reference(unbind = "-")
	protected void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		super.setDLFolderLocalService(dlFolderLocalService);
	}

	@Override
	@Reference(unbind = "-")
	protected void setLockManager(LockManager lockManager) {
		super.setLockManager(lockManager);
	}

	@Override
	@Reference(unbind = "-")
	protected void setRepositoryEntryLocalService(
		RepositoryEntryLocalService repositoryEntryLocalService) {

		super.setRepositoryEntryLocalService(repositoryEntryLocalService);
	}

	@Override
	@Reference(unbind = "-")
	protected void setRepositoryLocalService(
		RepositoryLocalService repositoryLocalService) {

		super.setRepositoryLocalService(repositoryLocalService);
	}

	@Override
	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		super.setUserLocalService(userLocalService);
	}

}