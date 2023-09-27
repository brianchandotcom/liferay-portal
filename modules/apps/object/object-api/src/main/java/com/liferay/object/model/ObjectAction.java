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
 * The extended model interface for the ObjectAction service. Represents a row in the &quot;ObjectAction&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectActionModel
 * @generated
 */
@ImplementationClassName("com.liferay.object.model.impl.ObjectActionImpl")
@ProviderType
public interface ObjectAction extends ObjectActionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectActionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectAction, Long> OBJECT_ACTION_ID_ACCESSOR =
		new Accessor<ObjectAction, Long>() {

			@Override
			public Long get(ObjectAction objectAction) {
				return objectAction.getObjectActionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ObjectAction> getTypeClass() {
				return ObjectAction.class;
			}

		};

	public com.liferay.portal.kernel.util.UnicodeProperties
		getParametersUnicodeProperties();

}