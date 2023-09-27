/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the BatchEngineImportTask service. Represents a row in the &quot;BatchEngineImportTask&quot; database table, with each column mapped to a property of this class.
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTaskModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.batch.engine.model.impl.BatchEngineImportTaskImpl"
)
@ProviderType
public interface BatchEngineImportTask
	extends BatchEngineImportTaskModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.batch.engine.model.impl.BatchEngineImportTaskImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BatchEngineImportTask, Long>
		BATCH_ENGINE_IMPORT_TASK_ID_ACCESSOR =
			new Accessor<BatchEngineImportTask, Long>() {

				@Override
				public Long get(BatchEngineImportTask batchEngineImportTask) {
					return batchEngineImportTask.getBatchEngineImportTaskId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<BatchEngineImportTask> getTypeClass() {
					return BatchEngineImportTask.class;
				}

			};

	public java.util.List<BatchEngineImportTaskError>
		getBatchEngineImportTaskErrors();

	public int getBatchEngineImportTaskErrorsCount();

}