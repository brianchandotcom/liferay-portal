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
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayList from '@clayui/list';
import React from 'react';

import type {ImpactValue} from 'axe-core';

type PanelNavigatorProps = {
	helpUrl: string;
	impact?: ImpactValue;
	onBack: (event: React.MouseEvent<HTMLButtonElement>) => void;
	title: string;
	tags: Array<string>;
};

function PanelNavigator({
	helpUrl,
	impact,
	onBack,
	tags,
	title,
}: PanelNavigatorProps) {
	return (
		<div className="sidebar-header">
			<ClayButton
				className="autofit-row sidebar-section"
				displayType="unstyled"
				onClick={onBack}
			>
				<div className="mr-2">
					<ClayIcon symbol="angle-left" />
				</div>
				<ClayLayout.ContentCol expand>
					<ClayList.ItemTitle>{title}</ClayList.ItemTitle>
					{impact && (
						<ClayList.ItemText className="text-secondary">
							{`${impact} - `}
							<ClayLink
								className="text-primary"
								displayType="unstyled"
								href={helpUrl}
								onClick={(event) => event.stopPropagation()}
							>
								{Liferay.Language.get('more-info')}
							</ClayLink>
						</ClayList.ItemText>
					)}
					<div className="list-group-detail">
						{tags.map((tag) => (
							<ClayLabel displayType="info" key={tag}>
								{tag}
							</ClayLabel>
						))}
					</div>
				</ClayLayout.ContentCol>
			</ClayButton>
		</div>
	);
}

export default PanelNavigator;
