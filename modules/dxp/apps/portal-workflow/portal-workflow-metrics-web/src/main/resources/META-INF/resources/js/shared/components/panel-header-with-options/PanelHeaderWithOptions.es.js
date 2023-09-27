/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayPanel from '@clayui/panel';
import React from 'react';

const PanelHeaderWithOptions = ({
	children,
	className,
	description,
	title,
	tooltipPosition = 'right',
}) => {
	return (
		<ClayPanel.Header className={className}>
			<ClayLayout.ContentRow>
				<ClayLayout.ContentRow className="flex-row" expand="true">
					<span className="mr-2">{title}</span>

					<span
						className="workflow-tooltip"
						data-tooltip-align={tooltipPosition}
						title={description}
					>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</ClayLayout.ContentRow>

				{children}
			</ClayLayout.ContentRow>
		</ClayPanel.Header>
	);
};

export default PanelHeaderWithOptions;
