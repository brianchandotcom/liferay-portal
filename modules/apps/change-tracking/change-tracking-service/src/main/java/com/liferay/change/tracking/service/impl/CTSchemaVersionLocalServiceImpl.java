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

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.model.CTSchemaVersion;
import com.liferay.change.tracking.service.base.CTSchemaVersionLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.version.Version;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Preston Crary
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.model.CTSchemaVersion",
	service = AopService.class
)
public class CTSchemaVersionLocalServiceImpl
	extends CTSchemaVersionLocalServiceBaseImpl {

	@Override
	public CTSchemaVersion addLatestCTSchemaVersion(long companyId) {
		CTSchemaVersion ctSchemaVersion = ctSchemaVersionPersistence.create(
			counterLocalService.increment(CTSchemaVersion.class.getName()));

		ctSchemaVersion.setCompanyId(companyId);

		Map<String, Serializable> schemaContext = new HashMap<>();

		for (Release release :
				releaseLocalService.getReleases(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			schemaContext.put(
				release.getServletContextName(), release.getSchemaVersion());
		}

		ctSchemaVersion.setSchemaContext(schemaContext);

		return ctSchemaVersionPersistence.update(ctSchemaVersion);
	}

	@Override
	public CTSchemaVersion getLatestCTSchemaVersion(long companyId) {
		CTSchemaVersion ctSchemaVersion =
			ctSchemaVersionPersistence.fetchByCompanyId_First(companyId, null);

		if ((ctSchemaVersion == null) ||
			!isLatestCTSchemaVersion(ctSchemaVersion, true)) {

			ctSchemaVersion =
				ctSchemaVersionLocalService.addLatestCTSchemaVersion(companyId);
		}

		return ctSchemaVersion;
	}

	@Override
	public boolean isLatestCTSchemaVersion(
		CTSchemaVersion ctSchemaVersion, boolean strict) {

		Map<String, Serializable> schemaContext =
			ctSchemaVersion.getSchemaContext();

		List<Release> releases = releaseLocalService.getReleases(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (releases.size() != schemaContext.size()) {
			return false;
		}

		for (Release release : releases) {
			String ctReleaseSchemaVersion = (String)schemaContext.get(
				release.getServletContextName());

			if (Objects.equals(
					ctReleaseSchemaVersion, release.getSchemaVersion())) {

				continue;
			}

			if (strict) {
				return false;
			}

			Version version1 = Version.parseVersion(ctReleaseSchemaVersion);

			Version version2 = Version.parseVersion(release.getSchemaVersion());

			if ((version1.getMajor() != version2.getMajor()) ||
				(version1.getMinor() != version2.getMinor())) {

				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isLatestCTSchemaVersion(long ctSchemaVersionId) {
		CTSchemaVersion ctSchemaVersion =
			ctSchemaVersionPersistence.fetchByPrimaryKey(ctSchemaVersionId);

		if (ctSchemaVersion == null) {
			return false;
		}

		return isLatestCTSchemaVersion(ctSchemaVersion, false);
	}

}