/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import {ClayCheckbox, ClayRadio, ClayRadioGroup} from '@clayui/form';
import React, {useEffect, useState} from 'react';

const FilterItem = ({
	active = false,
	description,
	dividerAfter,
	hideControl,
	labelPropertyName = 'name',
	multiple,
	name,
	onClick,
	...otherProps
}) => {
	const [checked, setChecked] = useState(active);
	const [selectedValue, setSelectedValue] = useState();
	const itemLabel = otherProps[labelPropertyName] || name;

	useEffect(() => {
		if (!hideControl && !multiple && active) {
			setSelectedValue(itemLabel);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		if (!multiple && !hideControl && !active) {
			setSelectedValue();
		}
		else if (multiple) {
			setChecked(active);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [active, selectedValue]);

	const onClickDescription = () => {
		onClick();

		if (multiple) {
			setChecked(!checked);
		}
		else {
			if (!hideControl && !active) {
				setSelectedValue(itemLabel);
			}
		}
	};

	return (
		<>
			<ClayDropDown.Item
				active={active}
				className={hideControl && 'control-hidden'}
			>
				{multiple ? (
					<ClayCheckbox
						checked={checked}
						label={itemLabel}
						onChange={() => {
							onClick();
							setChecked(!checked);
						}}
					/>
				) : hideControl ? (
					<div onClick={onClick}>{itemLabel}</div>
				) : (
					<ClayRadioGroup
						onChange={(newValue) => {
							onClick();
							setSelectedValue(newValue);
						}}
						value={selectedValue}
					>
						<ClayRadio label={itemLabel} value={itemLabel} />
					</ClayRadioGroup>
				)}

				{description && (
					<div
						className="filter-dropdown-item-description"
						onClick={onClickDescription}
					>
						{description}
					</div>
				)}
			</ClayDropDown.Item>

			{dividerAfter && <div className="dropdown-divider" />}
		</>
	);
};

export {FilterItem};
