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

import ClayDropDown from '@clayui/drop-down';
import React, {useState} from 'react';

import {FieldBase} from '../FieldBase';

import './index.scss';
import {CustomSelect} from './CustomSelect';

interface IAutoCompleteProps extends React.HTMLAttributes<HTMLElement> {
	children: (item: any) => React.ReactNode;
	contentRight?: React.ReactNode;
	disabled?: boolean;
	emptyStateMessage: string;
	error?: string;
	feedbackMessage?: string;
	hasEmptyItem?: boolean;
	items: any[];
	label: string;
	onChangeQuery: (value: string) => void;
	onSelectItem: (item: any) => void;
	placeholder?: string;
	query: string;
	required?: boolean;
	value?: string;
}

export default function AutoComplete({
	children,
	className,
	contentRight,
	disabled,
	emptyStateMessage,
	error,
	feedbackMessage,
	hasEmptyItem,
	id,
	items: initialItems,
	label,
	onChangeQuery,
	onSelectItem,
	placeholder,
	query,
	required = false,
	value,
}: IAutoCompleteProps) {
	const [active, setActive] = useState<boolean>(false);

	const items = hasEmptyItem
		? [
				{
					id: '',
					label: Liferay.Language.get('choose-an-option'),
				},
				...initialItems,
		  ]
		: initialItems;

	return (
		<FieldBase
			className={className}
			disabled={disabled}
			errorMessage={error}
			helpMessage={feedbackMessage}
			id={id}
			label={label}
			required={required}
		>
			<ClayDropDown
				active={!disabled && active}
				onActiveChange={(value: boolean) =>
					!disabled ? setActive(value) : setActive(false)
				}
				trigger={
					<CustomSelect
						contentRight={<>{value && contentRight}</>}
						disabled={disabled}
						placeholder={
							placeholder ??
							Liferay.Language.get('choose-an-option')
						}
						value={value}
					/>
				}
			>
				<ClayDropDown.Search
					onChange={onChangeQuery}
					placeholder={Liferay.Language.get('search')}
					value={query}
				/>

				{(items.length === 1 && items[0].id === '') || !items.length ? (
					<ClayDropDown.ItemList>
						<ClayDropDown.Item>
							{emptyStateMessage}
						</ClayDropDown.Item>
					</ClayDropDown.ItemList>
				) : (
					<ClayDropDown.ItemList>
						{items.map((item, index) => {
							return (
								<ClayDropDown.Item
									key={index}
									onClick={() => {
										setActive(false);
										onSelectItem(item);
									}}
								>
									{children && children(item)}
								</ClayDropDown.Item>
							);
						})}
					</ClayDropDown.ItemList>
				)}
			</ClayDropDown>
		</FieldBase>
	);
}
