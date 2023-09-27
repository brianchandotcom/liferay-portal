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
 * The extended model interface for the BatchPlannerPolicy service. Represents a row in the &quot;BatchPlannerPolicy&quot; database table, with each column mapped to a property of this class.
 *
 * @author Igor Beslic
 * @see BatchPlannerPolicyModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.batch.planner.model.impl.BatchPlannerPolicyImpl"
)
@ProviderType
public interface BatchPlannerPolicy
	extends BatchPlannerPolicyModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.batch.planner.model.impl.BatchPlannerPolicyImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<BatchPlannerPolicy, Long>
		BATCH_PLANNER_POLICY_ID_ACCESSOR =
			new Accessor<BatchPlannerPolicy, Long>() {

				@Override
				public Long get(BatchPlannerPolicy batchPlannerPolicy) {
					return batchPlannerPolicy.getBatchPlannerPolicyId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<BatchPlannerPolicy> getTypeClass() {
					return BatchPlannerPolicy.class;
				}

			};

}