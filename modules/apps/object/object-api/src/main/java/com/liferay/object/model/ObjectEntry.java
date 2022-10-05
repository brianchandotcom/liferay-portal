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
 * The extended model interface for the ObjectEntry service. Represents a row in the &quot;ObjectEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.object.model.impl.ObjectEntryImpl")
@ProviderType
public interface ObjectEntry extends ObjectEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectEntry, Long> OBJECT_ENTRY_ID_ACCESSOR =
		new Accessor<ObjectEntry, Long>() {

			@Override
			public Long get(ObjectEntry objectEntry) {
				return objectEntry.getObjectEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ObjectEntry> getTypeClass() {
				return ObjectEntry.class;
			}

		};

	public String getModelClassName();

	public long getNonzeroGroupId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getTitleValue()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.Map<String, java.io.Serializable> getValues();

	public void setTransientValues(
		java.util.Map<String, java.io.Serializable> values);

	public void setValues(java.util.Map<String, java.io.Serializable> values);

}