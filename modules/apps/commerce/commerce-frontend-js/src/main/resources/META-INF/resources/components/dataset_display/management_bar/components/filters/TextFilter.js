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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function getOdataString(value, key) {
	return `${key} eq '${value}'`;
}

function TextFilter(props) {
	const [value, setValue] = useState(props.value);

	let actionType = 'edit';

	if (props.value && !value) {
		actionType = 'delete';
	}

	if (!props.value && value) {
		actionType = 'add';
	}

	return (
		<>
			<ClayDropDown.Caption>
				<div className="form-group">
					<div className="input-group">
						<div
							className={classNames('input-group-item', {
								'input-group-prepend': props.inputText,
							})}
						>
							<input
								aria-label={props.label}
								className="form-control"
								onChange={(e) => setValue(e.target.value)}
								type="text"
								value={value || ''}
							/>
						</div>
						{props.inputText && (
							<div className="input-group-append input-group-item input-group-item-shrink">
								<span className="input-group-text">
									{props.inputText}
								</span>
							</div>
						)}
					</div>
				</div>
			</ClayDropDown.Caption>
			<ClayDropDown.Divider />
			<ClayDropDown.Caption>
				<ClayButton
					disabled={(!props.value && value) || props.value !== value}
					onClick={() =>
						props.actions.updateFilterState(
							props.id,
							value,
							value,
							getOdataString(value, props.id)
						)
					}
					small
				>
					{actionType === 'add' && Liferay.Language.get('add-filter')}
					{actionType === 'edit' &&
						Liferay.Language.get('edit-filter')}
					{actionType === 'delete' &&
						Liferay.Language.get('delete-filter')}
				</ClayButton>
			</ClayDropDown.Caption>
		</>
	);
}

TextFilter.propTypes = {
	id: PropTypes.string.isRequired,
	inputText: PropTypes.string,
	invisible: PropTypes.bool,
	label: PropTypes.string.isRequired,
	type: PropTypes.oneOf(['text']).isRequired,
	value: PropTypes.string,
};

export default TextFilter;
