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

import React from 'react';

import {ConfigContext} from '../config/index';
import {StoreContext} from '../store/index';

const {useContext} = React;

export default function PageEditor() {
	const config = useContext(ConfigContext);
	const state = useContext(StoreContext);

	return (
		<>
			<h3>Config</h3>
			<pre>{JSON.stringify(config, null, 2)}</pre>
			<h3>Store state</h3>
			<pre>{JSON.stringify(state, null, 2)}</pre>
		</>
	);
}
