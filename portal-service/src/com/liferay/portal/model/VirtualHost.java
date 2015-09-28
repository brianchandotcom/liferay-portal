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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the VirtualHost service. Represents a row in the &quot;VirtualHost&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHostModel
 * @see com.liferay.portal.model.impl.VirtualHostImpl
 * @see com.liferay.portal.model.impl.VirtualHostModelImpl
 * @generated
 */
@ProviderType
public interface VirtualHost extends VirtualHostModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.VirtualHostImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<VirtualHost, Long> VIRTUAL_HOST_ID_ACCESSOR = new Accessor<VirtualHost, Long>() {
			@Override
			public Long get(VirtualHost virtualHost) {
				return virtualHost.getVirtualHostId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<VirtualHost> getTypeClass() {
				return VirtualHost.class;
			}
		};
}