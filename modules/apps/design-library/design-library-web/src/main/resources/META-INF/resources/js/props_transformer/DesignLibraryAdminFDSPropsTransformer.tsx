/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFrontendDataSetProps} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-components-web';
import React from 'react';

import CreateDesignLibraryModal from '../modal/CreateDesignLibraryModal';
import {
	FromNowDateTimeRenderer,
	LinkRenderer,
	createSetItemComponentProps,
} from './cell_renderers';
import {TableCellContentType} from './constants';

export default function DesignLibraryAdminFDSPropsTransformer({
	additionalProps: {entryIdKey, redirectURL},
	id,
	...props
}: {
	additionalProps: {
		entryIdKey: string;
		redirectURL: string;
	};

	id: string;
	props: Record<string, unknown>;
}): IFrontendDataSetProps {
	const creationMenu = {
		primaryItems: [
			{
				label: Liferay.Language.get('new-design-library'),
				onClick: () => {
					openModal({
						contentComponent: ({closeModal}) =>
							CreateDesignLibraryModal({
								dataSetId: id,
								entryIdKey,
								onClose: closeModal,
								redirectURL,
							}),
						size: 'md',
					});
				},
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
							stickerClassName="design-library-fds-sticker-designlibrary"
							symbol="books"
						/>
					),
					name: TableCellContentType.DESIGN_LIBRARY_LINK,
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
		id,
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
					title: 'name',
				},
				setItemComponentProps: createSetItemComponentProps('books'),
				thumbnail: 'cards2',
			},
		],
	};
}
