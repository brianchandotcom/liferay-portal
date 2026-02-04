/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

type Creator = {
	name: string;
};

type AuditFieldChanges = {
	name: string;
};

type AuditEvent = {
	auditFieldChanges?: AuditFieldChanges[];
	creator: Creator;
	dateCreated: string;
	eventType: string;
};

type Data = {
	auditEvents: AuditEvent[];
};

export default function TaskHistory({apiURL}: {apiURL: string}) {
	const [auditEvents, setAuditEvents] = useState<AuditEvent[]>([]);

	const fetchData = useCallback(async () => {
		fetch(apiURL, {
			method: 'GET',
		}).then(async (response: Response) => {
			const data = (await response.json()) as Data;

			setAuditEvents(data.auditEvents);
		});
	}, [apiURL]);

	useEffect(() => {
		fetchData();
	}, [fetchData]);

	return (
		<div className="task-history-container">
			<ul style={{listStyleType: 'none', padding: 0}}>
				{auditEvents.map(
					(
						{auditFieldChanges, creator, dateCreated, eventType},
						index
					) => (
						<li key={index}>
							<strong>
								{new Date(dateCreated).toLocaleString()}
							</strong>

							–<span> {creator.name} </span>

							<em>({eventType})</em>
-
							<span style={{marginLeft: '5px', fontWeight: 500}}>
								{auditFieldChanges
									?.map((field) => field.name)
									.join(', ')}
							</span>
						</li>
					)
				)}
			</ul>
		</div>
	);
}
