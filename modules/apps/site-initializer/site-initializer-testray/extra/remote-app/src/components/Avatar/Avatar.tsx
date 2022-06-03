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
import classNames from 'classnames';
import React from 'react';

type AvatarProps = {
	className?: string;
	displayName?: boolean;
	expanded?: boolean;
	name?: string;
	url?: string;
};

type AvatarGroupProps = {
	assignedUsers: AvatarProps[];
	className?: string;
	groupSize: number;
};

function getInitials(name: string): string {
	return name
		.split(' ')
		.map((value) => value.charAt(0))
		.join('')
		.toLocaleUpperCase();
}

function getRandomColor(name: string) {
	const backgroundAccentColorsRegex = {
		'bg-accent-1': /^[A-Fa-f]/g,
		'bg-accent-2': /^[G-Lg-l]/g,
		'bg-accent-3': /^[M-Tm-t]/g,
		'bg-accent-4': /^[U-Zu-z]/g,
	};

	for (const bgAccent in backgroundAccentColorsRegex) {
		const value = (backgroundAccentColorsRegex as any)[bgAccent];

		if (new RegExp(value).test(name)) {
			return bgAccent;
		}
	}
}

const Avatar: React.FC<AvatarProps> & {Group: React.FC<AvatarGroupProps>} = ({
	className,
	displayName = false,
	expanded,
	name = '',
	url,
}) => (
	<div className="align-items-center d-flex">
		<ClaySticker
			className={classNames(
				className,
				'text-brand-secondary-lighten-6',
				getRandomColor(getInitials(name))
			)}
			shape="circle"
			size="lg"
		>
			{url ? (
				<ClaySticker.Image alt={name} src={url} title={name} />
			) : (
				getInitials(name)
			)}
		</ClaySticker>

		{displayName && (
			<span
				className={classNames('ml-3 testray-avatar-dropdown-text', {
					'testray-avatar-dropdown-text-expanded': expanded,
				})}
			>
				{name}
			</span>
		)}
	</div>
);

Avatar.Group = ({assignedUsers, groupSize}) => {
	const totalAssignedUsers = assignedUsers.length;

	return (
		<div className="d-flex">
			<div className="align-items-center avatar-group d-flex justify-content-end p-0">
				{assignedUsers
					.filter((_, index) => index < groupSize)
					.map((user, index) => (
						<div className="avatar-group-item" key={index}>
							<Avatar
								className="avatar avatar-skeleton-loader shadow-lg"
								name={user.name}
								url={user.url}
							/>
						</div>
					))}
			</div>

			<div
				className="align-items-center avatar-plus d-flex justify-content-center p-0 pl-4 text-nowrap"
				title={assignedUsers.map(({name}) => name).toString()}
			>
				+
				{totalAssignedUsers <= groupSize
					? `${totalAssignedUsers}`
					: `${totalAssignedUsers - groupSize}`}
			</div>
		</div>
	);
};

export default Avatar;
