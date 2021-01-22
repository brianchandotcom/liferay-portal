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

import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';

import {isWebCrawler, stringToSlug} from '../utils/utils.es';

export default (props) => {
	const [isCrawler, setIsCrawler] = useState(false);
	const [pathname, setPathname] = useState('');

	useEffect(() => {
		const pathname = window.location.pathname;
		setPathname(pathname.endsWith('/') ? pathname.slice(0, -1) : pathname);
	}, []);

	const onClick = (event) => {
		event.preventDefault();
		window.location.href = pathname + '/-' + props.to;
	};

	useEffect(() => {
		setIsCrawler(isWebCrawler());
	}, []);

	return (
		<>
			{isCrawler ? (
				<a
					className={props.className}
					href={stringToSlug(pathname + '/-' + props.to)}
					onClick={onClick}
				>
					{props.children}
				</a>
			) : (
				<Link {...props} to={stringToSlug(props.to)} />
			)}
		</>
	);
};
