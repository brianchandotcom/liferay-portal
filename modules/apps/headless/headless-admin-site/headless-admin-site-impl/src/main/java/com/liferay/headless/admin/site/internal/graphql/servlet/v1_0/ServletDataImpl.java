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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

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
						"mutation#deleteSiteSiteByExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodeDisplayPageTemplate"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodeDisplayPageTemplate"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"postSiteSiteByExternalReferenceCodeDisplayPageTemplate"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"postSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplate"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodeDisplayPageTemplatePageSpecification",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"postSiteSiteByExternalReferenceCodeDisplayPageTemplatePageSpecification"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"putSiteSiteByExternalReferenceCodeDisplayPageTemplate"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodeDisplayPageTemplatePermissionsPage",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"putSiteSiteByExternalReferenceCodeDisplayPageTemplatePermissionsPage"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"putSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage"));
					put(
						"mutation#deleteSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"postSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"putSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"putSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"putSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage"));
					put(
						"mutation#deleteSiteSiteByExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodeFragmentComposition"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodeFragmentComposition"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"postSiteSiteByExternalReferenceCodeFragmentComposition"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"putSiteSiteByExternalReferenceCodeFragmentComposition"));
					put(
						"mutation#deleteSiteSiteByExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodeMasterPage"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodeMasterPage"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"postSiteSiteByExternalReferenceCodeMasterPage"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodeMasterPagePageSpecification",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"postSiteSiteByExternalReferenceCodeMasterPagePageSpecification"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"putSiteSiteByExternalReferenceCodeMasterPage"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodeMasterPagePermissionsPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"putSiteSiteByExternalReferenceCodeMasterPagePermissionsPage"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeMasterPagePermissionsPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeMasterPagePermissionsPage"));
					put(
						"mutation#deleteSiteSiteByExternalReferenceCodePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodePageElement"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodePageElement"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodePageElementFragmentComposition",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"postSiteSiteByExternalReferenceCodePageElementFragmentComposition"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodePageExperiencePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"postSiteSiteByExternalReferenceCodePageExperiencePageElement"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"putSiteSiteByExternalReferenceCodePageElement"));
					put(
						"mutation#deleteSiteSiteByExternalReferenceCodePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodePageExperience"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodePageExperience"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodePageSpecificationPageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"postSiteSiteByExternalReferenceCodePageSpecificationPageExperience"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"putSiteSiteByExternalReferenceCodePageExperience"));
					put(
						"mutation#deleteSiteSiteByExternalReferenceCodePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodePageRule"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodePageRule"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodePageExperiencePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"postSiteSiteByExternalReferenceCodePageExperiencePageRule"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"putSiteSiteByExternalReferenceCodePageRule"));
					put(
						"mutation#deleteSiteSiteByExternalReferenceCodePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodePageRuleAction"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodePageRuleAction"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodePageRulePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"postSiteSiteByExternalReferenceCodePageRulePageRuleAction"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"putSiteSiteByExternalReferenceCodePageRuleAction"));
					put(
						"mutation#deleteSiteSiteByExternalReferenceCodePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodePageRuleCondition"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodePageRuleCondition"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodePageRulePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"postSiteSiteByExternalReferenceCodePageRulePageRuleCondition"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"putSiteSiteByExternalReferenceCodePageRuleCondition"));
					put(
						"mutation#deleteSiteSiteByExternalReferenceCodePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodePageSpecification"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodePageSpecification"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodePageSpecificationPublish",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"postSiteSiteByExternalReferenceCodePageSpecificationPublish"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"putSiteSiteByExternalReferenceCodePageSpecification"));
					put(
						"mutation#deleteSiteSiteByExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodePageTemplate"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodePageTemplate"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"postSiteSiteByExternalReferenceCodePageTemplate"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodePageTemplatePageSpecification",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"postSiteSiteByExternalReferenceCodePageTemplatePageSpecification"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"postSiteSiteByExternalReferenceCodePageTemplateSetPageTemplate"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"putSiteSiteByExternalReferenceCodePageTemplate"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodePageTemplatePermissionsPage",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"putSiteSiteByExternalReferenceCodePageTemplatePermissionsPage"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageTemplatePermissionsPage",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageTemplatePermissionsPage"));
					put(
						"mutation#deleteSiteSiteByExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodePageTemplateSet"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodePageTemplateSet"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"postSiteSiteByExternalReferenceCodePageTemplateSet"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"putSiteSiteByExternalReferenceCodePageTemplateSet"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodePageTemplateSetPermissionsPage",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"putSiteSiteByExternalReferenceCodePageTemplateSetPermissionsPage"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage"));
					put(
						"mutation#deleteSiteSiteByExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodeSitePage"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodeSitePage"));
					put(
						"mutation#createByExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"postByExternalReferenceCodeSitePage"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodeSitePagePageSpecification",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"postSiteSiteByExternalReferenceCodeSitePagePageSpecification"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"putSiteSiteByExternalReferenceCodeSitePage"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodeSitePagePermissionsPage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"putSiteSiteByExternalReferenceCodeSitePagePermissionsPage"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeSitePagePermissionsPage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeSitePagePermissionsPage"));
					put(
						"mutation#deleteSiteSiteByExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodeUtilityPage"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodeUtilityPage"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"postSiteSiteByExternalReferenceCodeUtilityPage"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodeUtilityPagePageSpecification",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"postSiteSiteByExternalReferenceCodeUtilityPagePageSpecification"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"putSiteSiteByExternalReferenceCodeUtilityPage"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodeUtilityPagePermissionsPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"putSiteSiteByExternalReferenceCodeUtilityPagePermissionsPage"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeUtilityPagePermissionsPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeUtilityPagePermissionsPage"));
					put(
						"mutation#deleteSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"deleteSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode"));
					put(
						"mutation#patchSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"patchSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode"));
					put(
						"mutation#createSiteSiteByExternalReferenceCodeSitePageWidgetInstance",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"postSiteSiteByExternalReferenceCodeSitePageWidgetInstance"));
					put(
						"mutation#updateSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"putSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode"));

					put(
						"query#siteByExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeDisplayPageTemplate"));
					put(
						"query#siteByExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplates",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage"));
					put(
						"query#siteByExternalReferenceCodeDisplayPageTemplatePermissions",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeDisplayPageTemplatePermissionsPage"));
					put(
						"query#siteByExternalReferenceCodeDisplayPageTemplates",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeDisplayPageTemplatesPage"));
					put(
						"query#siteExternalReferenceCodeDisplayPageTemplatePermissions",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage"));
					put(
						"query#siteByExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"query#siteByExternalReferenceCodeDisplayPageTemplateFolderPermissions",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage"));
					put(
						"query#siteByExternalReferenceCodeDisplayPageTemplateFolders",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeDisplayPageTemplateFoldersPage"));
					put(
						"query#siteExternalReferenceCodeDisplayPageTemplateFolderPermissions",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage"));
					put(
						"query#siteByExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeFragmentComposition"));
					put(
						"query#siteByExternalReferenceCodeFragmentCompositions",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeFragmentCompositionsPage"));
					put(
						"query#siteByExternalReferenceCodeDisplayPageTemplateFriendlyUrlHistory",
						new ObjectValuePair<>(
							FriendlyUrlHistoryResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeDisplayPageTemplateFriendlyUrlHistory"));
					put(
						"query#siteByExternalReferenceCodeSitePageFriendlyUrlHistory",
						new ObjectValuePair<>(
							FriendlyUrlHistoryResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeSitePageFriendlyUrlHistory"));
					put(
						"query#siteByExternalReferenceCodeUtilityPageFriendlyUrlHistory",
						new ObjectValuePair<>(
							FriendlyUrlHistoryResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeUtilityPageFriendlyUrlHistory"));
					put(
						"query#siteByExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeMasterPage"));
					put(
						"query#siteByExternalReferenceCodeMasterPagePermissions",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeMasterPagePermissionsPage"));
					put(
						"query#siteByExternalReferenceCodeMasterPages",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeMasterPagesPage"));
					put(
						"query#siteExternalReferenceCodeMasterPagePermissions",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeMasterPagePermissionsPage"));
					put(
						"query#siteByExternalReferenceCodePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageElement"));
					put(
						"query#siteByExternalReferenceCodePageElementPageElements",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageElementPageElementsPage"));
					put(
						"query#siteByExternalReferenceCodePageExperiencePageElements",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageExperiencePageElementsPage"));
					put(
						"query#siteByExternalReferenceCodePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageExperience"));
					put(
						"query#siteByExternalReferenceCodePageSpecificationPageExperiences",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageSpecificationPageExperiencesPage"));
					put(
						"query#siteByExternalReferenceCodePageExperiencePageRules",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageExperiencePageRulesPage"));
					put(
						"query#siteByExternalReferenceCodePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageRule"));
					put(
						"query#siteByExternalReferenceCodePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageRuleAction"));
					put(
						"query#siteByExternalReferenceCodePageRulePageRuleActions",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageRulePageRuleActionsPage"));
					put(
						"query#siteByExternalReferenceCodePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageRuleCondition"));
					put(
						"query#siteByExternalReferenceCodePageRulePageRuleConditions",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageRulePageRuleConditionsPage"));
					put(
						"query#siteByExternalReferenceCodeDisplayPageTemplatePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeDisplayPageTemplatePageSpecificationsPage"));
					put(
						"query#siteByExternalReferenceCodeMasterPagePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeMasterPagePageSpecificationsPage"));
					put(
						"query#siteByExternalReferenceCodePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageSpecification"));
					put(
						"query#siteByExternalReferenceCodePageTemplatePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageTemplatePageSpecificationsPage"));
					put(
						"query#siteByExternalReferenceCodeSitePagePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeSitePagePageSpecificationsPage"));
					put(
						"query#siteByExternalReferenceCodeUtilityPagePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeUtilityPagePageSpecificationsPage"));
					put(
						"query#siteByExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageTemplate"));
					put(
						"query#siteByExternalReferenceCodePageTemplatePermissions",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageTemplatePermissionsPage"));
					put(
						"query#siteByExternalReferenceCodePageTemplateSetPageTemplates",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageTemplateSetPageTemplatesPage"));
					put(
						"query#siteByExternalReferenceCodePageTemplates",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageTemplatesPage"));
					put(
						"query#siteExternalReferenceCodePageTemplatePermissions",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageTemplatePermissionsPage"));
					put(
						"query#siteByExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageTemplateSet"));
					put(
						"query#siteByExternalReferenceCodePageTemplateSetPermissions",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageTemplateSetPermissionsPage"));
					put(
						"query#siteByExternalReferenceCodePageTemplateSets",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getSiteSiteByExternalReferenceCodePageTemplateSetsPage"));
					put(
						"query#siteExternalReferenceCodePageTemplateSetPermissions",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage"));
					put(
						"query#siteByExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeSitePage"));
					put(
						"query#siteByExternalReferenceCodeSitePagePermissions",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeSitePagePermissionsPage"));
					put(
						"query#siteByExternalReferenceCodeSitePages",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeSitePagesPage"));
					put(
						"query#siteExternalReferenceCodeSitePagePermissions",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeSitePagePermissionsPage"));
					put(
						"query#siteByExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeUtilityPage"));
					put(
						"query#siteByExternalReferenceCodeUtilityPagePermissions",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeUtilityPagePermissionsPage"));
					put(
						"query#siteByExternalReferenceCodeUtilityPages",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeUtilityPagesPage"));
					put(
						"query#siteExternalReferenceCodeUtilityPagePermissions",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeUtilityPagePermissionsPage"));
					put(
						"query#siteByExternalReferenceCodeSitePageWidgetInstances",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeSitePageWidgetInstancesPage"));
					put(
						"query#siteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"getSiteSiteByExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode"));
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