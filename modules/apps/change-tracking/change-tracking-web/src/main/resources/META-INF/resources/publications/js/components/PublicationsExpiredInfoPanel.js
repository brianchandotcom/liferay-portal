/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLayout from '@clayui/layout';
import React, {useEffect, useState} from 'react';

import {WORKFLOW_STATUS_EXPIRED} from './WorkflowStatusLabel';

export default function PublicationsExpiredInfoPanel() {
	const [expired, setExpired] = useState(false);

	useEffect(() => {
		Liferay.Util.fetch(
			'/o/change-tracking-rest/v1.0/ct-collections?status=' +
				WORKFLOW_STATUS_EXPIRED,
			{
				headers: new Headers({
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				}),
				method: 'GET',
			}
		)
			.then((response) => response.json())
			.then((data) => {
				const items = data.items;

				if (items && !!items.length) {
					setExpired(true);
				}
			});
	}, []);

	return (
		expired && (
			<ClayLayout.ContainerFluid style={{marginTop: '1em'}}>
				<ClayAlert
					displayType="info"
					onClose={() => {
						setExpired(false);
					}}
				>
					{Liferay.Language.get(
						'there-is-one-or-more-out-of-date-publications.-you-have-an-option-to-reactivate-them-or-delete-them'
					)}
				</ClayAlert>
			</ClayLayout.ContainerFluid>
		)
	);
}
