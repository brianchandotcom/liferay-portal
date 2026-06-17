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
	EMappingMode,
	IdentifierField,
	TokenMapping,
	getMappingMode,
	isAutoResolved,
	isContentMapped,
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
		autoResolvableTokenNames: string;
		itemSelector: IDataSet;
	};
}

export default function DataSetConfigurationFields({
	onValueSelect,
	values,
}: IConfigurationField) {
	const [apiURLTokenMappings, setApiURLTokenMappings] = useState<
		Record<string, TokenMapping>
	>(() => {
		try {
			return JSON.parse(values?.apiURLTokenMappings || '{}');
		}
		catch (error) {
			return {};
		}
	});

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

	const [selectedTokenKey, setSelectedTokenKey] = useState('');

	useEffect(() => {
		if (tokenKeys.length === 1) {
			if (selectedTokenKey !== tokenKeys[0]) {
				setSelectedTokenKey(tokenKeys[0]);
			}
		}
		else if (selectedTokenKey && !tokenKeys.includes(selectedTokenKey)) {
			setSelectedTokenKey('');
		}
	}, [tokenKeys, selectedTokenKey]);

	const autoResolvableTokenNames = useMemo(
		() =>
			new Set<string>(
				JSON.parse(values.autoResolvableTokenNames || '[]')
			),
		[values.autoResolvableTokenNames]
	);

	const isCurrentTokenAutoResolvable =
		autoResolvableTokenNames.has(selectedTokenKey);

	const isTokenMapped = useCallback(
		(tokenKey: string): boolean => {
			const mapping = apiURLTokenMappings[tokenKey];

			if (mapping === undefined) {
				return autoResolvableTokenNames.has(tokenKey);
			}

			if (typeof mapping === 'string') {
				return !!mapping.trim().length;
			}

			if (isAutoResolved(mapping)) {
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
		[apiURLTokenMappings, autoResolvableTokenNames]
	);

	const isCompleted = useMemo(
		() => tokenKeys.every(isTokenMapped),
		[tokenKeys, isTokenMapped]
	);

	const [isTokenDropdownActive, setIsTokenDropdownActive] = useState(false);

	const currentTokenMapping: TokenMapping = useMemo(
		() =>
			apiURLTokenMappings[selectedTokenKey] ??
			(isCurrentTokenAutoResolvable
				? {mappingMode: EMappingMode.AUTO_RESOLVED}
				: ''),
		[apiURLTokenMappings, isCurrentTokenAutoResolvable, selectedTokenKey]
	);

	const currentMappingMode = getMappingMode(currentTokenMapping);

	const displayPageTemplate = isOnDisplayPageTemplate();

	const mappingOptions = [
		{
			label: Liferay.Language.get('input-token-value'),
			value: EMappingMode.LITERAL,
		},
		{
			label: Liferay.Language.get('map-to-selected-entity'),
			value: EMappingMode.CONTENT,
		},
		...(displayPageTemplate
			? [
					{
						label: Liferay.Language.get(
							'map-to-page-context-entity'
						),
						value: EMappingMode.CONTEXT,
					},
				]
			: []),
		...(isCurrentTokenAutoResolvable
			? [
					{
						label: Liferay.Language.get('resolve-automatically'),
						value: EMappingMode.AUTO_RESOLVED,
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
		(mappingMode: EMappingMode) => {
			if (mappingMode === currentMappingMode) {
				return;
			}

			if (mappingMode === EMappingMode.LITERAL) {
				updateTokenMapping(selectedTokenKey, '');

				return;
			}

			if (mappingMode === EMappingMode.AUTO_RESOLVED) {
				updateTokenMapping(selectedTokenKey, {
					mappingMode: EMappingMode.AUTO_RESOLVED,
				});

				return;
			}

			const existingFieldId =
				isMappedTokenValue(currentTokenMapping) &&
				!isAutoResolved(currentTokenMapping)
					? currentTokenMapping.fieldId
					: '';

			if (mappingMode === EMappingMode.CONTEXT) {
				updateTokenMapping(selectedTokenKey, {
					fieldId: existingFieldId,
					mappingMode: EMappingMode.CONTEXT,
				});

				return;
			}

			updateTokenMapping(selectedTokenKey, {
				className: '',
				classPK: '',
				externalReferenceCode: '',
				fieldId: '',
				mappingMode: EMappingMode.CONTENT,
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

			{Liferay.FeatureFlags['LPD-38564'] && !!tokenKeys.length && (
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
										{selectedTokenKey ? (
											<>
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
											</>
										) : (
											<>
												<span>
													{Liferay.Language.get(
														'select-an-option'
													)}
												</span>

												<ClayIcon symbol="caret-bottom" />
											</>
										)}
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
													setSelectedTokenKey(key);
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

						{selectedTokenKey && (
							<>
								<ClayForm.Group>
									<label htmlFor={mappingSelectId}>
										{Liferay.Language.get('mapping')}
									</label>

									<ClaySelectWithOption
										id={mappingSelectId}
										onChange={(event) =>
											changeMappingMode(
												event.target
													.value as EMappingMode
											)
										}
										options={mappingOptions}
										sizing="sm"
										value={currentMappingMode}
									/>
								</ClayForm.Group>

								{isContentMapped(currentTokenMapping) && (
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
													mappingMode:
														EMappingMode.CONTENT,
												}
											)
										}
									/>
								)}

								{currentMappingMode !==
									EMappingMode.AUTO_RESOLVED && (
									<ClayForm.Group>
										<label htmlFor={fieldInputId}>
											{currentMappingMode ===
											EMappingMode.LITERAL
												? Liferay.Language.get(
														'token-value'
													)
												: Liferay.Language.get('field')}
										</label>

										{currentMappingMode ===
										EMappingMode.LITERAL ? (
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
													isContentMapped(
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
														isAutoResolved(
															currentTokenMapping
														)
													) {
														return;
													}

													updateTokenMapping(
														selectedTokenKey,
														{
															...currentTokenMapping,
															fieldId: event
																.target
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
													!isAutoResolved(
														currentTokenMapping
													) &&
													!(
														isContentMapped(
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
							</>
						)}
					</ClayPanel.Body>
				</ClayPanel>
			)}
		</>
	);
}
