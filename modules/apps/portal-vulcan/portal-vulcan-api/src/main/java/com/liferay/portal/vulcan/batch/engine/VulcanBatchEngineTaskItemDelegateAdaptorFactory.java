/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.batch.engine;

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;

/**
 * @author Shuyang Zhou
 */
public interface VulcanBatchEngineTaskItemDelegateAdaptorFactory {

	public <T> BatchEngineTaskItemDelegate<T> create(
		VulcanBatchEngineTaskItemDelegate<T> vulcanBatchEngineTaskItemDelegate);

}