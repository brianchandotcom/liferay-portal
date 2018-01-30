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

package com.liferay.sharepoint.rest.oauth2.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the SharepointOAuth2TokenEntry service. Represents a row in the &quot;SharepointOAuth2TokenEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Adolfo Pérez
 * @see SharepointOAuth2TokenEntryModel
 * @see com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryImpl
 * @see com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryImpl")
@ProviderType
public interface SharepointOAuth2TokenEntry
	extends SharepointOAuth2TokenEntryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SharepointOAuth2TokenEntry, Long> SHAREPOINT_O_AUTH2_TOKEN_ENTRY_ID_ACCESSOR =
		new Accessor<SharepointOAuth2TokenEntry, Long>() {
			@Override
			public Long get(
				SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {
				return sharepointOAuth2TokenEntry.getSharepointOAuth2TokenEntryId();
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