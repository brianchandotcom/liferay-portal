/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useEffect, useRef, useState} from 'react';

import InstanceSelector from './InstanceSelector';

const EXECUTE_BUTTON_QUERY_SELECTOR = '.save-server-button';

const EXECUTION_MODES = {
	CONCURRENT: {
		description: Liferay.Language.get(
			'reindex-mode-concurrent-description'
		),
		label: Liferay.Language.get('concurrent'),
		symbol: 'change-list',
		value: 'concurrent',
	},
	REGULAR: {
		description: Liferay.Language.get('reindex-mode-full-description'),
		label: Liferay.Language.get('full'),
		symbol: 'globe-lines',
		value: 'regular',
	},
	SYNC: {
		description: Liferay.Language.get('reindex-mode-sync-description'),
		label: Liferay.Language.get('sync'),
		symbol: 'reload',
		value: 'sync',
	},
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
		initialExecutionMode || EXECUTION_MODES.REGULAR.value
	);
	const [
		executionModeDropdownActive,
		setExecutionModeDropdownActive,
	] = useState(false);
	const [selected, setSelected] = useState(initialCompanyIds);
	const [scope, setScope] = useState(initialScope || SCOPES.ALL);

	const alignElementRef = useRef();

	/**
	 * Disables execute buttons with the attribute `data-concurrent-disabled`
	 * if Concurrent execution mode is selected.
	 */
	useEffect(() => {
		const executeButtonsElement = document.querySelectorAll(
			EXECUTE_BUTTON_QUERY_SELECTOR
		);

		executeButtonsElement.forEach((element) => {
			if (
				executionMode === EXECUTION_MODES.CONCURRENT.value &&
				element.hasAttribute('data-concurrent-disabled')
			) {
				element.classList.add('disabled');
			}
			else {
				element.classList.remove('disabled');
			}
		});
	}, [executionMode]);

	const _handleExecutionModeChange = (mode) => {
		setExecutionMode(mode);
		setExecutionModeDropdownActive(false);
	};

	const _handleExecutionModeDropdownChange = () =>
		setExecutionModeDropdownActive(!executionModeDropdownActive);

	const _handleScopeChange = (value) => {
		setScope(value);
	};

	return (
		<div className="execution-scope-sheet sheet sheet-lg">
			{Liferay.FeatureFlags['LPS-183661'] && isConcurrentModeSupported && (
				<div className="c-mb-1 sheet-section">
					<div
						className="sheet-subtitle text-secondary"
						style={{textTransform: 'none'}}
					>
						<span>{Liferay.Language.get('reindex-mode')}</span>
					</div>

					<div className="form-group">
						<label htmlFor="executionMode">
							{Liferay.Language.get('reindex-mode')}
						</label>

						<ClayButton
							className="form-control form-control-select"
							displayType="secondary"
							id="executionMode"
							onClick={_handleExecutionModeDropdownChange}
							ref={alignElementRef}
						>
							{Object.values(EXECUTION_MODES).find(
								({value}) => value === executionMode
							)?.label || ''}
						</ClayButton>

						<input
							hidden
							id={`${portletNamespace}executionMode`}
							name={`${portletNamespace}executionMode`}
							readOnly
							value={executionMode}
						/>

						<ClayDropDown.Menu
							active={executionModeDropdownActive}
							alignElementRef={alignElementRef}
							closeOnClickOutside
							onActiveChange={setExecutionModeDropdownActive}
							style={{
								maxWidth: '100%',
								width:
									alignElementRef.current &&
									alignElementRef.current.clientWidth + 'px',
							}}
						>
							<ClayDropDown.ItemList>
								{[
									EXECUTION_MODES.REGULAR,
									EXECUTION_MODES.CONCURRENT,
									EXECUTION_MODES.SYNC,
								].map(({description, label, symbol, value}) => {
									return (
										<ClayDropDown.Item
											className="c-pb-2 c-pt-2"
											key={value}
											onClick={() =>
												_handleExecutionModeChange(
													value
												)
											}
										>
											<div className="d-flex">
												<div className="c-mr-2">
													<ClayIcon symbol={symbol} />
												</div>

												<div className="autofit-col-expand c-ml-2">
													<div className="list-group-title">
														{label}
													</div>

													<div className="list-group-subtext">
														{description}
													</div>
												</div>
											</div>
										</ClayDropDown.Item>
									);
								})}
							</ClayDropDown.ItemList>
						</ClayDropDown.Menu>

						{executionMode === EXECUTION_MODES.CONCURRENT.value && (
							<div className="font-weight-normal form-text">
								{Liferay.Language.get(
									'reindex-mode-concurrent-note'
								)}
							</div>
						)}
					</div>
				</div>
			)}

			<div className="sheet-section">
				{Liferay.FeatureFlags['LPS-183661'] ? (
					<div
						className="sheet-subtitle text-secondary"
						style={{textTransform: 'none'}}
					>
						<span>{Liferay.Language.get('reindex-scope')}</span>

						<ClayTooltipProvider>
							<ClaySticker
								data-tooltip-align="bottom-left"
								displayType="secondary"
								size="sm"
								title={Liferay.Language.get(
									'execution-scope-help'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</ClaySticker>
						</ClayTooltipProvider>
					</div>
				) : (
					<h2 className="sheet-title">
						{Liferay.Language.get('execution-scope')}

						<ClayTooltipProvider>
							<ClaySticker
								data-tooltip-align="bottom-left"
								displayType="secondary"
								size="md"
								title={Liferay.Language.get(
									'execution-scope-help'
								)}
							>
								<ClayIcon symbol="question-circle-full" />
							</ClaySticker>
						</ClayTooltipProvider>
					</h2>
				)}

				<ClayRadioGroup
					className="c-pb-2"
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
