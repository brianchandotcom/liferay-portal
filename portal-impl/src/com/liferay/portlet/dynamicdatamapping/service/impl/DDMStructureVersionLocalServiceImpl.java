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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureVersionLocalServiceBaseImpl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * The implementation of the d d m structure version local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.dynamicdatamapping.service.DDMStructureVersionLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Pablo Carvalho
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureVersionLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.DDMStructureVersionLocalServiceUtil
 */
@ProviderType
public class DDMStructureVersionLocalServiceImpl
	extends DDMStructureVersionLocalServiceBaseImpl {

	@Override
	public DDMStructureVersion getDDMStructureVersion(
		long ddmStructureVersionId) {

		return ddmStructureVersionPersistence.fetchByPrimaryKey(
			ddmStructureVersionId);
	}

	@Override
	public List<DDMStructureVersion> getDDMStructureVersions(
		long ddmStructureId, int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator) {

		return ddmStructureVersionPersistence.findByDDMStructureId(
			ddmStructureId, start, end, orderByComparator);
	}

	@Override
	public int getDDMStructureVersionsCount(long ddmStructureId) {
		return ddmStructureVersionPersistence.countByDDMStructureId(
			ddmStructureId);
	}

	@Override
	public DDMStructureVersion getLatestVersion(long ddmStructureId) {
		List<DDMStructureVersion> ddmStructureVersions =
			ddmStructureVersionPersistence.findByDDMStructureId(ddmStructureId);

		if (ddmStructureVersions.size() == 0) {
			return null;
		}

		Iterator<DDMStructureVersion> iterator =
			ddmStructureVersions.iterator();

		DDMStructureVersion latestVersion = iterator.next();

		while (iterator.hasNext()) {
			DDMStructureVersion ddmStructureVersion = iterator.next();

			if (latestVersion.compareTo(ddmStructureVersion) < 0) {
				latestVersion = ddmStructureVersion;
			}
		}

		return latestVersion;
	}

	@Override
	public DDMStructureVersion updateDDMStructureVersion(
			DDMStructure ddmStructure, boolean majorVersion,
			ServiceContext serviceContext)
		throws PortalException {

		Date now = new Date();

		long ddmStructureVersionId = counterLocalService.increment();

		long ddmStructureId = ddmStructure.getPrimaryKey();

		DDMStructureVersion ddmStructureVersion =
			ddmStructureVersionPersistence.create(ddmStructureVersionId);

		ddmStructureVersion.setGroupId(ddmStructure.getGroupId());
		ddmStructureVersion.setCompanyId(ddmStructure.getCompanyId());
		ddmStructureVersion.setUserId(ddmStructure.getUserId());
		ddmStructureVersion.setUserName(ddmStructure.getUserName());
		ddmStructureVersion.setCreateDate(serviceContext.getCreateDate(now));
		ddmStructureVersion.setDdmStructureId(ddmStructureId);
		ddmStructureVersion.setName(ddmStructure.getName());
		ddmStructureVersion.setDefinition(ddmStructure.getDefinition());
		ddmStructureVersion.setDescription(ddmStructure.getDescription());
		ddmStructureVersion.setStorageType(ddmStructure.getStorageType());
		ddmStructureVersion.setType(ddmStructure.getType());

		String version = _VERSION_DEFAULT;

		DDMStructureVersion latestVersion = getLatestVersion(ddmStructureId);

		if (latestVersion != null) {
			version = nextVersion(latestVersion.getVersion(), majorVersion);
		}

		ddmStructureVersion.setVersion(version);

		ddmStructureVersionPersistence.update(ddmStructureVersion);

		ddmStructure.setVersion(version);

		return ddmStructureVersion;
	}

	protected String nextVersion(String version, boolean majorVersion) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		if (majorVersion) {
			versionParts[0]++;
			versionParts[1] = 0;
		}
		else {
			versionParts[1]++;
		}

		return versionParts[0] + StringPool.PERIOD + versionParts[1];
	}

	private static final String _VERSION_DEFAULT = "1.0";

}