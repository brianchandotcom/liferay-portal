/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IView} from '@liferay/frontend-data-set-web';

import dateFormat from '../../../common/utils/dateFormat';

const OBJECT_ENTRY_FOLDER_CLASSNAME =
	'com.liferay.object.model.ObjectEntryFolder';

const getThumbnailProps = (item: any) => {
	if (item.entryClassName === OBJECT_ENTRY_FOLDER_CLASSNAME) {
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

export default function transformViewsItemProps(views: IView[]) {
	return views.map((view) => {
		if (view.name === 'cards') {
			view.setItemComponentProps = ({item, props}) => {
				return {
					...props,
					description: dateFormat(item.dateModified),
					...getThumbnailProps(item),
				};
			};
		}

		return view;
	});
}
