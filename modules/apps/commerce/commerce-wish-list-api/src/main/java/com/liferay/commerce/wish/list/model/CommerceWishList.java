/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.wish.list.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceWishList service. Represents a row in the &quot;CommerceWishList&quot; database table, with each column mapped to a property of this class.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.wish.list.model.impl.CommerceWishListImpl"
)
@ProviderType
public interface CommerceWishList
	extends CommerceWishListModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.wish.list.model.impl.CommerceWishListImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceWishList, Long>
		COMMERCE_WISH_LIST_ID_ACCESSOR =
			new Accessor<CommerceWishList, Long>() {

				@Override
				public Long get(CommerceWishList commerceWishList) {
					return commerceWishList.getCommerceWishListId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceWishList> getTypeClass() {
					return CommerceWishList.class;
				}

			};

	public boolean isGuestWishList()
		throws com.liferay.portal.kernel.exception.PortalException;

}