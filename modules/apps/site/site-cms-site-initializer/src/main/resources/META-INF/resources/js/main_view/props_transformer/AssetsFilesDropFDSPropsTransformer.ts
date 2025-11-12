/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IView} from '@liferay/frontend-data-set-web';

import {OBJECT_ENTRY_FOLDER_CLASS_NAME} from '../../common/utils/constants';
import AssetsFDSPropsTransformer, {
	AdditionalProps,
} from './AssetsFDSPropsTransformer';
import fileDropAction from './actions/fileDropAction';
import {MultipleFileUploaderData} from './actions/multipleFilesUploadAction';
import GalleryView from './views/GalleryView';

export default function AssetsFilesDropFDSPropsTransformer({
	additionalProps,
	creationMenu,
	itemsActions = [],
	views,
	...otherProps
}: {
	additionalProps: AdditionalProps & MultipleFileUploaderData;
	creationMenu: any;
	itemsActions?: any[];
	otherProps: any;
	views: IView[];
}) {
	const GalleryViewRenderer: IView = {
		component: GalleryView,
		default: true,
		label: Liferay.Language.get('gallery'),
		name: 'gallery',
		schema: {
			description: 'description',
			image: 'imageURL',
			link: '',
			sticker: '',
			symbol: '',
			title: 'title',
		},
		thumbnail: 'gallery',
	};

	const nonDefaultViews = views.map((view) => {
		return {
			...view,
			default: false,
		};
	});

	const mergedViews = [
		...nonDefaultViews,
		{
			...GalleryViewRenderer,
			additionalProps,
		},
	];

	const assetsData = AssetsFDSPropsTransformer({
		additionalProps,
		creationMenu,
		itemsActions,
		...otherProps,
		views: mergedViews,
	});

	return {
		...assetsData,
		fileDropSettings: {
			enabled: true,
			isDropTarget: ({item}: {item: any}) => {
				return item.entryClassName.includes(
					OBJECT_ENTRY_FOLDER_CLASS_NAME
				);
			},
			onFileDrop: (droppedFiles: any, dropTarget?: any) =>
				fileDropAction(additionalProps, droppedFiles, dropTarget),
		},
	};
}
