/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.app.builder.workflow.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AppBuilderWorkflowTaskLink service. Represents a row in the &quot;AppBuilderWorkflowTaskLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderWorkflowTaskLinkModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.app.builder.workflow.model.impl.AppBuilderWorkflowTaskLinkImpl"
)
@ProviderType
public interface AppBuilderWorkflowTaskLink
	extends AppBuilderWorkflowTaskLinkModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.app.builder.workflow.model.impl.AppBuilderWorkflowTaskLinkImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AppBuilderWorkflowTaskLink, Long>
		APP_BUILDER_WORKFLOW_TASK_LINK_ID_ACCESSOR =
			new Accessor<AppBuilderWorkflowTaskLink, Long>() {

				@Override
				public Long get(
					AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

					return appBuilderWorkflowTaskLink.
						getAppBuilderWorkflowTaskLinkId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<AppBuilderWorkflowTaskLink> getTypeClass() {
					return AppBuilderWorkflowTaskLink.class;
				}

			};

}