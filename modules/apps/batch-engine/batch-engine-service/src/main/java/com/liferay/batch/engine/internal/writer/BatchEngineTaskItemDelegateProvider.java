/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.writer;

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegateAdaptorFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(service = BatchEngineTaskItemDelegateProvider.class)
public class BatchEngineTaskItemDelegateProvider {

	public BatchEngineTaskItemDelegate<?> toBatchEngineTaskItemDelegate(
		Object service) {

		if (service instanceof BatchEngineTaskItemDelegate) {
			return (BatchEngineTaskItemDelegate<?>)service;
		}

		if (service instanceof VulcanBatchEngineTaskItemDelegate) {
			return _vulcanBatchEngineTaskItemDelegateAdaptorFactory.create(
				(VulcanBatchEngineTaskItemDelegate<?>)service);
		}

		throw new IllegalArgumentException("Unknown service :" + service);
	}

	@Reference
	private VulcanBatchEngineTaskItemDelegateAdaptorFactory
		_vulcanBatchEngineTaskItemDelegateAdaptorFactory;

}