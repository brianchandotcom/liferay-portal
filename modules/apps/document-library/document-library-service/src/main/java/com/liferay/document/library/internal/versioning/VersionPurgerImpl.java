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

package com.liferay.document.library.internal.versioning;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.versioning.VersionPurger;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	service = VersionPurger.class
)
public class VersionPurgerImpl implements VersionPurger {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);
	}

	@Override
	public Collection<FileVersion> getToPurgeFileVersions(FileEntry fileEntry) {
		int maximumNumberOfVersions =
			_dlConfiguration.maximumNumberOfVersions();

		if (maximumNumberOfVersions <= 0) {
			return Collections.emptyList();
		}

		int status = WorkflowConstants.STATUS_ANY;

		int numberOfVersions = fileEntry.getFileVersionsCount(status);

		if (numberOfVersions > maximumNumberOfVersions) {
			List<FileVersion> fileVersions = fileEntry.getFileVersions(status);

			int numberOfVersionsToPurge =
				numberOfVersions - maximumNumberOfVersions;

			return fileVersions.subList(
				fileVersions.size() - numberOfVersionsToPurge,
				fileVersions.size());
		}

		return Collections.emptyList();
	}

	private DLConfiguration _dlConfiguration;

}