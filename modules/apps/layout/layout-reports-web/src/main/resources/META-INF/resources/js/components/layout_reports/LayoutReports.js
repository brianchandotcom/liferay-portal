/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useEffect} from 'react';

import {LOAD_DATA, SET_DATA, SET_ERROR} from '../../constants/actionTypes';
import {ConstantsContext} from '../../context/ConstantsContext';
import {
	StoreDispatchContext,
	StoreStateContext,
} from '../../context/StoreContext';
import loadIssues from '../../utils/loadIssues';
import ErrorAlert from '../error-alert/ErrorAlert';
import BasicInformation from './BasicInformation';
import IssueDetail from './IssueDetail';
import IssuesList from './IssuesList';
import NotConfigured from './NotConfigured';

export default function LayoutReports({eventTriggered, url}) {
	const isMounted = useIsMounted();

	const {data, error, languageId, loading, selectedIssue} = useContext(
		StoreStateContext
	);

	const {isPanelStateOpen, layoutReportsDataURL} = useContext(
		ConstantsContext
	);

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
		[languageId, safeDispatch]
	);

	useEffect(() => {
		if (isPanelStateOpen && !data && !loading) {
			getData(
				Liferay.FeatureFlags['LPS-187284'] ? url : layoutReportsDataURL
			);
		}
	}, [data, isPanelStateOpen, layoutReportsDataURL, loading, getData, url]);

	useEffect(() => {
		if (eventTriggered && !data) {
			getData(
				Liferay.FeatureFlags['LPS-187284'] ? url : layoutReportsDataURL
			);
		}
	}, [eventTriggered, data, layoutReportsDataURL, getData, url]);

	if (!data) {
		return null;
	}

	const hasError = (data.validConnection && error) || data.privateLayout;
	const notConfigured = !loading && !data.validConnection;
	const hasApiKey = !notConfigured;

	return (
		<>
			{hasApiKey && (
				<div
					className={classNames('c-pb-3', {
						'c-px-3': !Liferay.FeatureFlags['LPS-187284'],
					})}
				>
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
