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

package com.liferay.portal.template.xsl.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.xsl.XSLURIResolver;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;

/**
 * @author Marta Medio
 */
public class XSLSecureURIResolver implements XSLURIResolver {

	public XSLSecureURIResolver(XSLURIResolver xsluriResolver) {
		_xsluriResolver = xsluriResolver;
	}

	@Override
	public String getLanguageId() {
		if (_xsluriResolver != null) {
			return _xsluriResolver.getLanguageId();
		}

		return null;
	}

	@Override
	public Source resolve(String href, String base)
		throws TransformerException {

		try {
			URL url = new URL(href);

			if (InetAddressUtil.isLocalInetAddress(
					InetAddressUtil.getInetAddressByName(url.getHost()))) {

				throw new TransformerException(
					StringBundler.concat(
						"Denied access to resource: ", href,
						". Access to local network is disabled by the secure ",
						"processing feature."));
			}
			else if (_xsluriResolver != null) {
				return _xsluriResolver.resolve(href, base);
			}

			return null;
		}
		catch (MalformedURLException | UnknownHostException e) {
			throw new TransformerException(
				"Unable to resolve URL reference", e);
		}
	}

	private final XSLURIResolver _xsluriResolver;

}