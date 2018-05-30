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

package com.liferay.commerce.wish.list.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceWishList service. Represents a row in the &quot;CommerceWishList&quot; database table, with each column mapped to a property of this class.
 *
 * @author Andrea Di Giorgi
 * @see CommerceWishListModel
 * @see com.liferay.commerce.wish.list.model.impl.CommerceWishListImpl
 * @see com.liferay.commerce.wish.list.model.impl.CommerceWishListModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.wish.list.model.impl.CommerceWishListImpl")
@ProviderType
public interface CommerceWishList extends CommerceWishListModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.wish.list.model.impl.CommerceWishListImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceWishList, Long> COMMERCE_WISH_LIST_ID_ACCESSOR =
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