/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import {
	IDataSet,
	getDataSetResourceURL,
} from '@liferay/frontend-data-set-admin-web';
import {openItemSelectorModal} from '@liferay/frontend-js-item-selector-web';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

interface IConfigurationField {
	onValueSelect: (name: string, value: any) => void;
	values: {
		apiURLTokenValues: string;
		itemSelector: IDataSet;
	};
}

export default function DataSetConfigurationFields({
	onValueSelect,
	values,
}: IConfigurationField) {
	const [localAPIURLTokenValues, setLocalAPIURLTokenValues] = useState<
		Record<string, string>
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

	const tokens = values.itemSelector.restEndpoint?.match(/{(.*?)}/g);

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

			{Liferay.FeatureFlags['LPD-68748'] &&
				tokens?.map((token) => {
					const tokenInputId = `${tokenBaseInputId}_${token}`;

					return (
						<ClayForm.Group key={token}>
							<label htmlFor={tokenInputId}>
								{token.replaceAll(/[{}]/g, '')}
							</label>

							<ClayInput
								defaultValue={
									localAPIURLTokenValues[token] || token
								}
								id={tokenInputId}
								onChange={(event) => {
									const newTokensValue = {
										...localAPIURLTokenValues,
										[token]: event.target.value,
									};

									setLocalAPIURLTokenValues(newTokensValue);

									onValueSelect(
										'apiURLTokenValues',
										JSON.stringify(newTokensValue)
									);
								}}
								sizing="sm"
								type="text"
							/>
						</ClayForm.Group>
					);
				})}
		</>
	);
}
