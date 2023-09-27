/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static String getIgnoreKey(String publicRenderParameterName) {
		return IGNORE_PREFIX.concat(publicRenderParameterName);
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