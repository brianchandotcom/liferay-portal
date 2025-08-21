/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IView} from '@liferay/frontend-data-set-web';

import dateFormat from '../../../common/utils/dateFormat';
import formatActionURL from '../../../common/utils/formatActionURL';

const OBJECT_ENTRY_FOLDER_CLASSNAME =
	'com.liferay.object.model.ObjectEntryFolder';

const getHrefLink = (item: any, props: any) => {
	const actionId = 'actionLink';
	const {actions} = props;

	if (!actions.length) {
		return null;
	}

	const isFolder = item.entryClassName === OBJECT_ENTRY_FOLDER_CLASSNAME;
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
					href: getHrefLink(item, props),
					...getThumbnailProps(item),
				};
			};
		}

		return view;
	});
}
