/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClaySticker from '@clayui/sticker';
import {findAction, replaceTokens} from '@liferay/frontend-data-set-web';
import classNames from 'classnames';
import React from 'react';

import {OBJECT_ENTRY_FOLDER_CLASS_NAME} from '../../../common/utils/constants';
import {getFileMimeTypeObjectDefinitionStickerValue} from '../utils/transformViewsItemProps';

export interface ActionItem {
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
	additionalProps?: {
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
	const title =
		value && value !== '' ? value : Liferay.Language.get('untitled-asset');

	if (!actions.length || !actionId) {
		return <>{title}</>;
	}

	const isFolder =
		itemData?.entryClassName === OBJECT_ENTRY_FOLDER_CLASS_NAME;

	const resolvedActionId = isFolder ? `${actionId}Folder` : actionId;

	const selectedAction = findAction(actions, resolvedActionId);

	if (!selectedAction?.href) {
		return <>{title}</>;
	}

	const formattedHref = replaceTokens(selectedAction.href, itemData);

	return (
		<div className="align-items-center d-flex table-list-title">
			{additionalProps && (
				<ClaySticker
					className={classNames(
						'c-mr-2',
						'flex-shrink-0',
						'inline-item',
						'inline-item-before',
						isFolder
							? 'file-icon-color-0'
							: getFileMimeTypeObjectDefinitionStickerValue(
									additionalProps.fileMimeTypeCssClasses,
									additionalProps.objectDefinitionCssClasses,
									itemData
								)
					)}
				>
					<ClayIcon
						symbol={
							isFolder
								? 'folder'
								: getFileMimeTypeObjectDefinitionStickerValue(
										additionalProps.fileMimeTypeIcons,
										additionalProps.objectDefinitionIcons,
										itemData
									)
						}
					/>
				</ClaySticker>
			)}

			<ClayLink aria-label={title} data-senna-off href={formattedHref}>
				{title}

				{itemData.system && (
					<ClayIcon
						aria-label={Liferay.Language.get(
							'system-default-structure'
						)}
						className="c-ml-2 lfr-portal-tooltip text-secondary"
						data-title={Liferay.Language.get(
							'system-default-structure'
						)}
						symbol="lock"
					/>
				)}
			</ClayLink>
		</div>
	);
}
