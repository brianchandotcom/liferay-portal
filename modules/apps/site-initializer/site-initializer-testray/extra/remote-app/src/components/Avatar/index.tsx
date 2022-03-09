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

type AvatarProps = {
	className?: string;
	displayName?: boolean;
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

const Avatar: React.FC<AvatarProps> = ({
	className,
	displayName = false,
	name = '',
	url,
}) => (
	<div className="align-items-center d-flex">
		<ClaySticker
			className={classNames(className, getRandomColor())}
			shape="circle"
			size="lg"
		>
			{url ? (
				<ClaySticker.Image alt={name} src={url} title={name} />
			) : (
				getInitials(name)
			)}
		</ClaySticker>

		{displayName && <span className="ml-3">{name}</span>}
	</div>
);

function getRandomColor() {
	const colors = [
		'bg-accent-1',
		'bg-accent-1-lighten',
		'bg-accent-2',
		'bg-accent-2-lighten',
		'bg-accent-3',
		'bg-accent-3-lighten',
		'bg-accent-4',
		'bg-accent-4-lighten',
		'bg-accent-5',
		'bg-accent-5-lighten',
		'bg-accent-6',
	];

	return colors[Math.floor(Math.random() * colors.length)];
}

const AvatarGroup: React.FC<AvatarGroupProps> = ({
	assignedUsers,
	groupSize,
}) => {
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
				{totalAssignedUsers <= groupSize
					? `${totalAssignedUsers}`
					: `${totalAssignedUsers - groupSize} +`}
			</div>
		</div>
	);
};

export {Avatar, AvatarGroup};
