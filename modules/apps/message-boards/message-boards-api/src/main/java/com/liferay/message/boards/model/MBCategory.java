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

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the MBCategory service. Represents a row in the &quot;MBCategory&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MBCategoryModel
 * @generated
 */
@ImplementationClassName("com.liferay.message.boards.model.impl.MBCategoryImpl")
@ProviderType
public interface MBCategory extends MBCategoryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.message.boards.model.impl.MBCategoryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MBCategory, Long> CATEGORY_ID_ACCESSOR =
		new Accessor<MBCategory, Long>() {

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

	public java.util.List<Long> getAncestorCategoryIds()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<MBCategory> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getMessageCount();

	public MBCategory getParentCategory()
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getThreadCount();

	public boolean isRoot();

}