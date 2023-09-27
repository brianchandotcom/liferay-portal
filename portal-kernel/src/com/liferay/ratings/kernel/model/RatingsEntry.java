/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ratings.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the RatingsEntry service. Represents a row in the &quot;RatingsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see RatingsEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.ratings.model.impl.RatingsEntryImpl"
)
@ProviderType
public interface RatingsEntry extends PersistedModel, RatingsEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.ratings.model.impl.RatingsEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RatingsEntry, Long> ENTRY_ID_ACCESSOR =
		new Accessor<RatingsEntry, Long>() {

			@Override
			public Long get(RatingsEntry ratingsEntry) {
				return ratingsEntry.getEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<RatingsEntry> getTypeClass() {
				return RatingsEntry.class;
			}

		};

}