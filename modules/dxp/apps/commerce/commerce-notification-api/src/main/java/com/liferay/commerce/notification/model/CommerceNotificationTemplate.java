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
 * The extended model interface for the CommerceNotificationTemplate service. Represents a row in the &quot;CommerceNotificationTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateModel
 * @see com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateImpl
 * @see com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateImpl")
@ProviderType
public interface CommerceNotificationTemplate
	extends CommerceNotificationTemplateModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceNotificationTemplate, Long> COMMERCE_NOTIFICATION_TEMPLATE_ID_ACCESSOR =
		new Accessor<CommerceNotificationTemplate, Long>() {
			@Override
			public Long get(
				CommerceNotificationTemplate commerceNotificationTemplate) {
				return commerceNotificationTemplate.getCommerceNotificationTemplateId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceNotificationTemplate> getTypeClass() {
				return CommerceNotificationTemplate.class;
			}
		};
}