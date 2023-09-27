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
 * The extended model interface for the ObjectLayout service. Represents a row in the &quot;ObjectLayout&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectLayoutModel
 * @generated
 */
@ImplementationClassName("com.liferay.object.model.impl.ObjectLayoutImpl")
@ProviderType
public interface ObjectLayout extends ObjectLayoutModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectLayoutImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectLayout, Long> OBJECT_LAYOUT_ID_ACCESSOR =
		new Accessor<ObjectLayout, Long>() {

			@Override
			public Long get(ObjectLayout objectLayout) {
				return objectLayout.getObjectLayoutId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ObjectLayout> getTypeClass() {
				return ObjectLayout.class;
			}

		};

	public java.util.List<ObjectLayoutTab> getObjectLayoutTabs();

	public void setObjectLayoutTabs(
		java.util.List<ObjectLayoutTab> objectLayoutTabs);

}