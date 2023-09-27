/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceNotificationTemplate service. Represents a row in the &quot;CommerceNotificationTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateImpl"
)
@ProviderType
public interface CommerceNotificationTemplate
	extends CommerceNotificationTemplateModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceNotificationTemplate, Long>
		COMMERCE_NOTIFICATION_TEMPLATE_ID_ACCESSOR =
			new Accessor<CommerceNotificationTemplate, Long>() {

				@Override
				public Long get(
					CommerceNotificationTemplate commerceNotificationTemplate) {

					return commerceNotificationTemplate.
						getCommerceNotificationTemplateId();
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