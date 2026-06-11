/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
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

	const currentTokenMapping: TokenMapping = useMemo(
		() =>
			apiURLTokenMappings[selectedTokenKey] ??
			(isCurrentTokenBackendResolvable ? {source: 'backend'} : ''),
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
				updateTokenMapping(selectedTokenKey, {source: 'backend'});

				return;
			}

			const existingFieldId =
				isMappedTokenValue(currentTokenMapping) &&
				!isBackendMapped(currentTokenMapping)
					? currentTokenMapping.fieldId
					: 'classPK';

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
				fieldId: existingFieldId,
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
					<>
						<ClayForm.Group>
							<label htmlFor={tokenSelectId}>
								{Liferay.Language.get('token')}
							</label>

							<ClaySelectWithOption
								id={tokenSelectId}
								onChange={(event) =>
									setSelectedTokenKey(event.target.value)
								}
								options={tokenKeys.map((key) => ({
									label: `{${key}}`,
									value: key,
								}))}
								sizing="sm"
								value={selectedTokenKey}
							/>
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
								/>
							)}

						{currentMappingMode !== 'backend' && (
							<ClayForm.Group>
								<label htmlFor={fieldInputId}>
									{currentMappingMode === 'literal'
										? Liferay.Language.get('token-value')
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
														.value as IdentifierField,
												}
											);
										}}
										options={[
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
											)
												? currentTokenMapping.fieldId
												: 'classPK'
										}
									/>
								)}
							</ClayForm.Group>
						)}
					</>
				)}
		</>
	);
}
