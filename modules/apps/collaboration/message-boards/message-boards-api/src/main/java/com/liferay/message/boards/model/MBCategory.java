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

package com.liferay.message.boards.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the MBCategory service. Represents a row in the &quot;MBCategory&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MBCategoryModel
 * @see com.liferay.message.boards.model.impl.MBCategoryImpl
 * @see com.liferay.message.boards.model.impl.MBCategoryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.message.boards.model.impl.MBCategoryImpl")
@ProviderType
public interface MBCategory extends MBCategoryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.message.boards.model.impl.MBCategoryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MBCategory, Long> CATEGORY_ID_ACCESSOR = new Accessor<MBCategory, Long>() {
			@Override
			public Long get(MBCategory mbCategory) {
				return mbCategory.getCategoryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MBCategory> getTypeClass() {
				return MBCategory.class;
			}
		};

	public java.util.List<java.lang.Long> getAncestorCategoryIds()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<MBCategory> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException;

	public MBCategory getParentCategory()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isRoot();
}