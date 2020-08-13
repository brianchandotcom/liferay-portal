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

	const classes = {
		control: getClassName(
			'custom-control',
			multiple ? 'custom-checkbox' : 'custom-radio'
		),
		dropdown: getClassName(
			'dropdown-item',

			active && 'active',
			description && 'with-description',
			hideControl && 'control-hidden'
		),
	};

	useEffect(() => {
		setChecked(active);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [active]);

	const onClickFilter = (event) => {
		onClick(event);
		setChecked(!checked);
	};

	return (
		<>
			<div
				className={classes.dropdown}
				data-testid="filterItem"
				onClick={onClickFilter}
			>
				<div className={classes.control}>
					<input
						checked={checked}
						className="custom-control-input"
						type={multiple ? 'checkbox' : 'radio'}
					/>

					<span className="custom-control-label">
						<span
							className="custom-control-label-text"
							data-testid="filterItemName"
						>
							{otherProps[labelPropertyName] || name}
						</span>

						{description && (
							<span className="custom-control-label-text dropdown-item-description">
								{description}
							</span>
						)}
					</span>
				</div>
			</div>

			{dividerAfter && <li className="dropdown-divider" />}
		</>
	);
};

export {FilterItem};
