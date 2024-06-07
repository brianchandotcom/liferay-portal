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
import {FeatureIndicator} from 'frontend-js-components-web';
import React, {useRef, useState} from 'react';

import {EXECUTION_MODES, SCOPES} from '../constants';
import InstanceSelector from './InstanceSelector';

/**
 * Options on the left of the Index Actions page that affect the reindex
 * actions.
 *
 * Current options:
 * 	- Execution Scope: Value is submitted as `companyIds`.
 * 	- Execution Mode: Value is submitted as `executionMode`.
 */
function ExecutionOptions({
	concurrentModeSupported,
	executionMode,
	executionScope,
	omniadmin,
	onExecutionModeChange,
	onExecutionScopeChange,
	onSelectedCompanyIdsChange,
	portletNamespace,
	selectedCompanyIds = [],
	virtualInstances = [],
}) {
	const [executionModeDropdownActive, setExecutionModeDropdownActive] =
		useState(false);

	const alignElementRef = useRef();

	const _handleExecutionModeChange = (mode) => {
		onExecutionModeChange(mode);
		setExecutionModeDropdownActive(false);
	};

	const _handleExecutionModeDropdownChange = () =>
		setExecutionModeDropdownActive(!executionModeDropdownActive);

	return (
		<div className="execution-scope-sheet sheet sheet-lg">
			<h2 className="sheet-title">
				{Liferay.Language.get('configuration')}
			</h2>

			{concurrentModeSupported && (
				<div className="c-mb-1 sheet-section">
					<div
						className="sheet-subtitle text-secondary"
						style={{textTransform: 'none'}}
					>
						<span>{Liferay.Language.get('reindex-mode')}</span>
					</div>

					<div className="form-group">
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
									EXECUTION_MODES.FULL,
									EXECUTION_MODES.CONCURRENT,
									EXECUTION_MODES.SYNC,
								].map(
									({
										description,
										label,
										showBetaBadge,
										symbol,
										value,
									}) => {
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
														<ClayIcon
															symbol={symbol}
														/>
													</div>

													<div className="autofit-col-expand c-ml-2">
														<div className="list-group-title">
															{label}

															{showBetaBadge && (
																<span className="c-ml-1">
																	<FeatureIndicator type="beta" />
																</span>
															)}
														</div>

														<div className="list-group-subtext">
															{description}
														</div>
													</div>
												</div>
											</ClayDropDown.Item>
										);
									}
								)}
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

			{omniadmin && (
				<div className="sheet-section">
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

					<ClayRadioGroup
						className="c-pb-2"
						name={`${portletNamespace}scope`}
						onChange={onExecutionScopeChange}
						value={executionScope}
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

					{executionScope === SCOPES.SELECTED && (
						<InstanceSelector
							onSelectedChange={onSelectedCompanyIdsChange}
							selected={selectedCompanyIds}
							virtualInstances={virtualInstances}
						/>
					)}

					<input
						name={`${portletNamespace}companyIds`}
						type="hidden"
						value={
							executionScope === SCOPES.ALL
								? virtualInstances.map(({id}) => id)?.toString()
								: selectedCompanyIds.toString()
						}
					/>
				</div>
			)}
		</div>
	);
}

export default ExecutionOptions;
