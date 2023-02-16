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

package com.liferay.batch.engine.internal.auto.deploy;

import com.liferay.batch.engine.internal.unit.BatchEngineUnitConfigurationHelper;
import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 * @author Raymond Augé
 */
@Component(service = AutoDeployListener.class)
public class BatchEngineAutoDeployListener implements AutoDeployListener {

	@Override
	public int deploy(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		try (ZipFile zipFile = new ZipFile(autoDeploymentContext.getFile())) {
			if (_log.isInfoEnabled()) {
				_log.info("Deploying batch engine file " + zipFile.getName());
			}

			_batchEngineUnitProcessor.processBatchEngineUnits(
				_getBatchEngineUnits(zipFile));
		}
		catch (Exception exception) {
			throw new AutoDeployException(exception);
		}

		return AutoDeployer.CODE_DEFAULT;
	}

	public boolean isBatchEngineTechnical(String zipEntryName) {
		if (zipEntryName.endsWith("jsont")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isDeployable(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		File file = autoDeploymentContext.getFile();

		String fileName = file.getName();

		if (!StringUtil.endsWith(fileName, ".zip")) {
			return false;
		}

		try (ZipFile zipFile = new ZipFile(file)) {
			for (BatchEngineUnit batchEngineUnit :
					_getBatchEngineUnits(zipFile)) {

				if (!batchEngineUnit.isValid()) {
					continue;
				}

				BatchEngineUnitConfiguration batchEngineUnitConfiguration =
					_batchEngineUnitConfigurationHelper.
						updateBatchEngineUnitConfiguration(
							batchEngineUnit.getBatchEngineUnitConfiguration());

				if ((batchEngineUnitConfiguration != null) &&
					(batchEngineUnitConfiguration.companyId > 0) &&
					(batchEngineUnitConfiguration.userId > 0) &&
					Validator.isNotNull(
						batchEngineUnitConfiguration.className) &&
					Validator.isNotNull(batchEngineUnitConfiguration.version)) {

					return true;
				}
			}
		}
		catch (Exception exception) {
			throw new AutoDeployException(exception);
		}

		return false;
	}

	private Iterable<BatchEngineUnit> _getBatchEngineUnits(ZipFile zipFile) {
		return new Iterable<BatchEngineUnit>() {

			@Override
			public Iterator<BatchEngineUnit> iterator() {
				return new BatchEngineUnitIterator(zipFile);
			}

		};
	}

	private String _getBatchEngineZipEntryKey(ZipEntry zipEntry) {
		String zipEntryName = zipEntry.getName();

		if (isBatchEngineTechnical(zipEntryName)) {
			return zipEntryName;
		}

		if (!zipEntryName.contains(StringPool.SLASH)) {
			return StringPool.BLANK;
		}

		return zipEntryName.substring(
			0, zipEntryName.lastIndexOf(StringPool.SLASH));
	}

	private Collection<BatchEngineUnit> _getBatchEngineZipUnitsCollection(
		ZipFile zipFile) {

		Map<String, ZipEntry> batchEngineZipEntries = new HashMap<>();
		Map<String, BatchEngineUnit> batchEngineUnits = new HashMap<>();
		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			if (zipEntry.isDirectory()) {
				continue;
			}

			String key = _getBatchEngineZipEntryKey(zipEntry);

			ZipEntry complementZipEntry = batchEngineZipEntries.get(key);

			if (complementZipEntry == null) {
				batchEngineZipEntries.put(key, zipEntry);

				batchEngineUnits.put(
					key, new AdvancedBatchEngineZipUnitImpl(zipFile, zipEntry));

				continue;
			}

			batchEngineUnits.put(
				key,
				new ClassicBatchEngineZipUnitImpl(
					zipFile, zipEntry, complementZipEntry));

			batchEngineZipEntries.remove(key);
		}

		return batchEngineUnits.values();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineAutoDeployListener.class);

	@Reference
	private BatchEngineUnitConfigurationHelper
		_batchEngineUnitConfigurationHelper;

	@Reference
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	private class BatchEngineUnitIterator implements Iterator<BatchEngineUnit> {

		public BatchEngineUnitIterator(ZipFile zipFile) {
			Collection<BatchEngineUnit> batchEngineUnits =
				_getBatchEngineZipUnitsCollection(zipFile);

			_iterator = batchEngineUnits.iterator();
		}

		@Override
		public boolean hasNext() {
			return _iterator.hasNext();
		}

		@Override
		public BatchEngineUnit next() {
			return _iterator.next();
		}

		private final Iterator<BatchEngineUnit> _iterator;

	}

}