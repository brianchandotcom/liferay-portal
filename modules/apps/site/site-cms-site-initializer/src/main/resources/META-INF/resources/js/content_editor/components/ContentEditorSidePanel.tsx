/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';

import '../../../css/content_editor/ContentEditorSidePanel.scss';

import {Button, VerticalBar} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {LiferayEditorConfig} from 'frontend-editor-ckeditor-web';
import {openToast} from 'frontend-js-components-web';
import {fetch, objectToFormData} from 'frontend-js-web';
import React, {useState} from 'react';

import CommentsPanel from './panels/CommentsPanel';
import GeneralPanel from './panels/GeneralPanel';
import {Comment} from './services/CommentService';

type Props = {
	addCommentURL: string;
	comments: Comment[];
	editorConfig: {configJSONObject: LiferayEditorConfig};
	id: string;
	isSubscribed: boolean;
	subscribeURL: string;
	type: string;
	version: string;
};

type Item = {
	component: React.ComponentType<Props>;
	divider?: boolean;
	icon: string;
	id: string;
	title: string;
};

const items: Item[] = [
	{
		component: GeneralPanel,
		icon: 'info-circle',
		id: 'general',
		title: Liferay.Language.get('general'),
	},
	{
		component: CommentsPanel,
		icon: 'comments',
		id: 'comments',
		title: Liferay.Language.get('comments'),
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
									{item.id === 'comments' ? (
										<SubscribeButton
											isSubscribed={props.isSubscribed}
											subscribeURL={props.subscribeURL}
										/>
									) : null}

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

function SubscribeButton({
	isSubscribed,
	subscribeURL,
}: {
	isSubscribed: boolean;
	subscribeURL: string;
}) {
	const [disabled, setDisabled] = useState<boolean>(false);
	const [subscribed, setSubscribed] = useState<boolean>(isSubscribed);

	const title = subscribed
		? Liferay.Language.get('subscribe')
		: Liferay.Language.get('unsubscribe');

	return (
		<ClayButtonWithIcon
			aria-label={title}
			borderless
			disabled={disabled}
			displayType="secondary"
			monospaced
			onClick={async () => {
				setDisabled(true);

				const response = await fetch(subscribeURL, {
					body: objectToFormData({
						cmd: !subscribed ? 'subscribe' : 'unsubscribe',
					}),
					method: 'POST',
				});

				const subscription = await response.json();

				setDisabled(false);

				if (subscription.error) {
					openToast({
						message:
							subscription.error ||
							Liferay.Language.get(
								'an-unexpected-system-error-occurred'
							),
						type: 'danger',
					});
				}
				else {
					setSubscribed((subscribed) => !subscribed);
				}
			}}
			size="sm"
			symbol={subscribed ? 'bell-on' : 'bell-off'}
			title={title}
		/>
	);
}
