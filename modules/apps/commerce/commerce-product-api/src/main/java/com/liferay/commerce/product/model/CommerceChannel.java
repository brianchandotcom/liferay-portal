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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceChannel service. Represents a row in the &quot;CommerceChannel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceChannelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CommerceChannelImpl"
)
@ProviderType
public interface CommerceChannel extends CommerceChannelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CommerceChannelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceChannel, Long>
		COMMERCE_CHANNEL_ID_ACCESSOR = new Accessor<CommerceChannel, Long>() {

			@Override
			public Long get(CommerceChannel commerceChannel) {
				return commerceChannel.getCommerceChannelId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceChannel> getTypeClass() {
				return CommerceChannel.class;
			}

		};

	public com.liferay.portal.kernel.model.Group getGroup();

	public long getGroupId();

	public com.liferay.portal.kernel.util.UnicodeProperties
		getTypeSettingsProperties();

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			typeSettingsProperties);

}