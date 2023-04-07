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
					label={Liferay.Language.get(strategy.label)}
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
