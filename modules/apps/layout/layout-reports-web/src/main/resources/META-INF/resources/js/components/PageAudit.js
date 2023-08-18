/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {fetch} from 'frontend-js-web';
import React, {useContext, useEffect, useState} from 'react';

import {ConstantsContext} from '../context/ConstantsContext';
import {StoreStateContext} from '../context/StoreContext';
import Tabs from './Tabs';

import './PageAudit.scss';

export default function PageAudit({panelIsOpen}) {
	const [data, setData] = useState(null);
	const [loading, setLoading] = useState(true);

	const {layoutReportsDataURL} = useContext(ConstantsContext);

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

	return (
		<PageAuditBody
			segments={data.segmentsExperienceSelectorData}
			tabs={data.tabsData}
		/>
	);
}

export function PageAuditBody({children, segments, tabs}) {
	const {selectedIssue} = useContext(StoreStateContext);

	if (selectedIssue) {
		return <div className="c-p-3">{children}</div>;
	}

	return <Tabs segments={segments} tabs={tabs} />;
}
