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

package com.liferay.portal.tools.service.builder.test.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryContentVersion;
import com.liferay.portal.tools.service.builder.test.service.base.VersionedEntryLocalServiceBaseImpl;

import java.util.List;

/**
 * The implementation of the versioned entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portal.tools.service.builder.test.service.VersionedEntryLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryLocalServiceBaseImpl
 * @see com.liferay.portal.tools.service.builder.test.service.VersionedEntryLocalServiceUtil
 */
public class VersionedEntryLocalServiceImpl
	extends VersionedEntryLocalServiceBaseImpl {

	@Override
	public VersionedEntryContentVersion fetchVersionedEntryContentVersion(
		long versionedEntryId, String languageId, int version) {

		return versionedEntryContentVersionPersistence.
			fetchByVersionedEntryId_LanguageId_Version(
				versionedEntryId, languageId, version);
	}

	@Override
	public List<VersionedEntryContentVersion> getVersionedEntryContentVersions(
		long versionedEntryId) {

		return versionedEntryContentVersionPersistence.findByVersionedEntryId(
			versionedEntryId);
	}

	@Override
	public List<VersionedEntryContentVersion> getVersionedEntryContentVersions(
			long versionedEntryId, String languageId)
		throws PortalException {

		return versionedEntryContentVersionPersistence.
			findByVersionedEntryId_LanguageId(versionedEntryId, languageId);
	}

}