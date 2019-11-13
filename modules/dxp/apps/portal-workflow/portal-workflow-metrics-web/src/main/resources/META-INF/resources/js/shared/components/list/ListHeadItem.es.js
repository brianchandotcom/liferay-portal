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

import pathToRegexp from 'path-to-regexp';
import React from 'react';
import {Link, withRouter} from 'react-router-dom';

import Icon from '../Icon.es';

/**
 * @class
 * @memberof shared/components/list
 */
const ListHeadItem = ({
	iconColor,
	iconName,
	location: {search},
	match: {params, path},
	name,
	title
}) => {
	const sort = params && params.sort ? params.sort : `${name}:asc`;

	const [field, order] = decodeURIComponent(sort).split(':');

	const sorted = field === name;

	const nextSort = `${name}:${sorted && order === 'desc' ? 'asc' : 'desc'}`;

	const sortIcon = order === 'asc' ? 'order-arrow-up' : 'order-arrow-down';

	const pathname = pathToRegexp.compile(path)({
		...params,
		sort: nextSort
	});

	return (
		<Link
			className="inline-item text-truncate-inline"
			to={{pathname, search}}
		>
			{iconName && (
				<span className="inline-item inline-item-before mr-2">
					<span className="sticker sticker-sm">
						<span className="inline-item">
							<Icon
								elementClasses={`text-${iconColor}`}
								iconName={iconName}
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
					<Icon
						iconName={sortIcon}
						key={`${name}_icon_${sortIcon}`}
					/>
				</span>
			)}
		</Link>
	);
};

export default withRouter(ListHeadItem);
