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

import React, {useEffect, useRef} from 'react';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';

const Separator = ({name, style, ...otherProps}) => {
	const elRef = useRef(null);

	useEffect(() => {
		if (elRef.current) {

			// The style is a string, to avoid creating a normalizer to generate compatibility
			// with React, we can just add the raw value in the attribute, we don't need to
			// worry about XSS here because it won't go to the server just for printing
			// on the screen.

			elRef.current.setAttribute('style', style);
		}
	}, [style]);

	return (
		<FieldBase name={name} {...otherProps}>
			<hr className="separator" ref={elRef} />

			<input name={name} type="hidden" />
		</FieldBase>
	);
};

export default Separator;
