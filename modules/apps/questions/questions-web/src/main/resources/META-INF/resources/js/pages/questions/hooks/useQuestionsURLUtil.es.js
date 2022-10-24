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

import {ALL_SECTIONS_ID} from '../../../utils/contants.es';
import {getBasePath, historyPushWithSlug} from '../../../utils/utils.es';

const useQuestionsURLUtil = ({context, history, params}) => {
	const historyPushParser = historyPushWithSlug(history.push);

	const {creatorId, sectionTitle, tag} = params;

	function buildURL(params) {
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

		let url = '/questions';

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

		if (sectionTitle || sectionTitle === ALL_SECTIONS_ID) {
			url += `/${sectionTitle}`;
		}

		if (tag) {
			url += `/tag/${tag}`;
		}

		if (creatorId) {
			url += `/creator/${creatorId}`;
		}

		return `${url}?${searchParams.toString()}`;
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

	return {changePage, historyPushParser, navigateToNewQuestion};
};

export default useQuestionsURLUtil;
