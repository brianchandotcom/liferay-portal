/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.sales.room.test.util;

import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.io.File;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Stefano Motta
 */
public class DigitalSalesRoomTestUtil {

	public static ObjectDefinition getObjectDefinition(Class<?> clazz)
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DSR_ROOM", TestPropsValues.getCompanyId());

		if (objectDefinition != null) {
			return objectDefinition;
		}

		BatchEngineUnitProcessor batchEngineUnitProcessor =
			_batchEngineUnitProcessorSnapshot.get();
		BatchEngineUnitReader batchEngineUnitReader =
			_batchEngineUnitReaderSnapshot.get();

		Bundle testBundle = FrameworkUtil.getBundle(clazz);

		BundleContext bundleContext = testBundle.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			if (!Objects.equals(
					bundle.getSymbolicName(),
					"com.liferay.digital.sales.room.impl")) {

				continue;
			}

			_deleteFile(bundle, "01.object.folder");
			_deleteFile(bundle, "02.object.definition");

			CompletableFuture<Void> completableFuture =
				batchEngineUnitProcessor.processBatchEngineUnits(
					batchEngineUnitReader.getBatchEngineUnits(bundle));

			completableFuture.join();
		}

		return ObjectDefinitionLocalServiceUtil.
			getObjectDefinitionByExternalReferenceCode(
				"L_DSR_ROOM", TestPropsValues.getCompanyId());
	}

	private static void _deleteFile(Bundle bundle, String fileName) {
		File file = bundle.getDataFile(
			".com.liferay.digital.sales.room.internal.batch." + fileName +
				".batch.engine.data.json.0.processed");

		if ((file != null) && file.exists()) {
			file.delete();
		}
	}

	private static final Snapshot<BatchEngineUnitProcessor>
		_batchEngineUnitProcessorSnapshot = new Snapshot<>(
			DigitalSalesRoomTestUtil.class, BatchEngineUnitProcessor.class);
	private static final Snapshot<BatchEngineUnitReader>
		_batchEngineUnitReaderSnapshot = new Snapshot<>(
			DigitalSalesRoomTestUtil.class, BatchEngineUnitReader.class);

}