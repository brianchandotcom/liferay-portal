/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import {Item} from '@clayui/multi-select/lib/types';
import classnames from 'classnames';
import React, {useCallback, useEffect, useState} from 'react';

// @ts-ignore

import {getListTypeEntries} from '../../data/listType';
import ErrorMessage from '../ErrorMessage';
import {TGenericFieldProps} from '../FieldsWrapper';

const MULTISELECT_PICKLIST_SEPARATOR = ', ';

const _itemsToValue = (items: TMultiselectPicklistItem[], i18n = false) =>
	items.reduce(
		(
			valueAsString: string,
			{key, label}: TMultiselectPicklistItem
		): string => {
			const stringChunk = i18n ? label : key;

			return valueAsString
				? `${valueAsString}${MULTISELECT_PICKLIST_SEPARATOR}${stringChunk}`
				: stringChunk;
		},
		''
	);

const _valueToItems = (
	value: string | undefined,
	sourceItems: TMultiselectPicklistItem[]
): TMultiselectPicklistItem[] =>
	sourceItems.filter((sourceItem) =>
		`${value}${MULTISELECT_PICKLIST_SEPARATOR}`?.includes(
			`${sourceItem.key}${MULTISELECT_PICKLIST_SEPARATOR}`
		)
	);

const MultiselectPicklist = ({
	disabled,
	id,
	label,
	mode = 'view',
	name,
	namespace,
	onChange,
	originalField: {listTypeDefinitionId},
	readOnly,
	required = false,
	value: keysAsValue,
}: TGenericFieldProps) => {
	const [commaSeparatedKeys] = useState<string | null>(keysAsValue);
	const [internalValue, setInternalValue] = useState<string | null>(null);
	const [items, setItems] = useState<TMultiselectPicklistItem[]>([]);
	const [error, setError] = useState<string | null>(null);
	const [sourceItems, setSourceItems] = useState<TMultiselectPicklistItem[]>(
		[]
	);

	const fetchSourceItems = useCallback(() => {
		getListTypeEntries(listTypeDefinitionId).then(
			({items: listTypeEntries}: {items: TPartialListTypeEntry[]}) => {
				const languageId = Liferay.ThemeDisplay.getBCP47LanguageId();

				setSourceItems(
					listTypeEntries.map(
						(
							entry: TPartialListTypeEntry
						): TMultiselectPicklistItem => ({
							key: entry.key,
							label: entry.name_i18n[languageId],
							value: entry.key,
						})
					)
				);
			}
		);
	}, [listTypeDefinitionId]);

	const onItemsChange = useCallback(
		(updatedItems: Item[] = []) => {
			if (updatedItems.length && updatedItems.length > items.length) {
				const [lastAdded]: Item[] = updatedItems.slice(-1);

				const isSelectable: boolean =
					-1 !==
					sourceItems.findIndex(({key}) => key === lastAdded.key);
				const isAlreadyAdded: boolean =
					-1 !== items.findIndex(({key}) => key === lastAdded.key);

				if (isAlreadyAdded || !isSelectable) {
					updatedItems.pop();
				}
			}

			setItems(updatedItems as TMultiselectPicklistItem[]);
		},
		[items, sourceItems]
	);

	useEffect(() => {
		if (sourceItems.length) {
			const selectedItems = _valueToItems(
				commaSeparatedKeys as string,
				sourceItems
			);

			setItems(selectedItems);
			setInternalValue(_itemsToValue(selectedItems, true));
		}
		else {
			fetchSourceItems();
		}
	}, [fetchSourceItems, sourceItems, commaSeparatedKeys]);

	useEffect(() => {
		if (mode === 'edit') {
			const hasError = required && !items.length;

			onChange({
				hasError,
				name,
				value: _itemsToValue(items),
			});

			setError(
				hasError ? Liferay.Language.get('this-field-is-required') : null
			);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [items]);

	return mode === 'edit' ? (
		<ClayForm.Group
			className={classnames({
				'has-error': !!error,
			})}
		>
			<label htmlFor={`${namespace}${name}`}>
				{label}

				{required && (
					<ClayIcon
						className="c-ml-1 reference-mark"
						symbol="asterisk"
					/>
				)}
			</label>

			{(!!sourceItems.length || !!items.length) && (
				<ClayMultiSelect
					aria-required={required}
					disabled={disabled || readOnly}
					id={`${namespace}${id}`}
					inputName={`${namespace}${name}`}
					items={items}
					onItemsChange={onItemsChange}
					placeholder={Liferay.Language.get('select-items')}
					sourceItems={sourceItems}
				/>
			)}

			<ErrorMessage error={error} />
		</ClayForm.Group>
	) : (
		<div key={`${namespace}_${id}`}>
			<div className="sidebar-dt">{label}</div>

			<div className="sidebar-dd text-wrap">{internalValue || '-'}</div>
		</div>
	);
};

type TMultiselectPicklistItem = {
	key: string;
	label: string;
	value: string;
};

type TPartialListTypeEntry = {
	key: string;
	name_i18n: {
		[BCP47LanguageId: string]: string;
	};
};

export default MultiselectPicklist;
