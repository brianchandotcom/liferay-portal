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

package com.liferay.osb.faro.functional.test.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Cheryl Tang
 */
public class FaroPagePool {

	public static final String ACCOUNTS = "/contacts/accounts";

	public static final String API_BASE_URL =
		PropsUtil.get("analytics.cloud.url") + "/o/faro";

	public static final String ASSETS = "/assets";

	public static final String DATA_SOURCE = "/settings/data-source";

	public static final String HOME = "/home";

	public static final String HOME_TITLE = "Liferay Experience Cloud";

	public static final String INDIVIDUALS = "/contacts/individuals";

	public static final String KEYWORDS =
		"/settings/definitions/interest-topics";

	public static final String PAGES = "/pages";

	public static final String PROPERTIES = "/settings/properties";

	public static final String SEGMENTS = "/contacts/segments";

	public static final String SITE_PAGES = "/sites/pages";

	public static final String SITES = "/sites";

	public static final String TOUCHPOINTS = "/web/analytics/touchpoints";

	public static final String USAGE = "/settings/usage";

	public static final String USER_MANAGEMENT = "/settings/users";

	public static final String WORKSPACE = "/settings/workspace";

	public static String getEndpoint(String application, String controller) {
		StringBundler sb = new StringBundler(5);

		sb.append(API_BASE_URL);
		sb.append(application);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(_projectId);
		sb.append(controller);

		return sb.toString();
	}

	public static String getProjectId() {
		return _projectId;
	}

	public static String getPropertyId() {
		return _propertyId;
	}

	public static void setProjectId(String projectId) {
		_projectId = projectId;
	}

	public static void setPropertyId(String propertyId) {
		_propertyId = propertyId;
	}

	/**
	 * Returns the title given a page name
	 *
	 * @param  pageName the name of the desired page
	 * @return the title of the page
	 * @throws Exception if an exception occurred
	 */
	public String getPageTitle(String pageName) throws Exception {
		Class<?> clazz = getClass();

		Stream<Field> stream = Arrays.stream(clazz.getFields());

		pageName = StringUtil.replace(
			pageName, CharPool.SPACE, CharPool.UNDERLINE);

		String titleFieldName = StringUtil.toUpperCase(pageName) + "_TITLE";

		boolean matchedField = stream.anyMatch(
			field -> titleFieldName.equals(field.getName()));

		if (!matchedField) {
			return pageName;
		}

		Field field = clazz.getDeclaredField(titleFieldName);

		return (String)field.get(clazz);
	}

	/**
	 * Returns the url path for a given page.
	 *
	 * @param  pageName the name of the desired page
	 * @return the url path of a page
	 * @throws Exception if an exception occurred
	 */
	public String getPageUrlPath(String pageName) throws Exception {
		Class<?> clazz = getClass();

		pageName = StringUtil.replace(
			pageName, CharPool.SPACE, CharPool.UNDERLINE);

		Field field = clazz.getDeclaredField(StringUtil.toUpperCase(pageName));

		return (String)field.get(clazz);
	}

	private static String _projectId;
	private static String _propertyId;

}