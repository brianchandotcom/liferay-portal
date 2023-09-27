/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the MicroblogsEntry service. Represents a row in the &quot;MicroblogsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MicroblogsEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.microblogs.model.impl.MicroblogsEntryImpl"
)
@ProviderType
public interface MicroblogsEntry extends MicroblogsEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.microblogs.model.impl.MicroblogsEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MicroblogsEntry, Long>
		MICROBLOGS_ENTRY_ID_ACCESSOR = new Accessor<MicroblogsEntry, Long>() {

			@Override
			public Long get(MicroblogsEntry microblogsEntry) {
				return microblogsEntry.getMicroblogsEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MicroblogsEntry> getTypeClass() {
				return MicroblogsEntry.class;
			}

		};

	public long fetchParentMicroblogsEntryUserId();

	public long getParentMicroblogsEntryUserId()
		throws com.liferay.portal.kernel.exception.PortalException;

}