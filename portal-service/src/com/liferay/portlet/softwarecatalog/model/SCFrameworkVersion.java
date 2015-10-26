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

package com.liferay.portlet.softwarecatalog.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the SCFrameworkVersion service. Represents a row in the &quot;SCFrameworkVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SCFrameworkVersionModel
 * @see com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionImpl
 * @see com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl
 * @generated
 */
@ProviderType
public interface SCFrameworkVersion extends SCFrameworkVersionModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SCFrameworkVersion, Long> FRAMEWORK_VERSION_ID_ACCESSOR =
		new Accessor<SCFrameworkVersion, Long>() {
			@Override
			public Long get(SCFrameworkVersion scFrameworkVersion) {
				return scFrameworkVersion.getFrameworkVersionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SCFrameworkVersion> getTypeClass() {
				return SCFrameworkVersion.class;
			}
		};
}