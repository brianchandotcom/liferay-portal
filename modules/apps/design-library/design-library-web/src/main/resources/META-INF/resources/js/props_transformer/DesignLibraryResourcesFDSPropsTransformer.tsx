/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFrontendDataSetProps} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-components-web';
import React from 'react';
import {AddStyleBookModalContent} from 'style-book-web';

import {TableCellContentType} from '../constants';
import {
	AuthorRenderer,
	FromNowDateTimeRenderer,
	LinkRenderer,
	createSetItemComponentProps,
} from './cell_renderers';

type FrontendTokenDefinitionProvider = {
	name: string;
	themeId: string;
};

interface DesignLibraryResourcesAdditionalProps {
	addStyleBookEntryURL?: string;
	canAddStyleBook: boolean;
	frontendTokenDefinitionProviders?: Array<FrontendTokenDefinitionProvider>;
	styleBookNamespace?: string;
}

export default function DesignLibraryResourcesFDSPropsTransformer(
	props: IFrontendDataSetProps & {
		additionalProps?: DesignLibraryResourcesAdditionalProps;
	}
): IFrontendDataSetProps {
	const {
		addStyleBookEntryURL,
		canAddStyleBook = false,
		frontendTokenDefinitionProviders = [],
		styleBookNamespace = '',
	} = props.additionalProps ?? {};

	const creationMenu =
		canAddStyleBook && addStyleBookEntryURL
			? {
					primaryItems: [
						{
							label: Liferay.Language.get('new-style-book'),
							onClick: () =>
								openModal({
									contentComponent: ({closeModal}) =>
										AddStyleBookModalContent({
											addStyleBookEntryURL,
											closeModal,
											frontendTokenDefinitionProviders,
											namespace: styleBookNamespace,
										}),
								}),
						},
					],
				}
			: undefined;

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
					component: AuthorRenderer,
					name: TableCellContentType.AUTHOR,
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
							fieldName: 'embedded.name',
							label: Liferay.Language.get('title'),
							localizeLabel: true,
						},
						{
							contentRenderer: TableCellContentType.AUTHOR,
							fieldName: 'embedded.creator.name',
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
					title: 'embedded.name',
				},
				setItemComponentProps: createSetItemComponentProps('book'),
				thumbnail: 'cards2',
			},
		],
	};
}
