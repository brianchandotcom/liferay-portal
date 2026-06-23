/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {NetworkStatus} from '@clayui/data-provider';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import {sub} from 'frontend-js-web';
import React, {ReactNode, useEffect, useState} from 'react';

import {LogoColor} from '../../../common/types/Space';

export type ScopeItem = {
	displayType?: LogoColor;
	label: string;
	scopeKey: string;
	value: any;
};

type ScopeLabels = {
	allItemsValue: string;
	ariaLabel: string;
	checkbox: string;
	field: string;
};

type PreselectedItem = {
	id?: number;
	name?: string;
};

export default function ScopeMultiSelect<T extends ScopeItem>({
	error,
	getItems,
	labels,
	onChange,
	onError,
	onSelectionChange,
	preselectedItems,
	renderItem,
	showErrorInitially = true,
}: {
	error: string;
	getItems: () => Promise<T[]>;
	labels: ScopeLabels;
	onChange?: (value: boolean) => void;
	onError: (value: string) => void;
	onSelectionChange: (value: any) => void;
	preselectedItems?: PreselectedItem[];
	renderItem: (item: T) => ReactNode;
	showErrorInitially?: boolean;
}) {
	const [availableItems, setAvailableItems] = useState<T[]>([]);
	const [availableItemsKey, setAvailableItemsKey] = useState(0);
	const [allItemsChecked, setAllItemsChecked] = useState(true);
	const [displayError, setDisplayError] = useState(showErrorInitially);
	const [query, setQuery] = useState('');
	const [selectedItems, setSelectedItems] = useState<T[]>([]);
	const [initialSelectedValues, setInitialSelectedValues] = useState<any[]>(
		[]
	);

	const loadingState = !availableItems.length
		? NetworkStatus.Polling
		: undefined;

	useEffect(() => {
		getItems().then((items) => {
			setAvailableItems(items);
			setAvailableItemsKey((key) => key + 1);

			const initialValues = preselectedItems?.map(
				(preselectedItem) =>
					items.find((item) => item.label === preselectedItem.name)
						?.value
			);

			setInitialSelectedValues(initialValues ?? []);

			if (
				!preselectedItems ||
				!preselectedItems.length ||
				preselectedItems.some((item) => item.id === -1)
			) {
				setAllItemsChecked(true);

				setSelectedItems([]);
			}
			else if (initialValues) {
				setAllItemsChecked(false);

				setSelectedItems(
					items.filter((item) => initialValues.includes(item.value))
				);
			}
		});
	}, [getItems, preselectedItems]);

	useEffect(() => {
		if (onChange) {
			if (allItemsChecked) {
				onChange(false);
			}
			else if (
				initialSelectedValues.some(
					(value) =>
						!selectedItems.find((item) => item.value === value)
				)
			) {
				onChange(true);
			}
			else {
				onChange(false);
			}
		}

		if (allItemsChecked || selectedItems.length) {
			onError('');
		}
		else {
			onError(
				sub(
					Liferay.Language.get('the-x-field-is-required'),
					labels.field
				)
			);
		}
	}, [
		allItemsChecked,
		initialSelectedValues,
		labels.field,
		onChange,
		onError,
		selectedItems,
	]);

	const _getAvailableItems = (items: T[]) =>
		availableItems.filter((availableItem) =>
			items.some((item) => availableItem.value === item.value)
		);

	const _handleToggleAllItems = () => {
		if (!showErrorInitially && allItemsChecked) {
			setDisplayError(false);
		}

		setSelectedItems([]);
		onSelectionChange([]);
		setQuery('');
		setAllItemsChecked((checked) => !checked);
	};

	const _handleChangeItems = (items: T[]) => {
		setDisplayError(true);
		setSelectedItems(_getAvailableItems(items));

		onSelectionChange(items.map((item) => item.scopeKey));
	};

	return (
		<div>
			<label htmlFor="multiSelect">
				{labels.field}

				<span className="ml-1 reference-mark">
					<ClayIcon symbol="asterisk" />
				</span>
			</label>

			<div className={displayError && error ? 'has-error' : ''}>
				<ClayMultiSelect
					aria-label={labels.ariaLabel}
					disabled={allItemsChecked}
					id="multiSelect"
					items={selectedItems}
					key={availableItemsKey}
					loadingState={loadingState}
					onChange={setQuery}
					onItemsChange={_handleChangeItems}
					sourceItems={availableItems}
					value={allItemsChecked ? labels.allItemsValue : query}
				>
					{(item) => (
						<ClayMultiSelect.Item
							key={item.value}
							textValue={item.label}
						>
							{renderItem(item)}
						</ClayMultiSelect.Item>
					)}
				</ClayMultiSelect>

				{displayError && error && (
					<ClayAlert displayType="danger" variant="feedback">
						<strong>{Liferay.Language.get('error')}: </strong>

						{error}
					</ClayAlert>
				)}
			</div>

			<div className="mt-2">
				<ClayCheckbox
					checked={allItemsChecked}
					label={labels.checkbox}
					onChange={_handleToggleAllItems}
				/>
			</div>
		</div>
	);
}
