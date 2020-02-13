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

import MillerColumnsColumn from './MillerColumnsColumn.es';

const MillerColumns = ({columns}) => {
	const rowRef = useRef();

	useEffect(() => {
		if (rowRef.current) {
			rowRef.current.scrollLeft = rowRef.current.scrollWidth;
		}
	}, []);

	return (
		<div className="bg-white miller-columns-row" ref={rowRef}>
			{columns.map((items, index) => (
				<MillerColumnsColumn items={items} key={index} />
			))}
		</div>
	);
};

export default MillerColumns;
