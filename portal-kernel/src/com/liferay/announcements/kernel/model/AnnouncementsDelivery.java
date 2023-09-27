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
 * The extended model interface for the AnnouncementsDelivery service. Represents a row in the &quot;AnnouncementsDelivery&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsDeliveryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl"
)
@ProviderType
public interface AnnouncementsDelivery
	extends AnnouncementsDeliveryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AnnouncementsDelivery, Long>
		DELIVERY_ID_ACCESSOR = new Accessor<AnnouncementsDelivery, Long>() {

			@Override
			public Long get(AnnouncementsDelivery announcementsDelivery) {
				return announcementsDelivery.getDeliveryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AnnouncementsDelivery> getTypeClass() {
				return AnnouncementsDelivery.class;
			}

		};

}