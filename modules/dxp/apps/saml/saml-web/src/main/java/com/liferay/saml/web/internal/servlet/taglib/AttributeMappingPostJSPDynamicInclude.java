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

package com.liferay.saml.web.internal.servlet.taglib;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.saml.opensaml.integration.field.expression.handler.registry.UserFieldExpressionHandlerRegistry;
import com.liferay.saml.opensaml.integration.field.expression.resolver.registry.UserFieldExpressionResolverRegistry;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE,
	service = DynamicInclude.class
)
public class AttributeMappingPostJSPDynamicInclude
	extends BaseJSPDynamicInclude {

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"com.liferay.saml.web#/admin" +
				"/edit_identity_provider_connection.jsp#post");
	}

	@Override
	protected String getJspPath() {
		return "/dynamic_include/saml_attribute_mapping_post.jsp";
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.saml.web)", unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AttributeMappingPostJSPDynamicInclude.class);

	@Reference
	private SamlSpIdpConnectionLocalService _samlSpIdpConnectionLocalService;

	@Reference
	private UserFieldExpressionHandlerRegistry
		_userFieldExpressionHandlerRegistry;

	@Reference
	private UserFieldExpressionResolverRegistry
		_userFieldExpressionResolverRegistry;

}