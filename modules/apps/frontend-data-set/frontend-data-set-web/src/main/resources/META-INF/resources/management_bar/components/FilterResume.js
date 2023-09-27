/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import ViewsContext from '../../views/ViewsContext';
import {VIEWS_ACTION_TYPES} from '../../views/viewsReducer';
import Filter from './filters/Filter';

function FilterResume(props) {
	const [{filters}, viewsDispatch] = useContext(ViewsContext);

	const [open, setOpen] = useState(false);

	const button = (
		<ClayButton
			className={classNames(
				'filter-resume component-label tbar-label',
				open && 'active'
			)}
			disabled={props.disabled}
			displayType="secondary"
			size="sm"
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
		</ClayButton>
	);

	const dropDownButtonGroup = (
		<ClayButton.Group>
			<ClayDropDown
				active={open}
				className="d-inline-flex"
				onActiveChange={setOpen}
				trigger={button}
			>
				<li className="dropdown-subheader">{props.label}</li>

				<Filter {...props} />
			</ClayDropDown>

			<ClayButton
				className="filter-resume-close"
				disabled={props.disabled}
				displayType="secondary"
				monospaced
				onClick={() =>
					viewsDispatch({
						type: VIEWS_ACTION_TYPES.UPDATE_FILTERS,
						value: filters.map((filter) => ({
							...filter,
							...(filter.id === props.id
								? {
										active: false,
										odataFilterString: undefined,
										selectedData: undefined,
								  }
								: {}),
						})),
					})
				}
				size="sm"
				title={Liferay.Language.get('remove-filter')}
			>
				<ClayIcon symbol="times-small" />
			</ClayButton>
		</ClayButton.Group>
	);

	return props.disabled ? button : dropDownButtonGroup;
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
