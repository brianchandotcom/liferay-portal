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

import axios from 'axios';
import React, {useEffect, useState} from 'react';
import Table from './Table.es';

export default function SearchContainer(props) {
	const [state, setState] = useState({
		items: []
	});

	useEffect(() => {
		const {endpoint, formatter, pageSize} = props;

		axios
			.get(endpoint, {
				params: {
					['p_auth']: Liferay.authToken,
					page: 1,
					keywords: '',
					pageSize
				}
			})
			.then(({data: {items}}) => setState({items: formatter(items)}));
	}, [props]);

	return <Table columns={props.columns} rows={state.items} />;
}
