/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.virtual.instances.internal.component.enabler;

import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.virtual.instances.internal.operation.CopyVirtualInstanceOperation;
import com.liferay.portal.virtual.instances.internal.operation.ExtractVirtualInstanceOperation;
import com.liferay.portal.virtual.instances.internal.operation.InsertVirtualInstanceOperation;

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
				CopyVirtualInstanceOperation.class.getName());
			componentContext.enableComponent(
				ExtractVirtualInstanceOperation.class.getName());
			componentContext.enableComponent(
				InsertVirtualInstanceOperation.class.getName());
		}
	}

}