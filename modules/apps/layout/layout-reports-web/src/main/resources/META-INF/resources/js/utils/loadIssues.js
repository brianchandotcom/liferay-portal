/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LOAD_DATA, SET_ERROR, SET_ISSUES} from '../constants/actionTypes';
import APIService from './APIService';

export default function loadIssues({
	dispatch,
	languageId,
	refreshCache = true,
	url,
}) {
	if (url) {
		dispatch({type: LOAD_DATA});

		APIService.getLayoutReportsIssues(
			url.layoutReportsIssuesURL,
			refreshCache
		)
			.then(({layoutReportsIssues}) => {
				dispatch({
					languageId,
					layoutReportsIssues,
					type: SET_ISSUES,
				});
			})
			.catch((error = {}) => {
				dispatch({
					error: error.googlePageSpeedError.error || error,
					type: SET_ERROR,
				});
			});
	}
	else {
		dispatch({
			error: Liferay.Language.get('an-unexpected-error-occurred'),
			type: SET_ERROR,
		});
	}
}
