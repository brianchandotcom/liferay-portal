/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import BOOST_ALL_KEYWORDS_MATCH from '../../elements/boost-all-keywords-match';
import BOOST_ASSET_TYPE from '../../elements/boost-asset-type';
import BOOST_COMMERCE_ITEMS_FOR_MY_ACCOUNT_GROUPS from '../../elements/boost-commerce-items-for-my-account-groups';
import BOOST_CONTENTS_IN_A_CATEGORY from '../../elements/boost-contents-in-a-category';
import BOOST_CONTENTS_IN_A_CATEGORY_BY_KEYWORD_MATCH from '../../elements/boost-contents-in-a-category-by-keyword-match';
import BOOST_CONTENTS_IN_A_CATEGORY_FOR_A_PERIOD_OF_TIME from '../../elements/boost-contents-in-a-category-for-a-period-of-time';
import BOOST_CONTENTS_IN_A_CATEGORY_FOR_A_USER_SEGMENT from '../../elements/boost-contents-in-a-category-for-a-user-segment';
import BOOST_CONTENTS_IN_A_CATEGORY_FOR_GUEST_USERS from '../../elements/boost-contents-in-a-category-for-guest-users';
import BOOST_CONTENTS_IN_A_CATEGORY_FOR_NEW_USER_ACCOUNTS from '../../elements/boost-contents-in-a-category-for-new-user-accounts';
import BOOST_CONTENTS_IN_A_CATEGORY_FOR_TIME_OF_DAY from '../../elements/boost-contents-in-a-category-for-time-of-day';
import BOOST_CONTENTS_ON_MY_SITES from '../../elements/boost-contents-on-my-sites';
import BOOST_CONTENTS_WITH_MORE_VERSIONS from '../../elements/boost-contents-with-more-versions';
import BOOST_CONTENTS_WITH_USERS_LANGUAGE_AS_THE_DEFAULT_LANGUAGE from '../../elements/boost-contents-with-user-language-as-the-default-language';
import BOOST_FRESHNESS from '../../elements/boost-freshness';
import BOOST_LONGER_CONTENTS from '../../elements/boost-longer-contents';
import BOOST_PROXIMITY from '../../elements/boost-proximity';
import BOOST_TAGGED_CONTENTS from '../../elements/boost-tagged-contents';
import BOOST_TAGS_MATCH from '../../elements/boost-tags-match';
import BOOST_WEB_CONTENTS_BY_KEYWORD_MATCH from '../../elements/boost-web-contents-by-keyword-match';
import FILTER_BY_EXACT_TERMS_MATCH from '../../elements/filter-by-exact-terms-match';
import HIDE_BY_EXACT_TERM_MATCH from '../../elements/hide-by-exact-term-match';
import HIDE_CONTENTS_IN_A_CATEGORY from '../../elements/hide-contents-in-a-category';
import HIDE_CONTENTS_IN_A_CATEGORY_FOR_GUEST_USERS from '../../elements/hide-contents-in-a-category-for-guest-users';
import HIDE_DEFAULT_USER from '../../elements/hide-default-user';
import HIDE_DISCUSSIONS from '../../elements/hide-discussions';
import HIDE_HIDDEN_CONTENTS from '../../elements/hide-hidden-contents';
import HIDE_TAGGED_CONTENTS from '../../elements/hide-tagged-contents';
import LIMIT_SEARCH_TO_PDF_FILES from '../../elements/limit-search-to-PDF-files';
import LIMIT_SEARCH_TO_CONTENTS_CREATED_WITHIN_A_PERIOD_OF_TIME from '../../elements/limit-search-to-contents-created-within-a-period-of-time';
import LIMIT_SEARCH_TO_HEAD_VERSION from '../../elements/limit-search-to-head-version';
import LIMIT_SEARCH_TO_MY_CONTENTS from '../../elements/limit-search-to-my-contents';
import LIMIT_SEARCH_TO_MY_SITES from '../../elements/limit-search-to-my-sites';
import LIMIT_SEARCH_TO_PUBLISHED_CONTENTS from '../../elements/limit-search-to-published-contents';
import LIMIT_SEARCH_TO_THE_CURRENT_SITE from '../../elements/limit-search-to-the-current-site';
import LIMIT_SEARCH_TO_THESE_SITES from '../../elements/limit-search-to-these-sites';
import PASTE_AN_ELASTICSEARCH_QUERY from '../../elements/paste-an-elasticsearch-query';
import SCHEDULING_AWARE from '../../elements/scheduling-aware';
import SEARCH_WITH_LUCENE_SYNTAX from '../../elements/search-with-lucene-syntax';
import STAGING_AWARE from '../../elements/staging-aware';
import TEXT_MATCH_OVER_MULTIPLE_FIELDS from '../../elements/text-match-over-multiple-fields';

export const CUSTOM_JSON_ELEMENT = {
	elementTemplateJSON: {
		category: 'custom',
		clauses: [],
		conditions: [],
		description: Liferay.Language.get('editable-json-text-area'),
		enabled: true,
		icon: 'custom-field',
		title: Liferay.Language.get('custom-json-element'),
	},
};

export const DEFAULT_ADVANCED_CONFIGURATION = {
	highlighting: {
		enabled: true,
		fields: [
			{
				field: 'localized_title${context.language_id}',
				fragment_size: 50,
				number_of_fragments: 3,
			},
			{
				field: 'content${context.language_id}',
			},
		],
		fragment_size: 100,
		number_of_fragments: 5,
		require_field_match: true,
	},
	query_processing: {
		exclude_query_contributors: '',
		exclude_query_post_processors: '',
	},
	source: {
		fetch_source: true,
		source_excludes: '',
		source_includes: '',
	},
};

export const DEFAULT_BASELINE_ELEMENTS = [
	TEXT_MATCH_OVER_MULTIPLE_FIELDS,
	STAGING_AWARE,
	SCHEDULING_AWARE,
	LIMIT_SEARCH_TO_HEAD_VERSION,
	LIMIT_SEARCH_TO_PUBLISHED_CONTENTS,
	HIDE_HIDDEN_CONTENTS,
	HIDE_DISCUSSIONS,
	HIDE_DEFAULT_USER,
];

export const DEFAULT_EDIT_ELEMENT = TEXT_MATCH_OVER_MULTIPLE_FIELDS;

export const DEFAULT_PARAMETER_CONFIGURATION = {};

export const DEFAULT_SORT_CONFIGURATION = {};

export const QUERY_ELEMENTS = [
	TEXT_MATCH_OVER_MULTIPLE_FIELDS,
	SEARCH_WITH_LUCENE_SYNTAX,
	FILTER_BY_EXACT_TERMS_MATCH,
	STAGING_AWARE,
	SCHEDULING_AWARE,
	LIMIT_SEARCH_TO_PUBLISHED_CONTENTS,
	LIMIT_SEARCH_TO_HEAD_VERSION,
	LIMIT_SEARCH_TO_THE_CURRENT_SITE,
	LIMIT_SEARCH_TO_MY_SITES,
	LIMIT_SEARCH_TO_THESE_SITES,
	LIMIT_SEARCH_TO_MY_CONTENTS,
	LIMIT_SEARCH_TO_CONTENTS_CREATED_WITHIN_A_PERIOD_OF_TIME,
	LIMIT_SEARCH_TO_PDF_FILES,
	BOOST_ALL_KEYWORDS_MATCH,
	BOOST_TAGS_MATCH,
	BOOST_LONGER_CONTENTS,
	BOOST_CONTENTS_WITH_MORE_VERSIONS,
	BOOST_CONTENTS_WITH_USERS_LANGUAGE_AS_THE_DEFAULT_LANGUAGE,
	BOOST_FRESHNESS,
	BOOST_PROXIMITY,
	BOOST_ASSET_TYPE,
	BOOST_CONTENTS_IN_A_CATEGORY,
	BOOST_TAGGED_CONTENTS,
	BOOST_CONTENTS_ON_MY_SITES,
	HIDE_BY_EXACT_TERM_MATCH,
	HIDE_DISCUSSIONS,
	HIDE_DEFAULT_USER,
	HIDE_HIDDEN_CONTENTS,
	HIDE_CONTENTS_IN_A_CATEGORY,
	HIDE_TAGGED_CONTENTS,
	BOOST_COMMERCE_ITEMS_FOR_MY_ACCOUNT_GROUPS,
	BOOST_WEB_CONTENTS_BY_KEYWORD_MATCH,
	BOOST_CONTENTS_IN_A_CATEGORY_BY_KEYWORD_MATCH,
	BOOST_CONTENTS_IN_A_CATEGORY_FOR_A_PERIOD_OF_TIME,
	BOOST_CONTENTS_IN_A_CATEGORY_FOR_A_USER_SEGMENT,
	BOOST_CONTENTS_IN_A_CATEGORY_FOR_TIME_OF_DAY,
	BOOST_CONTENTS_IN_A_CATEGORY_FOR_NEW_USER_ACCOUNTS,
	BOOST_CONTENTS_IN_A_CATEGORY_FOR_GUEST_USERS,
	HIDE_CONTENTS_IN_A_CATEGORY_FOR_GUEST_USERS,
	PASTE_AN_ELASTICSEARCH_QUERY,
];

export const PREDEFINED_VARIABLES = [
	{
		categoryName: 'User',
		variables: [
			{
				className: 'String',
				description: "Current user's full name",
				name: 'Full Name',
				variable: 'user_full_name',
			},
			{
				className: 'Number',
				description: "Current user's age",
				name: 'Age',
				variable: 'user_age',
			},
		],
	},
	{
		categoryName: 'Context',
		variables: [
			{
				className: 'String',
				description: 'Context Title',
				name: 'Title',
				variable: 'context_title',
			},
			{
				className: 'String',
				description: 'Current context description',
				name: 'Description',
				variable: 'context_description',
			},
			{
				className: 'Number',
				description: 'Current context boost',
				name: 'Boost',
				variable: 'context_boost',
			},
		],
	},
];
