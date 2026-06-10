/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.version.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the LayoutContentVersion service. Represents a row in the &quot;LayoutContentVersion&quot; database table, with each column mapped to a property of this class.
 *
 * @author Lourdes Fernández Besada
 * @see LayoutContentVersionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.layout.content.version.model.impl.LayoutContentVersionImpl"
)
@ProviderType
public interface LayoutContentVersion
	extends LayoutContentVersionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.layout.content.version.model.impl.LayoutContentVersionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LayoutContentVersion, Long>
		LAYOUT_CONTENT_VERSION_ID_ACCESSOR =
			new Accessor<LayoutContentVersion, Long>() {

				@Override
				public Long get(LayoutContentVersion layoutContentVersion) {
					return layoutContentVersion.getLayoutContentVersionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<LayoutContentVersion> getTypeClass() {
					return LayoutContentVersion.class;
				}

			};

}
// LIFERAY-SERVICE-BUILDER-HASH:-763120632