/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.oauth2.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SharepointOAuth2TokenEntry service. Represents a row in the &quot;SharepointOAuth2TokenEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Adolfo Pérez
 * @see SharepointOAuth2TokenEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryImpl"
)
@ProviderType
public interface SharepointOAuth2TokenEntry
	extends PersistedModel, SharepointOAuth2TokenEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SharepointOAuth2TokenEntry, Long>
		SHAREPOINT_O_AUTH2_TOKEN_ENTRY_ID_ACCESSOR =
			new Accessor<SharepointOAuth2TokenEntry, Long>() {

				@Override
				public Long get(
					SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {

					return sharepointOAuth2TokenEntry.
						getSharepointOAuth2TokenEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<SharepointOAuth2TokenEntry> getTypeClass() {
					return SharepointOAuth2TokenEntry.class;
				}

			};

}