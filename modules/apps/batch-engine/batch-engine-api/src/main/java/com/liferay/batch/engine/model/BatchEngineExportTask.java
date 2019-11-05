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
 * The extended model interface for the BatchEngineExportTask service. Represents a row in the &quot;BatchEngineExportTask&quot; database table, with each column mapped to a property of this class.
 *
 * @author Shuyang Zhou
 * @see BatchEngineExportTaskModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.batch.engine.model.impl.BatchEngineExportTaskImpl"
)
@ProviderType
public interface BatchEngineExportTask
	extends BatchEngineExportTaskModel, PersistedModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.batch.engine.model.impl.BatchEngineExportTaskImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BatchEngineExportTask, Long>
		BATCH_ENGINE_EXPORT_TASK_ID_ACCESSOR =
			new Accessor<BatchEngineExportTask, Long>() {

				@Override
				public Long get(BatchEngineExportTask batchEngineExportTask) {
					return batchEngineExportTask.getBatchEngineExportTaskId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<BatchEngineExportTask> getTypeClass() {
					return BatchEngineExportTask.class;
				}

			};

	public java.util.List<String> getFieldNamesList();

	public void setFieldNamesList(java.util.List<String> exportFieldsList);

}