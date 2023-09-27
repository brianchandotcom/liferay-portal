/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ObjectStateFlow service. Represents a row in the &quot;ObjectStateFlow&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectStateFlowModel
 * @generated
 */
@ImplementationClassName("com.liferay.object.model.impl.ObjectStateFlowImpl")
@ProviderType
public interface ObjectStateFlow extends ObjectStateFlowModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectStateFlowImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectStateFlow, Long>
		OBJECT_STATE_FLOW_ID_ACCESSOR = new Accessor<ObjectStateFlow, Long>() {

			@Override
			public Long get(ObjectStateFlow objectStateFlow) {
				return objectStateFlow.getObjectStateFlowId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ObjectStateFlow> getTypeClass() {
				return ObjectStateFlow.class;
			}

		};

	public java.util.List<ObjectState> getObjectStates();

	public void setObjectStates(java.util.List<ObjectState> objectStates);

}