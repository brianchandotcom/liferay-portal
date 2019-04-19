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

package com.liferay.batch.engine.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Ivica Cardic
 */
@ProviderType
public class BatchJobExecutionImpl extends BatchJobExecutionBaseImpl {

	@Override
	public UnicodeProperties getJobSettingsProperties() {
		if (_jobSettingsProperties == null) {
			_jobSettingsProperties = new UnicodeProperties(true);

			_jobSettingsProperties.fastLoad(getJobSettings());
		}

		return _jobSettingsProperties;
	}

	@Override
	public void setJobSettings(String jobSettings) {
		super.setJobSettings(jobSettings);

		_jobSettingsProperties = null;
	}

	@Override
	public void setJobSettingsProperties(
		UnicodeProperties jobSettingsProperties) {

		_jobSettingsProperties = jobSettingsProperties;

		if (_jobSettingsProperties == null) {
			_jobSettingsProperties = new UnicodeProperties();
		}

		super.setJobSettings(_jobSettingsProperties.toString());
	}

	private UnicodeProperties _jobSettingsProperties;

}