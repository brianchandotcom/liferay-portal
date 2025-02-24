/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.osgi.commands;

import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.batch.engine.unit.BatchEngineUnitThreadLocal;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.io.File;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"osgi.command.function=reset", "osgi.command.scope=headlessBuilder"
	},
	service = OSGiCommands.class
)
public class HeadlessBuilderOSGiCommands implements OSGiCommands {

	public void reset(long companyId) throws Exception {
		Bundle bundle = _bundleContext.getBundle();

		for (String externalReferenceCode :
				Arrays.asList(
					"L_API_APPLICATION", "L_API_ENDPOINT", "L_API_FILTER",
					"L_API_PROPERTY", "L_API_SCHEMA", "L_API_SORT")) {

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.
					fetchObjectDefinitionByExternalReferenceCode(
						externalReferenceCode, companyId);

			if (objectDefinition == null) {
				continue;
			}

			BatchEngineUnitThreadLocal.setFileName(bundle.toString());

			try {
				_objectDefinitionLocalService.deleteObjectDefinition(
					objectDefinition);
			}
			finally {
				BatchEngineUnitThreadLocal.setFileName(StringPool.BLANK);
			}
		}

		for (String processedFileName :
				Arrays.asList(
					"00.list.type.definition", "01.object.definition")) {

			File processedFile = bundle.getDataFile(
				".com.liferay.headless.builder.internal.batch." +
					processedFileName + ".batch.engine.data.json.0.processed");

			if ((processedFile != null) && processedFile.exists()) {
				processedFile.delete();
			}
		}

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(companyId)) {

			CompletableFuture<Void> completableFuture =
				_batchEngineUnitProcessor.processBatchEngineUnits(
					_batchEngineUnitReader.getBatchEngineUnits(bundle));

			completableFuture.join();
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Reference
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Reference
	private BatchEngineUnitReader _batchEngineUnitReader;

	private BundleContext _bundleContext;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}