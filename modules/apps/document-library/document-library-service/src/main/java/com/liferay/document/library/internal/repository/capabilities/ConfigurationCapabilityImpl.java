/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.repository.capabilities;

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
 * @author Iván Zaera
 */
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

			UnicodeProperties typeSettingsUnicodeProperties =
				repository.getTypeSettingsProperties();

			return typeSettingsUnicodeProperties.getProperty(
				_getUniqueKey(owner, key));
		}
		catch (PortalException portalException) {
			throw new SystemException(
				"Unable to read repository configuration property",
				portalException);
		}
	}

	@Override
	public void setProperty(
		Class<? extends Capability> owner, String key, String value) {

		try {
			Repository repository = _repositoryServiceAdapter.getRepository(
				_documentRepository.getRepositoryId());

			UnicodeProperties typeSettingsUnicodeProperties =
				repository.getTypeSettingsProperties();

			typeSettingsUnicodeProperties.setProperty(
				_getUniqueKey(owner, key), value);

			repository.setTypeSettingsProperties(typeSettingsUnicodeProperties);

			_repositoryServiceAdapter.updateRepository(repository);
		}
		catch (PortalException portalException) {
			throw new SystemException(
				"Unable to set repository configuration property",
				portalException);
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