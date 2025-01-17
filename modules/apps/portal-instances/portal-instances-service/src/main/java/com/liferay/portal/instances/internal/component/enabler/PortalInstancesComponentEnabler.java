/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.internal.component.enabler;

import com.liferay.portal.instances.internal.operation.CopyPortalInstanceOperation;
import com.liferay.portal.instances.internal.operation.ExtractPortalInstanceOperation;
import com.liferay.portal.instances.internal.operation.InsertPortalInstanceOperation;
import com.liferay.portal.kernel.db.partition.DBPartition;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(service = {})
public class PortalInstancesComponentEnabler {

	@Activate
	protected void activate(ComponentContext componentContext) {
		if (DBPartition.isPartitionEnabled()) {
			componentContext.enableComponent(
				CopyPortalInstanceOperation.class.getName());
			componentContext.enableComponent(
				ExtractPortalInstanceOperation.class.getName());
			componentContext.enableComponent(
				InsertPortalInstanceOperation.class.getName());
		}
	}

}