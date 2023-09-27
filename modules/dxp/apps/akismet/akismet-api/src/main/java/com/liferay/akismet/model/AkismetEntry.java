/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.akismet.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AkismetEntry service. Represents a row in the &quot;OSBCommunity_AkismetEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Jamie Sammons
 * @see AkismetEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.akismet.model.impl.AkismetEntryImpl")
@ProviderType
public interface AkismetEntry extends AkismetEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.akismet.model.impl.AkismetEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AkismetEntry, Long> AKISMET_ENTRY_ID_ACCESSOR =
		new Accessor<AkismetEntry, Long>() {

			@Override
			public Long get(AkismetEntry akismetEntry) {
				return akismetEntry.getAkismetEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AkismetEntry> getTypeClass() {
				return AkismetEntry.class;
			}

		};

}