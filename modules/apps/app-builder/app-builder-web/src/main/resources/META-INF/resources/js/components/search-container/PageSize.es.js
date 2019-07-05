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

import {ClaySelectWithOption} from '@clayui/select';
import React, {Fragment} from 'react';
import {sub} from '../../utils/lang.es';

export default function PageSize(props) {
	const {itemsCount, onPageSizeChange, page, pageSize, totalCount} = props;

	const options = [5, 10, 20, 30, 50, 75].map(entry => ({
		label: `${entry} ${Liferay.Language.get('entries')}`,
		selected: `${pageSize}` === `${entry}`,
		value: `${entry}`
	}));

	const firstEntry = pageSize * (page - 1) + 1;
	const lastEntry = firstEntry + itemsCount - 1;

	return (
		<Fragment>
			<ClaySelectWithOption
				id='pageSize'
				onChange={event => onPageSizeChange(event.target.value)}
				options={options}
			/>
			{sub(Liferay.Language.get('showing-x-to-x-of-x-entries'), [
				firstEntry,
				lastEntry,
				totalCount
			])}
		</Fragment>
	);
}
