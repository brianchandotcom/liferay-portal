/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the WorkflowInstanceLink service. Represents a row in the &quot;WorkflowInstanceLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowInstanceLinkModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.model.impl.WorkflowInstanceLinkImpl"
)
@ProviderType
public interface WorkflowInstanceLink
	extends PersistedModel, WorkflowInstanceLinkModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.WorkflowInstanceLinkImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<WorkflowInstanceLink, Long>
		WORKFLOW_INSTANCE_LINK_ID_ACCESSOR =
			new Accessor<WorkflowInstanceLink, Long>() {

				@Override
				public Long get(WorkflowInstanceLink workflowInstanceLink) {
					return workflowInstanceLink.getWorkflowInstanceLinkId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<WorkflowInstanceLink> getTypeClass() {
					return WorkflowInstanceLink.class;
				}

			};

}