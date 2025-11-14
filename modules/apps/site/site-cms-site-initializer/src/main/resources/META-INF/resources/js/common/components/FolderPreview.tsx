/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

import '../../../css/components/FolderPreview.scss';

import {sub} from 'frontend-js-web';

interface FolderPreviewProps {
	filesLength: number;
	name: string;
	subfoldersLength: number;
}

export default function FolderPreview(props: FolderPreviewProps) {
	const {filesLength, name, subfoldersLength} = props;

	const subfolderLabel =
		subfoldersLength === 1
			? sub(Liferay.Language.get('x-folder'), subfoldersLength)
			: sub(Liferay.Language.get('x-folders'), subfoldersLength);

	const fileLabel =
		filesLength === 1
			? sub(Liferay.Language.get('x-file'), filesLength)
			: sub(Liferay.Language.get('x-files'), filesLength);

	return (
		<div className="align-items-center bg-light d-flex folder-preview h-100 justify-content-center text-center w-100">
			<div>
				<ClayIcon
					className="folder-preview__icon mb-3"
					symbol="folder"
				/>

				<p className="mb-0 mt-n1 text-5 text-dark text-weight-semi-bold">
					{name}
				</p>

				<p className="mb-0 text-3 text-secondary">{`${subfolderLabel} · ${fileLabel}`}</p>
			</div>
		</div>
	);
}
