/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the KaleoTaskInstanceToken service. Represents a row in the &quot;KaleoTaskInstanceToken&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskInstanceTokenModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenImpl"
)
@ProviderType
public interface KaleoTaskInstanceToken
	extends KaleoTaskInstanceTokenModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<KaleoTaskInstanceToken, Long>
		KALEO_TASK_INSTANCE_TOKEN_ID_ACCESSOR =
			new Accessor<KaleoTaskInstanceToken, Long>() {

				@Override
				public Long get(KaleoTaskInstanceToken kaleoTaskInstanceToken) {
					return kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<KaleoTaskInstanceToken> getTypeClass() {
					return KaleoTaskInstanceToken.class;
				}

			};

	public KaleoTaskAssignmentInstance getFirstKaleoTaskAssignmentInstance();

	public KaleoInstanceToken getKaleoInstanceToken()
		throws com.liferay.portal.kernel.exception.PortalException;

	public KaleoTask getKaleoTask()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<KaleoTaskAssignmentInstance>
		getKaleoTaskAssignmentInstances();

}