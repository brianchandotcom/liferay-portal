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

import ClaySticker from '@clayui/sticker';

type AvatarProps = {
	className?: string;
	name?: string;
	url?: string;
};

type AvatarGroupProps = {
	assignedUsers: AvatarProps[];
	className?: string;
};

function getInitials(name: string): string {
	return name
		.split(' ')
		.map((value) => value.charAt(0))
		.join('')
		.toLocaleUpperCase();
}

const Avatar: React.FC<AvatarProps> = ({className, name = '', url}) => (
	<ClaySticker className={className} shape="circle" size="lg">
		{url ? <ClaySticker.Image alt={name} src={url} /> : getInitials(name)}
	</ClaySticker>
);

const AvatarGroup: React.FC<AvatarGroupProps> = ({assignedUsers}) => (
	<div className="align-items-center avatar-group d-flex justify-content-end p-0">
		{assignedUsers.map((user, index) => (
			<div className="avatar-group-item" key={index}>
				<Avatar className="avatar" name={user.name} url={user.url} />
			</div>
		))}
	</div>
);

export {Avatar, AvatarGroup};
