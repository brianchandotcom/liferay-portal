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
import {SET_SELECTED_ITEM} from '../constants/actionTypes';

export default function PageAudit({panelIsOpen}) {
	const [data, setData] = useState(null);
	const [loading, setLoading] = useState(true);

	const {layoutReportsDataURL} = useContext(ConstantsContext);
	const {selectedItem} = useContext(StoreStateContext);
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

	useEffect(() => {
		if (selectedItem) {
			document.querySelector('.lfr-layout-reports-panel')?.focus();
		}
	}, [selectedItem]);

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
			item: null,
			type: SET_SELECTED_ITEM,
		});
	};

	return (
		<>
			<SidebarHeader
				onBackButtonClick={selectedItem ? onBack : null}
				title={
					selectedItem
						? selectedItem.title
						: Liferay.Language.get('page-audit')
				}
			/>

			<SidebarBody>
				{selectedItem ? (
					<ItemDetail selectedItem={selectedItem} />
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
