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
 * The extended model interface for the AnnouncementsFlag service. Represents a row in the &quot;AnnouncementsFlag&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsFlagModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.announcements.model.impl.AnnouncementsFlagImpl"
)
@ProviderType
public interface AnnouncementsFlag
	extends AnnouncementsFlagModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.announcements.model.impl.AnnouncementsFlagImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AnnouncementsFlag, Long> FLAG_ID_ACCESSOR =
		new Accessor<AnnouncementsFlag, Long>() {

			@Override
			public Long get(AnnouncementsFlag announcementsFlag) {
				return announcementsFlag.getFlagId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AnnouncementsFlag> getTypeClass() {
				return AnnouncementsFlag.class;
			}

		};

}