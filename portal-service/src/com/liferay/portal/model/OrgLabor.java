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
 * The extended model interface for the OrgLabor service. Represents a row in the &quot;OrgLabor&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see OrgLaborModel
 * @see com.liferay.portal.model.impl.OrgLaborImpl
 * @see com.liferay.portal.model.impl.OrgLaborModelImpl
 * @generated
 */
@ProviderType
public interface OrgLabor extends OrgLaborModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.OrgLaborImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<OrgLabor, Long> ORG_LABOR_ID_ACCESSOR = new Accessor<OrgLabor, Long>() {
			@Override
			public Long get(OrgLabor orgLabor) {
				return orgLabor.getOrgLaborId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<OrgLabor> getTypeClass() {
				return OrgLabor.class;
			}
		};

	public com.liferay.portal.model.ListType getType()
		throws com.liferay.portal.kernel.exception.PortalException;
}