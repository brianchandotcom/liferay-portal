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

import {TypedDocumentNode, useLazyQuery} from '@apollo/client';
import ClayAutocomplete from '@clayui/autocomplete';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayDualListBox, ClayInput} from '@clayui/form';
import classNames from 'classnames';
import {InputHTMLAttributes, useEffect, useMemo, useState} from 'react';

import useDebounce from '../../hooks/useDebounce';
import InputWarning from './InputWarning';

type InputProps = {
	error?: string;
	id?: string;
	label?: string;
	name: string;
	required?: boolean;
	type?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const Input: React.FC<InputProps> = ({
	error,
	label,
	name,
	id = name,
	type,
	value,
	required = false,
	...otherProps
}) => (
	<ClayForm.Group>
		{label && (
			<label
				className={classNames(
					'font-weight-normal mt-3 mx-0 text-paragraph',
					{required}
				)}
				htmlFor={id}
			>
				{label}
			</label>
		)}

		<ClayInput
			className="rounded-xs"
			component={type === 'textarea' ? 'textarea' : 'input'}
			id={id}
			name={name}
			type={type}
			value={value || ''}
			{...otherProps}
		/>

		{error && <InputWarning>{error}</InputWarning>}
	</ClayForm.Group>
);

export type BoxItem = {
	label: string;
	value: string;
};

export type Boxes = Array<Array<BoxItem>>;

type DualListBoxProps = {
	boxes?: Boxes;
	leftLabel: string;
	rightLabel: string;
	setValue?: (value: any) => void;
};

const DualListBox: React.FC<DualListBoxProps> = ({
	boxes = [[], []],
	leftLabel,
	rightLabel,
	setValue = () => {},
}) => {
	const [items, setItems] = useState<Boxes>([[], []]);
	const [leftSelected, setLeftSelected] = useState<string[]>([]);
	const [rightSelected, setRightSelected] = useState<string[]>([]);

	const initialBoxes = JSON.stringify(boxes);

	useEffect(() => {
		setItems(JSON.parse(initialBoxes));
	}, [initialBoxes]);

	return (
		<ClayDualListBox
			items={items}
			left={{
				label: leftLabel,
				onSelectChange: setLeftSelected,
				selected: leftSelected,
			}}
			onItemsChange={(items) => {
				setItems(items);
				setValue(items);
			}}
			right={{
				label: rightLabel,
				onSelectChange: (value) => {
					setRightSelected(value);
				},
				selected: rightSelected,
			}}
			size={8}
		/>
	);
};

type AutoCompleteProps = {
	gqlQuery: TypedDocumentNode;
	label?: string;
	objectName: string;
	onSearch: (keyword: string) => any;
	transformData?: (item: any) => any;
};

const AutoComplete: React.FC<AutoCompleteProps> = ({
	gqlQuery,
	label,
	objectName,
	onSearch,
	transformData,
}) => {
	const [showValue, setShowValue] = useState('');
	const [value, setValue] = useState('');
	const [active, setActive] = useState(false);
	const [fetchQuery, {called, data, error, loading}] = useLazyQuery(gqlQuery);

	const debouncedValue = useDebounce(value, 1000);

	const items = useMemo(() => {
		return transformData
			? transformData(data)
			: data?.c[objectName].items || [];
	}, [data, objectName, transformData]);

	useEffect(() => {
		if (debouncedValue) {
			fetchQuery({
				variables: {filter: onSearch(debouncedValue)},
			}).then(() => {
				setActive(true);
			});
		}
	}, [debouncedValue, onSearch, fetchQuery]);

	const onClickItem = (name: string) => {
		setShowValue(name);
		setActive(false);
	};

	return (
		<ClayAutocomplete className="mb-4">
			<label>{label}</label>

			<ClayAutocomplete.Input
				onChange={(event) => {
					setValue(event.target.value);
					setShowValue(event.target.value);
				}}
				placeholder="Type here"
				value={showValue || value}
			/>

			<ClayAutocomplete.DropDown active={active}>
				<ClayDropDown.ItemList>
					{called && (error || (items && !items.length)) && (
						<ClayDropDown.Item className="disabled">
							No Results Found
						</ClayDropDown.Item>
					)}

					{!error &&
						items.map((item: any) => (
							<ClayAutocomplete.Item
								key={item.id}
								match={value}
								onClick={() => onClickItem(item.name)}
								value={item.name}
							/>
						))}
				</ClayDropDown.ItemList>
			</ClayAutocomplete.DropDown>

			{loading && <ClayAutocomplete.LoadingIndicator />}
		</ClayAutocomplete>
	);
};

export {AutoComplete, DualListBox};

export default Input;
