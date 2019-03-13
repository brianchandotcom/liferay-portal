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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.service.persistence.ServiceComponentPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ServiceComponentFinderBaseImpl
	extends BasePersistenceImpl<ServiceComponent> {

	public ServiceComponentFinderBaseImpl() {
		setModelClass(ServiceComponent.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("data", "data_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getServiceComponentPersistence().getBadColumnNames();
	}

	/**
	 * Returns the service component persistence.
	 *
	 * @return the service component persistence
	 */
	public ServiceComponentPersistence getServiceComponentPersistence() {
		return serviceComponentPersistence;
	}

	/**
	 * Sets the service component persistence.
	 *
	 * @param serviceComponentPersistence the service component persistence
	 */
	public void setServiceComponentPersistence(
		ServiceComponentPersistence serviceComponentPersistence) {

		this.serviceComponentPersistence = serviceComponentPersistence;
	}

	@BeanReference(type = ServiceComponentPersistence.class)
	protected ServiceComponentPersistence serviceComponentPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceComponentFinderBaseImpl.class);

}