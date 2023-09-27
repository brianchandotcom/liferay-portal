/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import React, {useContext} from 'react';

import EmptyState from '../empty-state/EmptyState';
import {SidebarContext} from '../sidebar/SidebarContext';
import Summary from '../summary/Summary';

const TotalEntriesLabel = ({totalEntries}) => {
	let label = Liferay.Language.get('there-are-no-entries');

	if (totalEntries === 1) {
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

export default function Card({
	children,
	field: {icon, label, title},
	index,
	summary,
	totalEntries,
}) {
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

							<div className="field-info" tabIndex={0}>
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
}
