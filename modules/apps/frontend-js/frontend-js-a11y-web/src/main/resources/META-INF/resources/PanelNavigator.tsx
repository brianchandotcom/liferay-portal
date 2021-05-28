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
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayList from '@clayui/list';
import React from 'react';

import type {ImpactValue} from 'axe-core';

type PanelNavigatorProps = {
	helpUrl: string;
	impact: ImpactValue;
	onBack: (event: React.MouseEvent<HTMLButtonElement>) => void;
	title: string;
};

function PanelNavigator({helpUrl, impact, onBack, title}: PanelNavigatorProps) {
	return (
		<div className="sidebar-header">
			<ClayButton
				className="autofit-row sidebar-section"
				displayType="unstyled"
				onClick={onBack}
			>
				<ClayLayout.ContentCol className="align-self-center mr-2">
					<ClayIcon symbol="angle-left" />
				</ClayLayout.ContentCol>
				<ClayLayout.ContentCol expand>
					<ClayList.ItemTitle>{title}</ClayList.ItemTitle>
					<ClayList.ItemText className="text-secondary">
						{`${impact} - `}
						<ClayLink
							className="text-primary"
							displayType="unstyled"
							href={helpUrl}
						>
							WCAG
						</ClayLink>
					</ClayList.ItemText>
				</ClayLayout.ContentCol>
			</ClayButton>
		</div>
	);
}

export default PanelNavigator;
