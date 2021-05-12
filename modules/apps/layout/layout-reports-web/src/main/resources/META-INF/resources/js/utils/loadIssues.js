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

import {LOAD_DATA, SET_DATA, SET_ERROR} from '../constants/actionTypes';
import APIService from './APIService';

export default function loadIssues({dispatch, portletNamespace, url}) {
	if (url) {
		dispatch({type: LOAD_DATA});

		APIService.getLayoutReportsIssues(
			url.layoutReportsIssuesURL,
			portletNamespace
		)
			.then(({layoutReportsIssues}) => {
				dispatch({
					data: {
						layoutReportsIssues,
					},
					type: SET_DATA,
				});
			})
			.catch(() => {
				dispatch({
					error: {
						buttonTitle: Liferay.Language.get('relaunch'),
						message: Liferay.Language.get('connection-failed'),
					},
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
