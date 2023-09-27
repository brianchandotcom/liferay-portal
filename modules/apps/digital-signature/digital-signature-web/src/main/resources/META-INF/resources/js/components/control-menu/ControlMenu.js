/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

export function BackButtonPortal({backURL = '/'}) {
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
					className="btn-monospaced btn-sm lfr-icon-item"
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
}
