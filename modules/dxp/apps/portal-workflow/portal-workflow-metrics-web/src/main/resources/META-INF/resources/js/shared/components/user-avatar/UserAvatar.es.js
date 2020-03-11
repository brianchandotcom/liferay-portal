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
import getCN from 'classnames';
import React from 'react';

const UserAvatar = ({className, image}) => {
	const avatarClassName = getCN(
		className,
		'rounded-circle sticker sticker-light sticker-user-icon'
	);

	return (
		<span className={avatarClassName}>
			<span className="sticker-overlay" data-testid="assigneeImage">
				{image ? (
					<img className="img-fluid" src={image} />
				) : (
					<ClayIcon symbol="user" />
				)}
			</span>
		</span>
	);
};

export default UserAvatar;
