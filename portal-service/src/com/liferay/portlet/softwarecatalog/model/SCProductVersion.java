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
 * The extended model interface for the SCProductVersion service. Represents a row in the &quot;SCProductVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SCProductVersionModel
 * @see com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionImpl
 * @see com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionModelImpl
 * @generated
 */
@ProviderType
public interface SCProductVersion extends SCProductVersionModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductVersionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SCProductVersion, Long> PRODUCT_VERSION_ID_ACCESSOR =
		new Accessor<SCProductVersion, Long>() {
			@Override
			public Long get(SCProductVersion scProductVersion) {
				return scProductVersion.getProductVersionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SCProductVersion> getTypeClass() {
				return SCProductVersion.class;
			}
		};

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions();

	public com.liferay.portlet.softwarecatalog.model.SCProductEntry getProductEntry();
}