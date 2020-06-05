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
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import React, {useState} from 'react';

import {prettifyFilterValue} from '../../../utilities/dates';
import {renderFilter} from '../../../utilities/filters';
import getAppContext from '../Context';

function Resume(props) {
	const {actions} = getAppContext();
	const [open, setOpen] = useState(false);

	const prettifiedValue = prettifyFilterValue(props);

	const label = (
		<ClayLabel
			className={classNames(
				'filter-resume component-label tbar-label mr-2',
				props.disabled && 'disabled',
				open && 'active'
			)}
			closeButtonProps={{
				className: 'filter-resume-close',
				disabled: props.disabled,
				onClick: () => actions.updateFilterValue(props.id, null),
			}}
			role="button"
		>
			<div className="filter-resume-content">
				<ClayIcon
					className="mr-2"
					symbol={open ? 'caret-top' : 'caret-bottom'}
				/>
				<div className="label-section">
					{props.label}: {prettifiedValue}
				</div>
			</div>
		</ClayLabel>
	);

	const dropDown = (
		<ClayDropDown
			active={open}
			className="d-inline-flex"
			onActiveChange={setOpen}
			trigger={label}
		>
			<ClayDropDown.ItemList>
				<div className="p-3">{renderFilter(props, 'edit')}</div>
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);

	return props.disabled ? label : dropDown;
}

export default Resume;
