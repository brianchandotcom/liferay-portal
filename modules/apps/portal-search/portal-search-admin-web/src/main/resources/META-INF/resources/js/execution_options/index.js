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

import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useEffect, useRef, useState} from 'react';

import InstanceSelector from './InstanceSelector';

const EXECUTE_BUTTON_QUERY_SELECTOR = '.save-server-button';

const EXECUTION_MODES = {
	CONCURRENT: 'concurrent',
	REGULAR: 'regular',
	SYNC: 'sync',
};

const SCOPES = {
	ALL: 'all',
	SELECTED: 'selected',
};

/**
 * Options on the left of the Index Actions page that affect the reindex
 * actions.
 *
 * Current options:
 * 	- Execution Scope: Value is submitted as `companyIds`.
 * 	- Execution Mode: Value is submitted as `executionMode`.
 */
function ExecutionOptions({
	initialCompanyIds = [],
	initialExecutionMode,
	initialScope,
	isConcurrentModeSupported,
	portletNamespace,
	virtualInstances = [],
}) {
	const [executionMode, setExecutionMode] = useState(
		initialExecutionMode || EXECUTION_MODES.REGULAR
	);
	const [selected, setSelected] = useState(initialCompanyIds);
	const [scope, setScope] = useState(initialScope || SCOPES.ALL);

	const executeButtonElementsRef = useRef(
		document.querySelectorAll(EXECUTE_BUTTON_QUERY_SELECTOR)
	);

	/**
	 * Disables execute buttons with the attribute `data-concurrent-disabled`
	 * if Concurrent execution mode is selected.
	 */
	useEffect(() => {
		executeButtonElementsRef.current?.forEach((element) => {
			if (
				executionMode === EXECUTION_MODES.CONCURRENT &&
				element.hasAttribute('data-concurrent-disabled')
			) {
				element.disabled = true;
			}
			else {
				element.disabled = false;
			}
		});
	}, [executionMode]);

	const _handleExecutionModeChange = (value) => {
		setExecutionMode(value);
	};

	const _handleScopeChange = (value) => {
		setScope(value);
	};

	const _renderExecutionModeRadioGroup = () => {
		const radioGroup = [
			<ClayRadio
				key={EXECUTION_MODES.REGULAR}
				label={Liferay.Language.get('regular')}
				value={EXECUTION_MODES.REGULAR}
			/>,
		];

		if (Liferay.FeatureFlags['LPS-177664']) {
			radioGroup.push(
				<ClayRadio
					key={EXECUTION_MODES.CONCURRENT}
					label={Liferay.Language.get('concurrent')}
					value={EXECUTION_MODES.CONCURRENT}
				/>
			);
		}

		if (Liferay.FeatureFlags['LPS-177668']) {
			radioGroup.push(
				<ClayRadio
					key={EXECUTION_MODES.SYNC}
					label={Liferay.Language.get('sync')}
					value={EXECUTION_MODES.SYNC}
				/>
			);
		}

		return radioGroup;
	};

	return (
		<div className="execution-scope-sheet sheet sheet-lg">
			{(Liferay.FeatureFlags['LPS-177664'] ||
				Liferay.FeatureFlags['LPS-177668']) &&
				isConcurrentModeSupported && (
					<div className="sheet-section">
						<h2 className="sheet-title">
							{Liferay.Language.get('execution-mode')}
						</h2>

						<ClayRadioGroup
							name={`${portletNamespace}executionMode`}
							onChange={_handleExecutionModeChange}
							value={executionMode}
						>
							{_renderExecutionModeRadioGroup()}
						</ClayRadioGroup>
					</div>
				)}

			<div className="sheet-section">
				<h2 className="sheet-title">
					{Liferay.Language.get('execution-scope')}

					<ClayTooltipProvider>
						<ClaySticker
							data-tooltip-align="bottom-left"
							displayType="secondary"
							size="md"
							title={Liferay.Language.get('execution-scope-help')}
						>
							<ClayIcon symbol="question-circle-full" />
						</ClaySticker>
					</ClayTooltipProvider>
				</h2>

				<ClayRadioGroup
					name={`${portletNamespace}scope`}
					onChange={_handleScopeChange}
					value={scope}
				>
					<ClayRadio
						label={Liferay.Language.get('all-instances')}
						value={SCOPES.ALL}
					/>

					<ClayRadio
						label={Liferay.Language.get('selected-instances')}
						value={SCOPES.SELECTED}
					/>
				</ClayRadioGroup>

				{scope === SCOPES.SELECTED && (
					<InstanceSelector
						selected={selected}
						setSelected={setSelected}
						virtualInstances={virtualInstances}
					/>
				)}

				<input
					name={`${portletNamespace}companyIds`}
					type="hidden"
					value={
						scope === SCOPES.ALL
							? virtualInstances.map(({id}) => id).toString()
							: selected.toString()
					}
				/>
			</div>
		</div>
	);
}

export default ExecutionOptions;
