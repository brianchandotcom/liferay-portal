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

import {ClayToggle} from '@clayui/form';
import {localStorage, setSessionValue} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

import {getSettingValue, toggleClassName} from './util';

const KEY_EVENT = 'Enter';

const AccessibilitySetting = (props) => {
	const {
		className,
		defaultValue,
		key,
		label,
		sessionClicksValue,
	} = props.setting;

	const [value, setValue] = useState(() =>
		getSettingValue(defaultValue, sessionClicksValue, key)
	);
	const [disabled, setDisabled] = useState(false);

	const toggleRef = useRef();

	const afterValueSet = (value) => {
		toggleClassName(className, value);

		setValue(value);

		setDisabled(false);

		toggleRef.current.focus();
	};

	const handleToggle = (value) => {
		setDisabled(true);

		if (themeDisplay.isSignedIn()) {
			setSessionValue(key, value).then(() => {
				afterValueSet(value);
			});
		}
		else {
			localStorage.setItem(key, value, localStorage.TYPES.FUNCTIONAL);

			afterValueSet(value);
		}
	};

	return (
		<li className={props.className}>
			<ClayToggle
				disabled={props.disabled || disabled}
				label={label}
				onKeyDown={(event) => {
					if (!disabled && event.key === KEY_EVENT) {
						handleToggle(!value);
					}
				}}
				onToggle={handleToggle}
				ref={toggleRef}
				toggled={value}
			/>
		</li>
	);
};

AccessibilitySetting.propTypes = {
	className: PropTypes.string,
	disabled: PropTypes.bool,
	setting: PropTypes.shape({
		className: PropTypes.string,
		defaultValue: PropTypes.string,
		key: PropTypes.string,
		label: PropTypes.string,
		sessionClicksValue: PropTypes.string,
	}).isRequired,
};

export default AccessibilitySetting;
