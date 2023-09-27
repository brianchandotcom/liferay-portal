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
 * The extended model interface for the CommerceNotificationTemplateCommerceAccountGroupRel service. Represents a row in the &quot;CNTemplateCAccountGroupRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateCommerceAccountGroupRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateCommerceAccountGroupRelImpl"
)
@ProviderType
public interface CommerceNotificationTemplateCommerceAccountGroupRel
	extends CommerceNotificationTemplateCommerceAccountGroupRelModel,
			PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateCommerceAccountGroupRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor
		<CommerceNotificationTemplateCommerceAccountGroupRel, Long>
			COMMERCE_NOTIFICATION_TEMPLATE_COMMERCE_ACCOUNT_GROUP_REL_ID_ACCESSOR =
				new Accessor
					<CommerceNotificationTemplateCommerceAccountGroupRel,
					 Long>() {

					@Override
					public Long get(
						CommerceNotificationTemplateCommerceAccountGroupRel
							commerceNotificationTemplateCommerceAccountGroupRel) {

						return commerceNotificationTemplateCommerceAccountGroupRel.
							getCommerceNotificationTemplateCommerceAccountGroupRelId();
					}

					@Override
					public Class<Long> getAttributeClass() {
						return Long.class;
					}

					@Override
					public Class
						<CommerceNotificationTemplateCommerceAccountGroupRel>
							getTypeClass() {

						return CommerceNotificationTemplateCommerceAccountGroupRel.class;
					}

				};
	public static final Accessor
		<CommerceNotificationTemplateCommerceAccountGroupRel, Long>
			COMMERCE_ACCOUNT_GROUP_ID_ACCESSOR =
				new Accessor
					<CommerceNotificationTemplateCommerceAccountGroupRel,
					 Long>() {

					@Override
					public Long get(
						CommerceNotificationTemplateCommerceAccountGroupRel
							commerceNotificationTemplateCommerceAccountGroupRel) {

						return commerceNotificationTemplateCommerceAccountGroupRel.
							getCommerceAccountGroupId();
					}

					@Override
					public Class<Long> getAttributeClass() {
						return Long.class;
					}

					@Override
					public Class
						<CommerceNotificationTemplateCommerceAccountGroupRel>
							getTypeClass() {

						return CommerceNotificationTemplateCommerceAccountGroupRel.class;
					}

				};

}