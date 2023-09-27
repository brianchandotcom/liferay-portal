/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SXPElement service. Represents a row in the &quot;SXPElement&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SXPElementModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.search.experiences.model.impl.SXPElementImpl"
)
@ProviderType
public interface SXPElement extends PersistedModel, SXPElementModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.search.experiences.model.impl.SXPElementImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SXPElement, Long> SXP_ELEMENT_ID_ACCESSOR =
		new Accessor<SXPElement, Long>() {

			@Override
			public Long get(SXPElement sxpElement) {
				return sxpElement.getSXPElementId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SXPElement> getTypeClass() {
				return SXPElement.class;
			}

		};

}