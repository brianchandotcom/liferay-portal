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
import {Link, withRouter} from 'react-router-dom';

import UserPopover from './UserPopover.es';

export default withRouter(
	({
		creator,
		match: {
			params: {sectionId},
		},
		statistics,
	}) => (
		<Link
			className="border-0 btn btn-block btn-secondary position-relative question-user text-left text-md-right"
			to={`/questions/${sectionId}/creator/${creator.id}`}
		>
			<p className="c-mb-0 small">
				{Liferay.Language.get('answered-by')}
			</p>

			<p className="c-mb-0 font-weight-bold text-dark">{creator.name}</p>

			<UserPopover creator={creator} statistics={statistics} />
		</Link>
	)
);
