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

export default ({creator, show}) => {
	return (
		<>
			{show && (
				<ClayPopover
					alignPosition="bottom"
					className="question-user-popover"
					disableScroll={true}
					header={
						<div className="autofit-padded autofit-row">
							<div className="autofit-col">
								<UserIcon
									fullName={creator.name}
									portraitURL={creator.image}
									size="sm"
									userId={String(creator.id)}
								/>
							</div>
							<div className="autofit-col">
								<strong>{creator.name}</strong>
							</div>
						</div>
					}
				></ClayPopover>
			)}
		</>
	);
};
