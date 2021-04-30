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

package com.liferay.batch.planner.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the BatchPlannerLog service. Represents a row in the &quot;BatchPlannerLog&quot; database table, with each column mapped to a property of this class.
 *
 * @author Igor Beslic
 * @see BatchPlannerLogModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.batch.planner.model.impl.BatchPlannerLogImpl"
)
@ProviderType
public interface BatchPlannerLog extends BatchPlannerLogModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.batch.planner.model.impl.BatchPlannerLogImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BatchPlannerLog, Long>
		BATCH_PLANNER_LOG_ID_ACCESSOR = new Accessor<BatchPlannerLog, Long>() {

			@Override
			public Long get(BatchPlannerLog batchPlannerLog) {
				return batchPlannerLog.getBatchPlannerLogId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<BatchPlannerLog> getTypeClass() {
				return BatchPlannerLog.class;
			}

		};

}