/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {Provider} from '@clayui/provider';
import React from 'react';

interface DashboardCardProps {
	active: boolean;
	category: string;
	description: string;
	detailURL: string;
	title: string;
}

export default function DashboardCard({
	active,
	category,
	description,
	detailURL,
	title,
}: DashboardCardProps) {
	return (
		<a className="dashboard-card" href={detailURL}>
			<div className="dashboard-card__header">
				<div className="dashboard-card__icon">
					<Provider spritemap={Liferay.Icons.spritemap}>
						<ClayIcon symbol="chatbot" />
					</Provider>
				</div>
			</div>

			<div className="dashboard-card__body">
				<h4 className="dashboard-card__title">{title}</h4>

				<p className="dashboard-card__description">{description}</p>
			</div>

			<div className="dashboard-card__footer">
				<Provider spritemap={Liferay.Icons.spritemap}>
					<ClayLabel displayType="secondary">{category}</ClayLabel>

					<ClayLabel displayType={active ? 'success' : 'danger'}>
						{active
							? Liferay.Language.get('running')
							: Liferay.Language.get('stopped')}
					</ClayLabel>
				</Provider>
			</div>
		</a>
	);
}
