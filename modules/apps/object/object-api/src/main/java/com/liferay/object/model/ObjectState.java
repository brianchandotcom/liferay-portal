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

package com.liferay.object.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ObjectState service. Represents a row in the &quot;ObjectState&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectStateModel
 * @generated
 */
@ImplementationClassName("com.liferay.object.model.impl.ObjectStateImpl")
@ProviderType
public interface ObjectState extends ObjectStateModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectStateImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectState, Long> OBJECT_STATE_ID_ACCESSOR =
		new Accessor<ObjectState, Long>() {

			@Override
			public Long get(ObjectState objectState) {
				return objectState.getObjectStateId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ObjectState> getTypeClass() {
				return ObjectState.class;
			}

		};

	public java.util.List<ObjectStateTransition> getObjectStateTransitions();

	public void setObjectStateTransitions(
		java.util.List<ObjectStateTransition> objectStateTransitions);

}