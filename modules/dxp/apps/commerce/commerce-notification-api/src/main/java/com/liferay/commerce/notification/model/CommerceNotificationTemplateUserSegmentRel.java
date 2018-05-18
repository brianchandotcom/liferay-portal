/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

	public static final Accessor<CommerceNotificationTemplateUserSegmentRel, Long> COMMERCE_USER_SEGMENT_ENTRY_ID_ACCESSOR =
		new Accessor<CommerceNotificationTemplateUserSegmentRel, Long>() {
			@Override
			public Long get(
				CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
				return commerceNotificationTemplateUserSegmentRel.getCommerceUserSegmentEntryId();
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