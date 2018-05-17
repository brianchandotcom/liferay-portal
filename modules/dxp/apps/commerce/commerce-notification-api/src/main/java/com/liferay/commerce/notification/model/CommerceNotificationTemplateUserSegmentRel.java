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

package com.liferay.commerce.notification.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceNotificationTemplateUserSegmentRel service. Represents a row in the &quot;CNTemplateUserSegmentRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateUserSegmentRelModel
 * @see com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateUserSegmentRelImpl
 * @see com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateUserSegmentRelModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateUserSegmentRelImpl")
@ProviderType
public interface CommerceNotificationTemplateUserSegmentRel
	extends CommerceNotificationTemplateUserSegmentRelModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateUserSegmentRelImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceNotificationTemplateUserSegmentRel, Long> COMMERCE_NOTIFICATION_TEMPLATE_USER_SEGMENT_REL_ID_ACCESSOR =
		new Accessor<CommerceNotificationTemplateUserSegmentRel, Long>() {
			@Override
			public Long get(
				CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
				return commerceNotificationTemplateUserSegmentRel.getCommerceNotificationTemplateUserSegmentRelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceNotificationTemplateUserSegmentRel> getTypeClass() {
				return CommerceNotificationTemplateUserSegmentRel.class;
			}
		};
}