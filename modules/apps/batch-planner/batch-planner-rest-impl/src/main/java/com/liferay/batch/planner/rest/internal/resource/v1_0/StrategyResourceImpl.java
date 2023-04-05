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

package com.liferay.batch.planner.rest.internal.resource.v1_0;

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.BatchEngineTaskItemDelegateRegistry;
import com.liferay.batch.planner.rest.dto.v1_0.Strategy;
import com.liferay.batch.planner.rest.resource.v1_0.StrategyResource;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Matija Petanjek
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/strategy.properties",
	scope = ServiceScope.PROTOTYPE, service = StrategyResource.class
)
public class StrategyResourceImpl extends BaseStrategyResourceImpl {

	@Override
	public Page<Strategy> getPlanInternalClassNameStrategiesPage(
			String internalClassName)
		throws Exception {

		BatchEngineTaskItemDelegate<?> batchEngineTaskItemDelegate =
			_batchEngineTaskItemDelegateRegistry.getBatchEngineTaskItemDelegate(
				internalClassName, "DEFAULT");

		List<Strategy> strategies = new ArrayList<>();

		for (String createStrategy :
				batchEngineTaskItemDelegate.getAvailableCreateStrategies()) {

			strategies.add(
				new Strategy() {
					{
						name = createStrategy;
						type = "create";
					}
				});
		}

		for (String updateStrategy :
				batchEngineTaskItemDelegate.getAvailableUpdateStrategies()) {

			strategies.add(
				new Strategy() {
					{
						name = updateStrategy;
						type = "update";
					}
				});
		}

		return Page.of(strategies);
	}

	@Reference
	private BatchEngineTaskItemDelegateRegistry
		_batchEngineTaskItemDelegateRegistry;

}