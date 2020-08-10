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

package com.liferay.commerce.frontend.internal.application;

import com.liferay.commerce.frontend.internal.account.CommerceAccountResource;
import com.liferay.commerce.frontend.internal.address.AddressResource;
import com.liferay.commerce.frontend.internal.application.context.provider.PaginationContextProvider;
import com.liferay.commerce.frontend.internal.application.context.provider.SortContextProvider;
import com.liferay.commerce.frontend.internal.application.context.provider.ThemeDisplayContextProvider;
import com.liferay.commerce.frontend.internal.cart.CommerceCartResource;
import com.liferay.commerce.frontend.internal.data.provider.CommerceDataSetDataProviderResource;
import com.liferay.commerce.frontend.internal.search.CommerceSearchResource;
import com.liferay.commerce.frontend.internal.wishlist.CommerceWishListResource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/commerce-ui",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=CommerceUi.Application",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"auth.verifier.guest.allowed=true", "liferay.oauth2=false"
	},
	service = Application.class
)
public class CommerceFrontEndApplication extends Application {

	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();

		singletons.add(_addressResource);
		singletons.add(_commerceAccountResource);
		singletons.add(_commerceCartResource);
		singletons.add(_commerceDataSetDataProviderResource);
		singletons.add(_commerceSearchResource);
		singletons.add(_commerceWishListResource);
		singletons.add(_paginationContextProvider);
		singletons.add(_sortContextProvider);
		singletons.add(_themeDisplayContextProvider);

		return singletons;
	}

	@Reference
	private AddressResource _addressResource;

	@Reference
	private CommerceAccountResource _commerceAccountResource;

	@Reference
	private CommerceCartResource _commerceCartResource;

	@Reference
	private CommerceDataSetDataProviderResource
		_commerceDataSetDataProviderResource;

	@Reference
	private CommerceSearchResource _commerceSearchResource;

	@Reference
	private CommerceWishListResource _commerceWishListResource;

	@Reference
	private PaginationContextProvider _paginationContextProvider;

	@Reference
	private SortContextProvider _sortContextProvider;

	@Reference
	private ThemeDisplayContextProvider _themeDisplayContextProvider;

}