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

import React from 'react';
import {withRouter} from 'react-router-dom';

import {timeDifference} from '../utils/utils.es';
import Link from './Link.es';
import UserIcon from './UserIcon.es';
import UserPopover from './UserPopover.es';

export default withRouter(
	({
		match: {
			params: {sectionTitle},
		},
		question: {creator = {}, creatorStatistics, dateCreated},
	}) => (
		<Link
			className="align-items-center border-light btn btn-secondary c-ml-md-3 c-mt-3 c-mt-md-0 c-p-3 d-inline-flex justify-content-center position-relative questions-user"
			to={`/questions/${sectionTitle}${
				creator ? `/creator/${creator.id}` : ''
			}`}
		>
			<UserIcon
				fullName={creator?.name}
				portraitURL={creator?.image}
				userId={String(creator?.id)}
			/>

			<div className="c-ml-3 text-left">
				<p className="c-mb-0 small">{timeDifference(dateCreated)}</p>

				<p className="c-mb-0 font-weight-bold text-dark">
					{creator?.name ||
						Liferay.Language.get(
							'anonymous-user-configuration-name'
						)}
				</p>
			</div>

			<UserPopover creator={creator} statistics={creatorStatistics} />
		</Link>
	)
);
