/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ClientExtensionEntryRel service. Represents a row in the &quot;ClientExtensionEntryRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ClientExtensionEntryRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.client.extension.model.impl.ClientExtensionEntryRelImpl"
)
@ProviderType
public interface ClientExtensionEntryRel
	extends ClientExtensionEntryRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.client.extension.model.impl.ClientExtensionEntryRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ClientExtensionEntryRel, Long>
		CLIENT_EXTENSION_ENTRY_REL_ID_ACCESSOR =
			new Accessor<ClientExtensionEntryRel, Long>() {

				@Override
				public Long get(
					ClientExtensionEntryRel clientExtensionEntryRel) {

					return clientExtensionEntryRel.
						getClientExtensionEntryRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ClientExtensionEntryRel> getTypeClass() {
					return ClientExtensionEntryRel.class;
				}

			};

}