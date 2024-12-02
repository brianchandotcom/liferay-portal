/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.FragmentComposition;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.resource.v1_0.PageElementResource;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortField;
import com.liferay.portal.odata.sort.SortParser;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.ActionUtil;

import java.io.Serializable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
@javax.ws.rs.Path("/v1.0")
public abstract class BasePageElementResourceImpl
	implements EntityModelResource, PageElementResource,
			   VulcanBatchEngineTaskItemDelegate<PageElement> {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-site/v1.0/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves all the page elements within an experience in a page specification of a site page."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageSpecificationExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageExperienceExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "fields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "flatten"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "nestedFields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "restrictFields"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "PageElement")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path(
		"/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<PageElement>
			getSiteSiteByExternalReferenceCodePageExperiencePageElementsPage(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("siteExternalReferenceCode")
				String siteExternalReferenceCode,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("pageSpecificationExternalReferenceCode")
				String pageSpecificationExternalReferenceCode,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("pageExperienceExternalReferenceCode")
				String pageExperienceExternalReferenceCode,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.ws.rs.QueryParam("flatten")
				Boolean flatten)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-site/v1.0/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements' -d $'{"definition": ___, "externalReferenceCode": ___, "pageElements": ___, "parentExternalReferenceCode": ___, "position": ___, "type": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Adds a new page element to an experience in a page specification in draft status of a site page."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageSpecificationExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageExperienceExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "fields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "nestedFields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "restrictFields"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "PageElement")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path(
		"/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements"
	)
	@javax.ws.rs.POST
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public PageElement
			postSiteSiteByExternalReferenceCodePageExperiencePageElement(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("siteExternalReferenceCode")
				String siteExternalReferenceCode,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("pageSpecificationExternalReferenceCode")
				String pageSpecificationExternalReferenceCode,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("pageExperienceExternalReferenceCode")
				String pageExperienceExternalReferenceCode,
				PageElement pageElement)
		throws Exception {

		return new PageElement();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'DELETE' 'http://localhost:8080/o/headless-admin-site/v1.0/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements/{pageElementExternalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Deletes a page element within an experience of a specific page specification of a site page within a site."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageSpecificationExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageExperienceExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageElementExternalReferenceCode"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "PageElement")}
	)
	@javax.ws.rs.DELETE
	@javax.ws.rs.Path(
		"/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements/{pageElementExternalReferenceCode}"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public void deleteSiteSiteByExternalReferenceCodePageElement(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteExternalReferenceCode")
			String siteExternalReferenceCode,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("pageSpecificationExternalReferenceCode")
			String pageSpecificationExternalReferenceCode,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("pageExperienceExternalReferenceCode")
			String pageExperienceExternalReferenceCode,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("pageElementExternalReferenceCode")
			String pageElementExternalReferenceCode)
		throws Exception {
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-site/v1.0/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements/{pageElementExternalReferenceCode}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves a page element within an experience of a specific page specification of a site page within a site."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageSpecificationExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageExperienceExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageElementExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "fields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "nestedFields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "restrictFields"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "PageElement")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path(
		"/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements/{pageElementExternalReferenceCode}"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public PageElement getSiteSiteByExternalReferenceCodePageElement(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteExternalReferenceCode")
			String siteExternalReferenceCode,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("pageSpecificationExternalReferenceCode")
			String pageSpecificationExternalReferenceCode,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("pageExperienceExternalReferenceCode")
			String pageExperienceExternalReferenceCode,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("pageElementExternalReferenceCode")
			String pageElementExternalReferenceCode)
		throws Exception {

		return new PageElement();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PATCH' 'http://localhost:8080/o/headless-admin-site/v1.0/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements/{pageElementExternalReferenceCode}' -d $'{"definition": ___, "externalReferenceCode": ___, "pageElements": ___, "parentExternalReferenceCode": ___, "position": ___, "type": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Updates a page element within an experience of a specific page specification of a site page within a site. Updates only the fields received in the request body, leaving any other fields untouched."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageSpecificationExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageExperienceExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageElementExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "fields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "nestedFields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "restrictFields"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "PageElement")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.PATCH
	@javax.ws.rs.Path(
		"/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements/{pageElementExternalReferenceCode}"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public PageElement patchSiteSiteByExternalReferenceCodePageElement(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteExternalReferenceCode")
			String siteExternalReferenceCode,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("pageSpecificationExternalReferenceCode")
			String pageSpecificationExternalReferenceCode,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("pageExperienceExternalReferenceCode")
			String pageExperienceExternalReferenceCode,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("pageElementExternalReferenceCode")
			String pageElementExternalReferenceCode,
			PageElement pageElement)
		throws Exception {

		PageElement existingPageElement =
			getSiteSiteByExternalReferenceCodePageElement(
				siteExternalReferenceCode,
				pageSpecificationExternalReferenceCode,
				pageExperienceExternalReferenceCode,
				pageElementExternalReferenceCode);

		if (pageElement.getDefinition() != null) {
			existingPageElement.setDefinition(pageElement.getDefinition());
		}

		if (pageElement.getExternalReferenceCode() != null) {
			existingPageElement.setExternalReferenceCode(
				pageElement.getExternalReferenceCode());
		}

		if (pageElement.getParentExternalReferenceCode() != null) {
			existingPageElement.setParentExternalReferenceCode(
				pageElement.getParentExternalReferenceCode());
		}

		if (pageElement.getPosition() != null) {
			existingPageElement.setPosition(pageElement.getPosition());
		}

		if (pageElement.getType() != null) {
			existingPageElement.setType(pageElement.getType());
		}

		preparePatch(pageElement, existingPageElement);

		return putSiteSiteByExternalReferenceCodePageElement(
			siteExternalReferenceCode, pageSpecificationExternalReferenceCode,
			pageExperienceExternalReferenceCode,
			pageElementExternalReferenceCode, existingPageElement);
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'PUT' 'http://localhost:8080/o/headless-admin-site/v1.0/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements/{pageElementExternalReferenceCode}' -d $'{"definition": ___, "externalReferenceCode": ___, "pageElements": ___, "parentExternalReferenceCode": ___, "position": ___, "type": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Updates a page element within an experience of a specific page specification of a site page within a site."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageSpecificationExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageExperienceExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageElementExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "fields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "nestedFields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "restrictFields"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "PageElement")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path(
		"/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements/{pageElementExternalReferenceCode}"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@javax.ws.rs.PUT
	@Override
	public PageElement putSiteSiteByExternalReferenceCodePageElement(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("siteExternalReferenceCode")
			String siteExternalReferenceCode,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("pageSpecificationExternalReferenceCode")
			String pageSpecificationExternalReferenceCode,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("pageExperienceExternalReferenceCode")
			String pageExperienceExternalReferenceCode,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("pageElementExternalReferenceCode")
			String pageElementExternalReferenceCode,
			PageElement pageElement)
		throws Exception {

		return new PageElement();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/headless-admin-site/v1.0/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements/{pageElementExternalReferenceCode}/fragment-compositions' -d $'{"creatorExternalReferenceCode": ___, "dateCreated": ___, "dateModified": ___, "datePublished": ___, "description": ___, "externalReferenceCode": ___, "fragmentSetExternalReferenceCode": ___, "key": ___, "name": ___, "pageElement": ___, "thumbnail": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Adds a new fragment composition under a page element of an experience in a page specification of a site page. If successful, the response will contain the page element in which the fragment composition is converted."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageSpecificationExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageExperienceExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageElementExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "fields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "nestedFields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "position"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "restrictFields"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "PageElement")}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path(
		"/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements/{pageElementExternalReferenceCode}/fragment-compositions"
	)
	@javax.ws.rs.POST
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public PageElement
			postSiteSiteByExternalReferenceCodePageElementFragmentComposition(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("siteExternalReferenceCode")
				String siteExternalReferenceCode,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("pageSpecificationExternalReferenceCode")
				String pageSpecificationExternalReferenceCode,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("pageExperienceExternalReferenceCode")
				String pageExperienceExternalReferenceCode,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("pageElementExternalReferenceCode")
				String pageElementExternalReferenceCode,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.ws.rs.QueryParam("position")
				Integer position,
				FragmentComposition fragmentComposition)
		throws Exception {

		return new PageElement();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/headless-admin-site/v1.0/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements/{pageElementExternalReferenceCode}/page-elements'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Retrieves all the descendant page elements of a page element within an experience in a page specification of a site page."
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "siteExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageSpecificationExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageExperienceExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "pageElementExternalReferenceCode"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "fields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "flatten"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "nestedFields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "restrictFields"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {@io.swagger.v3.oas.annotations.tags.Tag(name = "PageElement")}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path(
		"/sites/{siteExternalReferenceCode}/page-specifications/{pageSpecificationExternalReferenceCode}/page-experiences/{pageExperienceExternalReferenceCode}/page-elements/{pageElementExternalReferenceCode}/page-elements"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Page<PageElement>
			getSiteSiteByExternalReferenceCodePageElementPageElementsPage(
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("siteExternalReferenceCode")
				String siteExternalReferenceCode,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("pageSpecificationExternalReferenceCode")
				String pageSpecificationExternalReferenceCode,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("pageExperienceExternalReferenceCode")
				String pageExperienceExternalReferenceCode,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.validation.constraints.NotNull
				@javax.ws.rs.PathParam("pageElementExternalReferenceCode")
				String pageElementExternalReferenceCode,
				@io.swagger.v3.oas.annotations.Parameter(hidden = true)
				@javax.ws.rs.QueryParam("flatten")
				Boolean flatten)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	@SuppressWarnings("PMD.UnusedLocalVariable")
	public void create(
			Collection<PageElement> pageElements,
			Map<String, Serializable> parameters)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Override
	public void delete(
			Collection<PageElement> pageElements,
			Map<String, Serializable> parameters)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	public Set<String> getAvailableCreateStrategies() {
		return SetUtil.fromArray();
	}

	public Set<String> getAvailableUpdateStrategies() {
		return SetUtil.fromArray();
	}

	@Override
	public EntityModel getEntityModel(Map<String, List<String>> multivaluedMap)
		throws Exception {

		return getEntityModel(
			new MultivaluedHashMap<String, Object>(multivaluedMap));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return null;
	}

	public String getResourceName() {
		return "PageElement";
	}

	public String getVersion() {
		return "v1.0";
	}

	@Override
	public Page<PageElement> read(
			Filter filter, Pagination pagination, Sort[] sorts,
			Map<String, Serializable> parameters, String search)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Override
	public void setLanguageId(String languageId) {
		this.contextAcceptLanguage = new AcceptLanguage() {

			@Override
			public List<Locale> getLocales() {
				return null;
			}

			@Override
			public String getPreferredLanguageId() {
				return languageId;
			}

			@Override
			public Locale getPreferredLocale() {
				return LocaleUtil.fromLanguageId(languageId);
			}

		};
	}

	@Override
	public void update(
			Collection<PageElement> pageElements,
			Map<String, Serializable> parameters)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextBatchUnsafeBiConsumer(
		UnsafeBiConsumer
			<Collection<PageElement>,
			 UnsafeFunction<PageElement, PageElement, Exception>, Exception>
				contextBatchUnsafeBiConsumer) {

		this.contextBatchUnsafeBiConsumer = contextBatchUnsafeBiConsumer;
	}

	public void setContextBatchUnsafeConsumer(
		UnsafeBiConsumer
			<Collection<PageElement>, UnsafeConsumer<PageElement, Exception>,
			 Exception> contextBatchUnsafeConsumer) {

		this.contextBatchUnsafeConsumer = contextBatchUnsafeConsumer;
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany) {

		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {

		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {

		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser) {

		this.contextUser = contextUser;
	}

	public void setExpressionConvert(
		ExpressionConvert<Filter> expressionConvert) {

		this.expressionConvert = expressionConvert;
	}

	public void setFilterParserProvider(
		FilterParserProvider filterParserProvider) {

		this.filterParserProvider = filterParserProvider;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		this.resourceActionLocalService = resourceActionLocalService;
	}

	public void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		this.resourcePermissionLocalService = resourcePermissionLocalService;
	}

	public void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	public void setSortParserProvider(SortParserProvider sortParserProvider) {
		this.sortParserProvider = sortParserProvider;
	}

	public void setVulcanBatchEngineExportTaskResource(
		VulcanBatchEngineExportTaskResource
			vulcanBatchEngineExportTaskResource) {

		this.vulcanBatchEngineExportTaskResource =
			vulcanBatchEngineExportTaskResource;
	}

	public void setVulcanBatchEngineImportTaskResource(
		VulcanBatchEngineImportTaskResource
			vulcanBatchEngineImportTaskResource) {

		this.vulcanBatchEngineImportTaskResource =
			vulcanBatchEngineImportTaskResource;
	}

	@Override
	public Filter toFilter(
		String filterString, Map<String, List<String>> multivaluedMap) {

		try {
			EntityModel entityModel = getEntityModel(multivaluedMap);

			FilterParser filterParser = filterParserProvider.provide(
				entityModel);

			com.liferay.portal.odata.filter.Filter oDataFilter =
				new com.liferay.portal.odata.filter.Filter(
					filterParser.parse(filterString));

			return expressionConvert.convert(
				oDataFilter.getExpression(),
				contextAcceptLanguage.getPreferredLocale(), entityModel);
		}
		catch (Exception exception) {
			_log.error("Invalid filter " + filterString, exception);

			return null;
		}
	}

	@Override
	public Sort[] toSorts(String sortString) {
		if (Validator.isNull(sortString)) {
			return null;
		}

		try {
			SortParser sortParser = sortParserProvider.provide(
				getEntityModel(Collections.emptyMap()));

			if (sortParser == null) {
				return null;
			}

			com.liferay.portal.odata.sort.Sort oDataSort =
				new com.liferay.portal.odata.sort.Sort(
					sortParser.parse(sortString));

			List<SortField> sortFields = oDataSort.getSortFields();

			Sort[] sorts = new Sort[sortFields.size()];

			for (int i = 0; i < sortFields.size(); i++) {
				SortField sortField = sortFields.get(i);

				sorts[i] = new Sort(
					sortField.getSortableFieldName(
						contextAcceptLanguage.getPreferredLocale()),
					!sortField.isAscending());
			}

			return sorts;
		}
		catch (Exception exception) {
			_log.error("Invalid sort " + sortString, exception);

			return new Sort[0];
		}
	}

	protected Map<String, String> addAction(
		String actionName,
		com.liferay.portal.kernel.model.GroupedModel groupedModel,
		String methodName) {

		return ActionUtil.addAction(
			actionName, getClass(), groupedModel, methodName,
			contextScopeChecker, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName, Long ownerId,
		String permissionName, Long siteId) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			ownerId, permissionName, siteId, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName,
		ModelResourcePermission modelResourcePermission) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			modelResourcePermission, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, String methodName, String permissionName,
		Long siteId) {

		return addAction(
			actionName, siteId, methodName, null, permissionName, siteId);
	}

	protected void preparePatch(
		PageElement pageElement, PageElement existingPageElement) {
	}

	protected <T, R, E extends Throwable> List<R> transform(
		Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R, E extends Throwable> R[] transform(
		T[] array, UnsafeFunction<T, R, E> unsafeFunction,
		Class<? extends R> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R, E extends Throwable> R[] transformToArray(
		Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction,
		Class<? extends R> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R, E extends Throwable> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, E> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected <T, R, E extends Throwable> long[] transformToLongArray(
		Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction) {

		return TransformUtil.transformToLongArray(collection, unsafeFunction);
	}

	protected <T, R, E extends Throwable> List<R> unsafeTransform(
			Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction)
		throws E {

		return TransformUtil.unsafeTransform(collection, unsafeFunction);
	}

	protected <T, R, E extends Throwable> R[] unsafeTransform(
			T[] array, UnsafeFunction<T, R, E> unsafeFunction,
			Class<? extends R> clazz)
		throws E {

		return TransformUtil.unsafeTransform(array, unsafeFunction, clazz);
	}

	protected <T, R, E extends Throwable> R[] unsafeTransformToArray(
			Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction,
			Class<? extends R> clazz)
		throws E {

		return TransformUtil.unsafeTransformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R, E extends Throwable> List<R> unsafeTransformToList(
			T[] array, UnsafeFunction<T, R, E> unsafeFunction)
		throws E {

		return TransformUtil.unsafeTransformToList(array, unsafeFunction);
	}

	protected <T, R, E extends Throwable> long[] unsafeTransformToLongArray(
			Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction)
		throws E {

		return TransformUtil.unsafeTransformToLongArray(
			collection, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected UnsafeBiConsumer
		<Collection<PageElement>,
		 UnsafeFunction<PageElement, PageElement, Exception>, Exception>
			contextBatchUnsafeBiConsumer;
	protected UnsafeBiConsumer
		<Collection<PageElement>, UnsafeConsumer<PageElement, Exception>,
		 Exception> contextBatchUnsafeConsumer;
	protected com.liferay.portal.kernel.model.Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;
	protected com.liferay.portal.kernel.model.User contextUser;
	protected ExpressionConvert<Filter> expressionConvert;
	protected FilterParserProvider filterParserProvider;
	protected GroupLocalService groupLocalService;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;
	protected SortParserProvider sortParserProvider;
	protected VulcanBatchEngineExportTaskResource
		vulcanBatchEngineExportTaskResource;
	protected VulcanBatchEngineImportTaskResource
		vulcanBatchEngineImportTaskResource;

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BasePageElementResourceImpl.class);

}