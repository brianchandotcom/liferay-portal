/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
			<Link
				className="control-menu-icon"
				data-testid="headerBackButton"
				to={backPath}
			>
				<span className="icon-monospaced">
					<ClayIcon symbol="angle-left" />
				</span>
			</Link>
		</Portal>
	);
};

export default HeaderBackButton;
