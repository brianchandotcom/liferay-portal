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

import ClayIcon from '@clayui/icon';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import React, {useEffect, useState} from 'react';
import {Link as InternalLink} from 'react-router-dom';

const ExternalLink = ({children, to, ...props}) => {
	return (
		<a href={to} {...props}>
			{children}
		</a>
	);
};

export const BackButtonPortal = ({backURL = '/'}) => {
	const [container, setContainer] = useState(null);
	const Link =
		backURL && backURL.startsWith('http') ? ExternalLink : InternalLink;

	useEffect(() => {
		if (!container) {
			setContainer(
				document.querySelector('.sites-control-group .control-menu-nav')
			);
		}
	}, [container]);

	if (!container) {
		return <></>;
	}

	return (
		<ReactPortal container={container}>
			<li className="control-menu-nav-item">
				<Link
					className="control-menu-icon lfr-icon-item"
					tabIndex={1}
					to={backURL}
				>
					<span className="icon-monospaced">
						<ClayIcon symbol="angle-left" />
					</span>
				</Link>
			</li>
		</ReactPortal>
	);
};
