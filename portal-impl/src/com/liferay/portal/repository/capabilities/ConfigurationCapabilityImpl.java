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

package com.liferay.portal.repository.capabilities;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.ConfigurationCapability;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.repository.capabilities.util.RepositoryServiceAdapter;

/**
 * @author     Iván Zaera
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.document.library.internal.capabilities.ConfigurationCapabilityImpl}
 */
@Deprecated
public class ConfigurationCapabilityImpl implements ConfigurationCapability {

	public ConfigurationCapabilityImpl(
		DocumentRepository documentRepository,
		RepositoryServiceAdapter repositoryServiceAdapter) {

		_documentRepository = documentRepository;
		_repositoryServiceAdapter = repositoryServiceAdapter;
	}

	@Override
	public String getProperty(Class<? extends Capability> owner, String key) {
		try {
			Repository repository = _repositoryServiceAdapter.getRepository(
				_documentRepository.getRepositoryId());

			UnicodeProperties typeSettingsProperties =
				repository.getTypeSettingsProperties();

			return typeSettingsProperties.getProperty(
				_getUniqueKey(owner, key));
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to read repository configuration property", pe);
		}
	}

	@Override
	public void setProperty(
		Class<? extends Capability> owner, String key, String value) {

		try {
			Repository repository = _repositoryServiceAdapter.getRepository(
				_documentRepository.getRepositoryId());

			UnicodeProperties typeSettingsProperties =
				repository.getTypeSettingsProperties();

			typeSettingsProperties.setProperty(
				_getUniqueKey(owner, key), value);

			repository.setTypeSettingsProperties(typeSettingsProperties);

			_repositoryServiceAdapter.updateRepository(repository);
		}
		catch (PortalException pe) {
			throw new SystemException(
				"Unable to set repository configuration property", pe);
		}
	}

	private String _getUniqueKey(
		Class<? extends Capability> owner, String key) {

		Class<?> clazz = owner.getClass();

		return clazz.getName() + StringPool.POUND + key;
	}

	private final DocumentRepository _documentRepository;
	private final RepositoryServiceAdapter _repositoryServiceAdapter;

}