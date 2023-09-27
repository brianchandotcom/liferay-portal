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
 * The extended model interface for the BatchPlannerPlan service. Represents a row in the &quot;BatchPlannerPlan&quot; database table, with each column mapped to a property of this class.
 *
 * @author Igor Beslic
 * @see BatchPlannerPlanModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.batch.planner.model.impl.BatchPlannerPlanImpl"
)
@ProviderType
public interface BatchPlannerPlan
	extends BatchPlannerPlanModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.batch.planner.model.impl.BatchPlannerPlanImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BatchPlannerPlan, Long>
		BATCH_PLANNER_PLAN_ID_ACCESSOR =
			new Accessor<BatchPlannerPlan, Long>() {

				@Override
				public Long get(BatchPlannerPlan batchPlannerPlan) {
					return batchPlannerPlan.getBatchPlannerPlanId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<BatchPlannerPlan> getTypeClass() {
					return BatchPlannerPlan.class;
				}

			};

	public BatchPlannerPolicy fetchBatchPlannerPolicy(String name);

	public java.util.List<BatchPlannerMapping> getBatchPlannerMappings();

	public java.util.List<BatchPlannerPolicy> getBatchPlannerPolicies();

	public BatchPlannerPolicy getBatchPlannerPolicy(String name)
		throws com.liferay.portal.kernel.exception.PortalException;

}