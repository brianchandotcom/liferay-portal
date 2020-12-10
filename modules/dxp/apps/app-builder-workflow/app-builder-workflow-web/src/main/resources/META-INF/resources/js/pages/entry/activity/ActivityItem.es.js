/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {formatDate} from 'app-builder-web/js/utils/time.es';
import React from 'react';

export default function ActivityItem({commentLog, dateCreated, description}) {
	const formattedDate = `${formatDate(dateCreated, 'l')} — ${formatDate(
		dateCreated,
		'LT'
	)}`;

	return (
		<div className="activity">
			<p className="description">{description}</p>

			{commentLog && <p>{commentLog}</p>}

			<div className="font-weight-semi-bold text-secondary">
				{formattedDate}
			</div>
		</div>
	);
}
