/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';
import {Link} from 'react-router';

import {useRouter} from '../../hooks/useRouter.es';
import {getPathname} from '../router/routerUtil.es';

const ListHeadItem = ({iconColor, iconName, name, title}) => {
	const {
		location: {search},
		path,
		routeParams,
	} = useRouter();

	const sort = routeParams?.sort || `${name}:asc`;

	const [field, order] = decodeURIComponent(sort).split(':');

	const sorted = field === name;

	const nextSort = `${name}:${sorted && order === 'desc' ? 'asc' : 'desc'}`;
	const sortIcon = order === 'asc' ? 'order-arrow-up' : 'order-arrow-down';

	const pathname = getPathname({...routeParams, sort: nextSort}, path);

	return (
		<Link
			className="inline-item text-truncate-inline"
			to={{pathname, search}}
		>
			{iconName && (
				<span className="inline-item inline-item-before mr-2">
					<span className="sticker sticker-sm">
						<span className="inline-item">
							<ClayIcon
								className={`text-${iconColor}`}
								symbol={iconName}
							/>
						</span>
					</span>
				</span>
			)}

			<span
				className="text-truncate title"
				data-title={title}
				title={title}
			>
				{title}
			</span>

			{sorted && (
				<span className="inline-item inline-item-after">
					<ClayIcon symbol={sortIcon} />
				</span>
			)}
		</Link>
	);
};

export default ListHeadItem;
