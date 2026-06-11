/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayPanel from '@clayui/panel';
import {IDataSet} from '@liferay/frontend-data-set-admin-web';
import {useId} from 'frontend-js-components-web';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import DataSetSelector from './components/DataSetSelector';
import EntitySelectorRow from './components/EntitySelectorRow';
import LiteralInput from './components/LiteralInput';
import {
	IdentifierField,
	MappingMode,
	TokenMapping,
	getMappingMode,
	isBackendMapped,
	isContextMapped,
	isMappedTokenValue,
} from './tokenMapping';

function isOnDisplayPageTemplate(): boolean {
	return !!document.getElementById('infoItemSelectorContainer');
}

interface IConfigurationField {
	onValueSelect: (name: string, value: any) => void;
	values: {
		apiURLTokenMappings: string;
		backendResolvedTokens: string;
		itemSelector: IDataSet;
	};
}

export default function DataSetConfigurationFields({
	onValueSelect,
	values,
}: IConfigurationField) {
	const [apiURLTokenMappings, setApiURLTokenMappings] = useState<
		Record<string, TokenMapping>
	>(JSON.parse(values.apiURLTokenMappings || '{}'));

	const tokenSelectId = useId();
	const mappingSelectId = useId();
	const fieldInputId = useId();

	const apiURL =
		(values.itemSelector.restEndpoint || '') +
		(values.itemSelector.additionalAPIURLParameters || '');

	const tokenKeys = useMemo(() => {
		const matches = apiURL.match(/{(.*?)}/g) ?? [];

		return matches.map((token) => token.replaceAll(/[{}]/g, ''));
	}, [apiURL]);

	const [selectedTokenKey, setSelectedTokenKey] = useState(
		tokenKeys[0] ?? ''
	);

	useEffect(() => {
		if (!tokenKeys.length) {
			if (selectedTokenKey !== '') {
				setSelectedTokenKey('');
			}

			return;
		}

		if (!tokenKeys.includes(selectedTokenKey)) {
			setSelectedTokenKey(tokenKeys[0]);
		}
	}, [tokenKeys, selectedTokenKey]);

	const backendResolvedTokens = useMemo(
		() => new Set<string>(JSON.parse(values.backendResolvedTokens || '[]')),
		[values.backendResolvedTokens]
	);

	const isCurrentTokenBackendResolvable =
		backendResolvedTokens.has(selectedTokenKey);

	const isTokenMapped = useCallback(
		(tokenKey: string): boolean => {
			const mapping = apiURLTokenMappings[tokenKey];

			if (mapping === undefined) {
				return backendResolvedTokens.has(tokenKey);
			}

			if (typeof mapping === 'string') {
				return !!mapping.trim().length;
			}

			if (isBackendMapped(mapping)) {
				return true;
			}

			if (!mapping.fieldId) {
				return false;
			}

			if (isContextMapped(mapping)) {
				return true;
			}

			return mapping.fieldId === 'externalReferenceCode'
				? !!mapping.externalReferenceCode
				: !!mapping.classPK;
		},
		[apiURLTokenMappings, backendResolvedTokens]
	);

	const isCompleted = useMemo(
		() => tokenKeys.every(isTokenMapped),
		[tokenKeys, isTokenMapped]
	);

	const [isTokenDropdownActive, setIsTokenDropdownActive] = useState(false);

	const currentTokenMapping: TokenMapping = useMemo(
		() =>
			apiURLTokenMappings[selectedTokenKey] ??
			(isCurrentTokenBackendResolvable ? {source: 'backend-resolved'} : ''),
		[apiURLTokenMappings, isCurrentTokenBackendResolvable, selectedTokenKey]
	);

	const currentMappingMode = getMappingMode(currentTokenMapping);

	const displayPageTemplate = isOnDisplayPageTemplate();

	const mappingOptions = [
		{label: Liferay.Language.get('type-value'), value: 'literal'},
		{
			label: Liferay.Language.get('map-to-selected-entity'),
			value: 'content',
		},
		...(displayPageTemplate
			? [
					{
						label: Liferay.Language.get('map-to-context-entity'),
						value: 'context',
					},
				]
			: []),
		...(isCurrentTokenBackendResolvable
			? [
					{
						label: Liferay.Language.get(
							'use-backend-provided-value'
						),
						value: 'backend',
					},
				]
			: []),
	];

	const updateTokenMapping = useCallback(
		(tokenKey: string, tokenMapping: TokenMapping) => {
			const newTokenMappings = {
				...apiURLTokenMappings,
				[tokenKey]: tokenMapping,
			};

			setApiURLTokenMappings(newTokenMappings);

			onValueSelect(
				'apiURLTokenMappings',
				JSON.stringify(newTokenMappings)
			);
		},
		[apiURLTokenMappings, onValueSelect]
	);

	const changeMappingMode = useCallback(
		(mappingMode: MappingMode) => {
			if (mappingMode === currentMappingMode) {
				return;
			}

			if (mappingMode === 'literal') {
				updateTokenMapping(selectedTokenKey, '');

				return;
			}

			if (mappingMode === 'backend') {
				updateTokenMapping(selectedTokenKey, {source: 'backend-resolved'});

				return;
			}

			const existingFieldId =
				isMappedTokenValue(currentTokenMapping) &&
				!isBackendMapped(currentTokenMapping)
					? currentTokenMapping.fieldId
					: '';

			if (mappingMode === 'context') {
				updateTokenMapping(selectedTokenKey, {
					fieldId: existingFieldId,
					source: 'context',
				});

				return;
			}

			updateTokenMapping(selectedTokenKey, {
				className: '',
				classPK: '',
				externalReferenceCode: '',
				fieldId: '',
				source: 'content',
			});
		},
		[
			currentTokenMapping,
			currentMappingMode,
			selectedTokenKey,
			updateTokenMapping,
		]
	);

	return (
		<>
			<DataSetSelector
				onChange={(dataSet) => onValueSelect('itemSelector', dataSet)}
				value={values.itemSelector}
			/>

			{Liferay.FeatureFlags['LPD-38564'] &&
				!!tokenKeys.length &&
				selectedTokenKey && (
					<ClayPanel
						className="w-100"
						collapsable
						defaultExpanded
						displayTitle={Liferay.Language.get('url-token-mapping')}
						displayType="unstyled"
						showCollapseIcon
					>
						<ClayPanel.Body>
							<ClayForm.Group className="align-items-center d-flex justify-content-between">
								<label className="mb-0">
									{Liferay.Language.get('status')}
								</label>

								<ClayLabel
									className="text-uppercase"
									displayType={
										isCompleted ? 'success' : 'warning'
									}
								>
									{isCompleted
										? Liferay.Language.get('completed')
										: Liferay.Language.get('incomplete')}
								</ClayLabel>
							</ClayForm.Group>

							<ClayForm.Group>
								<label htmlFor={tokenSelectId}>
									{Liferay.Language.get('token')}
								</label>

								<ClayDropDown
									active={isTokenDropdownActive}
									onActiveChange={setIsTokenDropdownActive}
									trigger={
										<ClayButton
											className="align-items-center btn-sm d-flex justify-content-between w-100"
											displayType="secondary"
											id={tokenSelectId}
										>
											<span>{`{${selectedTokenKey}}`}</span>

											<span className="align-items-center d-flex">
												<ClayLabel
													className="text-uppercase"
													displayType={
														isTokenMapped(
															selectedTokenKey
														)
															? 'success'
															: 'info'
													}
												>
													{isTokenMapped(
														selectedTokenKey
													)
														? Liferay.Language.get(
																'mapped'
															)
														: Liferay.Language.get(
																'unmapped'
															)}
												</ClayLabel>

												<ClayIcon
													className="ml-2"
													symbol="caret-bottom"
												/>
											</span>
										</ClayButton>
									}
								>
									<ClayDropDown.ItemList>
										{tokenKeys.map((key) => {
											const mapped = isTokenMapped(key);

											return (
												<ClayDropDown.Item
													active={
														key === selectedTokenKey
													}
													key={key}
													onClick={() => {
														setSelectedTokenKey(
															key
														);
														setIsTokenDropdownActive(
															false
														);
													}}
												>
													<span className="align-items-center d-flex justify-content-between">
														<span>{`{${key}}`}</span>

														<ClayLabel
															className="ml-2 text-uppercase"
															displayType={
																mapped
																	? 'success'
																	: 'info'
															}
														>
															{mapped
																? Liferay.Language.get(
																		'mapped'
																	)
																: Liferay.Language.get(
																		'unmapped'
																	)}
														</ClayLabel>
													</span>
												</ClayDropDown.Item>
											);
										})}
									</ClayDropDown.ItemList>
								</ClayDropDown>
							</ClayForm.Group>

							<ClayForm.Group>
								<label htmlFor={mappingSelectId}>
									{Liferay.Language.get('mapping')}
								</label>

								<ClaySelectWithOption
									id={mappingSelectId}
									onChange={(event) =>
										changeMappingMode(
											event.target.value as MappingMode
										)
									}
									options={mappingOptions}
									sizing="sm"
									value={currentMappingMode}
								/>
							</ClayForm.Group>

							{currentMappingMode === 'content' &&
								isMappedTokenValue(currentTokenMapping) &&
								!isContextMapped(currentTokenMapping) &&
								!isBackendMapped(currentTokenMapping) && (
									<EntitySelectorRow
										entity={currentTokenMapping}
										onEntityChange={(entity) =>
											updateTokenMapping(
												selectedTokenKey,
												entity
											)
										}
										onEntityRemove={() =>
											updateTokenMapping(
												selectedTokenKey,
												{
													className: '',
													classPK: '',
													externalReferenceCode: '',
													fieldId: '',
													source: 'content',
												}
											)
										}
									/>
								)}

							{currentMappingMode !== 'backend' && (
								<ClayForm.Group>
									<label htmlFor={fieldInputId}>
										{currentMappingMode === 'literal'
											? Liferay.Language.get(
													'token-value'
												)
											: Liferay.Language.get('field')}
									</label>

									{currentMappingMode === 'literal' ? (
										<LiteralInput
											inputId={fieldInputId}
											onCommit={(value) =>
												updateTokenMapping(
													selectedTokenKey,
													value
												)
											}
											value={
												typeof currentTokenMapping ===
												'string'
													? currentTokenMapping
													: ''
											}
										/>
									) : (
										<ClaySelectWithOption
											disabled={
												currentMappingMode ===
													'content' &&
												isMappedTokenValue(
													currentTokenMapping
												) &&
												!isBackendMapped(
													currentTokenMapping
												) &&
												!isContextMapped(
													currentTokenMapping
												) &&
												!currentTokenMapping.className
											}
											id={fieldInputId}
											onChange={(event) => {
												if (
													!isMappedTokenValue(
														currentTokenMapping
													) ||
													isBackendMapped(
														currentTokenMapping
													)
												) {
													return;
												}

												updateTokenMapping(
													selectedTokenKey,
													{
														...currentTokenMapping,
														fieldId: event.target
															.value as
															| IdentifierField
															| '',
													}
												);
											}}
											options={[
												{
													label: `-- ${Liferay.Language.get(
														'unmapped'
													)} --`,
													value: '',
												},
												{
													label: Liferay.Language.get(
														'id'
													),
													value: 'classPK',
												},
												{
													label: Liferay.Language.get(
														'external-reference-code'
													),
													value: 'externalReferenceCode',
												},
											]}
											sizing="sm"
											value={
												isMappedTokenValue(
													currentTokenMapping
												) &&
												!isBackendMapped(
													currentTokenMapping
												) &&
												!(
													currentMappingMode ===
														'content' &&
													!isContextMapped(
														currentTokenMapping
													) &&
													!currentTokenMapping.className
												)
													? currentTokenMapping.fieldId
													: ''
											}
										/>
									)}
								</ClayForm.Group>
							)}
						</ClayPanel.Body>
					</ClayPanel>
				)}
		</>
	);
}
