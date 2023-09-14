/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

interface SelectProps {
	cleanUp?: voidReturn;
	disabled?: boolean;
	dropDownSearchAriaLabel?: string;
	invalid?: boolean;
	onClick: (value: string) => void;
	options: SelectOption[];
	placeholder?: string;
	required?: boolean;
	searchable?: boolean;
	selectedOption?: SelectOption;
	triggerAriaLabel?: string;
}

export function Select({
	cleanUp,
	disabled,
	dropDownSearchAriaLabel,
	invalid,
	onClick,
	options,
	placeholder,
	required,
	searchable,
	selectedOption,
	triggerAriaLabel,
}: SelectProps) {
	const [displayPlaceholder, setDisplayPlaceholder] = useState<boolean>(
		!!placeholder
	);
	const [dropdownActive, setDropdownActive] = useState<boolean>(false);
	const [dropdownWidth, setDropdownWidth] = useState<string | null>();
	const [
		selectTriggerElement,
		setSelectTriggerElement,
	] = useState<HTMLElement | null>();
	const [triggerLabel, setTriggerLabel] = useState<string>(placeholder ?? '');

	const handleBlur = () => {
		if (
			cleanUp &&
			!options.some((option) => option.label === triggerLabel)
		) {
			cleanUp();
		}
	};

	const handleOnActiveChange = () => {
		if (!disabled) {
			setDropdownActive((previousState) => !previousState);
		}
	};

	const handleSelect = (option: SelectOption) => {
		if (selectTriggerElement) {
			selectTriggerElement.focus();
		}
		setDisplayPlaceholder(false);
		setTriggerLabel(option.label);
		onClick(option.value);
		setDropdownActive(false);
	};

	useEffect(() => {
		if (options && selectedOption) {
			const selectedObjectLabel = selectedOption.label;

			if (selectedObjectLabel) {
				setTriggerLabel(selectedObjectLabel);
			}
		}
	}, [options, selectedOption]);

	useEffect(() => {
		const trigger = document.getElementById('selectTrigger');

		if (trigger) {
			setSelectTriggerElement(trigger);
			setDropdownWidth(
				trigger.getBoundingClientRect().width.toString() + 'px'
			);
		}
	}, []);

	return (
		<DropDown
			active={dropdownActive}
			closeOnClickOutside={true}
			filterKey="label"
			menuElementAttrs={{
				id: 'selectDropdown',
				style: {
					maxHeight: '300px',
					maxWidth: 'none',
					width: dropdownWidth ?? '550px',
				},
			}}
			onActiveChange={handleOnActiveChange}
			trigger={
				<div id="selectTriggerContainer">
					<ClayButton
						aria-controls="selectDropdown"
						aria-expanded={dropdownActive}
						aria-invalid={invalid}
						aria-label={triggerAriaLabel}
						aria-required={required}
						className={classNames({
							'button-select-trigger': true,
							'display-placeholder': displayPlaceholder,
						})}
						disabled={disabled}
						id="selectTrigger"
						onBlur={handleBlur}
						role="input"
					>
						{triggerLabel}

						<span className="inline-item inline-item-before">
							<ClayIcon
								className="select-field-icon"
								symbol="caret-double"
							/>
						</span>
					</ClayButton>
				</div>
			}
		>
			{searchable && (
				<DropDown.Search
					aria-label={dropDownSearchAriaLabel}
					placeholder={Liferay.Language.get('search')}
				/>
			)}

			<DropDown.ItemList items={options}>
				{(item: SelectOption) => (
					<DropDown.Item
						key={item.value}
						onClick={() => {
							handleSelect(item);
						}}
					>
						{item.label}
					</DropDown.Item>
				)}
			</DropDown.ItemList>
		</DropDown>
	);
}
