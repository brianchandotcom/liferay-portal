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

package com.liferay.dynamic.data.mapping.web.internal.portlet;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.constants.DDMWebKeys;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException;
import com.liferay.dynamic.data.mapping.exception.RequiredStructureException;
import com.liferay.dynamic.data.mapping.exception.RequiredTemplateException;
import com.liferay.dynamic.data.mapping.exception.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.exception.StructureDuplicateElementException;
import com.liferay.dynamic.data.mapping.exception.StructureNameException;
import com.liferay.dynamic.data.mapping.exception.TemplateNameException;
import com.liferay.dynamic.data.mapping.exception.TemplateScriptException;
import com.liferay.dynamic.data.mapping.exception.TemplateSmallImageContentException;
import com.liferay.dynamic.data.mapping.exception.TemplateSmallImageNameException;
import com.liferay.dynamic.data.mapping.exception.TemplateSmallImageSizeException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateService;
import com.liferay.dynamic.data.mapping.storage.StorageAdapterRegistry;
import com.liferay.dynamic.data.mapping.util.DDMDisplayRegistry;
import com.liferay.dynamic.data.mapping.util.DDMTemplateHelper;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustNotDuplicateFieldName;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetOptionsForField;
import com.liferay.dynamic.data.mapping.validator.DDMFormValidationException.MustSetValidCharactersForFieldName;
import com.liferay.dynamic.data.mapping.web.internal.configuration.DDMWebConfiguration;
import com.liferay.dynamic.data.mapping.web.internal.display.context.DDMDisplayContext;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.PortletPreferencesException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.taglib.DynamicIncludeUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	configurationPid = "com.liferay.dynamic.data.mapping.web.internal.configuration.DDMWebConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.autopropagated-parameters=backURL",
		"com.liferay.portlet.autopropagated-parameters=navigationStartsOn",
		"com.liferay.portlet.autopropagated-parameters=refererPortletName",
		"com.liferay.portlet.autopropagated-parameters=refererWebDAVToken",
		"com.liferay.portlet.autopropagated-parameters=scopeTitle",
		"com.liferay.portlet.autopropagated-parameters=showAncestorScopes",
		"com.liferay.portlet.autopropagated-parameters=showBackURL",
		"com.liferay.portlet.autopropagated-parameters=showCacheableInput",
		"com.liferay.portlet.autopropagated-parameters=showHeader",
		"com.liferay.portlet.autopropagated-parameters=showManageTemplates",
		"com.liferay.portlet.css-class-wrapper=portlet-dynamic-data-mapping",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.header-portlet-javascript=/js/custom_fields.js",
		"com.liferay.portlet.header-portlet-javascript=/js/main.js",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Dynamic Data Mapping Web",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.always-send-redirect=true",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class DDMPortlet extends MVCPortlet {

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		try {
			super.processAction(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchStructureException ||
				e instanceof NoSuchTemplateException ||
				e instanceof PortletPreferencesException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				include("/error.jsp", actionRequest, actionResponse);
			}
			else if (e instanceof DDMFormLayoutValidationException ||
					 e instanceof DDMFormValidationException ||
					 e instanceof LocaleException ||
					 e instanceof MustNotDuplicateFieldName ||
					 e instanceof MustSetOptionsForField ||
					 e instanceof MustSetValidCharactersForFieldName ||
					 e instanceof RequiredStructureException ||
					 e instanceof RequiredTemplateException ||
					 e instanceof StructureDefinitionException ||
					 e instanceof StructureDuplicateElementException ||
					 e instanceof StructureNameException ||
					 e instanceof TemplateNameException ||
					 e instanceof TemplateNameException ||
					 e instanceof TemplateScriptException ||
					 e instanceof TemplateSmallImageContentException ||
					 e instanceof TemplateSmallImageNameException ||
					 e instanceof TemplateSmallImageSizeException) {

				SessionErrors.add(actionRequest, e.getClass(), e);

				if (e instanceof RequiredStructureException ||
					e instanceof RequiredTemplateException) {

					String redirect = portal.escapeRedirect(
						ParamUtil.getString(actionRequest, "redirect"));

					if (Validator.isNotNull(redirect)) {
						actionResponse.sendRedirect(redirect);
					}
				}
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			setDDMDisplayContextRequestAttribute(renderRequest, renderResponse);

			setDDMTemplateRequestAttribute(renderRequest);

			setDDMStructureRequestAttribute(renderRequest);
		}
		catch (NoSuchStructureException | NoSuchTemplateException e) {

			// Let this slide because the user can manually input a structure
			// or template key for a new model that does not yet exist

			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
		catch (Exception e) {
			if (e instanceof PortletPreferencesException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				include("/error.jsp", renderRequest, renderResponse);
			}
			else {
				throw new PortletException(e);
			}
		}

		DynamicIncludeUtil.include(
			portal.getHttpServletRequest(renderRequest),
			portal.getHttpServletResponse(renderResponse),
			DDMPortlet.class.getName() + "#formRendered", true);

		super.render(renderRequest, renderResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		ddmWebConfiguration = ConfigurableUtil.createConfigurable(
			DDMWebConfiguration.class, properties);
	}

	protected void setDDMDisplayContextRequestAttribute(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		DDMDisplayContext ddmDisplayContext = new DDMDisplayContext(
			renderRequest, renderResponse, _ddmDisplayRegistry,
			ddmStructureLinkLocalService, ddmStructureService,
			_ddmTemplateHelper, ddmTemplateService, ddmWebConfiguration,
			_storageAdapterRegistry);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, ddmDisplayContext);
	}

	@Reference(unbind = "-")
	protected void setDDMDisplayRegistry(
		DDMDisplayRegistry ddmDisplayRegistry) {

		_ddmDisplayRegistry = ddmDisplayRegistry;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLinkLocalService(
		DDMStructureLinkLocalService ddmStructureLinkLocalService) {

		this.ddmStructureLinkLocalService = ddmStructureLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		this.ddmStructureLocalService = ddmStructureLocalService;
	}

	protected void setDDMStructureRequestAttribute(RenderRequest renderRequest)
		throws PortalException {

		long classNameId = ParamUtil.getLong(renderRequest, "classNameId");
		long classPK = ParamUtil.getLong(renderRequest, "classPK");

		if ((classNameId > 0) && (classPK > 0)) {
			DDMStructure structure = null;

			long structureClassNameId = portal.getClassNameId(
				DDMStructure.class);

			if ((structureClassNameId == classNameId) && (classPK > 0)) {
				structure = ddmStructureLocalService.getStructure(classPK);
			}

			renderRequest.setAttribute(
				DDMWebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE, structure);
		}
	}

	@Reference(unbind = "-")
	protected void setDDMStructureService(
		DDMStructureService ddmStructureService) {

		this.ddmStructureService = ddmStructureService;
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateHelper(DDMTemplateHelper ddmTemplateHelper) {
		_ddmTemplateHelper = ddmTemplateHelper;
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		this.ddmTemplateLocalService = ddmTemplateLocalService;
	}

	protected void setDDMTemplateRequestAttribute(RenderRequest renderRequest)
		throws PortalException {

		long templateId = ParamUtil.getLong(renderRequest, "templateId");

		if (templateId > 0) {
			DDMTemplate template = ddmTemplateLocalService.getDDMTemplate(
				templateId);

			renderRequest.setAttribute(
				DDMWebKeys.DYNAMIC_DATA_MAPPING_TEMPLATE, template);
		}
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateService(
		DDMTemplateService ddmTemplateService) {

		this.ddmTemplateService = ddmTemplateService;
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.dynamic.data.mapping.web)(&(release.schema.version>=1.0.0)(!(release.schema.version>=2.0.0))))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	@Reference(unbind = "-")
	protected void setStorageAdapterRegistry(
		StorageAdapterRegistry storageAdapterRegistry) {

		_storageAdapterRegistry = storageAdapterRegistry;
	}

	protected volatile DDMStructureLinkLocalService
		ddmStructureLinkLocalService;
	protected volatile DDMStructureLocalService ddmStructureLocalService;
	protected volatile DDMStructureService ddmStructureService;
	protected volatile DDMTemplateLocalService ddmTemplateLocalService;
	protected volatile DDMTemplateService ddmTemplateService;
	protected volatile DDMWebConfiguration ddmWebConfiguration;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(DDMPortlet.class);

	private DDMDisplayRegistry _ddmDisplayRegistry;
	private DDMTemplateHelper _ddmTemplateHelper;
	private StorageAdapterRegistry _storageAdapterRegistry;

}