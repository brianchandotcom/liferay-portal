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

package com.liferay.friendly.url.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the FriendlyURLMapping service. Represents a row in the &quot;FriendlyURLMapping&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLMappingModel
 * @see com.liferay.friendly.url.model.impl.FriendlyURLMappingImpl
 * @see com.liferay.friendly.url.model.impl.FriendlyURLMappingModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.friendly.url.model.impl.FriendlyURLMappingImpl")
@ProviderType
public interface FriendlyURLMapping extends FriendlyURLMappingModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.friendly.url.model.impl.FriendlyURLMappingImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FriendlyURLMapping, Long> CLASS_NAME_ID_ACCESSOR =
		new Accessor<FriendlyURLMapping, Long>() {
			@Override
			public Long get(FriendlyURLMapping friendlyURLMapping) {
				return friendlyURLMapping.getClassNameId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<FriendlyURLMapping> getTypeClass() {
				return FriendlyURLMapping.class;
			}
		};

	public static final Accessor<FriendlyURLMapping, Long> CLASS_PK_ACCESSOR = new Accessor<FriendlyURLMapping, Long>() {
			@Override
			public Long get(FriendlyURLMapping friendlyURLMapping) {
				return friendlyURLMapping.getClassPK();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<FriendlyURLMapping> getTypeClass() {
				return FriendlyURLMapping.class;
			}
		};
}