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

package com.liferay.portlet.announcements.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the AnnouncementsDelivery service. Represents a row in the &quot;AnnouncementsDelivery&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsDeliveryModel
 * @see com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl
 * @see com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryModelImpl
 * @generated
 */
@ProviderType
public interface AnnouncementsDelivery extends AnnouncementsDeliveryModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.announcements.model.impl.AnnouncementsDeliveryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AnnouncementsDelivery, Long> DELIVERY_ID_ACCESSOR =
		new Accessor<AnnouncementsDelivery, Long>() {
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