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

import classnames from 'classnames';
import React, {useState} from 'react';

import {timeDifference} from '../utils/utils.es';
import UserIcon from './UserIcon.es';
import UserPopover from './UserPopover.es';

export default ({question: {creator, dateCreated}}) => {
	const [showPopover, setShowPopover] = useState(false);

	return (
		<div
			className={classnames(
				'autofit-padded',
				'autofit-row',
				'autofit-row-center',
				'question-author-badge'
			)}
			onMouseLeave={() => setShowPopover(false)}
			onMouseOver={() => setShowPopover(true)}
		>
			<div className="autofit-col">
				<UserIcon
					fullName={creator.name}
					portraitURL={creator.image}
					size="sm"
					userId={String(creator.id)}
				/>
			</div>
			<div className="autofit-col">
				<p className="mb-0">
					<small>{timeDifference(dateCreated)}</small>
				</p>
				<p className="mb-0">
					<strong>{creator.name}</strong>
				</p>
			</div>

			<UserPopover creator={creator} show={showPopover} />
		</div>
	);
};
