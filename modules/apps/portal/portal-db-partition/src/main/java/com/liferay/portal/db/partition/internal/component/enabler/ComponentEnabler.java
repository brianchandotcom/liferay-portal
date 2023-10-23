/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.component.enabler;

import com.liferay.portal.db.partition.internal.configuration.persistence.listener.DBPartitionCompanyActivationConfigurationModelListener;
import com.liferay.portal.db.partition.internal.configuration.persistence.listener.DBPartitionCompanyDeactivationConfigurationModelListener;
import com.liferay.portal.kernel.db.partition.DBPartition;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(service = {})
public class ComponentEnabler {

	@Activate
	protected void activate(ComponentContext componentContext) {
		if (DBPartition.isPartitionEnabled()) {
			componentContext.enableComponent(
				DBPartitionCompanyActivationConfigurationModelListener.class.
					getName());
			componentContext.enableComponent(
				DBPartitionCompanyDeactivationConfigurationModelListener.class.
					getName());
		}
	}

}