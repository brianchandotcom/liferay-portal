/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {fetch} from 'frontend-js-web';
import React, {useContext, useEffect, useState} from 'react';

import {ConstantsContext} from '../context/ConstantsContext';
import {StoreDispatchContext, StoreStateContext} from '../context/StoreContext';
import ItemDetail from './ItemDetail';
import {SidebarBody, SidebarHeader} from './Sidebar';
import Tabs from './Tabs';

import './PageAudit.scss';
import {SET_SELECTED_ISSUE} from '../constants/actionTypes';

export default function PageAudit({panelIsOpen}) {
	const [data, setData] = useState(null);
	const [loading, setLoading] = useState(true);

	const {layoutReportsDataURL} = useContext(ConstantsContext);
	const {selectedIssue} = useContext(StoreStateContext);
	const dispatch = useContext(StoreDispatchContext);

	useEffect(() => {
		if (panelIsOpen && layoutReportsDataURL) {
			fetch(layoutReportsDataURL)
				.then((response) => response.json())
				.then((data) => setData(data))
				.catch((error) => console.error(error))
				.finally(() => setLoading(false));
		}
	}, [layoutReportsDataURL, panelIsOpen]);

	if (loading) {
		return <ClayLoadingIndicator displayType="secondary" size="sm" />;
	}

	if (!data) {
		return (
			<ClayAlert
				displayType="danger"
				title={Liferay.Language.get('error')}
			>
				{Liferay.Language.get('an-unexpected-error-occurred')}
			</ClayAlert>
		);
	}

	const onBack = () => {
		dispatch({
			issue: null,
			type: SET_SELECTED_ISSUE,
		});
	};

	return (
		<>
			<SidebarHeader
				onBackButtonClick={selectedIssue ? onBack : null}
				title={
					selectedIssue
						? selectedIssue.title
						: Liferay.Language.get('page-audit')
				}
			/>

			<SidebarBody>
				{selectedIssue ? (
					<ItemDetail selectedIssue={selectedIssue} />
				) : (
					<Tabs
						segments={data.segmentsExperienceSelectorData}
						tabs={data.tabsData}
					/>
				)}
			</SidebarBody>
		</>
	);
}
