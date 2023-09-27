/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelect} from '@clayui/form';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {
	CREATE_STRATEGIES,
	HEADLESS_BATCH_PLANNER_URL,
	SCHEMA_SELECTED_EVENT,
	UPDATE_STRATEGIES,
} from '../constants';

function StrategyItems({portletNamespace}) {
	const [strategies, setStrategies] = useState([]);

	useEffect(() => {
		const handleSchemaUpdated = (event) => {
			if (event.schemaName) {
				fetch(
					`${HEADLESS_BATCH_PLANNER_URL}/plans/${event.schemaName.replace(
						'#',
						encodeURIComponent('#')
					)}/strategies`
				)
					.then((response) => response.json())
					.then((json) => {
						setStrategies(json.items);
					});
			}
			else {
				setStrategies([]);
			}
		};

		Liferay.on(SCHEMA_SELECTED_EVENT, handleSchemaUpdated);

		return () => {
			Liferay.detach(SCHEMA_SELECTED_EVENT, handleSchemaUpdated);
		};
	}, []);

	const createStrategySelectId = `${portletNamespace}createStrategy`;
	const updateStrategySelectId = `${portletNamespace}updateStrategy`;

	const options = (allowedStrategies, allStrategies, type) => {
		return allStrategies
			.filter((strategy) => {
				for (let i = 0; i < allowedStrategies.length; i++) {
					if (
						strategy.name === allowedStrategies[i].name &&
						allowedStrategies[i].type === type
					) {
						return true;
					}
				}
			})
			.map((strategy) => (
				<ClaySelect.Option
					key={strategy.name}
					label={strategy.label}
					value={strategy.name}
				/>
			));
	};

	const getDefaultStrategy = (strategies) => {
		for (let i = 0; i < strategies.length; i++) {
			if (strategies[i].default) {
				return strategies[i].name;
			}
		}

		return null;
	};

	const createOptions = options(strategies, CREATE_STRATEGIES, 'create');
	const updateOptions = options(strategies, UPDATE_STRATEGIES, 'update');

	return (
		!!strategies.length && (
			<>
				<ClayForm.Group>
					<label htmlFor={createStrategySelectId}>
						{Liferay.Language.get('import-strategy')}
					</label>

					<ClaySelect
						defaultValue={getDefaultStrategy(CREATE_STRATEGIES)}
						id={createStrategySelectId}
						name={createStrategySelectId}
					>
						{createOptions}
					</ClaySelect>
				</ClayForm.Group>
				<ClayForm.Group>
					<label htmlFor={updateStrategySelectId}>
						{Liferay.Language.get('update-strategy')}
					</label>

					<ClaySelect
						defaultValue={getDefaultStrategy(UPDATE_STRATEGIES)}
						id={updateStrategySelectId}
						name={updateStrategySelectId}
					>
						{updateOptions}
					</ClaySelect>
				</ClayForm.Group>
			</>
		)
	);
}

export default StrategyItems;
