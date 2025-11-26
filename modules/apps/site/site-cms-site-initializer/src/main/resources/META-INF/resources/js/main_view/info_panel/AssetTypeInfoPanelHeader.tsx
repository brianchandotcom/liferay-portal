/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SidePanel} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useContext} from 'react';

import {getFileMimeTypeObjectDefinitionStickerValue} from '../props_transformer/utils/transformViewsItemProps';
import {AssetTypeInfoPanelContext, IAssetTypeInfoPanelContext} from './context';
import {ASSET_TYPE} from './util/constants';

const renderTitle = ({
	objectEntries,
	title,
	title_i18n,
	type,
}: IAssetTypeInfoPanelContext) => {
	if (!objectEntries?.length) {
		return <>{Liferay.Language.get('no-assets-selected')}</>;
	}
	else if (objectEntries?.length > 1) {
		return (
			<>
				{sub(
					Liferay.Language.get('x-assets-selected'),
					objectEntries.length
				)}
			</>
		);
	}
	else if (
		type === ASSET_TYPE.FILES ||
		type === ASSET_TYPE.CONTENTS ||
		type === ASSET_TYPE.FOLDER
	) {
		return (
			<>
				{title_i18n
					? title_i18n[
							Liferay.ThemeDisplay.getLanguageId() as keyof typeof title_i18n
						] || title
					: title || Liferay.Language.get('untitled-asset')}
			</>
		);
	}

	return null;
};

const AssetTypeInfoPanelHeader = ({
	additionalProps,
}: {
	additionalProps: {
		fileMimeTypeCssClasses: Record<string, string>;
		fileMimeTypeIcons: Record<string, string>;
		objectDefinitionCssClasses: Record<string, string>;
		objectDefinitionIcons: Record<string, string>;
	};
}) => {
	const {objectEntries = [], ...context} = useContext(
		AssetTypeInfoPanelContext
	);

	return (
		<SidePanel.Header>
			<SidePanel.Title>
				<span className="inline-flex text-nowrap">
					{objectEntries?.length === 1 && (
						<ClaySticker
							className={classNames(
								getFileMimeTypeObjectDefinitionStickerValue(
									additionalProps.fileMimeTypeCssClasses,
									additionalProps.objectDefinitionCssClasses,
									objectEntries[0]
								)
							)}
						>
							<ClayIcon
								symbol={getFileMimeTypeObjectDefinitionStickerValue(
									additionalProps.fileMimeTypeIcons,
									additionalProps.objectDefinitionIcons,
									objectEntries[0]
								)}
							/>
						</ClaySticker>
					)}

					<span className="inline-item text-truncate-inline">
						<h3 className="inline-item-after mb-0 text-truncate">
							{renderTitle({objectEntries, ...context})}
						</h3>
					</span>
				</span>
			</SidePanel.Title>
		</SidePanel.Header>
	);
};

export default AssetTypeInfoPanelHeader;
