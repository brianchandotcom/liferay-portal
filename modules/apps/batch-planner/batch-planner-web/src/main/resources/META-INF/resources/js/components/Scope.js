/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelect} from '@clayui/form';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {HEADLESS_BATCH_PLANNER_URL, SCHEMA_SELECTED_EVENT} from '../constants';

function Scope({portletNamespace}) {
	const [scopes, setScopes] = useState([]);

	useEffect(() => {
		const handleSchemaUpdated = (event) => {
			if (event.schemaName) {
				fetch(
					`${HEADLESS_BATCH_PLANNER_URL}/plans/${event.schemaName.replace(
						'#',
						encodeURIComponent('#')
					)}/site-scopes?export=${event.isExport}`
				)
					.then((response) => response.json())
					.then((json) => {
						setScopes(json.items);
					});
			}
			else {
				setScopes([]);
			}
		};

		Liferay.on(SCHEMA_SELECTED_EVENT, handleSchemaUpdated);

		return () => {
			Liferay.detach(SCHEMA_SELECTED_EVENT, handleSchemaUpdated);
		};
	}, []);

	const selectId = `${portletNamespace}siteId`;

	return (
		!!scopes.length && (
			<ClayForm.Group>
				<label htmlFor={selectId}>
					{Liferay.Language.get('scope')}
				</label>

				<ClaySelect id={selectId} name={selectId}>
					{scopes.map((scope) => (
						<ClaySelect.Option
							key={scope.value}
							label={scope.label}
							value={scope.value}
						/>
					))}
				</ClaySelect>
			</ClayForm.Group>
		)
	);
}

export default Scope;
