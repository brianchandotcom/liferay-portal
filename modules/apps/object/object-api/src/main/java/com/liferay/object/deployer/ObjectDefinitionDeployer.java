/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.deployer;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.db.partition.util.DBPartitionUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.ServiceRegistration;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public interface ObjectDefinitionDeployer {

	public default Map<String, List<ServiceRegistration<?>>> deploy(
		long companyId, List<ObjectDefinition> objectDefinitions) {

		Map<String, List<ServiceRegistration<?>>> serviceRegistrationsMap =
			new ConcurrentHashMap<>();

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			serviceRegistrationsMap.put(
				DBPartitionUtil.getPartitionKey(
					objectDefinition.getObjectDefinitionId()),
				deploy(objectDefinition));
		}

		return serviceRegistrationsMap;
	}

	public List<ServiceRegistration<?>> deploy(
		ObjectDefinition objectDefinition);

	public default void undeploy(ObjectDefinition objectDefinition) {
	}

}