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

import Link from '../components/Link.es';
import UserIcon from './UserIcon.es';
import UserPopover from './UserPopover.es';

export default withRouter(({creator = {}, statistics}) => (
	<Link
		className="align-items-center border-0 btn btn-block btn-secondary d-flex position-relative questions-user text-left text-md-right"
		to={`/questions/all/creator/${creator.id}`}
	>
		<UserIcon
			fullName={creator.name}
			portraitURL={creator.image}
			userId={String(creator.id)}
		/>

		<div className="align align-items-start c-ml-3 d-flex flex-column">
			<p className="c-mb-0 small">
				{Liferay.Language.get('answered-by')}
			</p>

			<p className="c-mb-0 font-weight-bold text-dark">{creator.name}</p>
		</div>

		<UserPopover creator={creator} statistics={statistics} />
	</Link>
));
