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

package com.liferay.commerce.product.type.simple.internal;

import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.commerce.product.content.web.configuration.CPContentConfigurationHelper;
import com.liferay.commerce.product.content.web.display.context.CPTypeDisplayContext;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueService;
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.commerce.product.type.CPTypeRenderer;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.commerce.product.util.CPContentContributorRegistry;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = "commerce.product.type.name=" + SimpleCPTypeConstants.NAME,
	service = CPTypeRenderer.class
)
public class SimpleCPTypeRenderer implements CPTypeRenderer {

	@Override
	public void render(
			CPDefinition cpDefinition, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CPTypeDisplayContext simpleCPTypeDisplayContext =
			new CPTypeDisplayContext(
				_assetCategoryService, _cpAttachmentFileEntryService,
				_cpContentConfigurationHelper, _cpContentContributorRegistry,
				cpDefinition, _cpInstanceHelper,
				_cpDefinitionSpecificationOptionValueService,
				_cpOptionCategoryService, httpServletRequest, _portal);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, simpleCPTypeDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/render/view.jsp");
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private CPContentConfigurationHelper _cpContentConfigurationHelper;

	@Reference
	private CPContentContributorRegistry _cpContentContributorRegistry;

	@Reference
	private CPDefinitionSpecificationOptionValueService
		_cpDefinitionSpecificationOptionValueService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPOptionCategoryService _cpOptionCategoryService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.type.simple)"
	)
	private ServletContext _servletContext;

}