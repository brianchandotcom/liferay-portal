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

package com.liferay.batch.engine.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the BatchEngineImportTaskError service. Represents a row in the &quot;BatchEngineImportTaskError&quot; database table, with each column mapped to a property of this class.
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTaskErrorModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.batch.engine.model.impl.BatchEngineImportTaskErrorImpl"
)
@ProviderType
public interface BatchEngineImportTaskError
	extends BatchEngineImportTaskErrorModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.batch.engine.model.impl.BatchEngineImportTaskErrorImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BatchEngineImportTaskError, Long>
		BATCH_ENGINE_IMPORT_TASK_ERROR_ID_ACCESSOR =
			new Accessor<BatchEngineImportTaskError, Long>() {

				@Override
				public Long get(
					BatchEngineImportTaskError batchEngineImportTaskError) {

					return batchEngineImportTaskError.
						getBatchEngineImportTaskErrorId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<BatchEngineImportTaskError> getTypeClass() {
					return BatchEngineImportTaskError.class;
				}

			};

}