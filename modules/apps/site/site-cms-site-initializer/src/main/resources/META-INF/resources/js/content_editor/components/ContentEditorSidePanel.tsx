/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/content_editor/ContentEditorSidePanel.scss';

import {Button, VerticalBar} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import React from 'react';

import GeneralPanel from './panels/GeneralPanel';

type Props = {
	id: string;
	type: string;
	version: string;
};

type Item = {
	component: React.ComponentType<Props>;
	divider?: boolean;
	icon: string;
	title: string;
};

const items: Item[] = [
	{
		component: GeneralPanel,
		icon: 'info-circle',
		title: Liferay.Language.get('general'),
	},
];

export default function ContentEditorSidePanel(props: Props) {
	return (
		<VerticalBar className="content-editor__side-panel">
			<VerticalBar.Content items={items}>
				{(item) => {
					const Component = item.component;

					return (
						<VerticalBar.Panel key={item.title}>
							<div className="sidebar-header">
								<div className="component-title">
									{item.title}
								</div>
							</div>

							<Component {...props} />
						</VerticalBar.Panel>
					);
				}}
			</VerticalBar.Content>

			<VerticalBar.Bar displayType="light" items={items}>
				{(item) => (
					<VerticalBar.Item divider={item.divider} key={item.title}>
						<Button aria-label={item.title} displayType={null}>
							<ClayIcon symbol={item.icon} />
						</Button>
					</VerticalBar.Item>
				)}
			</VerticalBar.Bar>
		</VerticalBar>
	);
}
