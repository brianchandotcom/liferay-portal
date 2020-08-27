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

package com.liferay.commerce.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceOrderNote service. Represents a row in the &quot;CommerceOrderNote&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderNoteModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.model.impl.CommerceOrderNoteImpl"
)
@ProviderType
public interface CommerceOrderNote
	extends CommerceOrderNoteModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.model.impl.CommerceOrderNoteImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceOrderNote, Long>
		COMMERCE_ORDER_NOTE_ID_ACCESSOR =
			new Accessor<CommerceOrderNote, Long>() {

				@Override
				public Long get(CommerceOrderNote commerceOrderNote) {
					return commerceOrderNote.getCommerceOrderNoteId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceOrderNote> getTypeClass() {
					return CommerceOrderNote.class;
				}

			};

	public com.liferay.portal.kernel.model.User getUser();

}