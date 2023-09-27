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
 * The extended model interface for the KaleoTransition service. Represents a row in the &quot;KaleoTransition&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTransitionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.workflow.kaleo.model.impl.KaleoTransitionImpl"
)
@ProviderType
public interface KaleoTransition extends KaleoTransitionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoTransitionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<KaleoTransition, Long>
		KALEO_TRANSITION_ID_ACCESSOR = new Accessor<KaleoTransition, Long>() {

			@Override
			public Long get(KaleoTransition kaleoTransition) {
				return kaleoTransition.getKaleoTransitionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<KaleoTransition> getTypeClass() {
				return KaleoTransition.class;
			}

		};

	public KaleoNode getSourceKaleoNode()
		throws com.liferay.portal.kernel.exception.PortalException;

	public KaleoNode getTargetKaleoNode()
		throws com.liferay.portal.kernel.exception.PortalException;

}