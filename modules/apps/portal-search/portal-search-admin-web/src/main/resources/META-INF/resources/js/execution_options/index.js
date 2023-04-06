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
import React, {useState} from 'react';

import InstanceSelector from './InstanceSelector';

const EXECUTION_MODES = {
	BLUE_GREEN: 'blue-green',
	REGULAR: 'regular',
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
	portletNamespace,
	virtualInstances = [],
}) {
	const [executionMode, setExecutionMode] = useState(
		initialExecutionMode || EXECUTION_MODES.REGULAR
	);
	const [selected, setSelected] = useState(initialCompanyIds);
	const [scope, setScope] = useState(initialScope || SCOPES.ALL);

	const _handleExecutionModeChange = (value) => {
		setExecutionMode(value);
	};

	const _handleScopeChange = (value) => {
		setScope(value);
	};

	return (
		<div className="execution-scope-sheet sheet sheet-lg">
			{Liferay.FeatureFlags['LPS-177664'] && (
				<div className="sheet-section">
					<h2 className="sheet-title">
						{Liferay.Language.get('execution-mode')}
					</h2>

					<ClayRadioGroup
						name={`${portletNamespace}executionMode`}
						onChange={_handleExecutionModeChange}
						value={executionMode}
					>
						<ClayRadio
							label={Liferay.Language.get('regular')}
							value={EXECUTION_MODES.REGULAR}
						/>

						<ClayRadio
							label={Liferay.Language.get('blue-green')}
							value={EXECUTION_MODES.BLUE_GREEN}
						/>
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
