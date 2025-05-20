/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/content_editor/ContentEditorSidePanel.scss';

import {Button, VerticalBar} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {sub} from 'frontend-js-web';
import React from 'react';

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

function GeneralPanel({id, type, version}: Props) {
	const Row = ({
		children,
		label,
	}: {
		children: React.ReactNode;
		label: string;
	}) => (
		<div className="border-bottom d-flex justify-content-between mb-2 pb-2">
			<span className="font-weight-semi-bold text-secondary">
				{label}
			</span>

			{children}
		</div>
	);

	return (
		<div className="p-3">
			<Row label={Liferay.Language.get('type')}>
				<span>{type}</span>
			</Row>

			<Row label={Liferay.Language.get('version')}>
				<ClayLabel displayType="info">
					{sub(Liferay.Language.get('version-x'), version)}
				</ClayLabel>
			</Row>

			<Row label={Liferay.Language.get('id')}>
				<span>{id}</span>
			</Row>
		</div>
	);
}
