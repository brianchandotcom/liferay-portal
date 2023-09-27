/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = BulkSelectionAction.class)
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