/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.helper;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.trash.TrashHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = DLTrashHelper.class)
public class DLTrashHelper {

	public boolean isTrashEnabled(long groupId, long repositoryId)
		throws PortalException {

		if (!_trashHelper.isTrashEnabled(groupId)) {
			return false;
		}

		if (repositoryId == groupId) {
			return true;
		}

		Repository repository = RepositoryProviderUtil.getRepository(
			repositoryId);

		return repository.isCapabilityProvided(TrashCapability.class);
	}

	@Reference
	private TrashHelper _trashHelper;

}