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

const PathList = (props) => {
	const {curPath, filter, onClick, paths} = props;

	const selectedPathEl = useRef(null);

	useEffect(() => {
		if (selectedPathEl && selectedPathEl.current) {
			selectedPathEl.current.scrollIntoView();

			window.scroll(0, 0);
		}
	}, []);

	let pathKeys = Object.keys(paths);

	if (filter.trim().length > 0) {
		pathKeys = pathKeys.filter((pathkey) =>
			pathkey.toLowerCase().includes(filter.toLowerCase())
		);
	}

	return (
		<>
			{pathKeys.map((path) => (
				<button
					className={`btn btn-block ${
						path === curPath ? 'btn-primary' : 'btn-secondary'
					} mb-3 text-left`}
					key={path}
					onClick={() => onClick(path)}
					ref={path === curPath ? selectedPathEl : null}
					type="button"
				>
					{path}
				</button>
			))}
		</>
	);
};

export default PathList;
