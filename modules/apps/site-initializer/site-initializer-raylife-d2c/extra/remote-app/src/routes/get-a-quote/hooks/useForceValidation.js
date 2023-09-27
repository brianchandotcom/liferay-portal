/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {useFormContext} from 'react-hook-form';

const FAKE_INPUT_NAME = 'raylife-form-input';

/**
 * @description This is a simple way to force a validation for the entire form context
 * and unblock the continue button validation
 * @returns {Function} to force validate the entire schema
 */

const useForceValidation = () => {
	const {register, setValue} = useFormContext();

	useEffect(() => {
		register(FAKE_INPUT_NAME);
	}, [register]);

	return () => {
		setValue(FAKE_INPUT_NAME, '', {shouldValidate: true});
	};
};

export default useForceValidation;
