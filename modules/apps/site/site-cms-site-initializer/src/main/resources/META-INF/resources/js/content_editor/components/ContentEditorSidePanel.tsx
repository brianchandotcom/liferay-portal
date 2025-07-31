/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';

import '../../../css/content_editor/ContentEditorSidePanel.scss';

import {Button, VerticalBar} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {datetimeUtils} from '@liferay/object-js-components-web';
import {LiferayEditorConfig} from 'frontend-editor-ckeditor-web';
import {openToast} from 'frontend-js-components-web';
import {fetch, objectToFormData} from 'frontend-js-web';
import moment from 'moment';
import React, {useEffect, useState} from 'react';

import CommentsPanel from './panels/CommentsPanel';
import GeneralPanel from './panels/GeneralPanel';
import SchedulePanel from './panels/SchedulePanel';
import {Comment} from './services/CommentService';

type Props = {
	addCommentURL: string;
	comments: Comment[];
	deleteCommentURL: string;
	editCommentURL: string;
	editorConfig: LiferayEditorConfig;
	expirationDate: string;
	id: string;
	isSubscribed: boolean;
	reviewDate: string;
	subscribeURL: string;
	type: string;
	version: string;
};

type SidePanelProps = Props & {
	dateConfig: datetimeUtils.DateConfig;
	fields: ScheduleFields;
};

type Item = {
	component: React.ComponentType<SidePanelProps>;
	divider?: boolean;
	icon: string;
	id: string;
	title: string;
};

type BaseData = {
	value: string;
};

export type FieldData = BaseData & {
	serverValue: string;
};

export type ScheduleFields = {expirationDate: FieldData; reviewDate: FieldData};

export type UpdateFieldProps = BaseData & {
	name: keyof ScheduleFields;
};

const items: Item[] = [
	{
		component: GeneralPanel,
		icon: 'info-circle',
		id: 'general',
		title: Liferay.Language.get('general'),
	},
	{
		component: SchedulePanel,
		icon: 'date-time',
		id: 'schedule',
		title: Liferay.Language.get('schedule'),
	},
	{
		component: CommentsPanel,
		icon: 'comments',
		id: 'comments',
		title: Liferay.Language.get('comments'),
	},
];

const dateConfig = datetimeUtils.generateDateConfigurations({
	defaultLanguageId: Liferay.ThemeDisplay.getDefaultLanguageId(),
	locale: Liferay.ThemeDisplay.getLanguageId(),
	type: 'DateTime',
});

export default function ContentEditorSidePanel(props: Props) {
	const [formId, setFormId] = useState<string | undefined>();
	const scheduleFields = {
		expirationDate: {
			serverValue: props.expirationDate,
			value: toMomentDate(props.expirationDate),
		},
		reviewDate: {
			serverValue: props.reviewDate,
			value: toMomentDate(props.reviewDate),
		},
	};

	useEffect(() => {
		const form = document.querySelector('.lfr-layout-structure-item-form');

		if (form) {
			setFormId(form.id);
		}
	}, []);

	return (
		<>
			<SidePanel
				{...props}
				dateConfig={dateConfig}
				fields={scheduleFields}
			/>
			{Object.entries(scheduleFields).map(([name, {serverValue}]) => (
				<input
					form={formId}
					key={name}
					name={name}
					type="hidden"
					value={serverValue}
				/>
			))}
		</>
	);
}

function SidePanel(props: SidePanelProps) {
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
		? Liferay.Language.get('unsubscribe')
		: Liferay.Language.get('subscribe');

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

					openToast({
						message: subscribed
							? Liferay.Language.get(
									'you-have-successfully-unsubscribed-from-comments'
								)
							: Liferay.Language.get(
									'you-have-successfully-subscribed-to-comments'
								),
						type: 'success',
					});
				}
			}}
			size="sm"
			symbol={subscribed ? 'bell-off' : 'bell-on'}
			title={title}
		/>
	);
}

function toMomentDate(value: string) {
	return value ? moment(value).format(dateConfig.momentFormat) : '';
}

export function toServerFormat(value: string) {
	return moment(value, dateConfig.momentFormat, true).format(
		dateConfig.serverFormat
	);
}
