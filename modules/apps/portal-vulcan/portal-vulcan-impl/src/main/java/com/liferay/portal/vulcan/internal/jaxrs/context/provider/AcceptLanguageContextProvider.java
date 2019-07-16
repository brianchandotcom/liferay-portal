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

package com.liferay.portal.vulcan.internal.jaxrs.context.provider;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.internal.accept.language.AcceptLanguageImpl;

import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Cristina González
 */
@Provider
public class AcceptLanguageContextProvider
	implements ContextProvider<AcceptLanguage> {

	public AcceptLanguageContextProvider(Language language, Portal portal) {
		_language = language;
		_portal = portal;
	}

	@Override
	public AcceptLanguage createContext(Message message) {
		return new AcceptLanguageImpl(
			ContextProviderUtil.getHttpServletRequest(message), _language,
			_portal);
	}

	private final Language _language;
	private final Portal _portal;

}