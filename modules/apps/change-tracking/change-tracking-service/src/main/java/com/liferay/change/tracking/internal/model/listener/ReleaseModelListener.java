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

package com.liferay.change.tracking.internal.model.listener;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.model.CTPreferencesTable;
import com.liferay.change.tracking.model.CTSchemaVersion;
import com.liferay.change.tracking.model.CTSchemaVersionTable;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.service.CTSchemaVersionLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ReleaseTable;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.model.impl.ReleaseImpl;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = ModelListener.class)
public class ReleaseModelListener extends BaseModelListener<Release> {

	@Override
	public void onBeforeCreate(Release release) {
		if (!Objects.equals(release.getSchemaVersion(), "0.0.0")) {
			_resetCTPreferences();
		}
	}

	@Override
	public void onBeforeRemove(Release release) {
		_resetCTPreferences();
	}

	@Override
	public void onBeforeUpdate(Release release) {
		ReleaseImpl releaseImpl = (ReleaseImpl)release;

		String originalSchemaVersion = releaseImpl.getColumnOriginalValue(
			ReleaseTable.INSTANCE.schemaVersion.getName());

		if (!Objects.equals(
				originalSchemaVersion, releaseImpl.getSchemaVersion())) {

			Version version1 = Version.parseVersion(originalSchemaVersion);

			Version version2 = Version.parseVersion(release.getSchemaVersion());

			if ((version1.getMajor() != version2.getMajor()) ||
				(version1.getMinor() != version2.getMinor())) {

				_resetCTPreferences();
			}
		}
	}

	@Activate
	protected void activate() {
		List<CTSchemaVersion> ctSchemaVersions =
			_ctSchemaVersionLocalService.dslQuery(
				DSLQueryFactoryUtil.select(
					CTSchemaVersionTable.INSTANCE
				).from(
					CTSchemaVersionTable.INSTANCE
				).orderBy(
					CTSchemaVersionTable.INSTANCE.schemaVersionId.descending()
				).limit(
					0, 1
				));

		if (!ctSchemaVersions.isEmpty() &&
			!_ctSchemaVersionLocalService.isLatestSchemaVersion(
				ctSchemaVersions.get(0), false)) {

			_resetCTPreferences();
		}
	}

	private void _resetCTPreferences() {
		for (CTPreferences ctPreferences :
				_ctPreferencesLocalService.<List<CTPreferences>>dslQuery(
					DSLQueryFactoryUtil.select(
						CTPreferencesTable.INSTANCE
					).from(
						CTPreferencesTable.INSTANCE
					).where(
						CTPreferencesTable.INSTANCE.previousCtCollectionId.neq(
							CTConstants.CT_COLLECTION_ID_PRODUCTION
						).or(
							CTPreferencesTable.INSTANCE.ctCollectionId.neq(
								CTConstants.CT_COLLECTION_ID_PRODUCTION)
						)
					))) {

			ctPreferences.setCtCollectionId(
				CTConstants.CT_COLLECTION_ID_PRODUCTION);
			ctPreferences.setPreviousCtCollectionId(
				CTConstants.CT_COLLECTION_ID_PRODUCTION);

			_ctPreferencesLocalService.updateCTPreferences(ctPreferences);
		}
	}

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private CTSchemaVersionLocalService _ctSchemaVersionLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTLETS_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

}