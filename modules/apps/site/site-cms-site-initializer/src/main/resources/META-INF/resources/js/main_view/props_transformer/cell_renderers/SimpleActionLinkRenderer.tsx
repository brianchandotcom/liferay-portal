/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import React from 'react';

import formatActionURL from '../../../common/utils/formatActionURL';
import {getFileMimeTypeValue} from '../utils/transformViewsItemProps';

import '../../../../css/main.scss';
const OBJECT_ENTRY_FOLDER_CLASS_NAME =
	'com.liferay.object.model.ObjectEntryFolder';

interface ActionItem {
	data: {id: string};
	href?: string;
}

export default function SimpleActionLinkRenderer({
	actions,
	additionalProps,
	itemData,
	options,
	value,
}: {
	actions: ActionItem[];
	additionalProps: {
		fileMimeTypeCssClasses: Record<string, string>;
		fileMimeTypeIcons: Record<string, string>;
		objectDefinitionCssClasses: Record<string, string>;
		objectDefinitionIcons: Record<string, string>;
	};
	itemData: any;
	options: {actionId: string};
	value: string;
}) {
	const {actionId} = options;

	if (!actions.length || !actionId) {
		return value ? <>{value}</> : null;
	}

	const isFolder =
		itemData?.entryClassName === OBJECT_ENTRY_FOLDER_CLASS_NAME;

	const resolvedActionId = isFolder ? `${actionId}Folder` : actionId;

	const selectedAction = actions.find(
		({data}) => data?.id === resolvedActionId
	);

	if (!selectedAction?.href) {
		return value ? <>{value}</> : null;
	}

	const formattedHref = formatActionURL(itemData, selectedAction.href);

	return (
		<div className="table-list-title">
			<ClayIcon
				className={classNames(
					'c-mr-2',
					isFolder
						? 'file-icon-color-0'
						: getFileMimeTypeValue(
								additionalProps.fileMimeTypeCssClasses,
								additionalProps.objectDefinitionCssClasses,
								itemData
							)
				)}
				symbol={
					isFolder
						? 'folder'
						: getFileMimeTypeValue(
								additionalProps.fileMimeTypeIcons,
								additionalProps.objectDefinitionIcons,
								itemData
							)
				}
			/>

			<ClayLink data-senna-off href={formattedHref}>
				{value}
			</ClayLink>
		</div>
	);
}
