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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the LayoutVersion service. Represents a row in the &quot;LayoutVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutVersionModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.LayoutVersionImpl")
@ProviderType
public interface LayoutVersion extends LayoutVersionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.LayoutVersionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LayoutVersion, Long>
		LAYOUT_VERSION_ID_ACCESSOR = new Accessor<LayoutVersion, Long>() {

			@Override
			public Long get(LayoutVersion layoutVersion) {
				return layoutVersion.getLayoutVersionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<LayoutVersion> getTypeClass() {
				return LayoutVersion.class;
			}

		};
	public static final Accessor<LayoutVersion, Long> LAYOUT_ID_ACCESSOR =
		new Accessor<LayoutVersion, Long>() {

			@Override
			public Long get(LayoutVersion layoutVersion) {
				return layoutVersion.getLayoutId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<LayoutVersion> getTypeClass() {
				return LayoutVersion.class;
			}

		};

}