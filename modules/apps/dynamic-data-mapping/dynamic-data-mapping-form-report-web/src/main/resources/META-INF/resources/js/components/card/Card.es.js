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

import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import React, {useContext} from 'react';

import EmptyState from '../empty-state/EmptyState.es';
import {SidebarContext} from '../sidebar/SidebarContext.es';
import Summary from '../summary/Summary.es';

const TotalEntriesLabel = ({totalEntries}) => {
	let label = Liferay.Language.get('there-are-no-entries');

	if (totalEntries == 1) {
		label = `1 ${Liferay.Language.get('entry').toLowerCase()}`;
	}

	if (totalEntries > 1) {
		label = `${totalEntries} ${Liferay.Language.get(
			'entries'
		).toLowerCase()}`;
	}

	return (
		<ClayCard.Description displayType="text" truncate={false}>
			{label}
		</ClayCard.Description>
	);
};

export default ({
	children,
	field: {icon, label, title},
	index,
	summary,
	totalEntries,
}) => {
	const {portletNamespace} = useContext(SidebarContext);

	return (
		<div className="card-item" id={`${portletNamespace}card_${index}`}>
			<ClayLayout.Sheet>
				<ClayLayout.Col>
					<ClayCard displayType="image">
						<ClayCard.AspectRatio className="card-header card-item-first">
							<div
								className="aspect-ratio-item aspect-ratio-item-center-left aspect-ratio-item-fluid card-symbol card-type-asset-icon"
								data-tooltip-align="bottom"
								data-tooltip-delay={300}
								title={title}
							>
								<ClayIcon symbol={icon} />
							</div>

							<div className="field-info">
								<ClayCard.Description displayType="title">
									{label}
								</ClayCard.Description>

								<TotalEntriesLabel
									totalEntries={totalEntries}
								/>
							</div>

							{!!Object.entries(summary).length && (
								<Summary summary={summary} />
							)}
						</ClayCard.AspectRatio>

						<ClayCard.Body>
							{totalEntries > 0 ? (
								children
							) : (
								<EmptyState
									description={Liferay.Language.get(
										'entries-submitted-with-this-field-filled-will-show-up-here'
									)}
								/>
							)}
						</ClayCard.Body>
					</ClayCard>
				</ClayLayout.Col>
			</ClayLayout.Sheet>
		</div>
	);
};
