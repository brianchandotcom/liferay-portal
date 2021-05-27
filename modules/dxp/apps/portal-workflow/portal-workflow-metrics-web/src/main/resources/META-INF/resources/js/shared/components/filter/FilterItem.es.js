/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
						onSelectedValueChange={(newValue) => {
							onClick();
							setSelectedValue(newValue);
						}}
						selectedValue={selectedValue}
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
