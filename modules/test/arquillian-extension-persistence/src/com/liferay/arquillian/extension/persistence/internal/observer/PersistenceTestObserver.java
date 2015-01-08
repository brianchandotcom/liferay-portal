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

package com.liferay.arquillian.extension.persistence.internal.observer;

import com.liferay.arquillian.extension.internal.event.LiferayContextCreatedEvent;
import com.liferay.arquillian.extension.persistence.internal.annotation.PersistenceTest;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.tools.DBUpgrader;

import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.TestClass;

/**
 * @author Cristina González
 */
public class PersistenceTestObserver {

	public void afterLiferayContexCreated(
			@Observes LiferayContextCreatedEvent liferayContextCreatedEvent)
		throws TemplateException {

		TestClass testClass = liferayContextCreatedEvent.getTestClass();

		if (testClass.getAnnotation(PersistenceTest.class) != null) {
			try {
				DBUpgrader.upgrade();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}