/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';
import {Link} from 'react-router-dom';

import {useRouter} from '../../hooks/useRouter.es';
import Portal from '../portal/Portal.es';
import {parse} from '../router/queryString.es';

const HeaderBackButton = ({basePath, container}) => {
	const {
		location: {pathname, search},
	} = useRouter();

	const {backPath} = parse(search);

	const isFirstPage = pathname === basePath || pathname === '/';

	if (isFirstPage || !backPath) {
		return null;
	}

	return (
		<Portal
			className="control-menu-nav-item"
			container={container}
			elementId="backButton"
		>
			<Link className="btn-monospaced btn-sm" to={backPath}>
				<ClayIcon symbol="angle-left" />
			</Link>
		</Portal>
	);
};

export default HeaderBackButton;
