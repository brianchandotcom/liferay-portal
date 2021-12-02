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

package com.liferay.search.experiences.internal.storage;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.search.experiences.constants.SXPElementConstants;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.model.impl.SXPElementImpl;
import com.liferay.search.experiences.rest.dto.v1_0.util.ElementDefinitionUtil;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPElementUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Petteri Karttunen
 */
public class SystemSXPElementStorage {

	public static SXPElement getSXPElement(long sxpElementId) {
		List<SXPElement> sxpeElements = ListUtil.filter(
			_sxpElements,
			sxpElement -> sxpElement.getSXPElementId() == sxpElementId);

		if (!sxpeElements.isEmpty()) {
			return sxpeElements.get(0);
		}

		return null;
	}

	public static List<SXPElement> getSXPElements() {
		return _sxpElements;
	}

	public static int getSXPElementsCount() {
		return _sxpElements.size();
	}

	protected static com.liferay.search.experiences.rest.dto.v1_0.SXPElement
		readSXPElement(String fileName) {

		String json = StringUtil.read(
			SystemSXPElementStorage.class,
			"dependencies/" + fileName + ".json");

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

	private static SXPElement _toSXPElement(
			com.liferay.search.experiences.rest.dto.v1_0.SXPElement
				sxpElementDTO)
		throws PortalException {

		SXPElement sxpElement = new SXPElementImpl();

		// TODO

		//sxpElement.setPrimaryKey(sxpElement.getSXPElementId());
		sxpElement.setPrimaryKey(CounterLocalServiceUtil.increment());

		// TODO

		// sxpElement.setUuid(uuid);

		UUID uuid = UUID.randomUUID();

		sxpElement.setUuid(uuid.toString());

		Company company = CompanyLocalServiceUtil.getCompany(
			PortalUtil.getDefaultCompanyId());

		User user = company.getDefaultUser();

		sxpElement.setCompanyId(company.getCompanyId());

		sxpElement.setUserId(user.getUserId());
		sxpElement.setUserName(user.getFullName());
		sxpElement.setCreateDate(sxpElementDTO.getCreateDate());
		sxpElement.setModifiedDate(sxpElementDTO.getModifiedDate());

		sxpElement.setDescriptionMap(
			LocalizedMapUtil.getLocalizedMap(
				LocaleUtil.US, sxpElementDTO.getDescription(),
				sxpElementDTO.getDescription_i18n()));
		sxpElement.setElementDefinitionJSON(
			String.valueOf(
				ElementDefinitionUtil.unpack(
					sxpElementDTO.getElementDefinition())));
		sxpElement.setHidden(false);
		sxpElement.setReadOnly(true);
		sxpElement.setTitleMap(
			LocalizedMapUtil.getLocalizedMap(
				LocaleUtil.US, sxpElementDTO.getTitle(),
				sxpElementDTO.getTitle_i18n()));
		sxpElement.setType(
			GetterUtil.getInteger(
				sxpElementDTO.getType(), SXPElementConstants.TYPE_QUERY));
		sxpElement.setStatus(WorkflowConstants.STATUS_APPROVED);

		return sxpElement;
	}

	private static final List<SXPElement> _sxpElements =
		new ArrayList<SXPElement>() {
			{
				try {
					for (String fileName : FILE_NAMES) {
						add(_toSXPElement(readSXPElement(fileName)));
					}
				}
				catch (PortalException portalException) {
					throw new RuntimeException(portalException);
				}
			}
		};

}