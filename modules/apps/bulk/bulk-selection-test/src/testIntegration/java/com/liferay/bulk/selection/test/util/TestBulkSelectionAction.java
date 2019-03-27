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

package com.liferay.bulk.selection.test.util;

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(service = {BulkSelectionAction.class, TestBulkSelectionAction.class})
public class TestBulkSelectionAction implements BulkSelectionAction<Integer> {

	public static Integer getLastResult() {
		return _lastResult;
	}

	@Override
	public void execute(
			User user, BulkSelection<Integer> bulkSelection,
			Map<String, Serializable> inputMap)
		throws PortalException {

		Integer integer = MapUtil.getInteger(inputMap, "integer");

		AtomicInteger result = new AtomicInteger();

		bulkSelection.forEach(n -> result.addAndGet(n * integer));

		_lastResult = result.get();
	}

	private static Integer _lastResult;

}