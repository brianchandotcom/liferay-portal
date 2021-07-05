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

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useEffect} from 'react';

import {LOAD_DATA, SET_DATA, SET_ERROR} from '../constants/actionTypes';
import {ConstantsContext} from '../context/ConstantsContext';
import {StoreDispatchContext, StoreStateContext} from '../context/StoreContext';
import loadIssues from '../utils/loadIssues';
import BasicInformation from './BasicInformation';
import IssueDetail from './IssueDetail';
import IssuesList from './IssuesList';
import NotConfigured from './NotConfigured';
import ErrorAlert from './error-alert/ErrorAlert';

export default function LayoutReports({eventTriggered}) {
	const isMounted = useIsMounted();

	const {data, error, languageId, loading, selectedIssue} = useContext(
		StoreStateContext
	);

	const {
		isPanelStateOpen,
		layoutReportsDataURL,
		portletNamespace,
	} = useContext(ConstantsContext);

	const dispatch = useContext(StoreDispatchContext);

	const safeDispatch = useCallback(
		(action) => {
			if (isMounted()) {
				dispatch(action);
			}
		},
		[dispatch, isMounted]
	);

	const getData = useCallback(
		(fetchURL) => {
			safeDispatch({type: LOAD_DATA});

			fetch(fetchURL, {method: 'GET'})
				.then((response) =>
					response.json().then((data) => {
						safeDispatch({
							data,
							loading: data.validConnection,
							type: SET_DATA,
						});

						if (data.validConnection) {
							const url = data.pageURLs.find(
								(pageURL) =>
									pageURL.languageId ===
									(languageId || data.defaultLanguageId)
							);

							loadIssues({
								dispatch: safeDispatch,
								languageId:
									languageId || data.defaultLanguageId,
								portletNamespace,
								refreshCache: false,
								url,
							});
						}
					})
				)
				.catch(() => {
					safeDispatch({
						error: Liferay.Language.get(
							'an-unexpected-error-occurred'
						),
						type: SET_ERROR,
					});
				});
		},
		[languageId, portletNamespace, safeDispatch]
	);

	useEffect(() => {
		if (isPanelStateOpen && !data && !loading) {
			getData(layoutReportsDataURL);
		}
	}, [data, isPanelStateOpen, layoutReportsDataURL, loading, getData]);

	useEffect(() => {
		if (eventTriggered && !data) {
			getData(layoutReportsDataURL);
		}
	}, [eventTriggered, data, layoutReportsDataURL, getData]);

	if (!data) {
		return null;
	}

	const hasError = (data.validConnection && error) || data.privateLayout;
	const notConfigured = !loading && !data.validConnection;
	const hasApiKey = !notConfigured;

	return (
		<>
			{hasApiKey && (
				<div className="pb-3 px-3">
					<BasicInformation
						defaultLanguageId={data.defaultLanguageId}
						pageURLs={data.pageURLs}
						selectedLanguageId={languageId}
					/>
				</div>
			)}
			{hasError ? (
				<ErrorAlert />
			) : notConfigured ? (
				<NotConfigured />
			) : selectedIssue ? (
				<IssueDetail />
			) : (
				<IssuesList />
			)}
		</>
	);
}

LayoutReports.propTypes = {
	eventTriggered: PropTypes.bool.isRequired,
};
