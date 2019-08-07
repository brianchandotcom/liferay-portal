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

package com.liferay.segments.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.IOException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model implementation for the SegmentsExperiment service. Represents a row in the &quot;SegmentsExperiment&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.segments.model.SegmentsExperiment</code> interface.
 * </p>
 *
 * @author Eduardo García
 */
@ProviderType
public class SegmentsExperimentImpl extends SegmentsExperimentBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a segments experiment model instance should use the {@link com.liferay.segments.model.SegmentsExperiment} interface instead.
	 */
	public SegmentsExperimentImpl() {
	}

	@Override
	public String getGoal() {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		return typeSettingsProperties.getProperty("goal");
	}

	@Override
	public String getGoalTarget() {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		return typeSettingsProperties.getProperty("goalTarget");
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsProperties == null) {
			_typeSettingsProperties = new UnicodeProperties(true);

			try {
				_typeSettingsProperties.load(super.getTypeSettings());
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return _typeSettingsProperties;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsExperimentImpl.class);

	private UnicodeProperties _typeSettingsProperties;

}