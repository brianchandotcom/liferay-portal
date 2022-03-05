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
