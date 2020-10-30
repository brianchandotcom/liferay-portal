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

import {getStatusLabel} from './utils.es';

export default function ViewEntryInfoBar({status, stepName}) {
	const statusLabel = getStatusLabel(status);

	const infoItems = [
		{
			label: Liferay.Language.get('status'),
			value: statusLabel,
		},
		{
			label: Liferay.Language.get('step'),
			value: stepName,
		},
	];

	return (
		<div className="info-bar">
			{infoItems.map(
				({label, value}, index) =>
					value && (
						<div className="info-item" key={index}>
							<span className="font-weight-semi-bold mr-1 text-secondary">
								{`${label}: `}
							</span>

							{value}
						</div>
					)
			)}
		</div>
	);
}
