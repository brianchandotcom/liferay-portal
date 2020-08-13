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

import ClayList from '@clayui/list';
import PropTypes from 'prop-types';
import React from 'react';

function Example({items}) {
	return (
		<ClayList className="bg-white mb-0 p-3">
			<pre className="mb-0 text-wrap">{JSON.stringify(items)}</pre>
		</ClayList>
	);
}

Example.propTypes = {
	dataRenderers: PropTypes.object,
	dataSetDisplayContext: PropTypes.any,
	items: PropTypes.array,
};

Example.defaultProps = {
	items: [],
};

export default Example;
