/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayNavigationBar from '@clayui/navigation-bar';
import ClayToolbar from '@clayui/toolbar';
import React from 'react';

export default function CategorizationToolbar({
	activeTab,
	onChangeTab,
	tabs,
}: {
	activeTab: string;
	onChangeTab: Function;
	tabs: string[];
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
				{tabs.map((tab) => (
					<ClayNavigationBar.Item
						active={activeTab === tab}
						key={tab}
					>
						<ClayButton onClick={() => onChangeTab(tab)}>
							{tab}
						</ClayButton>
					</ClayNavigationBar.Item>
				))}
			</ClayNavigationBar>
		</div>
	);
}
