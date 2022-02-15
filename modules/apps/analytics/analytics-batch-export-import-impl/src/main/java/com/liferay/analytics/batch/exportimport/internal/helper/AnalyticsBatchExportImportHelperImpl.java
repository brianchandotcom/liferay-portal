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

package com.liferay.analytics.batch.exportimport.internal.helper;

import com.liferay.analytics.batch.exportimport.helper.AnalyticsBatchExportImportHelper;
import com.liferay.analytics.batch.exportimport.internal.batch.BatchEngineExportTaskHelper;
import com.liferay.analytics.batch.exportimport.internal.batch.BatchEngineImportTaskHelper;
import com.liferay.analytics.batch.exportimport.internal.batch.BatchEngineTaskHelperFactory;
import com.liferay.analytics.message.sender.client.AnalyticsBatchClient;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = AnalyticsBatchExportImportHelper.class)
public class AnalyticsBatchExportImportHelperImpl
	implements AnalyticsBatchExportImportHelper {

	@Override
	public void exportToAnalyticsCloud(
			String batchEngineExportTaskItemDelegateName, long companyId,
			List<String> fieldList,
			UnsafeConsumer<String, Exception> notificationHandler,
			Date resourceLastModifiedDate, String resourceName, long userId)
		throws Exception {

		_notify(notificationHandler, "Exporting resource: " + resourceName);

		Map<String, Serializable> parameters = new HashMap<>();

		if (resourceLastModifiedDate != null) {
			parameters.put("filter", _getFilter(resourceLastModifiedDate));
		}

		BatchEngineExportTaskHelper batchEngineExportTaskHelper =
			_batchEngineTaskHelperFactory.getBatchEngineExportTaskHelper(
				batchEngineExportTaskItemDelegateName, companyId, fieldList,
				parameters, resourceName, userId);

		if (batchEngineExportTaskHelper.execute()) {
			int totalItemsCount =
				batchEngineExportTaskHelper.getTotalItemsCount();

			_notify(
				notificationHandler,
				String.format(
					"Exported %s items for resource: %s", totalItemsCount,
					resourceName));

			if (totalItemsCount == 0) {
				_notify(notificationHandler, "Nothing to upload");

				return;
			}

			_notify(notificationHandler, "Uploading resource: " + resourceName);

			InputStream contentInputStream =
				batchEngineExportTaskHelper.getContentInputStream();

			_analyticsBatchClient.uploadResource(
				companyId, contentInputStream, resourceName);

			contentInputStream.close();

			batchEngineExportTaskHelper.clean();

			_notify(
				notificationHandler,
				"Uploading resource complete for: " + resourceName);
		}
		else {
			throw new PortalException(
				"Exporting resource failed for: " + resourceName);
		}
	}

	@Override
	public void importFromAnalyticsCloud(
			String batchEngineImportTaskItemDelegateName, long companyId,
			Map<String, String> fieldMapping,
			UnsafeConsumer<String, Exception> notificationHandler,
			Date resourceLastModifiedDate, String resourceName, long userId)
		throws Exception {

		_notify(notificationHandler, "Checking updates for: " + resourceName);

		File resourceFile = _analyticsBatchClient.downloadResource(
			companyId, resourceLastModifiedDate, resourceName);

		if (resourceFile == null) {
			_notify(
				notificationHandler,
				"No updates for resource: " + resourceName);

			return;
		}

		_notify(notificationHandler, "Importing resource: " + resourceName);

		BatchEngineImportTaskHelper batchEngineImportTaskHelper =
			_batchEngineTaskHelperFactory.getBatchEngineImportTaskHelper(
				batchEngineImportTaskItemDelegateName, companyId, fieldMapping,
				resourceFile, resourceName, userId);

		if (batchEngineImportTaskHelper.execute()) {
			_notify(
				notificationHandler,
				String.format(
					"Imported %s items for resource: %s",
					batchEngineImportTaskHelper.getTotalItemsCount(),
					resourceName));

			batchEngineImportTaskHelper.clean();
		}
		else {
			throw new PortalException(
				"Importing resource failed for: " + resourceName);
		}
	}

	private Serializable _getFilter(Date resourceLastModifiedDate) {
		StringBundler sb = new StringBundler(3);

		sb.append(Field.getSortableFieldName(Field.MODIFIED_DATE));
		sb.append(" ge ");
		sb.append(resourceLastModifiedDate.getTime());

		return sb.toString();
	}

	private void _notify(
			UnsafeConsumer<String, Exception> notificationHandler,
			String message)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(message);
		}

		if (notificationHandler == null) {
			return;
		}

		notificationHandler.accept(message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsBatchExportImportHelperImpl.class);

	@Reference
	private AnalyticsBatchClient _analyticsBatchClient;

	@Reference
	private BatchEngineTaskHelperFactory _batchEngineTaskHelperFactory;

}