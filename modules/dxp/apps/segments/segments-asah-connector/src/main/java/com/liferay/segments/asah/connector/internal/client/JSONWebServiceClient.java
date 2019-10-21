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

package com.liferay.segments.asah.connector.internal.client;

import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

/**
 * @author David Arques
 */
public interface JSONWebServiceClient {

	public String doDelete(
		String url, Map<String, String> parameters,
		Map<String, String> headers);

	public String doGet(
		String url, MultivaluedMap<String, Object> parameters,
		Map<String, String> headers);

	public <T> void doPatch(String url, T object, Map<String, String> headers);

	public <T, V> V doPost(
		Class<V> clazz, String url, T object, Map<String, String> headers);

	public <T> void doPut(String url, T object, Map<String, String> headers);

	public String getBaseURI();

	public void setBaseURI(String baseURI);

}