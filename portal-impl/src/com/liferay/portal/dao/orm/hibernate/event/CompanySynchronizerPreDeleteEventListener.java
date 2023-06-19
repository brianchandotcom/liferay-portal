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

package com.liferay.portal.dao.orm.hibernate.event;

import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;

/**
 * @author Michael Bowerman
 */
public class CompanySynchronizerPreDeleteEventListener
	implements PreDeleteEventListener {

	public static final CompanySynchronizerPreDeleteEventListener INSTANCE =
		new CompanySynchronizerPreDeleteEventListener();

	public boolean onPreDelete(PreDeleteEvent event) {
		Object entity = event.getEntity();

		if (entity instanceof ShardedModel) {
			ShardedModel shardedModel = (ShardedModel)entity;

			CompanyThreadLocal.pushCompanyId(shardedModel.getCompanyId());
		}

		return false;
	}

}