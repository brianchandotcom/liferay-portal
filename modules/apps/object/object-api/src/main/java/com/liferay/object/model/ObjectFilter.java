/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ObjectFilter service. Represents a row in the &quot;ObjectFilter&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectFilterModel
 * @generated
 */
@ImplementationClassName("com.liferay.object.model.impl.ObjectFilterImpl")
@ProviderType
public interface ObjectFilter extends ObjectFilterModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectFilterImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectFilter, Long> OBJECT_FILTER_ID_ACCESSOR =
		new Accessor<ObjectFilter, Long>() {

			@Override
			public Long get(ObjectFilter objectFilter) {
				return objectFilter.getObjectFilterId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ObjectFilter> getTypeClass() {
				return ObjectFilter.class;
			}

		};

}