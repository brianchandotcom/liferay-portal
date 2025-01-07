/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import ClayLabel from '@clayui/label';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayPanel from '@clayui/panel';
import React from 'react';

export default function ChangeTrackingOverview({
	itemsOverview,
	publicationSizeClassification,
}) {
	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={
				<ClayPanel.Title>
					<ClayLayout.ContentRow className="align-items-center c-gap-2">
						<ClayLayout.ContentCol className="panel-title">
							{Liferay.Language.get('publication-overview')}
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol>
							{publicationSizeClassification ? (
								<ClayLabel displayType="info">
									{Liferay.Language.get('publication-size') +
										': ' +
										publicationSizeClassification}
								</ClayLabel>
							) : null}
						</ClayLayout.ContentCol>
					</ClayLayout.ContentRow>
				</ClayPanel.Title>
			}
			displayType="secondary"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				<div className="small text-secondary">
					<hr style={{marginTop: '-10px'}} />

					{itemsOverview.length ? (
						itemsOverview.map((item, i) => (
							<div key={i} style={{paddingBottom: '5px'}}>
								<b>
									{item.siteName} ({item.siteCount}):{' '}
								</b>

								{item.typeNameAndCount.map((item) => (
									<ClayLink href={item.href} key={i}>
										{item.label}
									</ClayLink>
								))}

								<br />
							</div>
						))
					) : (
						<ClayEmptyState
							className="mt-n4"
							description={Liferay.Language.get(
								'no-changes-were-found'
							)}
							small
							title=" "
						/>
					)}
				</div>
			</ClayPanel.Body>
		</ClayPanel>
	);
}
