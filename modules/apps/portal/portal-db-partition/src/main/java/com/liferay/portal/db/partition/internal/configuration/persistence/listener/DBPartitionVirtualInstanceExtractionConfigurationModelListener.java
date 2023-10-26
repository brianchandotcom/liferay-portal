/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.db.partition.internal.configuration.DBPartitionVirtualInstanceExtractionConfiguration;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.portal.db.partition.internal.configuration.DBPartitionVirtualInstanceExtractionConfiguration",
	service = ConfigurationModelListener.class
)
public class DBPartitionVirtualInstanceExtractionConfigurationModelListener
	extends BaseConfigurationModelListener {

	public DBPartitionVirtualInstanceExtractionConfigurationModelListener() {
		super(_PID);
	}

	@Override
	public void doOnAfterSave(Dictionary<String, Object> properties) {
	}

	@Override
	public Class<?> getConfigurationClass() {
		return DBPartitionVirtualInstanceExtractionConfiguration.class;
	}

	private static final String _PID =
		"com.liferay.portal.db.partition.internal.configuration." +
			"DBPartitionVirtualInstanceExtractionConfiguration";

}