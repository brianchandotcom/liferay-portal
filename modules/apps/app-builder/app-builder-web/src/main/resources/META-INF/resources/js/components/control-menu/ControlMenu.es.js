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
import React, {useEffect, useState} from 'react';
import {createPortal} from 'react-dom';
import {Link as InternalLink, withRouter} from 'react-router-dom';

const CONTROL_MENU_CONTENT = '.control-menu-nav-item-content';

const Portal = ({children, containerSelector}) => {
	const [container, setContainer] = useState();

	useEffect(() => {
		setContainer(document.querySelector(containerSelector));
	}, [containerSelector]);

	if (!container) {
		return <></>;
	}

	return createPortal(children, container);
};

const ExternalLink = ({children, to, ...props}) => {
	return (
		<a href={to} {...props}>
			{children}
		</a>
	);
};

export const ControlMenuBase = ({backURL, title, tooltip, url}) => {
	useEffect(() => {
		document.querySelector(CONTROL_MENU_CONTENT).innerHTML = '';

		if (!title) {
			return;
		}

		const titles = document.title.split(' - ');
		titles[0] = title;
		document.title = titles.join(' - ');
	}, [title]);

	if (backURL === '../') {
		const paths = url.split('/');
		paths.pop();
		backURL = paths.join('/');
	}

	const Link =
		backURL && backURL.startsWith('http') ? ExternalLink : InternalLink;

	return (
		<>
			{backURL && (
				<Portal containerSelector=".sites-control-group .control-menu-nav">
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
				</Portal>
			)}
			{title && (
				<Portal containerSelector={CONTROL_MENU_CONTENT}>
					<span className="control-menu-level-1-heading">
						{title}
					</span>
				</Portal>
			)}
			{tooltip && (
				<Portal containerSelector={CONTROL_MENU_CONTENT}>
					<span
						className="lfr-portal-tooltip taglib-icon-help"
						data-title={tooltip}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</Portal>
			)}
		</>
	);
};

export default withRouter(({match: {url}, ...props}) => {
	return <ControlMenuBase {...props} url={url} />;
});
