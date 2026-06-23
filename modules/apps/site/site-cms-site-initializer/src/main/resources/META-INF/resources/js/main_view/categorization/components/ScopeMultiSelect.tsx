/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {NetworkStatus} from '@clayui/data-provider';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import {sub} from 'frontend-js-web';
import React, {useEffect, useId, useState} from 'react';

import SpaceSticker from '../../../common/components/SpaceSticker';
import ErrorFeedback from '../../../common/components/forms/ErrorFeedback';
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
	sourceItems,
}: {
	error: string;
	labels: ScopeLabels;
	onChange?: (value: boolean) => void;
	onError: (value: string) => void;
	onSelectionChange: (value: any) => void;
	preselectedItems?: PreselectedItem[];
	sourceItems: T[];
}) {
	const [allScopesChecked, setAllScopesChecked] = useState(true);
	const [query, setQuery] = useState('');
	const [touched, setTouched] = useState(false);
	const [selectedItems, setSelectedItems] = useState<T[]>([]);
	const [initialSelectedValues, setInitialSelectedValues] = useState<any[]>(
		[]
	);

	const errorId = useId();

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
			const initialValueRemoved = initialSelectedValues.some(
				(value) => !selectedItems.some((item) => item.value === value)
			);

			onChange(!allScopesChecked && initialValueRemoved);
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

	const getSelectedItems = (items: T[]) =>
		sourceItems.filter((sourceItem) =>
			items.some((item) => sourceItem.value === item.value)
		);

	return (
		<div>
			<label htmlFor="multiSelect">
				{labels.field}

				<span className="ml-1 reference-mark">
					<ClayIcon symbol="asterisk" />
				</span>
			</label>

			<div className={touched && error ? 'has-error' : ''}>
				<ClayMultiSelect
					aria-describedby={touched && error ? errorId : undefined}
					aria-label={labels.ariaLabel}
					disabled={allScopesChecked}
					id="multiSelect"
					items={selectedItems}
					key={sourceItems.length ? 'loaded' : 'empty'}
					loadingState={loadingState}
					onBlur={() => setTouched(true)}
					onChange={setQuery}
					onItemsChange={(items: T[]) => {
						setTouched(true);
						setSelectedItems(getSelectedItems(items));

						onSelectionChange(items.map((item) => item.scopeKey));
					}}
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

				{touched && error && (
					<ClayForm.FeedbackGroup id={errorId} role="alert">
						<ErrorFeedback message={error} />
					</ClayForm.FeedbackGroup>
				)}
			</div>

			<div className="mt-2">
				<ClayCheckbox
					checked={allScopesChecked}
					label={labels.checkbox}
					onChange={() => {
						if (allScopesChecked) {
							setTouched(false);
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
