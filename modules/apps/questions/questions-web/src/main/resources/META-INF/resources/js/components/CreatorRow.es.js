/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {withRouter} from 'react-router-dom';

import {timeDifference} from '../utils/utils.es';
import Link from './Link.es';
import UserIcon from './UserIcon.es';
import UserPopover from './UserPopover.es';

export default withRouter(
	({
		isContentReviewer,
		match: {
			params: {sectionTitle},
		},
		question: {creator = {}, creatorStatistics, dateCreated},
	}) => (
		<Link
			className="align-items-center btn btn-secondary c-ml-md-3 c-mt-3 c-mt-md-0 c-p-3 d-inline-flex justify-content-center position-relative questions-user"
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

				{Liferay.FeatureFlags['LPS-185892'] &&
					isContentReviewer &&
					creator.userGroupBriefs
						?.map((userGroupBrief) => userGroupBrief.name)
						.join(', ')}
			</div>

			<UserPopover creator={creator} statistics={creatorStatistics} />
		</Link>
	)
);
