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
					`${HEADLESS_BATCH_PLANNER_URL}/plans/${event.schemaName}/site-scopes?export=${event.isExport}`
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
