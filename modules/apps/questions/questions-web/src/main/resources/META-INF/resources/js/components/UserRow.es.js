/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {withRouter} from 'react-router-dom';

import Link from '../components/Link.es';
import UserIcon from './UserIcon.es';
import UserPopover from './UserPopover.es';

export default withRouter(
	({creator = {}, statistics, hasCompanyMx, companyName}) => {
		return (
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

					<p className="c-mb-0 font-weight-bold text-dark">
						{creator.name}
					</p>

					{hasCompanyMx && companyName}
				</div>

				<UserPopover creator={creator} statistics={statistics} />
			</Link>
		);
	}
);
