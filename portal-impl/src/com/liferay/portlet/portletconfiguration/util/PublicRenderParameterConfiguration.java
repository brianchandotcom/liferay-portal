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

package com.liferay.portlet.portletconfiguration.util;

import com.liferay.portal.kernel.model.PublicRenderParameter;
import com.liferay.portal.kernel.portlet.PortletQNameUtil;

/**
 * @author Alberto Montero
 */
public class PublicRenderParameterConfiguration {

	public static final String IGNORE_PREFIX = "lfr-prp-ignore-";

	public static final String MAPPING_PREFIX = "lfr-prp-mapping-";

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getIgnoreKey(String)}
	 */
	@Deprecated
	public static String getIgnoreKey(
		PublicRenderParameter publicRenderParameter) {

		String publicRenderParameterName =
			PortletQNameUtil.getPublicRenderParameterName(
				publicRenderParameter.getQName());

		return IGNORE_PREFIX.concat(publicRenderParameterName);
	}

	public static String getIgnoreKey(String publicRenderParameterName) {
		return IGNORE_PREFIX.concat(publicRenderParameterName);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getMappingKey(String)}
	 */
	@Deprecated
	public static String getMappingKey(
		PublicRenderParameter publicRenderParameter) {

		String publicRenderParameterName =
			PortletQNameUtil.getPublicRenderParameterName(
				publicRenderParameter.getQName());

		return MAPPING_PREFIX.concat(publicRenderParameterName);
	}

	public static String getMappingKey(String publicRenderParameterName) {
		return MAPPING_PREFIX.concat(publicRenderParameterName);
	}

	public PublicRenderParameterConfiguration(
		PublicRenderParameter publicRenderParameter, String mappingValue,
		boolean ignoreValue) {

		_publicRenderParameter = publicRenderParameter;
		_mappingValue = mappingValue;
		_ignoreValue = ignoreValue;

		_publicRenderParameterName =
			PortletQNameUtil.getPublicRenderParameterName(
				publicRenderParameter.getQName());
	}

	public String getIgnoreKey() {
		return IGNORE_PREFIX.concat(_publicRenderParameterName);
	}

	public boolean getIgnoreValue() {
		return _ignoreValue;
	}

	public String getMappingKey() {
		return MAPPING_PREFIX.concat(_publicRenderParameterName);
	}

	public String getMappingValue() {
		return _mappingValue;
	}

	public PublicRenderParameter getPublicRenderParameter() {
		return _publicRenderParameter;
	}

	public String getPublicRenderParameterName() {
		return _publicRenderParameterName;
	}

	private final boolean _ignoreValue;
	private final String _mappingValue;
	private final PublicRenderParameter _publicRenderParameter;
	private final String _publicRenderParameterName;

}