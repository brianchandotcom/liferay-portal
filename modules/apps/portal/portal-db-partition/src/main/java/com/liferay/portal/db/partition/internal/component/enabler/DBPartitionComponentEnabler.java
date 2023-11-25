/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.component.enabler;

import com.liferay.portal.db.partition.internal.configuration.DBPartitionVirtualInstanceExtraction;
import com.liferay.portal.db.partition.internal.configuration.DBPartitionVirtualInstanceInsertion;
import com.liferay.portal.kernel.db.partition.DBPartition;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(service = {})
public class DBPartitionComponentEnabler {

	@Activate
	protected void activate(ComponentContext componentContext) {
		if (DBPartition.isPartitionEnabled()) {
			componentContext.enableComponent(
				DBPartitionVirtualInstanceExtraction.class.getName());
			componentContext.enableComponent(
				DBPartitionVirtualInstanceInsertion.class.getName());
		}
	}

}