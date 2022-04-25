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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayModal from '@clayui/modal';
import {fetch} from 'frontend-js-web';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import {defaultLanguageId} from '../../../utils/locale';
import AutoComplete from '../../Form/AutoComplete';
import {CheckboxItem} from '../../Form/CheckBoxItem';
import CustomSelect from '../../Form/CustomSelect/CustomSelect';
import Input from '../../Form/Input';
import ViewContext from '../context';
import {TName, TObjectField} from '../types';

const headers = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const PICKLIST_OPERATORS: IPickListOperators[] = [
	{
		label: Liferay.Language.get('includes'),
		value: 'includes',
	},
	{
		label: Liferay.Language.get('excludes'),
		value: 'excludes',
	},
];

export function ModalAddDefaultFilterColumn({
	editingFilter,
	header,
	observer,
	onClose,
}: IProps) {
	const [{objectFields, workflowStatusJSONArray}] = useContext(ViewContext);

	const [availableFields, setAvailableFields] = useState(objectFields);

	const [items, setItems] = useState<IItem[]>([]);

	const [selectedFilterBy, setSelectedFilterBy] = useState<TObjectField>();

	const [selectedFilterType, setSelectedFilterType] = useState<
		IPickListOperators
	>(PICKLIST_OPERATORS[0]);

	const [query, setQuery] = useState<string>('');

	const [active, setActive] = useState(false);

	const filtredAvailableFields = useMemo(() => {
		return availableFields.filter(({label}) => {
			return label[defaultLanguageId]
				.toLowerCase()
				.includes(query.toLowerCase());
		});
	}, [availableFields, query]);

	useEffect(() => {
		const filteredPickListFields = objectFields.filter((objectField) => {
			if (
				objectField.businessType === 'Picklist' ||
				objectField.name === 'status'
			) {
				return objectField;
			}
		});

		setAvailableFields(filteredPickListFields);
	}, [objectFields]);

	useEffect(() => {
		if (!selectedFilterBy) {
			setItems([]);
		}
		else if (selectedFilterBy?.businessType === 'Picklist') {
			const makeFetch = async () => {
				const response = await fetch(
					`/o/headless-admin-list-type/v1.0/list-type-definitions/${selectedFilterBy.listTypeDefinitionId}/list-type-entries`,
					{
						headers,
						method: 'GET',
					}
				);

				const {items = []} = (await response.json()) as {
					items?: TPickListValue[];
				};

				setItems(
					items.map((item) => {
						return {
							label: item.name,
							value: item.key,
						};
					})
				);
			};

			makeFetch();
		}
		else {
			setItems(
				workflowStatusJSONArray.map((worflowStatus) => {
					return {
						label: worflowStatus.label,
						value: worflowStatus.value,
					};
				})
			);
		}
	}, [selectedFilterBy, workflowStatusJSONArray]);

	return (
		<ClayModal observer={observer}>
			<ClayModal.Header>{header}</ClayModal.Header>

			<ClayModal.Body>
				{!editingFilter && (
					<AutoComplete
						emptyStateMessage=""
						items={filtredAvailableFields}
						label={Liferay.Language.get('filter-by')}
						onChangeQuery={setQuery}
						onSelectItem={setSelectedFilterBy}
						query={query}
						required
						value={selectedFilterBy?.label[defaultLanguageId]}
					>
						{({label}) => (
							<div className="d-flex justify-content-between">
								<div>{label[defaultLanguageId]}</div>
							</div>
						)}
					</AutoComplete>
				)}

				<CustomSelect
					label={Liferay.Language.get('filter-type')}
					onChange={(target: IPickListOperators) =>
						setSelectedFilterType(target)
					}
					options={PICKLIST_OPERATORS}
					value={selectedFilterType?.label}
				/>

				<ClayDropDown
					active={active}
					onActiveChange={setActive}
					trigger={
						<Input
							label={Liferay.Language.get('value')}
							placeholder={Liferay.Language.get(
								'choose-an-option'
							)}
							value={items
								.reduce<string[]>((acc, value) => {
									if (value.checked) {
										acc.push(value.label);
									}

									return acc;
								}, [])
								.join(', ')}
						/>
					}
				>
					<ClayDropDown.ItemList>
						{items.map(({checked, label, value}) => (
							<CheckboxItem
								checked={checked}
								key={value}
								label={label}
								onChange={({target: {checked}}) => {
									setItems(
										items.map((item) =>
											item.label === label
												? {
														...item,
														checked,
												  }
												: item
										)
									);
								}}
							/>
						))}
					</ClayDropDown.ItemList>
				</ClayDropDown>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onClose()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={!selectedFilterBy}
							displayType="primary"
							type="button"
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

interface IProps {
	editingFilter: boolean;
	header: string;
	observer: any;
	onClose: () => void;
}

interface IPickListOperators {
	label: string;
	value: string;
}

interface IItem extends IPickListOperators {
	checked?: boolean;
}

type TPickListValue = {
	dateCreated: string;
	dateModified: number;
	id: number;
	key: string;
	name: string;
	name_i18n: TName;
	type: string;
};
