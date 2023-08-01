/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React, {useContext} from 'react';

import {SET_SELECTED_ISSUE} from '../constants/actionTypes';
import {StoreDispatchContext, StoreStateContext} from '../context/StoreContext';
import loadIssues from '../utils/loadIssues';

export default function SidebarHeader() {
	const {selectedIssue} = useContext(StoreStateContext);

	return selectedIssue ? (
		<IssueDetailSidebarHeader />
	) : (
		<DefaultSidebarHeader />
	);
}

const DefaultSidebarHeader = () => {
	const {data, languageId, loading} = useContext(StoreStateContext);
	const dispatch = useContext(StoreDispatchContext);

	const showRefreshButton = data?.validConnection && !data?.privateLayout;

	return (
		<div className="d-flex justify-content-between sidebar-header">
			<span className="font-weight-bold">
				{Liferay.Language.get('page-audit')}
			</span>

			<div>
				{!Liferay.FeatureFlags['LPS-187284'] && showRefreshButton && (
					<ClayButtonWithIcon
						className="component-action mr-2 sidenav-relaunch text-secondary"
						disabled={loading}
						displayType="unstyled"
						onClick={() => {
							const url = data.pageURLs.find(
								(pagelURL) =>
									pagelURL.languageId ===
									(languageId || data.defaultLanguageId)
							);

							loadIssues({
								dispatch,
								languageId,
								url,
							});
						}}
						symbol="reload"
						title={Liferay.Language.get('relaunch')}
					/>
				)}

				<ClayButtonWithIcon
					className="component-action sidenav-close text-secondary"
					displayType="unstyled"
					symbol="times"
					title={Liferay.Language.get('close')}
				/>
			</div>
		</div>
	);
};

const IssueDetailSidebarHeader = () => {
	const {selectedIssue} = useContext(StoreStateContext);
	const dispatch = useContext(StoreDispatchContext);

	return (
		<div className="d-flex justify-content-between p-3 sidebar-header">
			<div className="d-flex">
				<ClayButtonWithIcon
					className="align-items-start component-action flex-shrink-0 mr-2 sidenav-back text-secondary"
					displayType="unstyled"
					onClick={() => {
						dispatch({
							issue: null,
							type: SET_SELECTED_ISSUE,
						});
					}}
					symbol="angle-left"
					title={Liferay.Language.get('go-back')}
				/>

				<span className="align-self-center font-weight-bold issue-detail-title">
					{selectedIssue.title}
				</span>
			</div>

			<ClayButtonWithIcon
				className="component-action flex-shrink-0 sidenav-close text-secondary"
				displayType="unstyled"
				symbol="times"
				title={Liferay.Language.get('close')}
			/>
		</div>
	);
};
