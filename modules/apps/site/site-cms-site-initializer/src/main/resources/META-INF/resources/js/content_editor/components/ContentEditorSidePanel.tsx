/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';

import '../../../css/content_editor/ContentEditorSidePanel.scss';

import {Button, VerticalBar} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

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
	const [panel, setPanel] = useState<React.Key | null>(null);

	return (
		<VerticalBar
			active={panel}
			className="content-editor__side-panel"
			onActiveChange={setPanel}
		>
			<VerticalBar.Content items={items}>
				{(item) => {
					const Component = item.component;

					return (
						<VerticalBar.Panel key={item.title}>
							<div className="align-items-center border-0 d-flex justify-content-between sidebar-header">
								<div className="component-title">
									{item.title}
								</div>

								<div>
									<ClayButtonWithIcon
										aria-label={Liferay.Language.get(
											'close'
										)}
										borderless
										displayType="secondary"
										monospaced
										onClick={() => setPanel(null)}
										size="sm"
										symbol="times"
										title={Liferay.Language.get('close')}
									/>
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
