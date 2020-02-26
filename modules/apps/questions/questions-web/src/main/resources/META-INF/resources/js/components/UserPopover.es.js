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

import UserIcon from './UserIcon.es';

export default ({creator}) => {
	return (
		<ClayPopover
			alignPosition="bottom"
			className="question-user-popover"
			disableScroll={true}
			header={
				<div className="align-items-center d-flex">
					<UserIcon
						fullName={creator.name}
						portraitURL={creator.image}
						userId={String(creator.id)}
					/>

					<div className="c-ml-2">
						<h4 className="font-weight-light h6 text-secondary">
							Liferay Legend
						</h4>

						<h3 className="h5">{creator.name}</h3>
					</div>
				</div>
			}
		>
			<div className="text-secondary">
				<p className="c-mb-0">Posts: 14.551</p>
				<p className="c-mb-0">Join Date: 2006-09-02</p>
				<p className="c-mb-0">Last Post Date: 2019-10-25</p>
			</div>

			<p className="c-mb-1 c-mt-2 font-weight-bold text-center text-primary">
				View Activity
			</p>
		</ClayPopover>
	);
};
