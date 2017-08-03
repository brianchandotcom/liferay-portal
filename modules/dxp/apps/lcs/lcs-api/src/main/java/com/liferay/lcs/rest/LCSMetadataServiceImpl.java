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

package com.liferay.lcs.rest;

import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Riccardo Ferrari
 */
public class LCSMetadataServiceImpl
	extends BaseLCSServiceImpl implements LCSMetadataService {

	@Override
	public int getSupportedLCSPortlet(
		String buildNumber, String portalEdition) {

		StringBuilder sb = new StringBuilder(5);

		sb.append(_URL_LCS_METADATA);
		sb.append("/");
		sb.append(buildNumber);
		sb.append("/");
		sb.append(portalEdition);

		Integer supportedLCSPortlet = _supportedLCSPortletMap.get(
			sb.toString());

		if (supportedLCSPortlet == null) {
			try {
				LCSMetadata lcsMetadata = doGetToObject(
					LCSMetadataImpl.class, _URL_LCS_METADATA, "buildNumber",
					buildNumber, "portalEdition", portalEdition);

				_supportedLCSPortletMap.put(
					sb.toString(), lcsMetadata.getSupportedLCSPortlet());

				return lcsMetadata.getSupportedLCSPortlet();
			}
			catch (Exception e) {
				if (e instanceof JSONWebServiceInvocationException) {
					JSONWebServiceInvocationException jsonwsie =
						(JSONWebServiceInvocationException)e;

					if (jsonwsie.getStatus() ==
							HttpServletResponse.SC_NOT_FOUND) {

						return -1;
					}
				}
				else if (e instanceof JSONWebServiceTransportException) {
					JSONWebServiceTransportException jsonwste =
						(JSONWebServiceTransportException)e;

					if (jsonwste.getStatus() ==
							HttpServletResponse.SC_NOT_FOUND) {

						return -1;
					}
				}

				throw new RuntimeException(e);
			}
		}

		return supportedLCSPortlet;
	}

	private static final String _URL_LCS_METADATA =
		"/osb-lcs-portlet/lcs/jsonws/v1_4/LCSMetadata";

	private static final Map<String, Integer> _supportedLCSPortletMap =
		new HashMap<String, Integer>();

}