/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {useFormState} from 'data-engine-js-components-web';
import React, {useEffect, useState} from 'react';

import {MultiSelectItem, MultiSelectProps} from './select';

const MultipleSelection = ({
	name,
	onChange,
	options,
	readOnly,
	required,
	value: values,
}: MultiSelectProps) => {
	const [items, setItems] = useState<MultiSelectItem[]>([]);
	const [loading, setLoading] = useState<boolean>();
	const {activeTabTitle, viewMode} = useFormState();

	useEffect(() => {
		const newItems = options.filter((option) => {
			if (values?.includes(option.value)) {
				return {label: option.label};
			}
		});

		setItems(newItems);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values]);

	useEffect(() => {
		if (
			!readOnly &&
			activeTabTitle !== Liferay.Language.get('advanced') &&
			!viewMode
		) {
			setLoading(true);
			setTimeout(() => setLoading(false), 200);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [options]);

	return (
		<>
			{!loading && (
				<ClayMultiSelect
					aria-labelledby={name}
					aria-required={required}
					disabled={readOnly}
					items={items}
					onItemsChange={(itemsChanged: MultiSelectItem[]) => {
						const uniqueItems = [
							...new Set(itemsChanged.map((item) => item.value)),
						];
						if (itemsChanged.length > uniqueItems.length) {
							uniqueItems.pop();
						}
						onChange({}, uniqueItems);
					}}
					onKeyDown={(event) => {
						if (event.key === 'Enter') {
							event.preventDefault();
						}
					}}
					sourceItems={options}
				>
					{(item) => (
						<ClayMultiSelect.Item
							key={item.value}
							textValue={item.label}
						>
							<div className="auto autofit-row-center fit-row">
								<ClayCheckbox
									aria-label={item.label}
									checked={values?.includes(item.value)!}
									data-itemValue={item.value}
									data-testid={`labelItem-${item.value}`}
									label={item.label}
									onChange={(event) => {
										const {
											target: {checked},
										} = event;
										let newValue: string[] = values as string[];
										if (checked) {
											options.forEach((option) => {
												if (
													option.value === item.value
												) {
													newValue.push(option.value);
												}
											});
										}
										else {
											options.forEach((option) => {
												if (
													option.value === item.value
												) {
													newValue = (values as string[]).filter(
														(value) =>
															value !== item.value
													);
												}
											});
										}

										onChange({}, newValue);
									}}
								/>
							</div>
						</ClayMultiSelect.Item>
					)}
				</ClayMultiSelect>
			)}
		</>
	);
};

export default MultipleSelection;
