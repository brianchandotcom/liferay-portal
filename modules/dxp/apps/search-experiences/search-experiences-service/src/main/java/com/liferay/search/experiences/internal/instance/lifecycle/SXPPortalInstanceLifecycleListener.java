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

package com.liferay.search.experiences.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPElementUtil;
import com.liferay.search.experiences.service.SXPElementLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	enabled = true, immediate = true,
	service = PortalInstanceLifecycleListener.class
)
public class SXPPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		for (String fileName : FILE_NAMES) {
			_addSXPElement(company, fileName);
		}
	}

	protected SXPElement readSXPElement(String fileName) {
		String json = StringUtil.read(
			getClass(), "dependencies/" + fileName + ".json");

		return SXPElementUtil.toSXPElement(json);
	}

	protected static final String[] FILE_NAMES = {
		"boost_all_keywords_match", "boost_asset_type",
		"boost_contents_for_the_current_language",
		"boost_contents_in_a_category_by_keyword_match",
		"boost_contents_in_a_category_for_a_user_segment",
		"boost_contents_in_a_category_for_a_period_of_time",
		"boost_contents_in_a_category_for_guest_users",
		"boost_contents_in_a_category_for_new_user_accounts",
		"boost_contents_in_a_category_for_the_time_of_day",
		"boost_contents_in_a_category", "boost_contents_on_my_sites",
		"boost_contents_with_more_versions", "boost_freshness",
		"boost_items_for_my_commerce_account_groups", "boost_longer_contents",
		"boost_proximity", "boost_tagged_contents", "boost_tags_match",
		"boost_web_contents_by_keyword_match", "filter_by_exact_terms_match",
		"filter_by_exact_terms_match", "hide_by_an_exact_term_match",
		"hide_comments", "hide_contents_in_a_category_for_guest_users",
		"hide_contents_in_a_category", "hide_default_user",
		"hide_hidden_contents",
		"limit_search_to_contents_created_within_a_period_of_time",
		"limit_search_to_head_version", "limit_search_to_my_contents",
		"limit_search_to_my_sites", "limit_search_to_pdf_files",
		"limit_search_to_published_contents",
		"limit_search_to_the_current_site", "limit_search_to_these_sites",
		"paste_any_elasticsearch_query", "scheduling_aware",
		"search_with_the_lucene_syntax", "staging_aware",
		"text_match_over_multiple_fields"
	};

	private void _addSXPElement(Company company, String fileName)
		throws Exception {

		SXPElement sxpElement = readSXPElement(fileName);

		if (ListUtil.exists(
				_sxpElementLocalService.getSXPElements(company.getCompanyId()),
				serviceBuilderSXPElement -> Objects.equals(
					MapUtil.getString(sxpElement.getTitle_i18n(), "en_US"),
					serviceBuilderSXPElement.getTitle(LocaleUtil.US)))) {

			// TODO Fix performance issue with getting every SXP element

			return;
		}

		User defaultUser = company.getDefaultUser();

		_sxpElementLocalService.addSXPElement(
			defaultUser.getUserId(),
			LocalizedMapUtil.getLocalizedMap(sxpElement.getDescription_i18n()),
			String.valueOf(sxpElement.getElementDefinition()), true,
			LocalizedMapUtil.getLocalizedMap(sxpElement.getTitle_i18n()), 0,
			new ServiceContext() {
				{
					setAddGroupPermissions(true);
					setAddGuestPermissions(true);
					setCompanyId(company.getCompanyId());
					setScopeGroupId(company.getGroupId());
					setUserId(defaultUser.getUserId());
				}
			});
	}

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

}