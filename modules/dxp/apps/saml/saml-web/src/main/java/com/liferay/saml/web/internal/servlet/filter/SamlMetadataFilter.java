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

package com.liferay.saml.web.internal.servlet.filter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.util.SamlHttpRequestUtil;

import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	immediate = true,
	property = {
		"before-filter=Auto Login Filter", "dispatcher=FORWARD",
		"dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=SSO SAML Metadata Filter",
		"url-pattern=/c/portal/saml/metadata"
	},
	service = Filter.class
)
public class SamlMetadataFilter extends BaseFilter {

	@Override
	public boolean isFilterEnabled() {
		if (_samlProviderConfigurationHelper.isEnabled()) {
			return true;
		}

		return false;
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		httpServletResponse.setContentType(ContentTypes.TEXT_XML);

		PrintWriter printWriter = httpServletResponse.getWriter();

		String metadata = _samlHttpRequestUtil.getEntityDescriptorString(
			httpServletRequest);

		printWriter.print(metadata);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SamlMetadataFilter.class);

	@Reference
	private SamlHttpRequestUtil _samlHttpRequestUtil;

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

}