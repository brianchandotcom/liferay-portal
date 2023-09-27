/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

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
		getTypeSettingsUnicodeProperties();

	public void setTypeSettingsUnicodeProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			typeSettingsUnicodeProperties);

}