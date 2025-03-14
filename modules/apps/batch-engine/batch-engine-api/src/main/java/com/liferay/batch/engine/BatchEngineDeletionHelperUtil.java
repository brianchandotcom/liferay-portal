/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.Set;

/**
 * @author Vendel Toreki
 */
public class BatchEngineDeletionHelperUtil {

	public static void addDeletionEvent(
		PortletDataContext portletDataContext, SystemEvent systemEvent) {

		BatchEngineDeletionHelper batchEngineDeletionHelper =
			_batchEngineDeletionHelper.get();

		batchEngineDeletionHelper.addDeletionEvent(
			portletDataContext, systemEvent);
	}

	public static void exportDeletions(PortletDataContext portletDataContext) {
		BatchEngineDeletionHelper batchEngineDeletionHelper =
			_batchEngineDeletionHelper.get();

		batchEngineDeletionHelper.exportDeletions(portletDataContext);
	}

	public static Set<String> getBatchDeleteSupportedClassNames() {
		BatchEngineDeletionHelper batchEngineDeletionHelper =
			_batchEngineDeletionHelper.get();

		return batchEngineDeletionHelper.getBatchDeleteSupportedClassNames();
	}

	public static void importDeletions(
			PortletDataContext portletDataContext, String portletId)
		throws Exception {

		BatchEngineDeletionHelper batchEngineDeletionHelper =
			_batchEngineDeletionHelper.get();

		batchEngineDeletionHelper.importDeletions(
			portletDataContext, portletId);
	}

	public static boolean isBatchPortlet(String portletId) {
		BatchEngineDeletionHelper batchEngineDeletionHelper =
			_batchEngineDeletionHelper.get();

		return batchEngineDeletionHelper.isBatchPortlet(portletId);
	}

	private static final Snapshot<BatchEngineDeletionHelper>
		_batchEngineDeletionHelper = new Snapshot<>(
			BatchEngineDeletionHelperUtil.class,
			BatchEngineDeletionHelper.class);

}