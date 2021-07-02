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

import {ClayButtonWithIcon} from '@clayui/button';
import React, {useContext} from 'react';

import {SET_SELECTED_ISSUE} from '../constants/actionTypes';
import {ConstantsContext} from '../context/ConstantsContext';
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
	const {portletNamespace} = useContext(ConstantsContext);
	const dispatch = useContext(StoreDispatchContext);

	const showRefreshButton = data?.validConnection && !data?.privateLayout;

	return (
		<div className="d-flex justify-content-between p-3 sidebar-header">
			<span className="font-weight-bold">
				{Liferay.Language.get('page-audit')}
			</span>
			<div>
				{showRefreshButton && (
					<ClayButtonWithIcon
						className="sidenav-relaunch text-secondary"
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
								portletNamespace,
								url,
							});
						}}
						symbol="reload"
						title={Liferay.Language.get('relaunch')}
					/>
				)}
				<ClayButtonWithIcon
					className="sidenav-close text-secondary"
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
		<div className="d-flex justify-content-between sidebar-header">
			<div>
				<ClayButtonWithIcon
					className="sidenav-back text-secondary"
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

				<span className="font-weight-bold">{selectedIssue.title}</span>
			</div>

			<ClayButtonWithIcon
				className="sidenav-close text-secondary"
				displayType="unstyled"
				symbol="times"
				title={Liferay.Language.get('close')}
			/>
		</div>
	);
};
