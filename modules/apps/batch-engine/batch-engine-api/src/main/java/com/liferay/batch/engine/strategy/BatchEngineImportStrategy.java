/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.strategy;

import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.petra.function.UnsafeFunction;

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Matija Petanjek
 */
@ProviderType
public interface BatchEngineImportStrategy {

	public <T> void apply(
			BatchEngineTaskItemDelegate<T> batchEngineTaskItemDelegate,
			Collection<T> collection,
			UnsafeFunction<T, T, Exception> unsafeFunction)
		throws Exception;

}