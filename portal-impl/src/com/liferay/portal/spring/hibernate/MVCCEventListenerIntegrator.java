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

package com.liferay.portal.spring.hibernate;

import com.liferay.portal.dao.orm.hibernate.event.MVCCSynchronizerPostUpdateEventListener;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

/**
 * @author Tina Tian
 */
public class MVCCEventListenerIntegrator implements Integrator {

	public static final MVCCEventListenerIntegrator INSTANCE =
		new MVCCEventListenerIntegrator();

	@Override
	public void disintegrate(
		SessionFactoryImplementor sessionFactoryImplementor,
		SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {
	}

	@Override
	public void integrate(
		Metadata metadata, SessionFactoryImplementor sessionFactoryImplementor,
		SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {

		EventListenerRegistry eventListenerRegistry =
			sessionFactoryServiceRegistry.getService(
				EventListenerRegistry.class);

		eventListenerRegistry.setListeners(
			EventType.POST_UPDATE,
			MVCCSynchronizerPostUpdateEventListener.INSTANCE);
	}

}