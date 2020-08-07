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

package com.liferay.commerce.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceSubscriptionEntry service. Represents a row in the &quot;CommerceSubscriptionEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceSubscriptionEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.model.impl.CommerceSubscriptionEntryImpl"
)
@ProviderType
public interface CommerceSubscriptionEntry
	extends CommerceSubscriptionEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.model.impl.CommerceSubscriptionEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceSubscriptionEntry, Long>
		COMMERCE_SUBSCRIPTION_ENTRY_ID_ACCESSOR =
			new Accessor<CommerceSubscriptionEntry, Long>() {

				@Override
				public Long get(
					CommerceSubscriptionEntry commerceSubscriptionEntry) {

					return commerceSubscriptionEntry.
						getCommerceSubscriptionEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceSubscriptionEntry> getTypeClass() {
					return CommerceSubscriptionEntry.class;
				}

			};

	public CommerceOrderItem fetchCommerceOrderItem();

	public com.liferay.commerce.product.model.CPDefinition fetchCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.product.model.CPInstance fetchCPInstance();

	public long getCPDefinitionId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getCPInstanceId();

	public com.liferay.portal.kernel.util.UnicodeProperties
		getDeliverySubscriptionTypeSettingsProperties();

	public com.liferay.portal.kernel.util.UnicodeProperties
		getSubscriptionTypeSettingsProperties();

	public void setDeliverySubscriptionTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			deliverySubscriptionTypeSettingsUnicodeProperties);

	public void setSubscriptionTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			subscriptionTypeSettingsUnicodeProperties);

}