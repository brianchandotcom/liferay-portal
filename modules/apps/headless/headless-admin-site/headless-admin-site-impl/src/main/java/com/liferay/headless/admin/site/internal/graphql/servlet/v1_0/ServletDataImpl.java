/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.graphql.servlet.v1_0;

import com.liferay.headless.admin.site.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.admin.site.internal.graphql.query.v1_0.Query;
import com.liferay.headless.admin.site.internal.resource.v1_0.DisplayPageTemplateFolderResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.DisplayPageTemplateResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.FragmentCompositionResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.FriendlyUrlHistoryResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.MasterPageResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageElementResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageExperienceResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageRuleActionResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageRuleConditionResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageRuleResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageSpecificationResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageTemplateResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageTemplateSetResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.SitePageResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.UtilityPageResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.WidgetPageWidgetInstanceResourceImpl;
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
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import jakarta.annotation.Generated;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Rubén Pulido
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setDisplayPageTemplateResourceComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects);
		Mutation.setDisplayPageTemplateFolderResourceComponentServiceObjects(
			_displayPageTemplateFolderResourceComponentServiceObjects);
		Mutation.setFragmentCompositionResourceComponentServiceObjects(
			_fragmentCompositionResourceComponentServiceObjects);
		Mutation.setMasterPageResourceComponentServiceObjects(
			_masterPageResourceComponentServiceObjects);
		Mutation.setPageElementResourceComponentServiceObjects(
			_pageElementResourceComponentServiceObjects);
		Mutation.setPageExperienceResourceComponentServiceObjects(
			_pageExperienceResourceComponentServiceObjects);
		Mutation.setPageRuleResourceComponentServiceObjects(
			_pageRuleResourceComponentServiceObjects);
		Mutation.setPageRuleActionResourceComponentServiceObjects(
			_pageRuleActionResourceComponentServiceObjects);
		Mutation.setPageRuleConditionResourceComponentServiceObjects(
			_pageRuleConditionResourceComponentServiceObjects);
		Mutation.setPageSpecificationResourceComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects);
		Mutation.setPageTemplateResourceComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects);
		Mutation.setPageTemplateSetResourceComponentServiceObjects(
			_pageTemplateSetResourceComponentServiceObjects);
		Mutation.setSitePageResourceComponentServiceObjects(
			_sitePageResourceComponentServiceObjects);
		Mutation.setUtilityPageResourceComponentServiceObjects(
			_utilityPageResourceComponentServiceObjects);
		Mutation.setWidgetPageWidgetInstanceResourceComponentServiceObjects(
			_widgetPageWidgetInstanceResourceComponentServiceObjects);

		Query.setDisplayPageTemplateResourceComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects);
		Query.setDisplayPageTemplateFolderResourceComponentServiceObjects(
			_displayPageTemplateFolderResourceComponentServiceObjects);
		Query.setFragmentCompositionResourceComponentServiceObjects(
			_fragmentCompositionResourceComponentServiceObjects);
		Query.setFriendlyUrlHistoryResourceComponentServiceObjects(
			_friendlyUrlHistoryResourceComponentServiceObjects);
		Query.setMasterPageResourceComponentServiceObjects(
			_masterPageResourceComponentServiceObjects);
		Query.setPageElementResourceComponentServiceObjects(
			_pageElementResourceComponentServiceObjects);
		Query.setPageExperienceResourceComponentServiceObjects(
			_pageExperienceResourceComponentServiceObjects);
		Query.setPageRuleResourceComponentServiceObjects(
			_pageRuleResourceComponentServiceObjects);
		Query.setPageRuleActionResourceComponentServiceObjects(
			_pageRuleActionResourceComponentServiceObjects);
		Query.setPageRuleConditionResourceComponentServiceObjects(
			_pageRuleConditionResourceComponentServiceObjects);
		Query.setPageSpecificationResourceComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects);
		Query.setPageTemplateResourceComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects);
		Query.setPageTemplateSetResourceComponentServiceObjects(
			_pageTemplateSetResourceComponentServiceObjects);
		Query.setSitePageResourceComponentServiceObjects(
			_sitePageResourceComponentServiceObjects);
		Query.setUtilityPageResourceComponentServiceObjects(
			_utilityPageResourceComponentServiceObjects);
		Query.setWidgetPageWidgetInstanceResourceComponentServiceObjects(
			_widgetPageWidgetInstanceResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Admin.Site";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-admin-site-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#deleteSiteDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"deleteSiteDisplayPageTemplate"));
					put(
						"mutation#patchSiteDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"patchSiteDisplayPageTemplate"));
					put(
						"mutation#createSiteDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"postSiteDisplayPageTemplate"));
					put(
						"mutation#createSiteDisplayPageTemplateBatch",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"postSiteDisplayPageTemplateBatch"));
					put(
						"mutation#createSiteDisplayPageTemplateFolderDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"postSiteDisplayPageTemplateFolderDisplayPageTemplate"));
					put(
						"mutation#createSiteDisplayPageTemplatePageSpecification",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"postSiteDisplayPageTemplatePageSpecification"));
					put(
						"mutation#createSiteDisplayPageTemplatesPageExportBatch",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"postSiteDisplayPageTemplatesPageExportBatch"));
					put(
						"mutation#updateSiteDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"putSiteDisplayPageTemplate"));
					put(
						"mutation#updateSiteDisplayPageTemplatePermissionsPage",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"putSiteDisplayPageTemplatePermissionsPage"));
					put(
						"mutation#deleteSiteDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"deleteSiteDisplayPageTemplateFolder"));
					put(
						"mutation#patchSiteDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"patchSiteDisplayPageTemplateFolder"));
					put(
						"mutation#createSiteDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"postSiteDisplayPageTemplateFolder"));
					put(
						"mutation#createSiteDisplayPageTemplateFolderBatch",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"postSiteDisplayPageTemplateFolderBatch"));
					put(
						"mutation#createSiteDisplayPageTemplateFoldersPageExportBatch",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"postSiteDisplayPageTemplateFoldersPageExportBatch"));
					put(
						"mutation#updateSiteDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"putSiteDisplayPageTemplateFolder"));
					put(
						"mutation#updateSiteDisplayPageTemplateFolderPermissionsPage",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"putSiteDisplayPageTemplateFolderPermissionsPage"));
					put(
						"mutation#deleteSiteFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"deleteSiteFragmentComposition"));
					put(
						"mutation#patchSiteFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"patchSiteFragmentComposition"));
					put(
						"mutation#createSiteFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"postSiteFragmentComposition"));
					put(
						"mutation#createSiteFragmentCompositionBatch",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"postSiteFragmentCompositionBatch"));
					put(
						"mutation#createSiteFragmentCompositionsPageExportBatch",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"postSiteFragmentCompositionsPageExportBatch"));
					put(
						"mutation#updateSiteFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"putSiteFragmentComposition"));
					put(
						"mutation#deleteSiteMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"deleteSiteMasterPage"));
					put(
						"mutation#patchSiteMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"patchSiteMasterPage"));
					put(
						"mutation#createSiteMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"postSiteMasterPage"));
					put(
						"mutation#createSiteMasterPageBatch",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"postSiteMasterPageBatch"));
					put(
						"mutation#createSiteMasterPagePageSpecification",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"postSiteMasterPagePageSpecification"));
					put(
						"mutation#createSiteMasterPagesPageExportBatch",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"postSiteMasterPagesPageExportBatch"));
					put(
						"mutation#updateSiteMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class, "putSiteMasterPage"));
					put(
						"mutation#updateSiteMasterPagePermissionsPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"putSiteMasterPagePermissionsPage"));
					put(
						"mutation#deleteSitePageSpecificationPageExperiencePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"deleteSitePageSpecificationPageExperiencePageElement"));
					put(
						"mutation#patchSitePageSpecificationPageExperiencePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"patchSitePageSpecificationPageExperiencePageElement"));
					put(
						"mutation#createSitePageSpecificationPageExperiencePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"postSitePageSpecificationPageExperiencePageElement"));
					put(
						"mutation#createSitePageSpecificationPageExperiencePageElementFragmentComposition",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"postSitePageSpecificationPageExperiencePageElementFragmentComposition"));
					put(
						"mutation#updateSitePageSpecificationPageExperiencePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"putSitePageSpecificationPageExperiencePageElement"));
					put(
						"mutation#deleteSitePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"deleteSitePageExperience"));
					put(
						"mutation#patchSitePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"patchSitePageExperience"));
					put(
						"mutation#createSitePageSpecificationPageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"postSitePageSpecificationPageExperience"));
					put(
						"mutation#updateSitePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"putSitePageExperience"));
					put(
						"mutation#deleteSitePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class, "deleteSitePageRule"));
					put(
						"mutation#patchSitePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class, "patchSitePageRule"));
					put(
						"mutation#createSitePageExperiencePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"postSitePageExperiencePageRule"));
					put(
						"mutation#updateSitePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class, "putSitePageRule"));
					put(
						"mutation#deleteSitePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"deleteSitePageRuleAction"));
					put(
						"mutation#patchSitePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"patchSitePageRuleAction"));
					put(
						"mutation#createSitePageRulePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"postSitePageRulePageRuleAction"));
					put(
						"mutation#updateSitePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"putSitePageRuleAction"));
					put(
						"mutation#deleteSitePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"deleteSitePageRuleCondition"));
					put(
						"mutation#patchSitePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"patchSitePageRuleCondition"));
					put(
						"mutation#createSitePageRulePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"postSitePageRulePageRuleCondition"));
					put(
						"mutation#updateSitePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"putSitePageRuleCondition"));
					put(
						"mutation#deleteSitePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"deleteSitePageSpecification"));
					put(
						"mutation#patchSitePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"patchSitePageSpecification"));
					put(
						"mutation#createSitePageSpecificationPublish",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"postSitePageSpecificationPublish"));
					put(
						"mutation#updateSitePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"putSitePageSpecification"));
					put(
						"mutation#deleteSitePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"deleteSitePageTemplate"));
					put(
						"mutation#patchSitePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"patchSitePageTemplate"));
					put(
						"mutation#createSitePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"postSitePageTemplate"));
					put(
						"mutation#createSitePageTemplateBatch",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"postSitePageTemplateBatch"));
					put(
						"mutation#createSitePageTemplatePageSpecification",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"postSitePageTemplatePageSpecification"));
					put(
						"mutation#createSitePageTemplateSetPageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"postSitePageTemplateSetPageTemplate"));
					put(
						"mutation#createSitePageTemplatesPageExportBatch",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"postSitePageTemplatesPageExportBatch"));
					put(
						"mutation#updateSitePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"putSitePageTemplate"));
					put(
						"mutation#updateSitePageTemplatePermissionsPage",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"putSitePageTemplatePermissionsPage"));
					put(
						"mutation#deleteSitePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"deleteSitePageTemplateSet"));
					put(
						"mutation#patchSitePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"patchSitePageTemplateSet"));
					put(
						"mutation#createSitePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"postSitePageTemplateSet"));
					put(
						"mutation#createSitePageTemplateSetBatch",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"postSitePageTemplateSetBatch"));
					put(
						"mutation#createSitePageTemplateSetsPageExportBatch",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"postSitePageTemplateSetsPageExportBatch"));
					put(
						"mutation#updateSitePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"putSitePageTemplateSet"));
					put(
						"mutation#updateSitePageTemplateSetPermissionsPage",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"putSitePageTemplateSetPermissionsPage"));
					put(
						"mutation#deleteSiteSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class, "deleteSiteSitePage"));
					put(
						"mutation#patchSiteSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class, "patchSiteSitePage"));
					put(
						"mutation#createSiteSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class, "postSiteSitePage"));
					put(
						"mutation#createSiteSitePageBatch",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"postSiteSitePageBatch"));
					put(
						"mutation#createSiteSitePagePageSpecification",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"postSiteSitePagePageSpecification"));
					put(
						"mutation#createSiteSitePagesPageExportBatch",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"postSiteSitePagesPageExportBatch"));
					put(
						"mutation#updateSiteSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class, "putSiteSitePage"));
					put(
						"mutation#updateSiteSitePagePermissionsPage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"putSiteSitePagePermissionsPage"));
					put(
						"mutation#deleteSiteUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"deleteSiteUtilityPage"));
					put(
						"mutation#patchSiteUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"patchSiteUtilityPage"));
					put(
						"mutation#createSiteUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"postSiteUtilityPage"));
					put(
						"mutation#createSiteUtilityPageBatch",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"postSiteUtilityPageBatch"));
					put(
						"mutation#createSiteUtilityPagePageSpecification",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"postSiteUtilityPagePageSpecification"));
					put(
						"mutation#createSiteUtilityPagesPageExportBatch",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"postSiteUtilityPagesPageExportBatch"));
					put(
						"mutation#updateSiteUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"putSiteUtilityPage"));
					put(
						"mutation#updateSiteUtilityPagePermissionsPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"putSiteUtilityPagePermissionsPage"));
					put(
						"mutation#deleteSiteSitePageWidgetInstance",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"deleteSiteSitePageWidgetInstance"));
					put(
						"mutation#patchSiteSitePageWidgetInstance",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"patchSiteSitePageWidgetInstance"));
					put(
						"mutation#createSiteSitePageWidgetInstance",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"postSiteSitePageWidgetInstance"));
					put(
						"mutation#updateSiteSitePageWidgetInstance",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"putSiteSitePageWidgetInstance"));

					put(
						"query#displayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getSiteDisplayPageTemplate"));
					put(
						"query#displayPageTemplateFolderDisplayPageTemplates",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getSiteDisplayPageTemplateFolderDisplayPageTemplatesPage"));
					put(
						"query#displayPageTemplatePermissions",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getSiteDisplayPageTemplatePermissionsPage"));
					put(
						"query#displayPageTemplates",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getSiteDisplayPageTemplatesPage"));
					put(
						"query#displayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getSiteDisplayPageTemplateFolder"));
					put(
						"query#displayPageTemplateFolderPermissions",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getSiteDisplayPageTemplateFolderPermissionsPage"));
					put(
						"query#displayPageTemplateFolders",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getSiteDisplayPageTemplateFoldersPage"));
					put(
						"query#fragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"getSiteFragmentComposition"));
					put(
						"query#fragmentCompositions",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"getSiteFragmentCompositionsPage"));
					put(
						"query#displayPageTemplateFriendlyUrlHistory",
						new ObjectValuePair<>(
							FriendlyUrlHistoryResourceImpl.class,
							"getSiteDisplayPageTemplateFriendlyUrlHistory"));
					put(
						"query#sitePageFriendlyUrlHistory",
						new ObjectValuePair<>(
							FriendlyUrlHistoryResourceImpl.class,
							"getSiteSitePageFriendlyUrlHistory"));
					put(
						"query#utilityPageFriendlyUrlHistory",
						new ObjectValuePair<>(
							FriendlyUrlHistoryResourceImpl.class,
							"getSiteUtilityPageFriendlyUrlHistory"));
					put(
						"query#masterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class, "getSiteMasterPage"));
					put(
						"query#masterPagePermissions",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"getSiteMasterPagePermissionsPage"));
					put(
						"query#masterPages",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"getSiteMasterPagesPage"));
					put(
						"query#pageSpecificationPageExperiencePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"getSitePageSpecificationPageExperiencePageElement"));
					put(
						"query#pageSpecificationPageExperiencePageElementPageElements",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"getSitePageSpecificationPageExperiencePageElementPageElementsPage"));
					put(
						"query#pageSpecificationPageExperiencePageElements",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"getSitePageSpecificationPageExperiencePageElementsPage"));
					put(
						"query#pageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"getSitePageExperience"));
					put(
						"query#pageSpecificationPageExperiences",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"getSitePageSpecificationPageExperiencesPage"));
					put(
						"query#pageExperiencePageRules",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"getSitePageExperiencePageRulesPage"));
					put(
						"query#pageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class, "getSitePageRule"));
					put(
						"query#pageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"getSitePageRuleAction"));
					put(
						"query#pageRulePageRuleActions",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"getSitePageRulePageRuleActionsPage"));
					put(
						"query#pageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"getSitePageRuleCondition"));
					put(
						"query#pageRulePageRuleConditions",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"getSitePageRulePageRuleConditionsPage"));
					put(
						"query#displayPageTemplatePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getSiteDisplayPageTemplatePageSpecificationsPage"));
					put(
						"query#masterPagePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getSiteMasterPagePageSpecificationsPage"));
					put(
						"query#pageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getSitePageSpecification"));
					put(
						"query#pageTemplatePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getSitePageTemplatePageSpecificationsPage"));
					put(
						"query#sitePagePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getSiteSitePagePageSpecificationsPage"));
					put(
						"query#utilityPagePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getSiteUtilityPagePageSpecificationsPage"));
					put(
						"query#pageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getSitePageTemplate"));
					put(
						"query#pageTemplatePermissions",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getSitePageTemplatePermissionsPage"));
					put(
						"query#pageTemplateSetPageTemplates",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getSitePageTemplateSetPageTemplatesPage"));
					put(
						"query#pageTemplates",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getSitePageTemplatesPage"));
					put(
						"query#pageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getSitePageTemplateSet"));
					put(
						"query#pageTemplateSetPermissions",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getSitePageTemplateSetPermissionsPage"));
					put(
						"query#pageTemplateSets",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getSitePageTemplateSetsPage"));
					put(
						"query#sitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class, "getSiteSitePage"));
					put(
						"query#sitePagePermissions",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getSiteSitePagePermissionsPage"));
					put(
						"query#sitePages",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getSiteSitePagesPage"));
					put(
						"query#utilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getSiteUtilityPage"));
					put(
						"query#utilityPagePermissions",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getSiteUtilityPagePermissionsPage"));
					put(
						"query#utilityPages",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getSiteUtilityPagesPage"));
					put(
						"query#sitePageWidgetInstance",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"getSiteSitePageWidgetInstance"));
					put(
						"query#sitePageWidgetInstances",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"getSiteSitePageWidgetInstancesPage"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DisplayPageTemplateResource>
		_displayPageTemplateResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DisplayPageTemplateFolderResource>
		_displayPageTemplateFolderResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FragmentCompositionResource>
		_fragmentCompositionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<MasterPageResource>
		_masterPageResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageElementResource>
		_pageElementResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageExperienceResource>
		_pageExperienceResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageRuleResource>
		_pageRuleResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageRuleActionResource>
		_pageRuleActionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageRuleConditionResource>
		_pageRuleConditionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageSpecificationResource>
		_pageSpecificationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageTemplateResource>
		_pageTemplateResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageTemplateSetResource>
		_pageTemplateSetResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SitePageResource>
		_sitePageResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<UtilityPageResource>
		_utilityPageResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WidgetPageWidgetInstanceResource>
		_widgetPageWidgetInstanceResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FriendlyUrlHistoryResource>
		_friendlyUrlHistoryResourceComponentServiceObjects;

}