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

package com.liferay.powwow.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the PowwowParticipant service. Represents a row in the &quot;PowwowParticipant&quot; database table, with each column mapped to a property of this class.
 *
 * @author Shinn Lok
 * @see PowwowParticipantModel
 * @see com.liferay.powwow.model.impl.PowwowParticipantImpl
 * @see com.liferay.powwow.model.impl.PowwowParticipantModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.powwow.model.impl.PowwowParticipantImpl")
@ProviderType
public interface PowwowParticipant extends PowwowParticipantModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.powwow.model.impl.PowwowParticipantImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<PowwowParticipant, Long> POWWOW_PARTICIPANT_ID_ACCESSOR =
		new Accessor<PowwowParticipant, Long>() {
			@Override
			public Long get(PowwowParticipant powwowParticipant) {
				return powwowParticipant.getPowwowParticipantId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<PowwowParticipant> getTypeClass() {
				return PowwowParticipant.class;
			}
		};
}