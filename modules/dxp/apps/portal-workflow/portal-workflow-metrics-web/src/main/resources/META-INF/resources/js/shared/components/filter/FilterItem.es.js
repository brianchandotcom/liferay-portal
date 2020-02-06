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

import getClassName from 'classnames';
import React, {useEffect, useMemo, useState} from 'react';

const FilterItem = ({
	active = false,
	description,
	dividerAfter,
	hideControl,
	itemKey,
	multiple,
	name,
	onChange,
	onClick
}) => {
	const [checked, setChecked] = useState(active);

	const classes = useMemo(
		() => ({
			control: getClassName(
				'custom-control',
				multiple ? 'custom-checkbox' : 'custom-radio'
			),
			dropdown: getClassName(
				'dropdown-item',
				active && 'active',
				description && 'with-description',
				hideControl && 'control-hidden'
			)
		}),
		[active, description, hideControl, multiple]
	);

	const handleChange = event => {
		setChecked(event.target.checked);
		onChange(event);
	};

	useEffect(() => {
		setChecked(active);
	}, [active]);

	return (
		<>
			<li className={classes.dropdown} data-testid="filterItem">
				<label className={classes.control}>
					<input
						checked={checked}
						className="custom-control-input"
						data-key={itemKey}
						data-testid="filterItemInput"
						onChange={handleChange}
						onClick={onClick}
						type={multiple ? 'checkbox' : 'radio'}
					/>

					<span className="custom-control-label">
						<span
							className="custom-control-label-text"
							data-testid="filterItemName"
						>
							{name}
						</span>

						{description && (
							<span className="custom-control-label-text dropdown-item-description">
								{description}
							</span>
						)}
					</span>
				</label>
			</li>

			{dividerAfter && <li className="dropdown-divider" />}
		</>
	);
};

export {FilterItem};
