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

package com.liferay.batch.planner.service.test.util;

import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerPlan;
import com.liferay.batch.planner.service.BatchPlannerMappingServiceUtil;
import com.liferay.batch.planner.service.persistence.BatchPlannerMappingPersistence;
import com.liferay.batch.planner.service.persistence.BatchPlannerMappingUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Beslic
 */
public class BatchPlannerMappingTestUtil {

	public static List<BatchPlannerMapping> addBatchPlannerMapping(
			String... mappings)
		throws PortalException {

		if ((mappings == null) || (mappings.length == 0) ||
			((mappings.length % 2) != 0)) {

			throw new IllegalArgumentException();
		}

		BatchPlannerPlan batchPlannerPlan =
			BatchPlannerPlanTestUtil.addBatchPlannerPlan(true, 300);

		List<BatchPlannerMapping> batchPlannerMappings = new ArrayList<>();

		for (int i = 0; i < mappings.length; i = i + 2) {
			BatchPlannerMapping batchPlannerMapping =
				_randomBatchPlannerMapping(mappings[i], mappings[i + 1]);

			batchPlannerMappings.add(
				BatchPlannerMappingServiceUtil.addBatchPlannerMapping(
					batchPlannerPlan.getBatchPlannerPlanId(),
					batchPlannerMapping.getExternalFieldName(),
					batchPlannerMapping.getExternalFieldType(),
					batchPlannerMapping.getInternalFieldName(),
					batchPlannerMapping.getInternalFieldType(),
					batchPlannerMapping.getScript()));
		}

		return batchPlannerMappings;
	}

	private static BatchPlannerMapping _randomBatchPlannerMapping(
		String externalFieldName, String internalFieldName) {

		BatchPlannerMappingPersistence batchPlannerMappingPersistence =
			BatchPlannerMappingUtil.getPersistence();

		BatchPlannerMapping batchPlannerMapping =
			batchPlannerMappingPersistence.create(RandomTestUtil.nextLong());

		batchPlannerMapping.setExternalFieldName(externalFieldName);

		String fieldType = _randomType();

		batchPlannerMapping.setExternalFieldType(fieldType);

		batchPlannerMapping.setInternalFieldName(internalFieldName);
		batchPlannerMapping.setInternalFieldType(fieldType);

		return batchPlannerMapping;
	}

	private static String _randomType() {
		return _RANDOM_TYPES
			[RandomTestUtil.randomInt(0, _RANDOM_TYPES.length - 1)];
	}

	private static final String[] _RANDOM_TYPES = {
		"int", "long", "string", "boolean"
	};

}