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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {ReactNode, cloneElement, useRef} from 'react';

import {FieldBase} from '../FieldBase';

import './index.scss';

export interface CustomItem<T = string> {
	checked?: boolean;
	description?: string;
	disabled?: boolean;
	label: string;
	name?: string;
	popover?: {body: string; header: string};
	type?: string;
	value?: T;
}

export interface SelectProps {
	className?: string;
	disabled?: boolean;
	error?: string;
	feedbackMessage?: string;
	id?: string;
	label?: string;
	placeholder?: string;
	required?: boolean;
	value?: string;
}

interface IProps extends SelectProps {
	children: ReactNode;
	dropdownActive: boolean;
	setDropdownActive: React.Dispatch<React.SetStateAction<boolean>>;
	trigger?: JSX.Element;
}

export function BaseSelect({
	children,
	className,
	disabled,
	dropdownActive,
	error,
	feedbackMessage,
	id,
	label,
	placeholder = Liferay.Language.get('choose-an-option'),
	required,
	setDropdownActive,
	trigger,
	value,
	...restProps
}: IProps) {
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
		>
			<ClayAutocomplete>
				{trigger ? (
					cloneElement(trigger, {
						disabled,
						placeholder,
						ref: inputRef,
						value,
						...restProps,
					})
				) : (
					<>
						<ClayIcon
							className={classNames('base-select__input-icon', {
								'base-select__input-icon--disabled': disabled,
							})}
							onClick={() =>
								!disabled &&
								setDropdownActive((active) => !active)
							}
							symbol="caret-double"
						/>

						<ClayAutocomplete.Input
							defaultValue={value}
							disabled={disabled}
							onClick={() =>
								setDropdownActive((active) => !active)
							}
							placeholder={placeholder}
							ref={inputRef}
							value={value}
							{...restProps}
						/>
					</>
				)}

				<ClayAutocomplete.DropDown
					active={dropdownActive}
					alignElementRef={trigger ? undefined : inputRef}
					alignmentByViewport
					closeOnClickOutside
					onSetActive={setDropdownActive}
					{...restProps}
				>
					<ClayDropDown.ItemList {...restProps}>
						{children}
					</ClayDropDown.ItemList>
				</ClayAutocomplete.DropDown>
			</ClayAutocomplete>
		</FieldBase>
	);
}
