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

import {ClayDualListBox, ClayInput} from '@clayui/form';
import classNames from 'classnames';
import {InputHTMLAttributes, useEffect, useState} from 'react';

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
	required = false,
	...otherProps
}) => (
	<>
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
			component={type === 'textarea' ? 'textarea' : 'input'}
			id={id}
			name={name}
			type={type}
			{...otherProps}
		/>

		{error && <InputWarning>{error}</InputWarning>}
	</>
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

export {DualListBox};

export default Input;
