/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/categorization/Categorization.scss';

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayNavigationBar from '@clayui/navigation-bar';
import ClayToolbar from '@clayui/toolbar';
import {navigate} from 'frontend-js-web';
import React from 'react';

export default function CategorizationToolbar({
	activeTab,
	tagsURL,
	vocabularyURL,
}: {
	activeTab: string;
	tagsURL: string;
	vocabularyURL: string;
}) {
	return (
		<div>
			<ClayToolbar
				aria-label={Liferay.Language.get('categorization')}
				className="categorization-toolbar"
				light
			>
				<div className="container-fluid">
					<ClayToolbar.Nav>
						<ClayToolbar.Item className="text-left">
							<ClayToolbar.Section>
								<div className="categorization-title">
									<span>Categorization</span>
								</div>
							</ClayToolbar.Section>
						</ClayToolbar.Item>

						<ClayToolbar.Item>
							<ClayDropDownWithItems
								items={[
									{
										label: Liferay.Language.get('order-by'),
										type: 'group',
									},
								]}
								trigger={
									<ClayButtonWithIcon
										aria-label={Liferay.Language.get(
											'more-actions'
										)}
										displayType="unstyled"
										size="xs"
										symbol="ellipsis-v"
									/>
								}
							/>
						</ClayToolbar.Item>
					</ClayToolbar.Nav>
				</div>
			</ClayToolbar>

			<ClayNavigationBar
				aria-label={Liferay.Language.get('navigation')}
				fluidSize={false}
				triggerLabel={activeTab}
			>
				<ClayNavigationBar.Item
					active={activeTab === 'vocabularies'}
					key={Liferay.Language.get('vocabularies')}
				>
					<ClayButton onClick={() => navigate(vocabularyURL)}>
						{Liferay.Language.get('vocabularies')}
					</ClayButton>
				</ClayNavigationBar.Item>

				<ClayNavigationBar.Item
					active={activeTab === 'tags'}
					key={Liferay.Language.get('tags')}
				>
					<ClayButton onClick={() => navigate(tagsURL)}>
						{Liferay.Language.get('tags')}
					</ClayButton>
				</ClayNavigationBar.Item>
			</ClayNavigationBar>
		</div>
	);
}
