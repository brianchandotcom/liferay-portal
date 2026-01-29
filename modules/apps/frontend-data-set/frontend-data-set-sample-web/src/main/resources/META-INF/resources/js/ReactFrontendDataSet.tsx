/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayBreadCrumb from '@clayui/breadcrumb';
import ClayButton from '@clayui/button';
import {
	DisplayType,
	FrontendDataSet,
	IFrontendDataSetProps,
} from '@liferay/frontend-data-set-web';
import React from 'react';

const ReactFrontendDataSet = (props: IFrontendDataSetProps) => {
	const cardsView = {
		_key: 'cards',
		contentRenderer: 'cards',
		default: false,
		label: 'Cards',
		name: 'cards',
		schema: {
			description: 'description',
			href: '',
			image: '',
			labels: [
				{
					displayType: DisplayType.INFO,
					value: 'color',
				},
				{
					displayTypeKey: 'status.label',
					displayTypeValues: {
						approved: DisplayType.SUCCESS,
						expired: DisplayType.DANGER,
					},
					value: 'status.label_i18n',
				},
			],
			sticker: '',
			symbol: '',
			title: 'title',
		},
		thumbnail: 'cards2',
	};

	props.views.push(cardsView);

	const listView = {
		contentRenderer: 'list',
		default: false,
		label: 'List',
		name: 'list',
		schema: {
			description: 'description',
			image: '',
			sticker: '',
			symbol: '',
			title: 'title',
		},
		thumbnail: 'list',
	};

	props.views.push(listView);

	const [fdsProps, setFdsProps] = React.useState(props);
	const [component, setComponent] = React.useState<string>('alert');
	const [selectedItems, setSelectedItems] = React.useState<any[]>([]);
	const [showInlineNotification, setShowInlineNotification] =
		React.useState(false);

	const onAlertActionClick = function () {
		setFdsProps({
			...props,
			additionalAPIURLParameters: `sort=dateCreated:desc&t=${Date.now()}`,
			filters: [],
			sorts: [],
		});
	};

	let notification = undefined;

	if (component === 'alert') {
		notification = ({context}: {context: any}) => (
			<ClayAlert
				displayType="info"
				onClose={() => setShowInlineNotification(false)}
				variant="stripe"
			>
				{context.selectedItems.length
					? `${selectedItems.length} items selected`
					: 'No items selected'}

				<ClayButton.Group className="pl-3" spaced>
					<ClayButton
						displayType="info"
						onClick={() => {
							onAlertActionClick();
							setShowInlineNotification(false);
						}}
						size="sm"
					>
						{Liferay.Language.get('reload')}
					</ClayButton>

					<ClayButton
						alert
						onClick={() => setShowInlineNotification(false)}
						size="sm"
					>
						{Liferay.Language.get('dismiss')}
					</ClayButton>
				</ClayButton.Group>
			</ClayAlert>
		);
	}
	else if (component === 'breadcrumb') {
		notification = () => (
			<ClayBreadCrumb
				items={[
					{
						href: '#1',
						label: 'Home',
					},
					{
						href: '#2',
						label: 'About',
					},
					{
						href: '#3',
						label: 'Contact',
					},
				]}
			/>
		);
	}
	else {
		notification = () => <span>This is a notification message</span>;
	}

	return (
		<>
			<ClayButton.Group spaced>
				<ClayButton
					displayType="primary"
					onClick={() => setSelectedItems([])}
				>
					Clear
				</ClayButton>

				<ClayButton
					displayType="info"
					onClick={() => {
						setComponent('alert');
						setShowInlineNotification(true);
					}}
				>
					Show info message
				</ClayButton>

				<ClayButton
					displayType="secondary"
					onClick={() => {
						setComponent('breadcrumb');
						setShowInlineNotification(true);
					}}
				>
					Show breadcrumb
				</ClayButton>

				<ClayButton
					displayType="secondary"
					onClick={() => {
						setComponent('span');
						setShowInlineNotification(true);
					}}
				>
					Show text
				</ClayButton>
			</ClayButton.Group>

			<FrontendDataSet
				{...fdsProps}
				inlineNotificationContent={notification}
				onSelectedItemsChange={setSelectedItems}
				selectedItems={selectedItems}
				selectionType="multiple"
				showInlineNotification={showInlineNotification}
			/>
		</>
	);
};

export default ReactFrontendDataSet;
