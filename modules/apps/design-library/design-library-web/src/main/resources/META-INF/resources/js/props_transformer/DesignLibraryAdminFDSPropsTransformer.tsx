/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFrontendDataSetProps} from '@liferay/frontend-data-set-web';
import {dateUtils} from 'frontend-js-web';

import {ActionItem, DesignLibraryItem} from '../types';
import {DesignLibraryLinkRenderer} from './cell_renderers/DesignLibraryLinkRenderer';
import {FromNowDateTimeRenderer} from './cell_renderers/FromNowDateTimeRenderer';

enum TableCelRenderer {
	DESIGN_LIBRARY_LINK = 'designLibraryLink',
	FROM_NOW_DATE_TIME = 'fromNowDateTime',
}

export default function DesignLibraryAdminFDSPropsTransformer(
	props: IFrontendDataSetProps
): IFrontendDataSetProps {
	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('new-design-library'),
			},
		],
	};

	return {
		...props,
		creationMenu,
		customRenderers: {
			tableCell: [
				{
					component: DesignLibraryLinkRenderer,
					name: TableCelRenderer.DESIGN_LIBRARY_LINK,
					type: 'internal',
				},
				{
					component: FromNowDateTimeRenderer,
					name: TableCelRenderer.FROM_NOW_DATE_TIME,
					type: 'internal',
				},
			],
		},
		hideManagementBarInEmptyState: true,
		views: [
			{
				contentRenderer: 'table',
				default: true,
				label: Liferay.Language.get('table'),
				name: 'table',
				schema: {
					fields: [
						{
							actionId: 'edit',
							contentRenderer:
								TableCelRenderer.DESIGN_LIBRARY_LINK,
							fieldName: 'name',
							label: Liferay.Language.get('title'),
							localizeLabel: true,
							sortable: true,
						},
						{
							fieldName: 'creatorUserId',
							label: Liferay.Language.get('author'),
							localizeLabel: true,
							truncate: true,
						},
						{
							contentRenderer:
								TableCelRenderer.FROM_NOW_DATE_TIME,
							fieldName: 'dateModified',
							label: Liferay.Language.get('modified'),
							localizeLabel: true,
							sortable: true,
						},
					],
				},
				thumbnail: 'table',
			},
			{
				contentRenderer: 'cards',
				label: Liferay.Language.get('cards'),
				name: 'cards',
				schema: {
					description: 'dateModified',
					symbol: '',
					title: 'name',
				},
				setItemComponentProps: ({
					item,
					props,
				}: {
					item: DesignLibraryItem;
					props: {actions: ActionItem[]};
				}) => {
					return {
						...props,
						description: dateUtils.fromNow(
							new Date(item.dateModified)
						),
						href: props.actions.find(
							(action) => action.data.id === 'edit'
						)?.href,
						symbol: 'books',
					};
				},

				thumbnail: 'cards2',
			},
		],
	};
}
