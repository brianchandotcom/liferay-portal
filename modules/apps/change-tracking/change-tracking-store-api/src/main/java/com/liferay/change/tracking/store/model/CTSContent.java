/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.store.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CTSContent service. Represents a row in the &quot;CTSContent&quot; database table, with each column mapped to a property of this class.
 *
 * @author Shuyang Zhou
 * @see CTSContentModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.change.tracking.store.model.impl.CTSContentImpl"
)
@ProviderType
public interface CTSContent extends CTSContentModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.change.tracking.store.model.impl.CTSContentImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CTSContent, Long> CTS_CONTENT_ID_ACCESSOR =
		new Accessor<CTSContent, Long>() {

			@Override
			public Long get(CTSContent ctsContent) {
				return ctsContent.getCtsContentId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CTSContent> getTypeClass() {
				return CTSContent.class;
			}

		};

}