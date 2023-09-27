/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.document.library.repository.cmis.configuration.CMISRepositoryConfiguration;
import com.liferay.document.library.repository.cmis.internal.constants.CMISRepositoryConstants;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.repository.RepositoryFactory;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.document.library.repository.cmis.configuration.CMISRepositoryConfiguration",
	property = "repository.target.class.name=" + CMISRepositoryConstants.CMIS_ATOMPUB_REPOSITORY_CLASS_NAME,
	service = RepositoryFactory.class
)
public class CMISAtomPubRepositoryFactory
	extends BaseCMISRepositoryFactory<CMISAtomPubRepository> {

	@Activate
	protected void activate(Map<String, Object> properties) {
		super.setCMISRepositoryConfiguration(
			ConfigurableUtil.createConfigurable(
				CMISRepositoryConfiguration.class, properties));
	}

	@Override
	protected CMISAtomPubRepository createBaseRepository() {
		return new CMISAtomPubRepository();
	}

}