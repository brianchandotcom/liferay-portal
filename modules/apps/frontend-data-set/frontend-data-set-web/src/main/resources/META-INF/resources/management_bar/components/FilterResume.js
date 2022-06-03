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
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import Filter from './filters/Filter';

function FilterResume(props) {
	const {setFilters} = useContext(FrontendDataSetContext);

	const [open, setOpen] = useState(false);

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
				onClick: () => {
					setFilters((filters) => {
						return filters.map((filter) => ({
							...filter,
							...(filter.id === props.id
								? {
										active: false,
										odataFilterString: undefined,
										selectedData: undefined,
								  }
								: {}),
						}));
					});
				},
			}}
			role="button"
		>
			<div className="filter-resume-content">
				<ClayIcon
					className="mr-2"
					symbol={open ? 'caret-top' : 'caret-bottom'}
				/>

				<div className="label-section">
					{props.label}: {props.selectedItemsLabel}
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
			<li className="dropdown-subheader">{props.label}</li>

			<Filter {...props} />
		</ClayDropDown>
	);

	return props.disabled ? label : dropDown;
}

FilterResume.propTypes = {
	disabled: PropTypes.bool,
	id: PropTypes.string,
	label: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
	selectedItemsLabel: PropTypes.oneOfType([
		PropTypes.string,
		PropTypes.number,
	]),
};

export default FilterResume;
