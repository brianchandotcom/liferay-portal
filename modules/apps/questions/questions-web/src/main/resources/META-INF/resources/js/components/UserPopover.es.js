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

import ClayPopover from '@clayui/popover';
import React from 'react';

import {dateToBriefInternationalHuman} from '../utils/utils.es';
import UserIcon from './UserIcon.es';

export default ({creator, statistics}) => {
	return (
		<ClayPopover
			alignPosition="bottom"
			className="questions-user-popover"
			disableScroll={true}
			header={
				<div className="align-items-center d-flex">
					<UserIcon
						fullName={creator?.name}
						portraitURL={creator?.image}
						userId={String(creator?.id)}
					/>

					<div className="c-ml-2">
						<h4 className="font-weight-light h6 text-secondary">
							{statistics?.rank}
						</h4>

						<h3 className="h5">
							{creator?.name ||
								Liferay.Language.get(
									'anonymous-user-configuration-name'
								)}
						</h3>
					</div>
				</div>
			}
		>
			<div className="text-secondary">
				<p className="c-mb-0">Posts: {statistics?.postsNumber}</p>
				<p className="c-mb-0">
					Join Date:{' '}
					{statistics
						? dateToBriefInternationalHuman(statistics.joinDate)
						: ''}
				</p>
				<p className="c-mb-0">
					Last Post Date:{' '}
					{statistics
						? dateToBriefInternationalHuman(statistics.lastPostDate)
						: ''}
				</p>
			</div>
		</ClayPopover>
	);
};
