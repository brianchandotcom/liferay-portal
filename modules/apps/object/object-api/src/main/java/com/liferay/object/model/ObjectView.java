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

package com.liferay.object.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ObjectView service. Represents a row in the &quot;ObjectView&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectViewModel
 * @generated
 */
@ImplementationClassName("com.liferay.object.model.impl.ObjectViewImpl")
@ProviderType
public interface ObjectView extends ObjectViewModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectViewImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectView, Long> OBJECT_VIEW_ID_ACCESSOR =
		new Accessor<ObjectView, Long>() {

			@Override
			public Long get(ObjectView objectView) {
				return objectView.getObjectViewId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ObjectView> getTypeClass() {
				return ObjectView.class;
			}

		};

	public java.util.List<ObjectViewColumn> getObjectViewColumns();

	public java.util.List<ObjectViewSortColumn> getObjectViewSortColumns();

	public void setObjectViewColumns(
		java.util.List<ObjectViewColumn> objectViewColumns);

	public void setObjectViewSortColumns(
		java.util.List<ObjectViewSortColumn> objectViewSortColumns);

}