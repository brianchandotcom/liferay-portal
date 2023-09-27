/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AnnouncementsEntry service. Represents a row in the &quot;AnnouncementsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.announcements.model.impl.AnnouncementsEntryImpl"
)
@ProviderType
public interface AnnouncementsEntry
	extends AnnouncementsEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.announcements.model.impl.AnnouncementsEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AnnouncementsEntry, Long> ENTRY_ID_ACCESSOR =
		new Accessor<AnnouncementsEntry, Long>() {

			@Override
			public Long get(AnnouncementsEntry announcementsEntry) {
				return announcementsEntry.getEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AnnouncementsEntry> getTypeClass() {
				return AnnouncementsEntry.class;
			}

		};

	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException;

}