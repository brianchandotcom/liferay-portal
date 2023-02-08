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

import ClayAutocomplete from '@clayui/autocomplete';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useRef, useState} from 'react';

import './index.scss';
import {FieldBase} from '../FieldBase';

type Item = {
	label: string;
	options: LabelValueObject[];
	type: string;
};
interface SelectWithOptionProps
	extends React.SelectHTMLAttributes<HTMLSelectElement> {
	ariaLabel?: string;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	items: Item[];
	label?: string;
	onSelectChange: (label: string, value: string) => void;
	required?: boolean;
	tooltip?: string;
}

export function SelectWithOption({
	ariaLabel,
	className,
	disabled,
	error,
	feedbackMessage,
	id,
	items,
	label,
	onSelectChange,
	placeholder = Liferay.Language.get('choose-an-option'),
	required,
	tooltip,
	value,
}: SelectWithOptionProps) {
	const [dropdownActive, setDropdownActive] = useState(false);
	const inputRef = useRef(null);

	return (
		<FieldBase
			className={className}
			disabled={disabled}
			errorMessage={error}
			helpMessage={feedbackMessage}
			id={id}
			label={label}
			required={required}
			tooltip={tooltip}
		>
			<>
				<ClayIcon
					className={classNames('base-select__input-icon', {
						'base-select__input-icon--disabled': disabled,
					})}
					onClick={() =>
						!disabled && setDropdownActive((active) => !active)
					}
					symbol="caret-double"
				/>

				<ClayAutocomplete.Input
					aria-label={ariaLabel}
					disabled={disabled}
					onClick={() => setDropdownActive((active) => !active)}
					placeholder={placeholder}
					ref={inputRef}
					value={value}
				/>
			</>

			<DropDown.Menu
				active={dropdownActive}
				alignElementRef={inputRef}
				alignmentByViewport
				onActiveChange={() => setDropdownActive((active) => !active)}
				triggerRef={inputRef}
			>
				<DropDown.ItemList items={items}>
					{(item: Item) => (
						<DropDown.Group
							header={item.label}
							items={item.options}
							key={item.label}
						>
							{(item) => (
								<DropDown.Item
									key={item.value}
									onClick={() => {
										onSelectChange(item.label, item.value);
										setDropdownActive(false);
									}}
								>
									{item.label}
								</DropDown.Item>
							)}
						</DropDown.Group>
					)}
				</DropDown.ItemList>
			</DropDown.Menu>
		</FieldBase>
	);
}
