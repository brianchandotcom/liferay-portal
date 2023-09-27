/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {fetch, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useEffect} from 'react';

import {LOAD_DATA, SET_DATA, SET_ERROR} from '../../constants/actionTypes';
import {ConstantsContext} from '../../context/ConstantsContext';
import {
	StoreDispatchContext,
	StoreStateContext,
} from '../../context/StoreContext';
import loadIssues from '../../utils/loadIssues';
import BasicInformation from './BasicInformation';
import ErrorAlert from './ErrorAlert';
import IssueDetail from './IssueDetail';
import IssuesList from './IssuesList';
import NotConfigured from './NotConfigured';

export default function LayoutReports({eventTriggered, url}) {
	const isMounted = useIsMounted();

	const {data, error, languageId, loading, selectedItem} = useContext(
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

	const updateData = () => {
		const url = data.pageURLs.find(
			(pagelURL) =>
				pagelURL.languageId === (languageId || data.defaultLanguageId)
		);

		loadIssues({
			dispatch,
			languageId,
			url,
		});
	};

	useEffect(() => {
		if (Liferay.FeatureFlags['LPS-187284'] && !data && !loading) {
			getData(url);
		}
		else if (isPanelStateOpen && !data && !loading) {
			getData(layoutReportsDataURL);
		}
	}, [data, isPanelStateOpen, layoutReportsDataURL, loading, getData, url]);

	useEffect(() => {
		if (!Liferay.FeatureFlags['LPS-187284'] && eventTriggered && !data) {
			getData(layoutReportsDataURL);
		}
	}, [eventTriggered, data, layoutReportsDataURL, getData]);

	if (!data) {
		return null;
	}

	const hasError = (data.validConnection && error) || data.privateLayout;
	const localizedIssues = data.layoutReportsIssues?.[languageId];
	const notConfigured = !loading && !data.validConnection;
	const showAlert =
		!selectedItem &&
		!loading &&
		localizedIssues &&
		data.validConnection &&
		!data.privateLayout;

	const hasApiKey = !notConfigured;

	const Component = () => {
		if (hasError) {
			return <ErrorAlert />;
		}
		else if (notConfigured) {
			return <NotConfigured />;
		}
		else if (selectedItem && !Liferay.FeatureFlags['LPS-187284']) {
			return <IssueDetail />;
		}
		else {
			return <IssuesList />;
		}
	};

	return (
		<>
			{hasApiKey && (
				<>
					{Liferay.FeatureFlags['LPS-187284'] && showAlert ? (
						<ClayAlert
							className="c-mb-4"
							displayType="info"
							role="none"
						>
							{sub(
								Liferay.Language.get('showing-data-from-x'),
								localizedIssues.date
							)}

							<ClayAlert.Footer>
								<ClayButton.Group>
									<ClayButton alert onClick={updateData}>
										{Liferay.Language.get(
											'relaunch-to-update-data'
										)}
									</ClayButton>
								</ClayButton.Group>
							</ClayAlert.Footer>
						</ClayAlert>
					) : null}

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
				</>
			)}
			<Component />
		</>
	);
}

LayoutReports.propTypes = {
	eventTriggered: PropTypes.bool,
};
