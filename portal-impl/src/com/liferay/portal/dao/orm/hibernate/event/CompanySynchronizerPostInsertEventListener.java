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

import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;

/**
 * @author Michael Bowerman
 */
public class CompanySynchronizerPostInsertEventListener
	implements PostInsertEventListener {

	public static final CompanySynchronizerPostInsertEventListener INSTANCE =
		new CompanySynchronizerPostInsertEventListener();

	public void onPostInsert(PostInsertEvent event) {
		Object entity = event.getEntity();

		if (entity instanceof ShardedModel) {
			CompanyThreadLocal.popCompanyId();
		}
	}

	/** @deprecated */
	@Deprecated
	@Override
	public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
		throw new UnsupportedOperationException();
	}

}