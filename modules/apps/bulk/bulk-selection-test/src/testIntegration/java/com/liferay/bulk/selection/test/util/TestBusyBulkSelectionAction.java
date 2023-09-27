/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bulk.selection.test.util;

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.bulk.selection.BulkSelectionRunner;
import com.liferay.portal.kernel.model.User;

import java.io.Serializable;

import java.util.Map;

import org.junit.Assert;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = BulkSelectionAction.class)
public class TestBusyBulkSelectionAction
	implements BulkSelectionAction<Integer> {

	@Override
	public void execute(
		User user, BulkSelection<Integer> bulkSelection,
		Map<String, Serializable> inputMap) {

		Assert.assertTrue(_bulkSelectionRunner.isBusy(user));
	}

	@Reference
	private BulkSelectionRunner _bulkSelectionRunner;

}