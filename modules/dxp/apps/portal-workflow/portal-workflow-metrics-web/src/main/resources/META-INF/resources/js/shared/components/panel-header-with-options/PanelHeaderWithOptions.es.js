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
