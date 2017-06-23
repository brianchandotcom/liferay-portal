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
 * The extended model interface for the PowwowServer service. Represents a row in the &quot;PowwowServer&quot; database table, with each column mapped to a property of this class.
 *
 * @author Shinn Lok
 * @see PowwowServerModel
 * @see com.liferay.powwow.model.impl.PowwowServerImpl
 * @see com.liferay.powwow.model.impl.PowwowServerModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.powwow.model.impl.PowwowServerImpl")
@ProviderType
public interface PowwowServer extends PowwowServerModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.powwow.model.impl.PowwowServerImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<PowwowServer, Long> POWWOW_SERVER_ID_ACCESSOR = new Accessor<PowwowServer, Long>() {
			@Override
			public Long get(PowwowServer powwowServer) {
				return powwowServer.getPowwowServerId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<PowwowServer> getTypeClass() {
				return PowwowServer.class;
			}
		};
}