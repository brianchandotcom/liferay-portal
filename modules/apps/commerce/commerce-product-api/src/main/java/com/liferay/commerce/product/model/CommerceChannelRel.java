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
 * The extended model interface for the CommerceChannelRel service. Represents a row in the &quot;CommerceChannelRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceChannelRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CommerceChannelRelImpl"
)
@ProviderType
public interface CommerceChannelRel
	extends CommerceChannelRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CommerceChannelRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceChannelRel, Long>
		COMMERCE_CHANNEL_REL_ID_ACCESSOR =
			new Accessor<CommerceChannelRel, Long>() {

				@Override
				public Long get(CommerceChannelRel commerceChannelRel) {
					return commerceChannelRel.getCommerceChannelRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceChannelRel> getTypeClass() {
					return CommerceChannelRel.class;
				}

			};

	public CommerceChannel getCommerceChannel()
		throws com.liferay.portal.kernel.exception.PortalException;

}