/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../config/index';
import serviceFetch from './serviceFetch';

export interface FileEntry {
	fileEntryURL: string;
}

export interface ImageSize {
	label: string;
	mediaQuery?: string;
	size: string;
	url?: string;
	value: string;
	width: number;
}

export default {
	getAvailableImageConfigurations({fileEntryId}: {fileEntryId: string}) {
		return serviceFetch<ImageSize[]>(
			config.getAvailableImageConfigurationsURL,
			{body: {fileEntryId}}
		);
	},

	getFileEntry({fileEntryId}: {fileEntryId: string}) {
		return serviceFetch<FileEntry>(config.getFileEntryURL, {
			body: {fileEntryId},
		});
	},
};
