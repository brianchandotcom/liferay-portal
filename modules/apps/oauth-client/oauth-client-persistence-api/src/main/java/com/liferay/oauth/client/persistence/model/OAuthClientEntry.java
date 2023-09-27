/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the OAuthClientEntry service. Represents a row in the &quot;OAuthClientEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.oauth.client.persistence.model.impl.OAuthClientEntryImpl"
)
@ProviderType
public interface OAuthClientEntry
	extends OAuthClientEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.oauth.client.persistence.model.impl.OAuthClientEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<OAuthClientEntry, Long>
		O_AUTH_CLIENT_ENTRY_ID_ACCESSOR =
			new Accessor<OAuthClientEntry, Long>() {

				@Override
				public Long get(OAuthClientEntry oAuthClientEntry) {
					return oAuthClientEntry.getOAuthClientEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<OAuthClientEntry> getTypeClass() {
					return OAuthClientEntry.class;
				}

			};

}