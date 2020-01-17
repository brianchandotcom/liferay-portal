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

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;

import javax.mvc.engine.ViewEngine;

import javax.portlet.PortletContext;

import javax.ws.rs.core.Configuration;

/**
 * @author Neil Griffin
 */
@ApplicationScoped
public class ViewEnginesProducer {

	@ApplicationScoped
	@Produces
	@ViewEngines
	public List<ViewEngine> getViewEngines(
		BeanManager beanManager, Configuration configuration,
		PortletContext portletContext) {

		List<ViewEngine> viewEngines = BeanUtil.getBeanInstances(
			beanManager, ViewEngine.class);

		viewEngines.add(new ViewEngineJspImpl(configuration, portletContext));

		Collections.sort(viewEngines, new ViewEnginePriorityComparator());

		return viewEngines;
	}

	private static class ViewEnginePriorityComparator
		extends DescendingPriorityComparator<ViewEngine> {

		private ViewEnginePriorityComparator() {

			// The Javadoc for javax.mvc.engine.ViewEngine states "View engines
			// can be decorated with javax.annotation.Priority to indicate their
			// priority; otherwise the priority is assumed to be
			// ViewEngine.PRIORITY_APPLICATION."

			super(ViewEngine.PRIORITY_APPLICATION);
		}

	}

}