/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IBulkActionSelectedData} from '../TaskStatusType';

function generateUrlParams(
	selectedData: IBulkActionSelectedData,
	...otherProps: any
) {
	let urlParams;

	if (selectedData.selectAll) {
		const searchQuery = selectedData.searchQuery;

		const filterParams = new URL(
			`${Liferay.ThemeDisplay.getPortalURL()}${otherProps[0].apiURL}`
		).searchParams.get('filter');

		if (filterParams?.includes('cmsKind')) {
			urlParams = `(cmsSection eq 'contents' or cmsSection eq 'files')&search=${
				searchQuery ? searchQuery : ''
			}`;
		}
		else if (filterParams?.includes(`cmsSection eq 'contents'`)) {
			urlParams = `cmsSection eq 'contents'&search=${
				searchQuery ? searchQuery : ''
			}`;
		}
		else if (filterParams?.includes('folderId')) {
			const regex = /folderId eq (\d+)/;
			const match = filterParams.match(regex);

			if (match && match[1]) {
				const folderId = parseInt(match[1], 10);
				urlParams = `folderId eq ${folderId}&search=${
					searchQuery ? searchQuery : ''
				}`;
			}
			else {
				throw new Error('Missing folderID');
			}
		}
		else {
			urlParams = `cmsSection eq 'files'&search=${
				searchQuery ? searchQuery : ''
			}`;
		}

		return urlParams;
	}
}

export default generateUrlParams;
