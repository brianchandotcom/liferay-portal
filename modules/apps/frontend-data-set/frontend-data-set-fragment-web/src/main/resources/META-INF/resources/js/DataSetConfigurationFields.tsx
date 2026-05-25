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
import React, {useCallback, useState} from 'react';

const EDITOR_PORTLET_ID =
	'com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet';

const ITEM_SELECTOR_URL_RESOURCE_COMMAND =
	'/frontend_data_set_fragment/get_info_item_selector_url';

type IdentifierField = 'classPK' | 'externalReferenceCode';

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

type TokenValue = string | IMappedTokenValue;

function isMappedTokenValue(value: TokenValue): value is IMappedTokenValue {
	return typeof value === 'object' && value !== null;
}

function isContextMapped(
	value: IMappedTokenValue
): value is IContextMappedTokenValue {
	return value.source === 'context';
}

function isOnDisplayPageTemplate(): boolean {
	return !!document.getElementById('infoItemSelectorContainer');
}

interface IConfigurationField {
	onValueSelect: (name: string, value: any) => void;
	values: {
		apiURLTokenValues: string;
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

function TokenRow({
	onChange,
	token,
	tokenInputId,
	value,
}: {
	onChange: (next: TokenValue) => void;
	token: string;
	tokenInputId: string;
	value: TokenValue;
}) {
	const fieldSelectId = useId();

	const [inputValue, setInputValue] = useState(
		isMappedTokenValue(value) ? '' : value
	);

	const displayPageTemplate = isOnDisplayPageTemplate();

	const mapToContext = useCallback(() => {
		const currentFieldId = isMappedTokenValue(value)
			? value.fieldId
			: 'classPK';

		onChange({
			fieldId: currentFieldId,
			source: 'context',
		});
	}, [onChange, value]);

	const openInfoItemSelector = useCallback(async () => {
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

				const currentFieldId = isMappedTokenValue(value)
					? value.fieldId
					: 'classPK';

				onChange({
					className: selectedValue.className,
					classPK: String(selectedValue.classPK ?? ''),
					externalReferenceCode:
						selectedValue.externalReferenceCode ?? '',
					fieldId: currentFieldId,
					source: 'content',
					title: selectedValue.title,
				});
			},
			selectEventName: eventName,
			title: Liferay.Language.get('select-an-entity'),
			url,
		});
	}, [onChange, value]);

	const unmap = useCallback(() => {
		onChange('');
	}, [onChange]);

	const fieldOptions = [
		{label: Liferay.Language.get('id'), value: 'classPK'},
		{
			label: Liferay.Language.get('external-reference-code'),
			value: 'externalReferenceCode',
		},
	];

	if (isMappedTokenValue(value)) {
		const displayValue = isContextMapped(value)
			? Liferay.Language.get('current-entity-display-page-template')
			: value.title ||
				(value.fieldId === 'externalReferenceCode'
					? value.externalReferenceCode
					: value.classPK);

		return (
			<ClayForm.Group key={token}>
				<label htmlFor={tokenInputId}>{token}</label>

				<ClayInput.Group small>
					<ClayInput.GroupItem>
						<ClayInput
							id={tokenInputId}
							readOnly
							sizing="sm"
							type="text"
							value={displayValue}
						/>
					</ClayInput.GroupItem>

					{!isContextMapped(value) && (
						<ClayInput.GroupItem shrink>
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'change-entity'
								)}
								displayType="secondary"
								onClick={openInfoItemSelector}
								size="sm"
								symbol="change"
								title={Liferay.Language.get('change-entity')}
							/>
						</ClayInput.GroupItem>
					)}

					<ClayInput.GroupItem shrink>
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('unmap')}
							displayType="secondary"
							onClick={unmap}
							size="sm"
							symbol="times"
							title={Liferay.Language.get('unmap')}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>

				<label htmlFor={fieldSelectId}>
					{Liferay.Language.get('identifier-field')}
				</label>

				<ClaySelectWithOption
					id={fieldSelectId}
					onChange={(event) => {
						onChange({
							...value,
							fieldId: event.target.value as IdentifierField,
						});
					}}
					options={fieldOptions}
					sizing="sm"
					value={value.fieldId}
				/>
			</ClayForm.Group>
		);
	}

	return (
		<ClayForm.Group key={token}>
			<label htmlFor={tokenInputId}>{token}</label>

			<ClayInput.Group small>
				<ClayInput.GroupItem>
					<ClayInput
						id={tokenInputId}
						onBlur={(event) => onChange(event.target.value)}
						onChange={(event) => setInputValue(event.target.value)}
						sizing="sm"
						type="text"
						value={inputValue || ''}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem shrink>
					{displayPageTemplate ? (
						<ClayDropDownWithItems
							items={[
								{
									onClick: mapToContext,
									symbolLeft: 'page',
									title: Liferay.Language.get(
										'map-to-current-entity'
									),
								},
								{
									onClick: openInfoItemSelector,
									symbolLeft: 'link',
									title: Liferay.Language.get(
										'map-to-a-specific-entity'
									),
								},
							]}
							trigger={
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get('map')}
									displayType="secondary"
									size="sm"
									symbol="link"
									title={Liferay.Language.get('map')}
								/>
							}
						/>
					) : (
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get(
								'map-to-a-specific-entity'
							)}
							displayType="secondary"
							onClick={openInfoItemSelector}
							size="sm"
							symbol="link"
							title={Liferay.Language.get(
								'map-to-a-specific-entity'
							)}
						/>
					)}
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}

export default function DataSetConfigurationFields({
	onValueSelect,
	values,
}: IConfigurationField) {
	const [localAPIURLTokenValues, setLocalAPIURLTokenValues] = useState<
		Record<string, TokenValue>
	>(JSON.parse(values.apiURLTokenValues || '{}'));

	const itemSelectorInputId = useId();
	const tokenBaseInputId = useId();

	const label = Liferay.Language.get('data-set');

	const dataSetSelected: boolean =
		Object.keys(values.itemSelector).length !== 0;

	const selectContentButtonIcon = dataSetSelected ? 'change' : 'plus';

	const selectContentButtonLabel = sub(
		dataSetSelected
			? Liferay.Language.get('change-x')
			: Liferay.Language.get('select-x'),
		label
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
		values.itemSelector.restEndpoint +
		values.itemSelector.additionalAPIURLParameters || '';

	const tokens = apiURL?.match(/{(.*?)}/g);

	const updateTokenValue = useCallback(
		(tokenKey: string, value: TokenValue) => {
			const newTokensValue = {
				...localAPIURLTokenValues,
				[tokenKey]: value,
			};

			setLocalAPIURLTokenValues(newTokensValue);

			onValueSelect('apiURLTokenValues', JSON.stringify(newTokensValue));
		},
		[localAPIURLTokenValues, onValueSelect]
	);

	return (
		<>
			<ClayForm.Group>
				<label htmlFor={itemSelectorInputId}>{label}</label>

				<ClayInput.Group small>
					<ClayInput.GroupItem>
						<ClayInput
							className="page-editor__item-selector__content-input"
							id={itemSelectorInputId}
							placeholder={sub(
								Liferay.Language.get('no-x-selected'),
								label
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
									itemTypeLabel: label,
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
											label
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
											label
										)}
										displayType="secondary"
										size="sm"
										symbol="ellipsis-v"
										title={sub(
											Liferay.Language.get(
												'view-x-options'
											),
											label
										)}
									/>
								}
							/>
						</ClayInput.GroupItem>
					)}
				</ClayInput.Group>
			</ClayForm.Group>

			{Liferay.FeatureFlags['LPD-38564'] &&
				tokens?.map((token) => {
					const tokenKey = token.replaceAll(/[{}]/g, '');

					const tokenInputId = `${tokenBaseInputId}_${tokenKey}`;

					const tokenValue: TokenValue =
						localAPIURLTokenValues[tokenKey] ?? '';

					return (
						<TokenRow
							key={token}
							onChange={(value) =>
								updateTokenValue(tokenKey, value)
							}
							token={token}
							tokenInputId={tokenInputId}
							value={tokenValue}
						/>
					);
				})}
		</>
	);
}
