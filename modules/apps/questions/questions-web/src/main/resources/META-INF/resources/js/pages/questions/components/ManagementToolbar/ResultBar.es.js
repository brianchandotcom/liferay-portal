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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayManagementToolbar, {
	ClayResultsBar,
} from '@clayui/management-toolbar';
import React from 'react';

export default function ResultsBar({filter, resultsNumber}) {
	return (
		<ClayManagementToolbar className="w-100">
			<ClayResultsBar>
				<ClayResultsBar.Item>
					<span className="component-text text-truncate-inline">
						<span className="text-truncate">
							{`${resultsNumber} ${
								resultsNumber > 1
									? Liferay.Language.get('results')
									: Liferay.Language.get('result')
							}`}
						</span>
					</span>
				</ClayResultsBar.Item>

				<ClayResultsBar.Item expand>
					<ClayLabel
						className="component-label tbar-label"
						displayType="unstyled"
					>
						{filter}

						<ClayButton
							className="component-link tbar-link"
							displayType="unstyled"
						>
							<ClayIcon symbol="times-small" />
						</ClayButton>
					</ClayLabel>
				</ClayResultsBar.Item>

				<ClayResultsBar.Item>
					<ClayButton
						className="component-link tbar-link"
						displayType="unstyled"
					>
						{Liferay.Language.get('clear')}
					</ClayButton>
				</ClayResultsBar.Item>
			</ClayResultsBar>
		</ClayManagementToolbar>
	);
}
