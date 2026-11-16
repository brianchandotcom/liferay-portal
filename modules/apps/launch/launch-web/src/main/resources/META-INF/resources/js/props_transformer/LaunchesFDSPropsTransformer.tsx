/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import React from 'react';

import confirmAndUnscheduleLaunchAction from '../actions/confirmAndUnscheduleLaunchAction';
import {Launch, getLaunchStatus} from '../api/launches';
import openNewLaunchModal from '../modals/openNewLaunchModal';
import openScheduleLaunchModal from '../modals/openScheduleLaunchModal';
import fillViewLaunchURL from '../util/fillViewLaunchURL';
import {openSuccessToast} from '../util/toasts';

import type {TimeZoneOption} from '../util/dateTime';

interface CreationMenu {
	primaryItems: Array<{[key: string]: unknown}>;
}

interface ItemsAction {
	data?: {id?: string};
	[key: string]: unknown;
}

interface Props {
	additionalProps: {
		portletNamespace: string;
		scheduleLaunchURL: string;
		timeZoneId: string;
		timeZones: TimeZoneOption[];
		unscheduleLaunchURL: string;
		viewLaunchURL: string;
	};
	creationMenu?: CreationMenu;
	itemsActions?: ItemsAction[];
	[key: string]: unknown;
}

export default function LaunchesFDSPropsTransformer({
	additionalProps,
	creationMenu,
	itemsActions,
	...props
}: Props) {
	return {
		...props,
		additionalProps,
		creationMenu: creationMenu && {
			...creationMenu,
			primaryItems: creationMenu.primaryItems.map((primaryItem) => ({
				...primaryItem,
				onClick: () =>
					openNewLaunchModal({
						viewLaunchURL: additionalProps.viewLaunchURL,
					}),
			})),
		},
		customRenderers: {
			tableCell: [
				{
					component: ({itemData}: {itemData: Launch}) => {
						const launchStatus = getLaunchStatus(itemData);

						if (launchStatus === 'published') {
							return (
								<ClayLabel displayType="success">
									{Liferay.Language.get('published')}
								</ClayLabel>
							);
						}

						if (launchStatus === 'failed') {
							return (
								<ClayLabel displayType="danger">
									{Liferay.Language.get('failed')}
								</ClayLabel>
							);
						}

						if (launchStatus === 'scheduled') {
							return (
								<ClayLabel displayType="info">
									{Liferay.Language.get('scheduled')}
								</ClayLabel>
							);
						}

						return (
							<ClayLabel displayType="info">
								{Liferay.Language.get('in-progress')}
							</ClayLabel>
						);
					},
					name: 'launchStatus',
					type: 'internal',
				},
			],
		},
		itemsActions: itemsActions?.map((action) => {
			if (action.data?.id === 'schedule') {
				return {
					...action,
					isVisible: (launch: Launch) => {
						const launchStatus = getLaunchStatus(launch);

						return (
							launchStatus === 'failed' || launchStatus === 'open'
						);
					},
				};
			}

			if (action.data?.id === 'unschedule') {
				return {
					...action,
					isVisible: (launch: Launch) =>
						getLaunchStatus(launch) === 'scheduled',
				};
			}

			if (action.data?.id === 'view-error-details') {
				return {
					...action,
					isVisible: (launch: Launch) =>
						getLaunchStatus(launch) === 'failed',
				};
			}

			return action;
		}),
		onActionDropdownItemClick({
			action,
			itemData,
			loadData,
		}: {
			action: ItemsAction;
			itemData: Launch;
			loadData: () => void;
		}) {
			if (action.data?.id === 'schedule') {
				openScheduleLaunchModal({
					launchName: itemData.name,
					onScheduled: () => {
						loadData();

						openSuccessToast(
							Liferay.Language.get(
								'your-request-completed-successfully'
							)
						);
					},
					portletNamespace: additionalProps.portletNamespace,
					scheduleLaunchURL: fillViewLaunchURL(
						additionalProps.scheduleLaunchURL,
						itemData
					),
					timeZoneId: additionalProps.timeZoneId,
					timeZones: additionalProps.timeZones,
				});
			}
			else if (action.data?.id === 'unschedule') {
				confirmAndUnscheduleLaunchAction({
					loadData,
					unscheduleLaunchURL: fillViewLaunchURL(
						additionalProps.unscheduleLaunchURL,
						itemData
					),
				});
			}
		},
	};
}
