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
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayModal from '@clayui/modal';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {FormEvent, useEffect, useState} from 'react';

import {defaultLanguageId} from '../../../utils/locale';
import {ManagementToolbarSearch} from '../ManagementToolbarSearch/ManagementToolbarSearch';
import {TYPES} from '../context';

import './ModalAddColumns.scss';
import {TObjectField} from '../types';
interface IProps extends React.HTMLAttributes<HTMLElement> {
	handleSubmit: any;
	isActionBuilder?: boolean;
	objectFields: any[];
	objectViewColumns: any;
	observer: any;
	onClose: () => void;
}

const ModalAddColumns: React.FC<IProps> = ({
	handleSubmit,
	isActionBuilder,
	objectFields,
	objectViewColumns,
	observer,
	onClose,
}) => {
	const [checkedItems, setCheckedItems] = useState<unknown[]>(
		objectViewColumns ?? []
	);

	const [filteredItems, setFilteredItems] = useState(objectFields);
	const [fieldsChecked, setFieldsChecked] = useState(false);
	const [allFieldsChecked, setAllFieldsChecked] = useState(false);
	const [query, setQuery] = useState('');

	useEffect(() => {
		if (isActionBuilder && objectViewColumns) {
			objectFields.forEach((objectField) => {
				if (
					objectViewColumns.find(
						(requiedField: {id: string}) =>
							requiedField.id === objectField.id
					)
				) {
					objectField.checked = true;
				}
			});
			setFilteredItems(objectFields);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		const checkedItems = filteredItems?.filter(
			({checked}) => checked === true
		);

		setAllFieldsChecked(checkedItems?.length === filteredItems?.length);

		setFieldsChecked(checkedItems?.length > 0);
	}, [fieldsChecked, filteredItems]);

	useEffect(() => {
		setFilteredItems((filteredItems) =>
			filteredItems?.map((field) => {
				const filtered =
					!query ||
					field.label[defaultLanguageId]
						.toLowerCase()
						.includes(query.toLowerCase());

				return {...field, filtered};
			})
		);
	}, [query]);

	const handleAllFieldsChecked = (checked: boolean) => {
		setCheckedItems([]);

		const setSelection = (checked: boolean) => {
			if (allFieldsChecked) {
				return false;
			}
			else if (fieldsChecked) {
				return true;
			}
			else if (!checked) {
				return false;
			}
			else {
				setFieldsChecked(true);

				return true;
			}
		};

		const newFiltredItems = filteredItems?.map((field) => {
			return {
				...field,
				checked: setSelection(checked),
			};
		});

		setFilteredItems(newFiltredItems);

		if (checked === true) {
			setCheckedItems(
				newFiltredItems.map((filteredItem, index) => {
					return {
						defaultSort: false,
						fieldLabel: filteredItem.label[defaultLanguageId],
						label: filteredItem.label,
						objectFieldName: filteredItem.name,
						priority: index,
					};
				})
			);
		}
		else {
			setCheckedItems([]);
		}
	};

	const toggleFieldCheckbox = (name: string) => {
		const newfilteredItems: TObjectField[] = [];

		filteredItems?.map((field, index) => {
			if (field.name === name) {
				newfilteredItems.push({
					...field,
					checked: !field.checked,
				});

				if (!field.checked) {
					setCheckedItems([
						...checkedItems,
						{
							defaultSort: false,
							fieldLabel: field.label[defaultLanguageId],
							label: field.label,
							objectFieldName: field.name,
							priority: index,
						},
					]);
				}
				else {
					const newCheckedItems = checkedItems.filter(
						(checkedItem: any) =>
							field.name !== checkedItem.objectFieldName
					);
					setCheckedItems(newCheckedItems);
				}
			}
			else {
				newfilteredItems.push(field);
			}
		});

		setFilteredItems(newfilteredItems);
	};

	const onSubmit = (event: FormEvent) => {
		event.preventDefault();

		if (isActionBuilder) {
			handleSubmit(
				filteredItems.filter((field) => field.checked || field.required)
			);
		}
		else {
			handleSubmit({
				payload: {
					checkedItems,
					filteredItems,
				},
				type: TYPES.ADD_OBJECT_VIEW_COLUMN,
			});
		}

		onClose();
	};

	return (
		<ClayModal
			className="lfr-object__object-view-modal-add-columns"
			observer={observer}
		>
			<ClayModal.Header>
				{Liferay.Language.get('add-columns')}
			</ClayModal.Header>

			<ClayModal.Body>
				<div className="lfr-object__object-view-modal-add-columns-selection-title">
					{Liferay.Language.get('select-the-columns')}
				</div>

				<ManagementToolbar.Container>
					<ManagementToolbar.ItemList>
						<ManagementToolbar.Item>
							<ClayCheckbox
								checked={fieldsChecked}
								indeterminate={
									!allFieldsChecked && fieldsChecked
								}
								onChange={({target}) => {
									if (!allFieldsChecked && fieldsChecked) {
										handleAllFieldsChecked(true);
									}
									else {
										handleAllFieldsChecked(target.checked);
									}
								}}
							/>
						</ManagementToolbar.Item>
					</ManagementToolbar.ItemList>

					<ManagementToolbarSearch
						query={query}
						setQuery={setQuery}
					/>
				</ManagementToolbar.Container>
			</ClayModal.Body>

			<ClayForm onSubmit={(event) => onSubmit(event)}>
				<ClayList className="lfr-object__object-view-modal-add-columns-list">
					{filteredItems?.map((field, index) => (
						<ClayList.Item
							className={field?.filtered ? '' : 'hide'}
							flex
							key={`list-item-${index}`}
						>
							<ClayCheckbox
								checked={
									isActionBuilder
										? field.required || field.checked
										: field.checked
								}
								disabled={
									isActionBuilder ? field.required : false
								}
								label={
									isActionBuilder
										? field.name
										: field.label[defaultLanguageId]
								}
								onChange={() => {
									toggleFieldCheckbox(field.name);
								}}
							/>

							{isActionBuilder && field.required && (
								<span className="reference-mark">
									<ClayIcon symbol="asterisk" />
								</span>
							)}
						</ClayList.Item>
					))}
				</ClayList>

				<ClayModal.Footer
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton displayType="primary" type="submit">
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
};

export default ModalAddColumns;
