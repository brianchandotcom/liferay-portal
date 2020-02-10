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

import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

function _isValueValid(value) {
	const noSpacesValue = value.replace(/\s/g, '');

	return !!noSpacesValue;
}

function ValidatedInput(props) {
	const {
		autofocus = false,
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
	useEffect(() => {
		if (node.current && autofocus) {
			node.current.focus();
		}
	}, [autofocus]);

	const formGroupClasses = getCN('form-group w-100', {
		'has-error': invalid
	});

	return (
		<label className={formGroupClasses}>
			{label && (
				<>
					{label}
					<ClayIcon
						className="ml-1 reference-mark text-warning"
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
					<div className="form-feedback-item">
						<ClayIcon
							className="mr-1 text-danger"
							symbol="info-circle"
						/>
						{errorMessage}
					</div>
				</div>
			)}
		</label>
	);

	function _handleNameInputBlur(event) {
		if (!_isValueValid(value)) {
			_setInvalid(true);
		}
		onBlur(event);
	}
	function _handleNameInputFocus(event) {
		_setInvalid(false);
		onFocus(event);
	}

	function _setInvalid(newInvalid) {
		setInvalid(newInvalid);
		if (newInvalid !== invalid) {
			onValidationChange(newInvalid);
		}
	}
}

ValidatedInput.propTypes = {
	autofocus: PropTypes.bool,
	errorMessage: PropTypes.string,
	label: PropTypes.string,
	onBlur: PropTypes.func,
	onChange: PropTypes.func,
	onFocus: PropTypes.func,
	onValidationChange: PropTypes.func,
	value: PropTypes.string
};

export default ValidatedInput;
