/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {replaceTokens} from '@liferay/frontend-data-set-web';
import React from 'react';

import {ISearchAssetObjectEntry} from '../types/AssetType';
import {OBJECT_ENTRY_FOLDER_CLASS_NAME} from '../utils/constants';
import ContentPreview from './ContentPreview';
import FilePreview from './FilePreview';
import FolderPreview from './FolderPreview';

interface AssetPreviewProps {
	item: ISearchAssetObjectEntry;
	url: string;
}

export default function AssetPreview(props: AssetPreviewProps) {
	const {item, url} = props;

	if (item.embedded.file) {
		return <FilePreview file={item.embedded.file} />;
	}

	if (item.entryClassName === OBJECT_ENTRY_FOLDER_CLASS_NAME) {
		return (
			<FolderPreview
				filesLength={3}
				name={item.embedded.title ?? ''}
				subfoldersLength={1}
			/>
		);
	}

	return <ContentPreview url={replaceTokens(url, item)} />;
}
