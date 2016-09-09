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

import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.engine.PersistenceContext;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.event.EventSource;
import org.hibernate.event.FlushEvent;
import org.hibernate.event.def.DefaultFlushEventListener;
import org.hibernate.stat.Statistics;
import org.hibernate.stat.StatisticsImplementor;

/**
 * @author Matthew Tambara
 */
public class NestableFlushEventListener extends DefaultFlushEventListener {

	public static final NestableFlushEventListener INSTANCE =
		new NestableFlushEventListener();

	@Override
	public void onFlush(FlushEvent event) throws HibernateException {
		EventSource eventSource = event.getSession();

		PersistenceContext persistenceContext =
			eventSource.getPersistenceContext();

		Map<?, ?> entityEntries = persistenceContext.getEntityEntries();

		if (entityEntries.isEmpty()) {
			Map<?, ?> collectionEntries =
				persistenceContext.getCollectionEntries();

			if (collectionEntries.isEmpty()) {
				return;
			}
		}

		boolean flushing = persistenceContext.isFlushing();

		try {
			flushEverythingToExecutions(event);

			persistenceContext.setFlushing(true);

			performExecutions(eventSource);

			postFlush(eventSource);
		}
		finally {
			persistenceContext.setFlushing(flushing);
		}

		SessionFactoryImplementor sessionFactoryImplementor =
			eventSource.getFactory();

		Statistics statistics = sessionFactoryImplementor.getStatistics();

		if (statistics.isStatisticsEnabled()) {
			StatisticsImplementor statisticsImplementor =
				sessionFactoryImplementor.getStatisticsImplementor();

			statisticsImplementor.flush();
		}
	}

}