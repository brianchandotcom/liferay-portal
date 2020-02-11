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

import classNames from 'classnames';
import React from 'react';

const Breadcrumb = ({active, title, url}) => {
	return (
		<li
			className={classNames('breadcrumb-item', {
				active
			})}
		>
			{!active && (
				<a className="breadcrumb-link" href={url}>
					<span className="breadcrumb-text-truncate">{title}</span>
				</a>
			)}

			{active && (
				<span className="breadcrumb-text-truncate">{title}</span>
			)}
		</li>
	);
};

const Breadcrumbs = ({entries}) => {
	return (
		<ol className="breadcrumb">
			{entries.map((entry, index) => (
				<Breadcrumb
					active={index === entries.length - 1}
					key={entry.url}
					title={entry.title}
					url={entry.url}
				/>
			))}
		</ol>
	);
};

export default Breadcrumbs;
export {Breadcrumb, Breadcrumbs};
