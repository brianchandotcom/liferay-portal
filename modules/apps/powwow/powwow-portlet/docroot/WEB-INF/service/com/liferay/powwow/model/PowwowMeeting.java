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
 * The extended model interface for the PowwowMeeting service. Represents a row in the &quot;PowwowMeeting&quot; database table, with each column mapped to a property of this class.
 *
 * @author Shinn Lok
 * @see PowwowMeetingModel
 * @see com.liferay.powwow.model.impl.PowwowMeetingImpl
 * @see com.liferay.powwow.model.impl.PowwowMeetingModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.powwow.model.impl.PowwowMeetingImpl")
@ProviderType
public interface PowwowMeeting extends PowwowMeetingModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.powwow.model.impl.PowwowMeetingImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<PowwowMeeting, Long> POWWOW_MEETING_ID_ACCESSOR =
		new Accessor<PowwowMeeting, Long>() {
			@Override
			public Long get(PowwowMeeting powwowMeeting) {
				return powwowMeeting.getPowwowMeetingId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<PowwowMeeting> getTypeClass() {
				return PowwowMeeting.class;
			}
		};

	public java.util.Map<java.lang.String, java.io.Serializable> getProviderTypeMetadataMap();
}