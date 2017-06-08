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

package com.liferay.arquillian.extension.persistence.internal.descriptor;

import com.liferay.arquillian.extension.internal.descriptor.SpringDescriptor;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.util.List;

/**
 * @author Cristina González
 */
public class PersistenceTestSpringDescriptorImpl implements SpringDescriptor {

	@Override
	public List<String> getConfigLocations() {
		List<String> configLocations = ListUtil.fromArray(
			PropsUtil.getArray(PropsKeys.SPRING_CONFIGS));

		configLocations.remove("META-INF/model-listener-spring.xml");

		return configLocations;
	}

}