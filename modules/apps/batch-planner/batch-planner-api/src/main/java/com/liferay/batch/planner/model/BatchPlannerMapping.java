/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the BatchPlannerMapping service. Represents a row in the &quot;BatchPlannerMapping&quot; database table, with each column mapped to a property of this class.
 *
 * @author Igor Beslic
 * @see BatchPlannerMappingModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.batch.planner.model.impl.BatchPlannerMappingImpl"
)
@ProviderType
public interface BatchPlannerMapping
	extends BatchPlannerMappingModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.batch.planner.model.impl.BatchPlannerMappingImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BatchPlannerMapping, Long>
		BATCH_PLANNER_MAPPING_ID_ACCESSOR =
			new Accessor<BatchPlannerMapping, Long>() {

				@Override
				public Long get(BatchPlannerMapping batchPlannerMapping) {
					return batchPlannerMapping.getBatchPlannerMappingId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<BatchPlannerMapping> getTypeClass() {
					return BatchPlannerMapping.class;
				}

			};

}