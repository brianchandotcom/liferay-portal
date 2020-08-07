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

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CPInstance service. Represents a row in the &quot;CPInstance&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPInstanceModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPInstanceImpl"
)
@ProviderType
public interface CPInstance extends CPInstanceModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPInstanceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPInstance, Long> CP_INSTANCE_ID_ACCESSOR =
		new Accessor<CPInstance, Long>() {

			@Override
			public Long get(CPInstance cpInstance) {
				return cpInstance.getCPInstanceId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPInstance> getTypeClass() {
				return CPInstance.class;
			}

		};

	public CommerceCatalog getCommerceCatalog()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CPSubscriptionInfo getCPSubscriptionInfo()
		throws com.liferay.portal.kernel.exception.PortalException;

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