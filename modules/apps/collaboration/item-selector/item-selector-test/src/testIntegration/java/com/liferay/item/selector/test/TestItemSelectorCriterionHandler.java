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

package com.liferay.item.selector.test;

import com.liferay.item.selector.BaseItemSelectorCriterionHandler;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Roberto Díaz
 */
@Component(service = TestItemSelectorCriterionHandler.class)
public class TestItemSelectorCriterionHandler
	extends BaseItemSelectorCriterionHandler<TestItemSelectorCriterion> {

	@Activate
	@Override
	public void activate(BundleContext bundleContext) {
		super.activate(bundleContext);
	}

	@Override
	public Class getItemSelectorCriterionClass() {
		return TestItemSelectorCriterion.class;
	}

	@Deactivate
	@Override
	protected void deactivate() {
		super.deactivate();
	}

}