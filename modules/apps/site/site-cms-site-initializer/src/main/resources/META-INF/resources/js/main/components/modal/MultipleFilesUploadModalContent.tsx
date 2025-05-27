/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal from '@clayui/modal';
import {sub} from 'frontend-js-web';
import React from 'react';

import {AssetLibrary} from '../../../types/AssetLibrary';
import MultipleFileUploader from '../MultipleFileUploader';

export default function MultipleFilesUploadModalContent({
	assetLibraries,
	closeModal,
}: {
	assetLibraries: AssetLibrary[];
	closeModal: () => void;
}) {
	return (
		<>
			<ClayModal.Header>
				{sub(
					Liferay.Language.get('upload-x'),
					Liferay.Language.get('multiple-files')
				)}
			</ClayModal.Header>

			<MultipleFileUploader
				assetLibraries={assetLibraries}
				closeModal={closeModal}
			/>
		</>
	);
}
