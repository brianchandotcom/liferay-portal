/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ALL_SECTIONS_ID} from '../../../utils/contants.es';
import {getBasePath, historyPushWithSlug} from '../../../utils/utils.es';

const useQuestionsURLUtil = ({context, history, params}) => {
	const historyPushParser = historyPushWithSlug(history.push);

	const {creatorId, sectionTitle, tag} = params;

	function buildParams(params) {
		const {
			filterBy = '',
			page,
			pageSize,
			search,
			sortBy = '',
			taggedWith = '',
			selectedTags = [],
		} = params;
		const searchParams = new URLSearchParams();

		searchParams.set('page', page);
		searchParams.set('pagesize', pageSize);

		if (search) {
			searchParams.set('search', search);
		}

		if (filterBy) {
			searchParams.set('filterby', filterBy);
		}

		if (sortBy) {
			searchParams.set('sortby', sortBy);
		}

		if (taggedWith) {
			searchParams.set('taggedwith', taggedWith);

			if (selectedTags.length) {
				searchParams.set('selectedtags', selectedTags.join(','));
			}
		}

		return searchParams.toString();
	}

	function buildURL(params) {
		let url = '/questions';

		const searchParams = buildParams(params);

		if (sectionTitle || sectionTitle === ALL_SECTIONS_ID) {
			url += `/${sectionTitle}`;
		}

		if (tag) {
			url += `/tag/${tag}`;
		}

		if (creatorId) {
			url += `/creator/${creatorId}`;
		}

		return `${url}?${searchParams}`;
	}

	function changePage(params) {
		historyPushParser(buildURL(params));
	}

	const navigateToNewQuestion = () => {
		if (context.redirectToLogin && !themeDisplay.isSignedIn()) {
			const baseURL = getBasePath();

			window.location.replace(
				`/c/portal/login?redirect=${baseURL}${
					context.historyRouterBasePath
						? context.historyRouterBasePath
						: '#'
				}/questions/${sectionTitle}/new`
			);
		}
		else {
			historyPushParser(`/questions/${sectionTitle}/new`);
		}

		return false;
	};

	return {buildParams, changePage, historyPushParser, navigateToNewQuestion};
};

export default useQuestionsURLUtil;
