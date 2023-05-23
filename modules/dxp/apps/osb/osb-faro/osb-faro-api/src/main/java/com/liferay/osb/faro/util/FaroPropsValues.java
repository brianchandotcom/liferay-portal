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

package com.liferay.osb.faro.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * @author Leilany Ulisses
 * @author Marcos Martins
 */
public class FaroPropsValues {

	public static final String FARO_DEFAULT_WE_DEPLOY_KEY =
		GetterUtil.getString(PropsUtil.get("faro.default.we.deploy.key"));

	public static final String FARO_DEMO_CREATOR_METHOD = GetterUtil.getString(
		PropsUtil.get("faro.demo.creator.method"));

	public static final String FARO_MOCK_OSB_ACCOUNT_ENTRY =
		GetterUtil.getString(PropsUtil.get("faro.mock.osb.account.entry"));

	public static final String FARO_PROJECT_ID = GetterUtil.getString(
		PropsUtil.get("faro.project.id"));

	public static final String FARO_PROJECT_ID_PREFIX = GetterUtil.getString(
		PropsUtil.get("faro.project.id.prefix"));

	public static final String FARO_URL = GetterUtil.getString(
		PropsUtil.get("faro.url"));

	public static final String ISSUES_EMAIL_ADDRESS = GetterUtil.getString(
		PropsUtil.get("issues.email.address"));

	public static final String MOCK_PROJECT_ID = GetterUtil.getString(
		PropsUtil.get("mock.project.id"));

	public static final Integer OSB_API_PORT = GetterUtil.getInteger(
		PropsUtil.get("osb.api.port"));

	public static final String OSB_API_PROTOCOL = GetterUtil.getString(
		PropsUtil.get("osb.api.protocol"));

	public static final String OSB_API_TOKEN = GetterUtil.getString(
		PropsUtil.get("osb.api.token"));

	public static final String OSB_API_URL = GetterUtil.getString(
		PropsUtil.get("osb.api.url"));

	public static final String OSB_ASAH_BACKEND_URL = GetterUtil.getString(
		PropsUtil.get("osb.asah.backend.url"));

	public static final String OSB_ASAH_LOCAL_CLUSTER_URL =
		GetterUtil.getString(PropsUtil.get("osb.asah.local.cluster.url"));

	public static final String OSB_ASAH_PUBLISHER_URL = GetterUtil.getString(
		PropsUtil.get("osb.asah.publisher.url"));

	public static final String OSB_ASAH_SECURITY_TOKEN = GetterUtil.getString(
		PropsUtil.get("osb.asah.security.token"));

	public static final String OSB_ASAH_TOKEN = GetterUtil.getString(
		PropsUtil.get("osb.asah.token"));

	public static final boolean OSB_FARO_ANTIVIRUS_ENABLED =
		GetterUtil.getBoolean(PropsUtil.get("osb.faro.antivirus.enabled"));

	public static final String OSB_FARO_CLAMAV_HOSTNAME = GetterUtil.getString(
		PropsUtil.get("osb.faro.clamav.hostname"));

	public static final Integer OSB_FARO_CLAMAV_PORT = GetterUtil.getInteger(
		PropsUtil.get("osb.faro.clamav.port"));

	public static final Integer OSB_FARO_CLAMAV_TIMEOUT = GetterUtil.getInteger(
		PropsUtil.get("osb.faro.clamav.timeout"));

}