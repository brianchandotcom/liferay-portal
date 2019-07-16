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

export default function EmptyState(props) {
	const {description, title} = props;

	return (
		<div className="taglib-empty-result-message">
			<div className="text-center">
				<div className="taglib-empty-state" />

				{title && (
					<h1 className="taglib-empty-result-message-title">
						{title}
					</h1>
				)}

				<p className="taglib-empty-result-message-description">
					{description}
				</p>
			</div>
		</div>
	);
}
