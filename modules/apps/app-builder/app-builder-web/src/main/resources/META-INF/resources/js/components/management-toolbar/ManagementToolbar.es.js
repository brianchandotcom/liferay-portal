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

import React, {Children, useCallback, useState} from 'react';

export default ({children}) => {
	const [{messageRenderer}, updateMessageRenderer] = useState({
		messageRenderer: () => {}
	});

	const renderMessage = useCallback(
		value => updateMessageRenderer({messageRenderer: value}),
		[]
	);

	return (
		<>
			<nav className="management-bar management-bar-light navbar navbar-expand-md">
				<div className="container-fluid container-fluid-max-xl">
					{Children.map(children, child =>
						React.cloneElement(child, {renderMessage})
					)}
				</div>
			</nav>

			{messageRenderer()}
		</>
	);
};
