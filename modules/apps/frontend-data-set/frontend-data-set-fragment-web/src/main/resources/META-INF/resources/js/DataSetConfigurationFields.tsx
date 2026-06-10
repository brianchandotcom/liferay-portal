/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import {
	IDataSet,
	getDataSetResourceURL,
} from '@liferay/frontend-data-set-admin-web';
import {openItemSelectorModal} from '@liferay/frontend-js-item-selector-web';
import {openSelectionModal, useId} from 'frontend-js-components-web';
import {fetch, sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

const EDITOR_PORTLET_ID =
	'com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet';

const ITEM_SELECTOR_URL_RESOURCE_COMMAND =
	'/frontend_data_set_fragment/get_info_item_selector_url';

type IdentifierField = 'classPK' | 'externalReferenceCode';

type MappingMode = 'literal' | 'content' | 'context';

interface IContentMappedTokenValue {
	className: string;
	classPK: string;
	externalReferenceCode: string;
	fieldId: IdentifierField;
	source?: 'content';
	title?: string;
}

interface IContextMappedTokenValue {
	fieldId: IdentifierField;
	source: 'context';
}

type IMappedTokenValue = IContentMappedTokenValue | IContextMappedTokenValue;

type TokenMapping = string | IMappedTokenValue;

function isMappedTokenValue(value: TokenMapping): value is IMappedTokenValue {
	return typeof value === 'object' && value !== null;
}

function isContextMapped(
	value: IMappedTokenValue
): value is IContextMappedTokenValue {
	return value.source === 'context';
}

function getMappingMode(value: TokenMapping): MappingMode {
	if (!isMappedTokenValue(value)) {
		return 'literal';
	}

	return isContextMapped(value) ? 'context' : 'content';
}

function isOnDisplayPageTemplate(): boolean {
	return !!document.getElementById('infoItemSelectorContainer');
}

interface IConfigurationField {
	onValueSelect: (name: string, value: any) => void;
	values: {
		apiURLTokenMappings: string;
		itemSelector: IDataSet;
	};
}

function buildResourceURL(resourceCommand: string): string {
	const url = new URL(window.location.href);

	const params = new URLSearchParams();

	url.searchParams.forEach((paramValue, paramKey) => {
		if (!paramKey.startsWith('p_p_') && !paramKey.startsWith('_')) {
			params.append(paramKey, paramValue);
		}
	});

	params.set('p_p_id', EDITOR_PORTLET_ID);
	params.set('p_p_lifecycle', '2');
	params.set('p_p_resource_id', resourceCommand);
	params.set('p_p_state', 'normal');

	if (Liferay.authToken) {
		params.set('p_auth', Liferay.authToken);
	}

	url.search = params.toString();

	return url.toString();
}

function LiteralInput({
	inputId,
	onCommit,
	value,
}: {
	inputId: string;
	onCommit: (value: string) => void;
	value: string;
}) {
	const [localValue, setLocalValue] = useState(value);

	useEffect(() => {
		setLocalValue(value);
	}, [value]);

	return (
		<ClayInput
			id={inputId}
			onBlur={() => {
				if (localValue !== value) {
					onCommit(localValue);
				}
			}}
			onChange={(event) => setLocalValue(event.target.value)}
			sizing="sm"
			type="text"
			value={localValue}
		/>
	);
}

function EntitySelectorRow({
	entity,
	onSelectEntity,
}: {
	entity: IContentMappedTokenValue;
	onSelectEntity: () => void;
}) {
	const entityLabel = Liferay.Language.get('entity');
	const isEntitySelected = !!entity.className;

	const selectButtonIcon = isEntitySelected ? 'change' : 'plus';

	const selectButtonLabel = sub(
		isEntitySelected
			? Liferay.Language.get('change-x')
			: Liferay.Language.get('select-x'),
		entityLabel
	);

	const displayValue = isEntitySelected
		? entity.title ||
			(entity.fieldId === 'externalReferenceCode'
				? entity.externalReferenceCode
				: entity.classPK)
		: '';

	return (
		<ClayForm.Group>
			<label>{entityLabel}</label>

			<ClayInput.Group small>
				<ClayInput.GroupItem>
					<ClayInput
						placeholder={sub(
							Liferay.Language.get('no-x-selected'),
							entityLabel
						)}
						readOnly
						sizing="sm"
						type="text"
						value={displayValue}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem shrink>
					<ClayButtonWithIcon
						aria-label={selectButtonLabel}
						displayType="secondary"
						onClick={onSelectEntity}
						size="sm"
						symbol={selectButtonIcon}
						title={selectButtonLabel}
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}

export default function DataSetConfigurationFields({
	onValueSelect,
	values,
}: IConfigurationField) {
	const [apiURLTokenMappings, setApiURLTokenMappings] = useState<
		Record<string, TokenMapping>
	>(JSON.parse(values.apiURLTokenMappings || '{}'));

	const itemSelectorInputId = useId();
	const tokenSelectId = useId();
	const mappingSelectId = useId();
	const fieldInputId = useId();

	const dataSetLabel = Liferay.Language.get('data-set');

	const dataSetSelected: boolean =
		Object.keys(values.itemSelector).length !== 0;

	const selectContentButtonIcon = dataSetSelected ? 'change' : 'plus';

	const selectContentButtonLabel = sub(
		dataSetSelected
			? Liferay.Language.get('change-x')
			: Liferay.Language.get('select-x'),
		dataSetLabel
	);

	const fdsViews = [
		{
			contentRenderer: 'list',
			name: 'list',
			schema: {
				description: 'description',
				sticker: 'sticker',
				symbol: 'symbol',
				title: 'label',
				tooltip: 'tooltip',
			},
			setItemComponentProps: ({item, props}: {item: any; props: any}) => {
				if (
					!item.dataSetToDataSetCardsSections.length &&
					!item.dataSetToDataSetTableSections.length &&
					!item.dataSetToDataSetListSections.length
				) {
					return {
						...props,
						item: {
							...item,
							sticker: {displayType: 'warning'},
							symbol: 'exclamation-circle',
							tooltip: Liferay.Language.get(
								'no-visualization-modes-have-been-defined'
							),
						},
					};
				}
				else {
					return {
						...props,
						item: {
							...item,
							sticker: {displayType: 'unstyled'},
							symbol: 'catalog',
						},
					};
				}
			},
		},
	];

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

	const currentTokenMapping: TokenMapping =
		apiURLTokenMappings[selectedTokenKey] ?? '';

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

	const openInfoItemSelector = useCallback(
		async (handleSelectedEntity: (selected: any) => void) => {
			const response = await fetch(
				buildResourceURL(ITEM_SELECTOR_URL_RESOURCE_COMMAND)
			);

			if (!response.ok) {
				return;
			}

			const {eventName, url} = (await response.json()) as {
				eventName: string;
				url: string;
			};

			if (!url) {
				return;
			}

			openSelectionModal({
				onSelect: (selection: any) => {
					if (!selection) {
						return;
					}

					const selectedValue = JSON.parse(selection.value);

					if (!selectedValue) {
						return;
					}

					handleSelectedEntity(selectedValue);
				},
				selectEventName: eventName,
				title: Liferay.Language.get('select-an-entity'),
				url,
			});
		},
		[]
	);

	const openEntitySelector = useCallback(
		(tokenKey: string) => {
			const currentMapping = apiURLTokenMappings[tokenKey] ?? '';
			const mappedFieldId = isMappedTokenValue(currentMapping)
				? currentMapping.fieldId
				: 'classPK';

			openInfoItemSelector((selected) => {
				updateTokenMapping(tokenKey, {
					className: selected.className,
					classPK: String(selected.classPK ?? ''),
					externalReferenceCode: selected.externalReferenceCode ?? '',
					fieldId: mappedFieldId,
					source: 'content',
					title: selected.title,
				});
			});
		},
		[apiURLTokenMappings, openInfoItemSelector, updateTokenMapping]
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

			const existingFieldId = isMappedTokenValue(currentTokenMapping)
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
			<ClayForm.Group>
				<label htmlFor={itemSelectorInputId}>{dataSetLabel}</label>

				<ClayInput.Group small>
					<ClayInput.GroupItem>
						<ClayInput
							className="page-editor__item-selector__content-input"
							id={itemSelectorInputId}
							placeholder={sub(
								Liferay.Language.get('no-x-selected'),
								dataSetLabel
							)}
							readOnly
							sizing="sm"
							type="text"
							value={values.itemSelector.label || ''}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButtonWithIcon
							aria-label={selectContentButtonLabel}
							displayType="secondary"
							onClick={() => {
								openItemSelectorModal({
									apiURL: getDataSetResourceURL({
										params: {
											nestedFields:
												'dataSetToDataSetCardsSections, dataSetToDataSetTableSections, dataSetToDataSetListSections',
										},
									}),
									fdsProps: {
										id: 'dataSetsItemSelectorModal',
										views: fdsViews,
									},
									itemTypeLabel: dataSetLabel,
									items: values.itemSelector
										.externalReferenceCode
										? [values.itemSelector]
										: [],
									onItemsChange: (items: IDataSet[]) => {
										onValueSelect('itemSelector', {
											additionalAPIURLParameters:
												items[0]
													.additionalAPIURLParameters,
											externalReferenceCode:
												items[0].externalReferenceCode,
											id: items[0].id,
											label: items[0].label,
											restEndpoint: items[0].restEndpoint,
										});
									},
								});
							}}
							size="sm"
							symbol={selectContentButtonIcon}
							title={selectContentButtonLabel}
						/>
					</ClayInput.GroupItem>

					{dataSetSelected && (
						<ClayInput.GroupItem shrink>
							<ClayDropDownWithItems
								items={[
									{
										label: sub(
											Liferay.Language.get('remove-x'),
											dataSetLabel
										),
										onClick: () => {
											onValueSelect('itemSelector', {});
										},
										symbolLeft: 'trash',
									},
								]}
								menuElementAttrs={{
									containerProps: {
										className: 'cadmin',
									},
								}}
								trigger={
									<ClayButtonWithIcon
										aria-label={sub(
											Liferay.Language.get(
												'view-x-options'
											),
											dataSetLabel
										)}
										displayType="secondary"
										size="sm"
										symbol="ellipsis-v"
										title={sub(
											Liferay.Language.get(
												'view-x-options'
											),
											dataSetLabel
										)}
									/>
								}
							/>
						</ClayInput.GroupItem>
					)}
				</ClayInput.Group>
			</ClayForm.Group>

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
							!isContextMapped(currentTokenMapping) && (
								<EntitySelectorRow
									entity={currentTokenMapping}
									onSelectEntity={() =>
										openEntitySelector(selectedTokenKey)
									}
								/>
							)}

						<ClayForm.Group>
							<label htmlFor={fieldInputId}>
								{Liferay.Language.get('field')}
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
										typeof currentTokenMapping === 'string'
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
											)
										) {
											return;
										}

										updateTokenMapping(selectedTokenKey, {
											...currentTokenMapping,
											fieldId: event.target
												.value as IdentifierField,
										});
									}}
									options={[
										{
											label: Liferay.Language.get('id'),
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
										isMappedTokenValue(currentTokenMapping)
											? currentTokenMapping.fieldId
											: 'classPK'
									}
								/>
							)}
						</ClayForm.Group>
					</>
				)}
		</>
	);
}
