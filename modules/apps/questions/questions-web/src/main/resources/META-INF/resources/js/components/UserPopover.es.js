/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPopover from '@clayui/popover';
import React from 'react';

import {dateToBriefInternationalHuman} from '../utils/utils.es';
import UserIcon from './UserIcon.es';

export default function UserPopover({creator, statistics}) {
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
				<p className="c-mb-0">
					{Liferay.Language.get('posts')}: {statistics?.postsNumber}
				</p>

				<p className="c-mb-0">
					{`${Liferay.Language.get('join-date')}: ${
						statistics
							? dateToBriefInternationalHuman(statistics.joinDate)
							: ''
					}`}
				</p>

				<p className="c-mb-0">
					{`${Liferay.Language.get('last-post-date')}: ${
						statistics
							? dateToBriefInternationalHuman(
									statistics.lastPostDate
							  )
							: ''
					}`}
				</p>
			</div>
		</ClayPopover>
	);
}
