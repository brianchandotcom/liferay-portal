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

import React, {useState, useRef} from 'react';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import getCN from 'classnames';

function _isValueValid(value) {
	const noSpacesValue = value.replace(/\s/g, '');
	return !!noSpacesValue;
}

function ValidatedInput(props) {
	const {
		errorMessage,
		label,
		onBlur = () => {},
		onChange = () => {},
		onFocus = () => {},
		onValidationChange = () => {},
		value = ''
	} = props;

	const [invalid, setInvalid] = useState(false);
	const node = useRef();

	const formGroupClasses = getCN('form-group w-100', {
		'has-error': invalid
	});

	return (
		<label className={formGroupClasses}>
			{label && (
				<>
					{label}
					<ClayIcon
						className="reference-mark text-warning ml-1"
						symbol="asterisk"
					/>
				</>
			)}

			<input
				className="form-control mt-1"
				maxLength="75"
				onBlur={_handleNameInputBlur}
				onChange={onChange}
				onFocus={_handleNameInputFocus}
				ref={node}
				type="text"
				value={value}
			/>
			{invalid && errorMessage && (
				<div className="form-feedback-group">
					<div className="form-feedback-item">{errorMessage}</div>
				</div>
			)}
		</label>
	);

	function _handleNameInputBlur(event) {
		if (!_isValueValid(value)) _setInvalid(true);
		onBlur(event);
	}
	function _handleNameInputFocus(event) {
		_setInvalid(false);
		onFocus(event);
	}

	function _setInvalid(newInvalid) {
		setInvalid(newInvalid);
		if (newInvalid !== invalid) onValidationChange(newInvalid);
	}
}

ValidatedInput.propTypes = {
	errorMessage: PropTypes.string,
	label: PropTypes.string,
	onBlur: PropTypes.func,
	onChange: PropTypes.func,
	onFocus: PropTypes.func,
	onValidationChange: PropTypes.func,
	value: PropTypes.string
};

export default ValidatedInput;
