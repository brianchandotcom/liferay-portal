/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {IView} from '@liferay/frontend-data-set-web';
import React from 'react';

import dateFormat from '../../../common/utils/dateFormat';
import formatActionURL from '../../../common/utils/formatActionURL';

const OBJECT_ENTRY_FOLDER_CLASS_NAME =
	'com.liferay.object.model.ObjectEntryFolder';

const getHrefLink = (item: any, props: any) => {
	const actionId = 'actionLink';
	const {actions} = props;

	if (!actions.length) {
		return null;
	}

	const isFolder = item.entryClassName === OBJECT_ENTRY_FOLDER_CLASS_NAME;
	const resolvedActionId = isFolder ? `${actionId}Folder` : actionId;

	const selectedAction = actions.find(
		({data}: {data: any}) => data?.id === resolvedActionId
	);

	if (!selectedAction?.href) {
		return null;
	}

	return formatActionURL(item, selectedAction.href);
};

const getThumbnailProps = (item: any) => {
	if (item.entryClassName === OBJECT_ENTRY_FOLDER_CLASS_NAME) {
		return {symbol: 'folder'};
	}

	if (item.embedded.file) {
		const {thumbnailURL} = item.embedded.file;

		if (thumbnailURL) {
			return {imgProps: thumbnailURL};
		}
		else {
			return {symbol: 'documents-and-media'};
		}
	}

	return {symbol: 'web-content'};
};

function isMultimediaMimeType(mimeType: string): boolean {
	const multimediaTypes = ['audio/', 'image/', 'video/'];

	return multimediaTypes.some((prefix) => mimeType.startsWith(prefix));
}

const getFileMimeTypeValue = (
	fileMimeTypeValues: Record<string, string> | undefined,
	item: any
) => {
	if (!fileMimeTypeValues) {
		return '';
	}

	if (item.embedded.file) {
		const mimeType = item.embedded.file.mimeType;

		const cssClass = fileMimeTypeValues[mimeType];

		if (cssClass) {
			return cssClass;
		}

		if (isMultimediaMimeType(mimeType)) {
			const mimeTypeParts = mimeType.split('/');

			const cssClass = fileMimeTypeValues[mimeTypeParts[0]];

			if (cssClass) {
				return cssClass;
			}
		}

		return fileMimeTypeValues['default'];
	}

	return '';
};

type ViewsItemsProps = {
	fileMimeTypeCssClasses?: Record<string, string>;
	fileMimeTypeIcons?: Record<string, string>;
	views: IView[];
};

export default function transformViewsItemProps({
	fileMimeTypeCssClasses,
	fileMimeTypeIcons,
	views,
}: ViewsItemsProps) {
	return views.map((view) => {
		if (view.name === 'cards') {
			view.setItemComponentProps = ({item, props}) => {
				return {
					...props,
					description: dateFormat(item.dateModified),
					href: getHrefLink(item, props),
					stickerProps: {
						className: getFileMimeTypeValue(
							fileMimeTypeCssClasses,
							item
						),
						content: (
							<ClayIcon
								symbol={getFileMimeTypeValue(
									fileMimeTypeIcons,
									item
								)}
							/>
						),
					},
					...getThumbnailProps(item),
				};
			};
		}

		return view;
	});
}
