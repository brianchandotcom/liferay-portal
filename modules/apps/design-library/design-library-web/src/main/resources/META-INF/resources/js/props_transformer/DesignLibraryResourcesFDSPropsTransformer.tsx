/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFrontendDataSetProps} from '@liferay/frontend-data-set-web';
import React from 'react';

import {
	FromNowDateTimeRenderer,
	LinkRenderer,
	createSetItemComponentProps,
} from './cell_renderers';
import {TableCellContentType} from './constants';

export default function DesignLibraryResourcesFDSPropsTransformer(
	props: IFrontendDataSetProps
): IFrontendDataSetProps {
	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('new-style-book'),
			},
		],
	};

	return {
		...props,
		creationMenu,
		customRenderers: {
			tableCell: [
				{
					component: (props) => (
						<LinkRenderer
							{...props}
							stickerClassName="design-library-fds-sticker-stylebook"
							symbol="book"
						/>
					),
					name: TableCellContentType.DESIGN_LIBRARY_LINK,
					type: 'internal',
				},
				{
					component: () => (
						<span>{Liferay.Language.get('style-book')}</span>
					),
					name: TableCellContentType.RESOURCE_TYPE,
					type: 'internal',
				},
				{
					component: FromNowDateTimeRenderer,
					name: TableCellContentType.FROM_NOW_DATE_TIME,
					type: 'internal',
				},
			],
		},
		hideManagementBarInEmptyState: true,
		showSelectAll: true,
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
								TableCellContentType.DESIGN_LIBRARY_LINK,
							fieldName: 'title',
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
							contentRenderer: TableCellContentType.RESOURCE_TYPE,
							fieldName: 'type',
							label: Liferay.Language.get('type'),
							localizeLabel: true,
							truncate: true,
						},
						{
							contentRenderer:
								TableCellContentType.FROM_NOW_DATE_TIME,
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
					title: 'title',
				},
				setItemComponentProps: createSetItemComponentProps('book'),
				thumbnail: 'cards2',
			},
		],
	};
}
