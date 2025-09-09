/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.graphql.query.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.DisplayPageTemplate;
import com.liferay.headless.admin.site.dto.v1_0.DisplayPageTemplateFolder;
import com.liferay.headless.admin.site.dto.v1_0.FragmentComposition;
import com.liferay.headless.admin.site.dto.v1_0.FriendlyUrlHistory;
import com.liferay.headless.admin.site.dto.v1_0.MasterPage;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.dto.v1_0.PageExperience;
import com.liferay.headless.admin.site.dto.v1_0.PageRule;
import com.liferay.headless.admin.site.dto.v1_0.PageRuleAction;
import com.liferay.headless.admin.site.dto.v1_0.PageRuleCondition;
import com.liferay.headless.admin.site.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.PageTemplate;
import com.liferay.headless.admin.site.dto.v1_0.PageTemplateSet;
import com.liferay.headless.admin.site.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.dto.v1_0.UtilityPage;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPageWidgetInstance;
import com.liferay.headless.admin.site.resource.v1_0.DisplayPageTemplateFolderResource;
import com.liferay.headless.admin.site.resource.v1_0.DisplayPageTemplateResource;
import com.liferay.headless.admin.site.resource.v1_0.FragmentCompositionResource;
import com.liferay.headless.admin.site.resource.v1_0.FriendlyUrlHistoryResource;
import com.liferay.headless.admin.site.resource.v1_0.MasterPageResource;
import com.liferay.headless.admin.site.resource.v1_0.PageElementResource;
import com.liferay.headless.admin.site.resource.v1_0.PageExperienceResource;
import com.liferay.headless.admin.site.resource.v1_0.PageRuleActionResource;
import com.liferay.headless.admin.site.resource.v1_0.PageRuleConditionResource;
import com.liferay.headless.admin.site.resource.v1_0.PageRuleResource;
import com.liferay.headless.admin.site.resource.v1_0.PageSpecificationResource;
import com.liferay.headless.admin.site.resource.v1_0.PageTemplateResource;
import com.liferay.headless.admin.site.resource.v1_0.PageTemplateSetResource;
import com.liferay.headless.admin.site.resource.v1_0.SitePageResource;
import com.liferay.headless.admin.site.resource.v1_0.UtilityPageResource;
import com.liferay.headless.admin.site.resource.v1_0.WidgetPageWidgetInstanceResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.aggregation.Facet;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.annotation.Generated;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.validation.constraints.NotEmpty;

import jakarta.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class Query {

	public static void setDisplayPageTemplateResourceComponentServiceObjects(
		ComponentServiceObjects<DisplayPageTemplateResource>
			displayPageTemplateResourceComponentServiceObjects) {

		_displayPageTemplateResourceComponentServiceObjects =
			displayPageTemplateResourceComponentServiceObjects;
	}

	public static void
		setDisplayPageTemplateFolderResourceComponentServiceObjects(
			ComponentServiceObjects<DisplayPageTemplateFolderResource>
				displayPageTemplateFolderResourceComponentServiceObjects) {

		_displayPageTemplateFolderResourceComponentServiceObjects =
			displayPageTemplateFolderResourceComponentServiceObjects;
	}

	public static void setFragmentCompositionResourceComponentServiceObjects(
		ComponentServiceObjects<FragmentCompositionResource>
			fragmentCompositionResourceComponentServiceObjects) {

		_fragmentCompositionResourceComponentServiceObjects =
			fragmentCompositionResourceComponentServiceObjects;
	}

	public static void setFriendlyUrlHistoryResourceComponentServiceObjects(
		ComponentServiceObjects<FriendlyUrlHistoryResource>
			friendlyUrlHistoryResourceComponentServiceObjects) {

		_friendlyUrlHistoryResourceComponentServiceObjects =
			friendlyUrlHistoryResourceComponentServiceObjects;
	}

	public static void setMasterPageResourceComponentServiceObjects(
		ComponentServiceObjects<MasterPageResource>
			masterPageResourceComponentServiceObjects) {

		_masterPageResourceComponentServiceObjects =
			masterPageResourceComponentServiceObjects;
	}

	public static void setPageElementResourceComponentServiceObjects(
		ComponentServiceObjects<PageElementResource>
			pageElementResourceComponentServiceObjects) {

		_pageElementResourceComponentServiceObjects =
			pageElementResourceComponentServiceObjects;
	}

	public static void setPageExperienceResourceComponentServiceObjects(
		ComponentServiceObjects<PageExperienceResource>
			pageExperienceResourceComponentServiceObjects) {

		_pageExperienceResourceComponentServiceObjects =
			pageExperienceResourceComponentServiceObjects;
	}

	public static void setPageRuleResourceComponentServiceObjects(
		ComponentServiceObjects<PageRuleResource>
			pageRuleResourceComponentServiceObjects) {

		_pageRuleResourceComponentServiceObjects =
			pageRuleResourceComponentServiceObjects;
	}

	public static void setPageRuleActionResourceComponentServiceObjects(
		ComponentServiceObjects<PageRuleActionResource>
			pageRuleActionResourceComponentServiceObjects) {

		_pageRuleActionResourceComponentServiceObjects =
			pageRuleActionResourceComponentServiceObjects;
	}

	public static void setPageRuleConditionResourceComponentServiceObjects(
		ComponentServiceObjects<PageRuleConditionResource>
			pageRuleConditionResourceComponentServiceObjects) {

		_pageRuleConditionResourceComponentServiceObjects =
			pageRuleConditionResourceComponentServiceObjects;
	}

	public static void setPageSpecificationResourceComponentServiceObjects(
		ComponentServiceObjects<PageSpecificationResource>
			pageSpecificationResourceComponentServiceObjects) {

		_pageSpecificationResourceComponentServiceObjects =
			pageSpecificationResourceComponentServiceObjects;
	}

	public static void setPageTemplateResourceComponentServiceObjects(
		ComponentServiceObjects<PageTemplateResource>
			pageTemplateResourceComponentServiceObjects) {

		_pageTemplateResourceComponentServiceObjects =
			pageTemplateResourceComponentServiceObjects;
	}

	public static void setPageTemplateSetResourceComponentServiceObjects(
		ComponentServiceObjects<PageTemplateSetResource>
			pageTemplateSetResourceComponentServiceObjects) {

		_pageTemplateSetResourceComponentServiceObjects =
			pageTemplateSetResourceComponentServiceObjects;
	}

	public static void setSitePageResourceComponentServiceObjects(
		ComponentServiceObjects<SitePageResource>
			sitePageResourceComponentServiceObjects) {

		_sitePageResourceComponentServiceObjects =
			sitePageResourceComponentServiceObjects;
	}

	public static void setUtilityPageResourceComponentServiceObjects(
		ComponentServiceObjects<UtilityPageResource>
			utilityPageResourceComponentServiceObjects) {

		_utilityPageResourceComponentServiceObjects =
			utilityPageResourceComponentServiceObjects;
	}

	public static void
		setWidgetPageWidgetInstanceResourceComponentServiceObjects(
			ComponentServiceObjects<WidgetPageWidgetInstanceResource>
				widgetPageWidgetInstanceResourceComponentServiceObjects) {

		_widgetPageWidgetInstanceResourceComponentServiceObjects =
			widgetPageWidgetInstanceResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {displayPageTemplate(displayPageTemplateExternalReferenceCode: ___, siteExternalReferenceCode: ___){contentTypeReference, creator, creatorExternalReferenceCode, dateCreated, dateModified, datePublished, displayPageTemplateSettings, externalReferenceCode, friendlyUrlHistory, friendlyUrlPath_i18n, key, markedAsDefault, name, pageSpecifications, parentFolder, thumbnail, uuid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a specific display page template of a site."
	)
	public DisplayPageTemplate displayPageTemplate(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("displayPageTemplateExternalReferenceCode") String
				displayPageTemplateExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateResource ->
				displayPageTemplateResource.getSiteDisplayPageTemplate(
					siteExternalReferenceCode,
					displayPageTemplateExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {displayPageTemplateFolderDisplayPageTemplates(displayPageTemplateFolderExternalReferenceCode: ___, flatten: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the display page templates within a display page template folder of a site page."
	)
	public DisplayPageTemplatePage
			displayPageTemplateFolderDisplayPageTemplates(
				@GraphQLName("siteExternalReferenceCode") @NotEmpty String
					siteExternalReferenceCode,
				@GraphQLName("displayPageTemplateFolderExternalReferenceCode")
					String displayPageTemplateFolderExternalReferenceCode,
				@GraphQLName("flatten") Boolean flatten)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateResource -> new DisplayPageTemplatePage(
				displayPageTemplateResource.
					getSiteDisplayPageTemplateFolderDisplayPageTemplatesPage(
						siteExternalReferenceCode,
						displayPageTemplateFolderExternalReferenceCode,
						flatten)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {displayPageTemplatePermissions(displayPageTemplateExternalReferenceCode: ___, roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public DisplayPageTemplatePage displayPageTemplatePermissions(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("displayPageTemplateExternalReferenceCode") String
				displayPageTemplateExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateResource -> new DisplayPageTemplatePage(
				displayPageTemplateResource.
					getSiteDisplayPageTemplatePermissionsPage(
						siteExternalReferenceCode,
						displayPageTemplateExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {displayPageTemplates(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the display page templates of the site"
	)
	public DisplayPageTemplatePage displayPageTemplates(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("aggregation") List<String> aggregations,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateResource -> new DisplayPageTemplatePage(
				displayPageTemplateResource.getSiteDisplayPageTemplatesPage(
					siteExternalReferenceCode, search,
					_aggregationBiFunction.apply(
						displayPageTemplateResource, aggregations),
					_filterBiFunction.apply(
						displayPageTemplateResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						displayPageTemplateResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {displayPageTemplateFolder(displayPageTemplateFolderExternalReferenceCode: ___, siteExternalReferenceCode: ___){creator, creatorExternalReferenceCode, dateCreated, dateModified, description, externalReferenceCode, key, name, parentDisplayPageTemplateFolder, parentDisplayPageTemplateFolderExternalReferenceCode, uuid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a specific display page template folder of a site."
	)
	public DisplayPageTemplateFolder displayPageTemplateFolder(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("displayPageTemplateFolderExternalReferenceCode")
				String displayPageTemplateFolderExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateFolderResource ->
				displayPageTemplateFolderResource.
					getSiteDisplayPageTemplateFolder(
						siteExternalReferenceCode,
						displayPageTemplateFolderExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {displayPageTemplateFolderPermissions(displayPageTemplateFolderExternalReferenceCode: ___, roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public DisplayPageTemplateFolderPage displayPageTemplateFolderPermissions(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("displayPageTemplateFolderExternalReferenceCode")
				String displayPageTemplateFolderExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateFolderResource ->
				new DisplayPageTemplateFolderPage(
					displayPageTemplateFolderResource.
						getSiteDisplayPageTemplateFolderPermissionsPage(
							siteExternalReferenceCode,
							displayPageTemplateFolderExternalReferenceCode,
							roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {displayPageTemplateFolders(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the display page template folders of the site."
	)
	public DisplayPageTemplateFolderPage displayPageTemplateFolders(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("aggregation") List<String> aggregations,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateFolderResource ->
				new DisplayPageTemplateFolderPage(
					displayPageTemplateFolderResource.
						getSiteDisplayPageTemplateFoldersPage(
							siteExternalReferenceCode, search,
							_aggregationBiFunction.apply(
								displayPageTemplateFolderResource,
								aggregations),
							_filterBiFunction.apply(
								displayPageTemplateFolderResource,
								filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								displayPageTemplateFolderResource,
								sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {fragmentComposition(fragmentCompositionExternalReferenceCode: ___, siteExternalReferenceCode: ___){creator, creatorExternalReferenceCode, dateCreated, dateModified, datePublished, description, externalReferenceCode, fragmentSetExternalReferenceCode, key, name, pageElement, thumbnail}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a specific fragment composition of a site."
	)
	public FragmentComposition fragmentComposition(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("fragmentCompositionExternalReferenceCode") String
				fragmentCompositionExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_fragmentCompositionResourceComponentServiceObjects,
			this::_populateResourceContext,
			fragmentCompositionResource ->
				fragmentCompositionResource.getSiteFragmentComposition(
					siteExternalReferenceCode,
					fragmentCompositionExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {fragmentCompositions(filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the fragment compositions of the site."
	)
	public FragmentCompositionPage fragmentCompositions(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_fragmentCompositionResourceComponentServiceObjects,
			this::_populateResourceContext,
			fragmentCompositionResource -> new FragmentCompositionPage(
				fragmentCompositionResource.getSiteFragmentCompositionsPage(
					siteExternalReferenceCode, search,
					_filterBiFunction.apply(
						fragmentCompositionResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						fragmentCompositionResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {displayPageTemplateFriendlyUrlHistory(displayPageTemplateExternalReferenceCode: ___, siteExternalReferenceCode: ___){friendlyUrlPath_i18n}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the history of previously used URLs for a display page template."
	)
	public FriendlyUrlHistory displayPageTemplateFriendlyUrlHistory(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("displayPageTemplateExternalReferenceCode") String
				displayPageTemplateExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_friendlyUrlHistoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			friendlyUrlHistoryResource ->
				friendlyUrlHistoryResource.
					getSiteDisplayPageTemplateFriendlyUrlHistory(
						siteExternalReferenceCode,
						displayPageTemplateExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {sitePageFriendlyUrlHistory(siteExternalReferenceCode: ___, sitePageExternalReferenceCode: ___){friendlyUrlPath_i18n}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the history of previously used URLs for a page."
	)
	public FriendlyUrlHistory sitePageFriendlyUrlHistory(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("sitePageExternalReferenceCode") String
				sitePageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_friendlyUrlHistoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			friendlyUrlHistoryResource ->
				friendlyUrlHistoryResource.getSiteSitePageFriendlyUrlHistory(
					siteExternalReferenceCode, sitePageExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {utilityPageFriendlyUrlHistory(siteExternalReferenceCode: ___, utilityPageExternalReferenceCode: ___){friendlyUrlPath_i18n}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the history of previously used URLs for a utility page."
	)
	public FriendlyUrlHistory utilityPageFriendlyUrlHistory(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("utilityPageExternalReferenceCode") String
				utilityPageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_friendlyUrlHistoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			friendlyUrlHistoryResource ->
				friendlyUrlHistoryResource.getSiteUtilityPageFriendlyUrlHistory(
					siteExternalReferenceCode,
					utilityPageExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {masterPage(masterPageExternalReferenceCode: ___, siteExternalReferenceCode: ___){creator, creatorExternalReferenceCode, dateCreated, dateModified, datePublished, externalReferenceCode, key, keywords, markedAsDefault, name, pageSpecifications, taxonomyCategoryItemExternalReferences, thumbnail, uuid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a specific master page of a site.")
	public MasterPage masterPage(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("masterPageExternalReferenceCode") String
				masterPageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_masterPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			masterPageResource -> masterPageResource.getSiteMasterPage(
				siteExternalReferenceCode, masterPageExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {masterPagePermissions(masterPageExternalReferenceCode: ___, roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public MasterPagePage masterPagePermissions(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("masterPageExternalReferenceCode") String
				masterPageExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_masterPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			masterPageResource -> new MasterPagePage(
				masterPageResource.getSiteMasterPagePermissionsPage(
					siteExternalReferenceCode, masterPageExternalReferenceCode,
					roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {masterPages(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the master pages of the site.")
	public MasterPagePage masterPages(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("aggregation") List<String> aggregations,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_masterPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			masterPageResource -> new MasterPagePage(
				masterPageResource.getSiteMasterPagesPage(
					siteExternalReferenceCode, search,
					_aggregationBiFunction.apply(
						masterPageResource, aggregations),
					_filterBiFunction.apply(masterPageResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(masterPageResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageSpecificationPageExperiencePageElement(pageElementExternalReferenceCode: ___, pageExperienceExternalReferenceCode: ___, pageSpecificationExternalReferenceCode: ___, siteExternalReferenceCode: ___){externalReferenceCode, pageElementDefinition, pageElements, parentExternalReferenceCode, position}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a page element within an experience of a specific page specification of a site page within a site."
	)
	public PageElement pageSpecificationPageExperiencePageElement(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageSpecificationExternalReferenceCode") String
				pageSpecificationExternalReferenceCode,
			@GraphQLName("pageExperienceExternalReferenceCode") String
				pageExperienceExternalReferenceCode,
			@GraphQLName("pageElementExternalReferenceCode") String
				pageElementExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageElementResource ->
				pageElementResource.
					getSitePageSpecificationPageExperiencePageElement(
						siteExternalReferenceCode,
						pageSpecificationExternalReferenceCode,
						pageExperienceExternalReferenceCode,
						pageElementExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageSpecificationPageExperiencePageElementPageElements(flatten: ___, pageElementExternalReferenceCode: ___, pageExperienceExternalReferenceCode: ___, pageSpecificationExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the descendant page elements of a page element within an experience in a page specification of a site page."
	)
	public PageElementPage
			pageSpecificationPageExperiencePageElementPageElements(
				@GraphQLName("siteExternalReferenceCode") @NotEmpty String
					siteExternalReferenceCode,
				@GraphQLName("pageSpecificationExternalReferenceCode") String
					pageSpecificationExternalReferenceCode,
				@GraphQLName("pageExperienceExternalReferenceCode") String
					pageExperienceExternalReferenceCode,
				@GraphQLName("pageElementExternalReferenceCode") String
					pageElementExternalReferenceCode,
				@GraphQLName("flatten") Boolean flatten)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageElementResource -> new PageElementPage(
				pageElementResource.
					getSitePageSpecificationPageExperiencePageElementPageElementsPage(
						siteExternalReferenceCode,
						pageSpecificationExternalReferenceCode,
						pageExperienceExternalReferenceCode,
						pageElementExternalReferenceCode, flatten)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageSpecificationPageExperiencePageElements(flatten: ___, pageExperienceExternalReferenceCode: ___, pageSpecificationExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page elements within an experience in a page specification of a site page."
	)
	public PageElementPage pageSpecificationPageExperiencePageElements(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageSpecificationExternalReferenceCode") String
				pageSpecificationExternalReferenceCode,
			@GraphQLName("pageExperienceExternalReferenceCode") String
				pageExperienceExternalReferenceCode,
			@GraphQLName("flatten") Boolean flatten)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageElementResource -> new PageElementPage(
				pageElementResource.
					getSitePageSpecificationPageExperiencePageElementsPage(
						siteExternalReferenceCode,
						pageSpecificationExternalReferenceCode,
						pageExperienceExternalReferenceCode, flatten)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageExperience(pageExperienceExternalReferenceCode: ___, siteExternalReferenceCode: ___){externalReferenceCode, key, name_i18n, pageElements, pageRules, pageSpecificationExternalReferenceCode, priority, segmentExternalReferenceCode}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves an experience of a specific page specification of a site page within a site."
	)
	public PageExperience pageExperience(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageExperienceExternalReferenceCode") String
				pageExperienceExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageExperienceResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageExperienceResource ->
				pageExperienceResource.getSitePageExperience(
					siteExternalReferenceCode,
					pageExperienceExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageSpecificationPageExperiences(pageSpecificationExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the experiences of a page specification."
	)
	public PageExperiencePage pageSpecificationPageExperiences(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageSpecificationExternalReferenceCode") String
				pageSpecificationExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageExperienceResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageExperienceResource -> new PageExperiencePage(
				pageExperienceResource.
					getSitePageSpecificationPageExperiencesPage(
						siteExternalReferenceCode,
						pageSpecificationExternalReferenceCode)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageExperiencePageRules(flatten: ___, pageExperienceExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page rules within an experience in a page specification of a site page."
	)
	public PageRulePage pageExperiencePageRules(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageExperienceExternalReferenceCode") String
				pageExperienceExternalReferenceCode,
			@GraphQLName("flatten") Boolean flatten)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageRuleResource -> new PageRulePage(
				pageRuleResource.getSitePageExperiencePageRulesPage(
					siteExternalReferenceCode,
					pageExperienceExternalReferenceCode, flatten)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageRule(pageRuleExternalReferenceCode: ___, siteExternalReferenceCode: ___){conditionType, externalReferenceCode, name, pageRuleActions, pageRuleConditions}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves page rule within an experience of a specific page specification of a site page within a site."
	)
	public PageRule pageRule(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageRuleExternalReferenceCode") String
				pageRuleExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageRuleResource -> pageRuleResource.getSitePageRule(
				siteExternalReferenceCode, pageRuleExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageRuleAction(pageRuleActionExternalReferenceCode: ___, siteExternalReferenceCode: ___){action, externalReferenceCode, itemId, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a page rule action within a page rule of an experience of a specific page specification of a site page within a site."
	)
	public PageRuleAction pageRuleAction(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageRuleActionExternalReferenceCode") String
				pageRuleActionExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageRuleActionResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageRuleActionResource ->
				pageRuleActionResource.getSitePageRuleAction(
					siteExternalReferenceCode,
					pageRuleActionExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageRulePageRuleActions(flatten: ___, pageRuleExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page rule action actions within an experience in a page specification of a site page."
	)
	public PageRuleActionPage pageRulePageRuleActions(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageRuleExternalReferenceCode") String
				pageRuleExternalReferenceCode,
			@GraphQLName("flatten") Boolean flatten)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageRuleActionResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageRuleActionResource -> new PageRuleActionPage(
				pageRuleActionResource.getSitePageRulePageRuleActionsPage(
					siteExternalReferenceCode, pageRuleExternalReferenceCode,
					flatten)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageRuleCondition(pageRuleConditionExternalReferenceCode: ___, siteExternalReferenceCode: ___){condition, externalReferenceCode, type, value}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a page rule condition within a page rule of an experience of a specific page specification of a site page within a site."
	)
	public PageRuleCondition pageRuleCondition(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageRuleConditionExternalReferenceCode") String
				pageRuleConditionExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageRuleConditionResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageRuleConditionResource ->
				pageRuleConditionResource.getSitePageRuleCondition(
					siteExternalReferenceCode,
					pageRuleConditionExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageRulePageRuleConditions(flatten: ___, pageRuleExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page rule condition conditions within an experience in a page specification of a site page."
	)
	public PageRuleConditionPage pageRulePageRuleConditions(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageRuleExternalReferenceCode") String
				pageRuleExternalReferenceCode,
			@GraphQLName("flatten") Boolean flatten)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageRuleConditionResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageRuleConditionResource -> new PageRuleConditionPage(
				pageRuleConditionResource.getSitePageRulePageRuleConditionsPage(
					siteExternalReferenceCode, pageRuleExternalReferenceCode,
					flatten)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {displayPageTemplatePageSpecifications(displayPageTemplateExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page specifications of a display page template."
	)
	public PageSpecificationPage displayPageTemplatePageSpecifications(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("displayPageTemplateExternalReferenceCode") String
				displayPageTemplateExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageSpecificationResource -> new PageSpecificationPage(
				pageSpecificationResource.
					getSiteDisplayPageTemplatePageSpecificationsPage(
						siteExternalReferenceCode,
						displayPageTemplateExternalReferenceCode)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {masterPagePageSpecifications(masterPageExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page specifications of a master page."
	)
	public PageSpecificationPage masterPagePageSpecifications(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("masterPageExternalReferenceCode") String
				masterPageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageSpecificationResource -> new PageSpecificationPage(
				pageSpecificationResource.
					getSiteMasterPagePageSpecificationsPage(
						siteExternalReferenceCode,
						masterPageExternalReferenceCode)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageSpecification(pageSpecificationExternalReferenceCode: ___, siteExternalReferenceCode: ___){customFields, externalReferenceCode, settings, siteTemplatePageSpecificationExternalReferenceCode, status, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a page specification of a site page."
	)
	public PageSpecification pageSpecification(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageSpecificationExternalReferenceCode") String
				pageSpecificationExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageSpecificationResource ->
				pageSpecificationResource.getSitePageSpecification(
					siteExternalReferenceCode,
					pageSpecificationExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageTemplatePageSpecifications(pageTemplateExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page specifications of a page template."
	)
	public PageSpecificationPage pageTemplatePageSpecifications(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageTemplateExternalReferenceCode") String
				pageTemplateExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageSpecificationResource -> new PageSpecificationPage(
				pageSpecificationResource.
					getSitePageTemplatePageSpecificationsPage(
						siteExternalReferenceCode,
						pageTemplateExternalReferenceCode)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {sitePagePageSpecifications(siteExternalReferenceCode: ___, sitePageExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page specifications of a site page."
	)
	public PageSpecificationPage sitePagePageSpecifications(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("sitePageExternalReferenceCode") String
				sitePageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageSpecificationResource -> new PageSpecificationPage(
				pageSpecificationResource.getSiteSitePagePageSpecificationsPage(
					siteExternalReferenceCode, sitePageExternalReferenceCode)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {utilityPagePageSpecifications(siteExternalReferenceCode: ___, utilityPageExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page specifications of a utility page."
	)
	public PageSpecificationPage utilityPagePageSpecifications(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("utilityPageExternalReferenceCode") String
				utilityPageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageSpecificationResource -> new PageSpecificationPage(
				pageSpecificationResource.
					getSiteUtilityPagePageSpecificationsPage(
						siteExternalReferenceCode,
						utilityPageExternalReferenceCode)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageTemplate(pageTemplateExternalReferenceCode: ___, siteExternalReferenceCode: ___){creator, creatorExternalReferenceCode, dateCreated, dateModified, datePublished, externalReferenceCode, key, keywords, name, pageSpecifications, pageTemplateSet, pageTemplateSettings, taxonomyCategoryItemExternalReferences, type, uuid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a specific page template of a site.")
	public PageTemplate pageTemplate(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageTemplateExternalReferenceCode") String
				pageTemplateExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateResource -> pageTemplateResource.getSitePageTemplate(
				siteExternalReferenceCode, pageTemplateExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageTemplatePermissions(pageTemplateExternalReferenceCode: ___, roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public PageTemplatePage pageTemplatePermissions(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageTemplateExternalReferenceCode") String
				pageTemplateExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateResource -> new PageTemplatePage(
				pageTemplateResource.getSitePageTemplatePermissionsPage(
					siteExternalReferenceCode,
					pageTemplateExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageTemplateSetPageTemplates(flatten: ___, pageTemplateSetExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page templates within a page template set of a site page."
	)
	public PageTemplatePage pageTemplateSetPageTemplates(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageTemplateSetExternalReferenceCode") String
				pageTemplateSetExternalReferenceCode,
			@GraphQLName("flatten") Boolean flatten)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateResource -> new PageTemplatePage(
				pageTemplateResource.getSitePageTemplateSetPageTemplatesPage(
					siteExternalReferenceCode,
					pageTemplateSetExternalReferenceCode, flatten)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageTemplates(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the page templates of the site")
	public PageTemplatePage pageTemplates(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("aggregation") List<String> aggregations,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateResource -> new PageTemplatePage(
				pageTemplateResource.getSitePageTemplatesPage(
					siteExternalReferenceCode, search,
					_aggregationBiFunction.apply(
						pageTemplateResource, aggregations),
					_filterBiFunction.apply(pageTemplateResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						pageTemplateResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageTemplateSet(pageTemplateSetExternalReferenceCode: ___, siteExternalReferenceCode: ___){creator, creatorExternalReferenceCode, dateCreated, dateModified, description, externalReferenceCode, key, name, uuid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a specific page template set of a site."
	)
	public PageTemplateSet pageTemplateSet(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageTemplateSetExternalReferenceCode") String
				pageTemplateSetExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateSetResource ->
				pageTemplateSetResource.getSitePageTemplateSet(
					siteExternalReferenceCode,
					pageTemplateSetExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageTemplateSetPermissions(pageTemplateSetExternalReferenceCode: ___, roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public PageTemplateSetPage pageTemplateSetPermissions(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("pageTemplateSetExternalReferenceCode") String
				pageTemplateSetExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateSetResource -> new PageTemplateSetPage(
				pageTemplateSetResource.getSitePageTemplateSetPermissionsPage(
					siteExternalReferenceCode,
					pageTemplateSetExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {pageTemplateSets(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the page template sets of the site")
	public PageTemplateSetPage pageTemplateSets(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("aggregation") List<String> aggregations,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateSetResource -> new PageTemplateSetPage(
				pageTemplateSetResource.getSitePageTemplateSetsPage(
					siteExternalReferenceCode, search,
					_aggregationBiFunction.apply(
						pageTemplateSetResource, aggregations),
					_filterBiFunction.apply(
						pageTemplateSetResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						pageTemplateSetResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {sitePage(siteExternalReferenceCode: ___, sitePageExternalReferenceCode: ___){availableLanguages, creator, creatorExternalReferenceCode, dateCreated, dateModified, datePublished, externalReferenceCode, friendlyUrlHistory, friendlyUrlPath_i18n, keywords, name_i18n, pageSettings, pageSpecifications, parentSitePageExternalReferenceCode, taxonomyCategoryItemExternalReferences, type, uuid, viewableBy}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a specific public page of a site.")
	public SitePage sitePage(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("sitePageExternalReferenceCode") String
				sitePageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_sitePageResourceComponentServiceObjects,
			this::_populateResourceContext,
			sitePageResource -> sitePageResource.getSiteSitePage(
				siteExternalReferenceCode, sitePageExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {sitePagePermissions(roleNames: ___, siteExternalReferenceCode: ___, sitePageExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SitePagePage sitePagePermissions(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("sitePageExternalReferenceCode") String
				sitePageExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_sitePageResourceComponentServiceObjects,
			this::_populateResourceContext,
			sitePageResource -> new SitePagePage(
				sitePageResource.getSiteSitePagePermissionsPage(
					siteExternalReferenceCode, sitePageExternalReferenceCode,
					roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {sitePages(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the public pages of the site")
	public SitePagePage sitePages(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("aggregation") List<String> aggregations,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_sitePageResourceComponentServiceObjects,
			this::_populateResourceContext,
			sitePageResource -> new SitePagePage(
				sitePageResource.getSiteSitePagesPage(
					siteExternalReferenceCode, search,
					_aggregationBiFunction.apply(
						sitePageResource, aggregations),
					_filterBiFunction.apply(sitePageResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(sitePageResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {utilityPage(siteExternalReferenceCode: ___, utilityPageExternalReferenceCode: ___){creator, creatorExternalReferenceCode, dateCreated, dateModified, datePublished, externalReferenceCode, friendlyUrlHistory, friendlyUrlPath_i18n, markedAsDefault, name, pageSpecifications, thumbnail, type, utilityPageSettings, uuid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a specific utility page of a site.")
	public UtilityPage utilityPage(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("utilityPageExternalReferenceCode") String
				utilityPageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_utilityPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			utilityPageResource -> utilityPageResource.getSiteUtilityPage(
				siteExternalReferenceCode, utilityPageExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {utilityPagePermissions(roleNames: ___, siteExternalReferenceCode: ___, utilityPageExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public UtilityPagePage utilityPagePermissions(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("utilityPageExternalReferenceCode") String
				utilityPageExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_utilityPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			utilityPageResource -> new UtilityPagePage(
				utilityPageResource.getSiteUtilityPagePermissionsPage(
					siteExternalReferenceCode, utilityPageExternalReferenceCode,
					roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {utilityPages(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the utility pages of the site.")
	public UtilityPagePage utilityPages(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("aggregation") List<String> aggregations,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_utilityPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			utilityPageResource -> new UtilityPagePage(
				utilityPageResource.getSiteUtilityPagesPage(
					siteExternalReferenceCode, search,
					_aggregationBiFunction.apply(
						utilityPageResource, aggregations),
					_filterBiFunction.apply(utilityPageResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(utilityPageResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {sitePageWidgetInstance(siteExternalReferenceCode: ___, sitePageExternalReferenceCode: ___, widgetInstanceExternalReferenceCode: ___){widgetName, parentSectionId, widgetInstanceId, widgetLookAndFeelConfig, widgetPermissions, parentWidgetInstanceExternalReferenceCode, position, widgetConfig, externalReferenceCode}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a widget instance of a widget page or widget page template within a site."
	)
	public WidgetPageWidgetInstance sitePageWidgetInstance(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("sitePageExternalReferenceCode") String
				sitePageExternalReferenceCode,
			@GraphQLName("widgetInstanceExternalReferenceCode") String
				widgetInstanceExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_widgetPageWidgetInstanceResourceComponentServiceObjects,
			this::_populateResourceContext,
			widgetPageWidgetInstanceResource ->
				widgetPageWidgetInstanceResource.getSiteSitePageWidgetInstance(
					siteExternalReferenceCode, sitePageExternalReferenceCode,
					widgetInstanceExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {sitePageWidgetInstances(siteExternalReferenceCode: ___, sitePageExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the widget instances of a widget page."
	)
	public WidgetPageWidgetInstancePage sitePageWidgetInstances(
			@GraphQLName("siteExternalReferenceCode") @NotEmpty String
				siteExternalReferenceCode,
			@GraphQLName("sitePageExternalReferenceCode") String
				sitePageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_widgetPageWidgetInstanceResourceComponentServiceObjects,
			this::_populateResourceContext,
			widgetPageWidgetInstanceResource ->
				new WidgetPageWidgetInstancePage(
					widgetPageWidgetInstanceResource.
						getSiteSitePageWidgetInstancesPage(
							siteExternalReferenceCode,
							sitePageExternalReferenceCode)));
	}

	@GraphQLName("DisplayPageTemplatePage")
	public class DisplayPageTemplatePage {

		public DisplayPageTemplatePage(Page displayPageTemplatePage) {
			actions = displayPageTemplatePage.getActions();

			facets = displayPageTemplatePage.getFacets();

			items = displayPageTemplatePage.getItems();
			lastPage = displayPageTemplatePage.getLastPage();
			page = displayPageTemplatePage.getPage();
			pageSize = displayPageTemplatePage.getPageSize();
			totalCount = displayPageTemplatePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<DisplayPageTemplate> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("DisplayPageTemplateFolderPage")
	public class DisplayPageTemplateFolderPage {

		public DisplayPageTemplateFolderPage(
			Page displayPageTemplateFolderPage) {

			actions = displayPageTemplateFolderPage.getActions();

			facets = displayPageTemplateFolderPage.getFacets();

			items = displayPageTemplateFolderPage.getItems();
			lastPage = displayPageTemplateFolderPage.getLastPage();
			page = displayPageTemplateFolderPage.getPage();
			pageSize = displayPageTemplateFolderPage.getPageSize();
			totalCount = displayPageTemplateFolderPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<DisplayPageTemplateFolder> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("FragmentCompositionPage")
	public class FragmentCompositionPage {

		public FragmentCompositionPage(Page fragmentCompositionPage) {
			actions = fragmentCompositionPage.getActions();

			facets = fragmentCompositionPage.getFacets();

			items = fragmentCompositionPage.getItems();
			lastPage = fragmentCompositionPage.getLastPage();
			page = fragmentCompositionPage.getPage();
			pageSize = fragmentCompositionPage.getPageSize();
			totalCount = fragmentCompositionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<FragmentComposition> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("FriendlyUrlHistoryPage")
	public class FriendlyUrlHistoryPage {

		public FriendlyUrlHistoryPage(Page friendlyUrlHistoryPage) {
			actions = friendlyUrlHistoryPage.getActions();

			facets = friendlyUrlHistoryPage.getFacets();

			items = friendlyUrlHistoryPage.getItems();
			lastPage = friendlyUrlHistoryPage.getLastPage();
			page = friendlyUrlHistoryPage.getPage();
			pageSize = friendlyUrlHistoryPage.getPageSize();
			totalCount = friendlyUrlHistoryPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<FriendlyUrlHistory> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("MasterPagePage")
	public class MasterPagePage {

		public MasterPagePage(Page masterPagePage) {
			actions = masterPagePage.getActions();

			facets = masterPagePage.getFacets();

			items = masterPagePage.getItems();
			lastPage = masterPagePage.getLastPage();
			page = masterPagePage.getPage();
			pageSize = masterPagePage.getPageSize();
			totalCount = masterPagePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<MasterPage> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageElementPage")
	public class PageElementPage {

		public PageElementPage(Page pageElementPage) {
			actions = pageElementPage.getActions();

			facets = pageElementPage.getFacets();

			items = pageElementPage.getItems();
			lastPage = pageElementPage.getLastPage();
			page = pageElementPage.getPage();
			pageSize = pageElementPage.getPageSize();
			totalCount = pageElementPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageElement> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageExperiencePage")
	public class PageExperiencePage {

		public PageExperiencePage(Page pageExperiencePage) {
			actions = pageExperiencePage.getActions();

			facets = pageExperiencePage.getFacets();

			items = pageExperiencePage.getItems();
			lastPage = pageExperiencePage.getLastPage();
			page = pageExperiencePage.getPage();
			pageSize = pageExperiencePage.getPageSize();
			totalCount = pageExperiencePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageExperience> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageRulePage")
	public class PageRulePage {

		public PageRulePage(Page pageRulePage) {
			actions = pageRulePage.getActions();

			facets = pageRulePage.getFacets();

			items = pageRulePage.getItems();
			lastPage = pageRulePage.getLastPage();
			page = pageRulePage.getPage();
			pageSize = pageRulePage.getPageSize();
			totalCount = pageRulePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageRule> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageRuleActionPage")
	public class PageRuleActionPage {

		public PageRuleActionPage(Page pageRuleActionPage) {
			actions = pageRuleActionPage.getActions();

			facets = pageRuleActionPage.getFacets();

			items = pageRuleActionPage.getItems();
			lastPage = pageRuleActionPage.getLastPage();
			page = pageRuleActionPage.getPage();
			pageSize = pageRuleActionPage.getPageSize();
			totalCount = pageRuleActionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageRuleAction> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageRuleConditionPage")
	public class PageRuleConditionPage {

		public PageRuleConditionPage(Page pageRuleConditionPage) {
			actions = pageRuleConditionPage.getActions();

			facets = pageRuleConditionPage.getFacets();

			items = pageRuleConditionPage.getItems();
			lastPage = pageRuleConditionPage.getLastPage();
			page = pageRuleConditionPage.getPage();
			pageSize = pageRuleConditionPage.getPageSize();
			totalCount = pageRuleConditionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageRuleCondition> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageSpecificationPage")
	public class PageSpecificationPage {

		public PageSpecificationPage(Page pageSpecificationPage) {
			actions = pageSpecificationPage.getActions();

			facets = pageSpecificationPage.getFacets();

			items = pageSpecificationPage.getItems();
			lastPage = pageSpecificationPage.getLastPage();
			page = pageSpecificationPage.getPage();
			pageSize = pageSpecificationPage.getPageSize();
			totalCount = pageSpecificationPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageSpecification> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageTemplatePage")
	public class PageTemplatePage {

		public PageTemplatePage(Page pageTemplatePage) {
			actions = pageTemplatePage.getActions();

			facets = pageTemplatePage.getFacets();

			items = pageTemplatePage.getItems();
			lastPage = pageTemplatePage.getLastPage();
			page = pageTemplatePage.getPage();
			pageSize = pageTemplatePage.getPageSize();
			totalCount = pageTemplatePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageTemplate> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageTemplateSetPage")
	public class PageTemplateSetPage {

		public PageTemplateSetPage(Page pageTemplateSetPage) {
			actions = pageTemplateSetPage.getActions();

			facets = pageTemplateSetPage.getFacets();

			items = pageTemplateSetPage.getItems();
			lastPage = pageTemplateSetPage.getLastPage();
			page = pageTemplateSetPage.getPage();
			pageSize = pageTemplateSetPage.getPageSize();
			totalCount = pageTemplateSetPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageTemplateSet> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SitePagePage")
	public class SitePagePage {

		public SitePagePage(Page sitePagePage) {
			actions = sitePagePage.getActions();

			facets = sitePagePage.getFacets();

			items = sitePagePage.getItems();
			lastPage = sitePagePage.getLastPage();
			page = sitePagePage.getPage();
			pageSize = sitePagePage.getPageSize();
			totalCount = sitePagePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<SitePage> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("UtilityPagePage")
	public class UtilityPagePage {

		public UtilityPagePage(Page utilityPagePage) {
			actions = utilityPagePage.getActions();

			facets = utilityPagePage.getFacets();

			items = utilityPagePage.getItems();
			lastPage = utilityPagePage.getLastPage();
			page = utilityPagePage.getPage();
			pageSize = utilityPagePage.getPageSize();
			totalCount = utilityPagePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<UtilityPage> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("WidgetPageWidgetInstancePage")
	public class WidgetPageWidgetInstancePage {

		public WidgetPageWidgetInstancePage(Page widgetPageWidgetInstancePage) {
			actions = widgetPageWidgetInstancePage.getActions();

			facets = widgetPageWidgetInstancePage.getFacets();

			items = widgetPageWidgetInstancePage.getItems();
			lastPage = widgetPageWidgetInstancePage.getLastPage();
			page = widgetPageWidgetInstancePage.getPage();
			pageSize = widgetPageWidgetInstancePage.getPageSize();
			totalCount = widgetPageWidgetInstancePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<WidgetPageWidgetInstance> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(
			DisplayPageTemplateResource displayPageTemplateResource)
		throws Exception {

		displayPageTemplateResource.setContextAcceptLanguage(_acceptLanguage);
		displayPageTemplateResource.setContextCompany(_company);
		displayPageTemplateResource.setContextHttpServletRequest(
			_httpServletRequest);
		displayPageTemplateResource.setContextHttpServletResponse(
			_httpServletResponse);
		displayPageTemplateResource.setContextUriInfo(_uriInfo);
		displayPageTemplateResource.setContextUser(_user);
		displayPageTemplateResource.setGroupLocalService(_groupLocalService);
		displayPageTemplateResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			DisplayPageTemplateFolderResource displayPageTemplateFolderResource)
		throws Exception {

		displayPageTemplateFolderResource.setContextAcceptLanguage(
			_acceptLanguage);
		displayPageTemplateFolderResource.setContextCompany(_company);
		displayPageTemplateFolderResource.setContextHttpServletRequest(
			_httpServletRequest);
		displayPageTemplateFolderResource.setContextHttpServletResponse(
			_httpServletResponse);
		displayPageTemplateFolderResource.setContextUriInfo(_uriInfo);
		displayPageTemplateFolderResource.setContextUser(_user);
		displayPageTemplateFolderResource.setGroupLocalService(
			_groupLocalService);
		displayPageTemplateFolderResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(
			FragmentCompositionResource fragmentCompositionResource)
		throws Exception {

		fragmentCompositionResource.setContextAcceptLanguage(_acceptLanguage);
		fragmentCompositionResource.setContextCompany(_company);
		fragmentCompositionResource.setContextHttpServletRequest(
			_httpServletRequest);
		fragmentCompositionResource.setContextHttpServletResponse(
			_httpServletResponse);
		fragmentCompositionResource.setContextUriInfo(_uriInfo);
		fragmentCompositionResource.setContextUser(_user);
		fragmentCompositionResource.setGroupLocalService(_groupLocalService);
		fragmentCompositionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			FriendlyUrlHistoryResource friendlyUrlHistoryResource)
		throws Exception {

		friendlyUrlHistoryResource.setContextAcceptLanguage(_acceptLanguage);
		friendlyUrlHistoryResource.setContextCompany(_company);
		friendlyUrlHistoryResource.setContextHttpServletRequest(
			_httpServletRequest);
		friendlyUrlHistoryResource.setContextHttpServletResponse(
			_httpServletResponse);
		friendlyUrlHistoryResource.setContextUriInfo(_uriInfo);
		friendlyUrlHistoryResource.setContextUser(_user);
		friendlyUrlHistoryResource.setGroupLocalService(_groupLocalService);
		friendlyUrlHistoryResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(MasterPageResource masterPageResource)
		throws Exception {

		masterPageResource.setContextAcceptLanguage(_acceptLanguage);
		masterPageResource.setContextCompany(_company);
		masterPageResource.setContextHttpServletRequest(_httpServletRequest);
		masterPageResource.setContextHttpServletResponse(_httpServletResponse);
		masterPageResource.setContextUriInfo(_uriInfo);
		masterPageResource.setContextUser(_user);
		masterPageResource.setGroupLocalService(_groupLocalService);
		masterPageResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PageElementResource pageElementResource)
		throws Exception {

		pageElementResource.setContextAcceptLanguage(_acceptLanguage);
		pageElementResource.setContextCompany(_company);
		pageElementResource.setContextHttpServletRequest(_httpServletRequest);
		pageElementResource.setContextHttpServletResponse(_httpServletResponse);
		pageElementResource.setContextUriInfo(_uriInfo);
		pageElementResource.setContextUser(_user);
		pageElementResource.setGroupLocalService(_groupLocalService);
		pageElementResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PageExperienceResource pageExperienceResource)
		throws Exception {

		pageExperienceResource.setContextAcceptLanguage(_acceptLanguage);
		pageExperienceResource.setContextCompany(_company);
		pageExperienceResource.setContextHttpServletRequest(
			_httpServletRequest);
		pageExperienceResource.setContextHttpServletResponse(
			_httpServletResponse);
		pageExperienceResource.setContextUriInfo(_uriInfo);
		pageExperienceResource.setContextUser(_user);
		pageExperienceResource.setGroupLocalService(_groupLocalService);
		pageExperienceResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(PageRuleResource pageRuleResource)
		throws Exception {

		pageRuleResource.setContextAcceptLanguage(_acceptLanguage);
		pageRuleResource.setContextCompany(_company);
		pageRuleResource.setContextHttpServletRequest(_httpServletRequest);
		pageRuleResource.setContextHttpServletResponse(_httpServletResponse);
		pageRuleResource.setContextUriInfo(_uriInfo);
		pageRuleResource.setContextUser(_user);
		pageRuleResource.setGroupLocalService(_groupLocalService);
		pageRuleResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PageRuleActionResource pageRuleActionResource)
		throws Exception {

		pageRuleActionResource.setContextAcceptLanguage(_acceptLanguage);
		pageRuleActionResource.setContextCompany(_company);
		pageRuleActionResource.setContextHttpServletRequest(
			_httpServletRequest);
		pageRuleActionResource.setContextHttpServletResponse(
			_httpServletResponse);
		pageRuleActionResource.setContextUriInfo(_uriInfo);
		pageRuleActionResource.setContextUser(_user);
		pageRuleActionResource.setGroupLocalService(_groupLocalService);
		pageRuleActionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PageRuleConditionResource pageRuleConditionResource)
		throws Exception {

		pageRuleConditionResource.setContextAcceptLanguage(_acceptLanguage);
		pageRuleConditionResource.setContextCompany(_company);
		pageRuleConditionResource.setContextHttpServletRequest(
			_httpServletRequest);
		pageRuleConditionResource.setContextHttpServletResponse(
			_httpServletResponse);
		pageRuleConditionResource.setContextUriInfo(_uriInfo);
		pageRuleConditionResource.setContextUser(_user);
		pageRuleConditionResource.setGroupLocalService(_groupLocalService);
		pageRuleConditionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PageSpecificationResource pageSpecificationResource)
		throws Exception {

		pageSpecificationResource.setContextAcceptLanguage(_acceptLanguage);
		pageSpecificationResource.setContextCompany(_company);
		pageSpecificationResource.setContextHttpServletRequest(
			_httpServletRequest);
		pageSpecificationResource.setContextHttpServletResponse(
			_httpServletResponse);
		pageSpecificationResource.setContextUriInfo(_uriInfo);
		pageSpecificationResource.setContextUser(_user);
		pageSpecificationResource.setGroupLocalService(_groupLocalService);
		pageSpecificationResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PageTemplateResource pageTemplateResource)
		throws Exception {

		pageTemplateResource.setContextAcceptLanguage(_acceptLanguage);
		pageTemplateResource.setContextCompany(_company);
		pageTemplateResource.setContextHttpServletRequest(_httpServletRequest);
		pageTemplateResource.setContextHttpServletResponse(
			_httpServletResponse);
		pageTemplateResource.setContextUriInfo(_uriInfo);
		pageTemplateResource.setContextUser(_user);
		pageTemplateResource.setGroupLocalService(_groupLocalService);
		pageTemplateResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PageTemplateSetResource pageTemplateSetResource)
		throws Exception {

		pageTemplateSetResource.setContextAcceptLanguage(_acceptLanguage);
		pageTemplateSetResource.setContextCompany(_company);
		pageTemplateSetResource.setContextHttpServletRequest(
			_httpServletRequest);
		pageTemplateSetResource.setContextHttpServletResponse(
			_httpServletResponse);
		pageTemplateSetResource.setContextUriInfo(_uriInfo);
		pageTemplateSetResource.setContextUser(_user);
		pageTemplateSetResource.setGroupLocalService(_groupLocalService);
		pageTemplateSetResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(SitePageResource sitePageResource)
		throws Exception {

		sitePageResource.setContextAcceptLanguage(_acceptLanguage);
		sitePageResource.setContextCompany(_company);
		sitePageResource.setContextHttpServletRequest(_httpServletRequest);
		sitePageResource.setContextHttpServletResponse(_httpServletResponse);
		sitePageResource.setContextUriInfo(_uriInfo);
		sitePageResource.setContextUser(_user);
		sitePageResource.setGroupLocalService(_groupLocalService);
		sitePageResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			UtilityPageResource utilityPageResource)
		throws Exception {

		utilityPageResource.setContextAcceptLanguage(_acceptLanguage);
		utilityPageResource.setContextCompany(_company);
		utilityPageResource.setContextHttpServletRequest(_httpServletRequest);
		utilityPageResource.setContextHttpServletResponse(_httpServletResponse);
		utilityPageResource.setContextUriInfo(_uriInfo);
		utilityPageResource.setContextUser(_user);
		utilityPageResource.setGroupLocalService(_groupLocalService);
		utilityPageResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			WidgetPageWidgetInstanceResource widgetPageWidgetInstanceResource)
		throws Exception {

		widgetPageWidgetInstanceResource.setContextAcceptLanguage(
			_acceptLanguage);
		widgetPageWidgetInstanceResource.setContextCompany(_company);
		widgetPageWidgetInstanceResource.setContextHttpServletRequest(
			_httpServletRequest);
		widgetPageWidgetInstanceResource.setContextHttpServletResponse(
			_httpServletResponse);
		widgetPageWidgetInstanceResource.setContextUriInfo(_uriInfo);
		widgetPageWidgetInstanceResource.setContextUser(_user);
		widgetPageWidgetInstanceResource.setGroupLocalService(
			_groupLocalService);
		widgetPageWidgetInstanceResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<DisplayPageTemplateResource>
		_displayPageTemplateResourceComponentServiceObjects;
	private static ComponentServiceObjects<DisplayPageTemplateFolderResource>
		_displayPageTemplateFolderResourceComponentServiceObjects;
	private static ComponentServiceObjects<FragmentCompositionResource>
		_fragmentCompositionResourceComponentServiceObjects;
	private static ComponentServiceObjects<FriendlyUrlHistoryResource>
		_friendlyUrlHistoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<MasterPageResource>
		_masterPageResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageElementResource>
		_pageElementResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageExperienceResource>
		_pageExperienceResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageRuleResource>
		_pageRuleResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageRuleActionResource>
		_pageRuleActionResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageRuleConditionResource>
		_pageRuleConditionResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageSpecificationResource>
		_pageSpecificationResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageTemplateResource>
		_pageTemplateResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageTemplateSetResource>
		_pageTemplateSetResourceComponentServiceObjects;
	private static ComponentServiceObjects<SitePageResource>
		_sitePageResourceComponentServiceObjects;
	private static ComponentServiceObjects<UtilityPageResource>
		_utilityPageResourceComponentServiceObjects;
	private static ComponentServiceObjects<WidgetPageWidgetInstanceResource>
		_widgetPageWidgetInstanceResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private BiFunction<Object, List<String>, Aggregation>
		_aggregationBiFunction;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction
		<Object, String, com.liferay.portal.kernel.search.filter.Filter>
			_filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, com.liferay.portal.kernel.search.Sort[]>
		_sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}