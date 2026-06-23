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
import React, {useEffect, useState} from 'react';

import SpaceSticker from '../../../common/components/SpaceSticker';
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
	labels,
	onChange,
	onError,
	onSelectionChange,
	preselectedItems,
	showErrorInitially = true,
	sourceItems,
}: {
	error: string;
	labels: ScopeLabels;
	onChange?: (value: boolean) => void;
	onError: (value: string) => void;
	onSelectionChange: (value: any) => void;
	preselectedItems?: PreselectedItem[];
	showErrorInitially?: boolean;
	sourceItems: T[];
}) {
	const [allScopesChecked, setAllScopesChecked] = useState(true);
	const [displayError, setDisplayError] = useState(showErrorInitially);
	const [query, setQuery] = useState('');
	const [selectedItems, setSelectedItems] = useState<T[]>([]);
	const [initialSelectedValues, setInitialSelectedValues] = useState<any[]>(
		[]
	);

	const loadingState = !sourceItems.length
		? NetworkStatus.Polling
		: undefined;

	useEffect(() => {
		if (!sourceItems.length) {
			return;
		}

		const initialValues = preselectedItems?.map(
			(preselectedItem) =>
				sourceItems.find((item) => item.label === preselectedItem.name)
					?.value
		);

		setInitialSelectedValues(initialValues ?? []);

		if (
			!preselectedItems ||
			!preselectedItems.length ||
			preselectedItems.some((item) => item.id === -1)
		) {
			setAllScopesChecked(true);

			setSelectedItems([]);
		}
		else if (initialValues) {
			setAllScopesChecked(false);

			setSelectedItems(
				sourceItems.filter((item) => initialValues.includes(item.value))
			);
		}
	}, [preselectedItems, sourceItems]);

	useEffect(() => {
		if (onChange) {
			if (allScopesChecked) {
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

		if (allScopesChecked || selectedItems.length) {
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
		allScopesChecked,
		initialSelectedValues,
		labels.field,
		onChange,
		onError,
		selectedItems,
	]);

	const _getAvailableItems = (items: T[]) =>
		sourceItems.filter((sourceItem) =>
			items.some((item) => sourceItem.value === item.value)
		);

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
					disabled={allScopesChecked}
					id="multiSelect"
					items={selectedItems}
					key={sourceItems.length ? 'loaded' : 'empty'}
					loadingState={loadingState}
					onChange={setQuery}
					onItemsChange={_handleChangeItems}
					sourceItems={sourceItems}
					value={allScopesChecked ? labels.allItemsValue : query}
				>
					{(item) => (
						<ClayMultiSelect.Item
							key={item.value}
							textValue={item.label}
						>
							<SpaceSticker
								displayType={item.displayType}
								name={item.label}
								size="sm"
							/>
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
					checked={allScopesChecked}
					label={labels.checkbox}
					onChange={() => {
						if (!showErrorInitially && allScopesChecked) {
							setDisplayError(false);
						}

						setAllScopesChecked((checked) => !checked);
						setSelectedItems([]);
						onSelectionChange([]);
						setQuery('');
					}}
				/>
			</div>
		</div>
	);
}
