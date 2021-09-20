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

package com.liferay.search.experiences.internal.blueprint.parameter.contributor;

import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.blueprint.parameter.BooleanSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.LongSXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterContributionDefinition;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterContributor;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterDataBuilder;
import com.liferay.search.experiences.blueprint.parameter.StringSXPParameter;
import com.liferay.search.experiences.internal.blueprint.util.SearchContextUtil;
import com.liferay.search.experiences.internal.problem.ProblemUtil;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=context",
	service = SXPParameterContributor.class
)
public class ContextSXPParameterContributor implements SXPParameterContributor {

	@Override
	public void contribute(
		SearchRequestBuilder searchRequestBuilder, SXPBlueprint sxpBlueprint,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		_addCompanyId(searchRequestBuilder, sxpParameterDataBuilder);

		_addCTCollectionParameter(sxpParameterDataBuilder);

		_addGroupParameters(searchRequestBuilder, sxpParameterDataBuilder);

		_addLanguage(searchRequestBuilder, sxpParameterDataBuilder);

		_addLayoutParameters(searchRequestBuilder, sxpParameterDataBuilder);
	}

	@Override
	public String getCategoryNameKey() {
		return "context";
	}

	@Override
	public List<SXPParameterContributionDefinition>
		getSXPParameterContributionDefinitions() {

		return ListUtil.fromArray(
			new SXPParameterContributionDefinition(
				LongSXPParameter.class.getName(), "company-id",
				"context.company_id"),
			new SXPParameterContributionDefinition(
				LongSXPParameter.class.getName(), "ct-collection-id",
				"context.ct_collection_id"),
			new SXPParameterContributionDefinition(
				LongSXPParameter.class.getName(), "scope-group-id",
				"context.scope_group_id"),
			new SXPParameterContributionDefinition(
				StringSXPParameter.class.getName(), "layout-locale-name",
				"context.layout_locale_name"),
			new SXPParameterContributionDefinition(
				LongSXPParameter.class.getName(), "plid", "context.plid"),
			new SXPParameterContributionDefinition(
				StringSXPParameter.class.getName(), "language",
				"context.language"),
			new SXPParameterContributionDefinition(
				StringSXPParameter.class.getName(), "language-id",
				"context.language_id"));
	}

	private void _addCompanyId(
		SearchRequestBuilder searchRequestBuilder,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		sxpParameterDataBuilder.addSXPParameter(
			new LongSXPParameter(
				"context.company_id", true,
				SearchContextUtil.getCompanyId(searchRequestBuilder)));
	}

	private void _addCTCollectionParameter(
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		sxpParameterDataBuilder.addSXPParameter(
			new LongSXPParameter(
				"context.ct_collection_id", true,
				CTCollectionThreadLocal.getCTCollectionId()));
	}

	private void _addGroupParameters(
		SearchRequestBuilder searchRequestBuilder,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		Long scopeGroupId = SearchContextUtil.getLongAttribute(
			"search.experiences.scope_group_id", searchRequestBuilder);

		if (scopeGroupId == null) {
			return;
		}

		sxpParameterDataBuilder.addSXPParameter(
			new LongSXPParameter("context.scope_group_id", true, scopeGroupId));

		Group group = _getGroup(scopeGroupId);

		if (group == null) {
			return;
		}

		sxpParameterDataBuilder.addSXPParameter(
			new BooleanSXPParameter(
				"context.is_staging_group", true, group.isStagingGroup()));
	}

	private void _addLanguage(
		SearchRequestBuilder searchRequestBuilder,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		Locale locale = SearchContextUtil.getLocale(searchRequestBuilder);

		sxpParameterDataBuilder.addSXPParameter(
			new StringSXPParameter(
				"context.language_id", true,
				"_" + _language.getLanguageId(locale)));

		sxpParameterDataBuilder.addSXPParameter(
			new StringSXPParameter(
				"context.language", true, locale.getLanguage()));
	}

	private void _addLayoutParameters(
		SearchRequestBuilder searchRequestBuilder,
		SXPParameterDataBuilder sxpParameterDataBuilder) {

		Layout layout = SearchContextUtil.getLayout(searchRequestBuilder);

		if (layout == null) {
			return;
		}

		sxpParameterDataBuilder.addSXPParameter(
			new StringSXPParameter(
				"context.layout_locale_name", true,
				layout.getName(
					SearchContextUtil.getLocale(searchRequestBuilder), true)));

		sxpParameterDataBuilder.addSXPParameter(
			new LongSXPParameter("plid", true, layout.getPlid()));
	}

	private Group _getGroup(long groupId) {
		try {
			return _groupLocalService.getGroup(groupId);
		}
		catch (PortalException portalException) {
			ProblemUtil.addError(
				getClass().getName(), "group-not-found", null, null,
				GetterUtil.getString(groupId), portalException);
		}

		return null;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

}