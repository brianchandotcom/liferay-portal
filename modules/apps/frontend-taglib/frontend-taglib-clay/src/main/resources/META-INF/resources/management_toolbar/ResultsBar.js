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

import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import {ClayResultsBar} from '@clayui/management-toolbar';
import {navigate} from 'frontend-js-web';
import React from 'react';

const ResultsBar = ({
	clearResultsURL,
	filterLabelItems,
	itemsTotal,
	searchValue,
}) => {
	return (
		<>
			<ClayResultsBar>
				<ClayResultsBar.Item expand={!(filterLabelItems?.length > 0)}>
					<span className="component-text text-truncate-inline">
						<span className="text-truncate">
							{Liferay.Util.sub(
								Liferay.Language.get('x-results-for-x'),
								itemsTotal,
								searchValue || ''
							)}
						</span>
					</span>
				</ClayResultsBar.Item>

				{filterLabelItems?.map((item, index) => (
					<ClayResultsBar.Item
						expand={index === filterLabelItems.length - 1}
						key={index}
					>
						<ClayLabel
							className="component-label tbar-label"
							closeButtonProps={{
								onClick: () => {
									navigate(item.data?.removeLabelURL);
								},
							}}
							dismissible
							displayType="unstyled"
							withClose
						>
							{item.label}
						</ClayLabel>
					</ClayResultsBar.Item>
				))}

				<ClayResultsBar.Item>
					<ClayLink
						className="component-link tbar-link"
						href={clearResultsURL}
					>
						{Liferay.Language.get('clear')}
					</ClayLink>
				</ClayResultsBar.Item>
			</ClayResultsBar>
		</>
	);
};

export default ResultsBar;
