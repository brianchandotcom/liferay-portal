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

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';

const Separator = ({style}) => {
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

	return <div className="separator" ref={elRef} />;
};

const SeparatorProxy = connectStore(({style, ...otherProps}) => (
	<FieldBaseProxy {...otherProps}>
		<Separator style={style} />
	</FieldBaseProxy>
));

const ReactSeparatorAdapter = getConnectedReactComponentAdapter(
	SeparatorProxy,
	'separator'
);

export {ReactSeparatorAdapter};
export default ReactSeparatorAdapter;
