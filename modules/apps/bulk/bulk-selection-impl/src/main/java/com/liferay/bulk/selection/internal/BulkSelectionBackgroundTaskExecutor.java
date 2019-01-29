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

package com.liferay.bulk.selection.internal;

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.BulkSelectionInputParameters;
import com.liferay.bulk.selection.internal.constants.BulkSelectionBackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;

import java.io.Serializable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "background.task.executor.class.name=com.liferay.bulk.selection.internal.BulkSelectionBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class BulkSelectionBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public BulkSelectionBackgroundTaskExecutor() {
		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_TASK_NAME);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask) {
		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		String bulkSelectionActionClassName = (String)taskContextMap.get(
			BulkSelectionBackgroundTaskConstants.
				BULK_SELECTION_ACTION_CLASS_NAME);

		Optional<BulkSelectionAction> bulkSelectionActionOptional = _getService(
			BulkSelectionAction.class, bulkSelectionActionClassName);

		bulkSelectionActionOptional.ifPresent(
			bulkSelectionAction -> {
				Map<String, String[]> parameterMap =
					(Map<String, String[]>)taskContextMap.get(
						BulkSelectionBackgroundTaskConstants.
							BULK_SELECTION_PARAMETER_MAP);

				String bulkSelectionFactoryClassName =
					(String)taskContextMap.get(
						BulkSelectionBackgroundTaskConstants.
							BULK_SELECTION_FACTORY_CLASS_NAME);

				Optional<BulkSelectionFactory> bulkSelectionFactoryOptional =
					_getService(
						BulkSelectionFactory.class,
						bulkSelectionFactoryClassName);

				bulkSelectionFactoryOptional.ifPresent(
					bulkSelectionFactory -> {
						try {
							BulkSelection bulkSelection =
								bulkSelectionFactory.create(parameterMap);

							Map<String, Serializable> inputMap =
								(Map<String, Serializable>)taskContextMap.get(
									BulkSelectionBackgroundTaskConstants.
										BULK_SELECTION_ACTION_INPUT_MAP);

							boolean assetEntryBulkSelection =
								(boolean)inputMap.getOrDefault(
									BulkSelectionInputParameters.
										ASSET_ENTRY_BULK_SELECTION,
									false);

							if (assetEntryBulkSelection) {
								bulkSelection =
									bulkSelection.toAssetEntryBulkSelection();
							}

							bulkSelectionAction.execute(
								_userLocalService.getUser(
									backgroundTask.getUserId()),
								bulkSelection, inputMap);
						}
						catch (Exception e) {
							_log.error(e, e);
						}
					});
			});

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private <T> Optional<T> _getService(
		Class<T> clazz, String concreteClassName) {

		try {
			Collection<ServiceReference<T>> serviceReferences =
				_bundleContext.getServiceReferences(
					clazz, "(objectClass=" + concreteClassName + ")");

			Iterator<ServiceReference<T>> iterator =
				serviceReferences.iterator();

			if (iterator.hasNext()) {
				return Optional.of(_bundleContext.getService(iterator.next()));
			}

			return Optional.empty();
		}
		catch (InvalidSyntaxException ise) {
			return Optional.empty();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BulkSelectionBackgroundTaskExecutor.class);

	private BundleContext _bundleContext;

	@Reference
	private UserLocalService _userLocalService;

}