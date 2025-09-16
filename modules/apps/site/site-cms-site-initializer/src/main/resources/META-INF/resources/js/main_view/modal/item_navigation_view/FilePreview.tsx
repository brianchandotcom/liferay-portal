/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';

// @ts-ignore

import {ImagePreviewer} from 'document-library-preview-image';
import React from 'react';

import {File} from '../FilePreviewerModalContent';

export default function FilePreview({file}: {file: File}) {
	const {link, name, thumbnailURL} = file;
	const params = new URLSearchParams(thumbnailURL);
	const hasImagePreview = params.has('imageThumbnail');

	return hasImagePreview ? (
		<ImagePreviewer alt={name} imageURL={link.href} />
	) : (
		<div className="bg-light d-flex height-100">
			<ClayEmptyState
				description={Liferay.Language.get(
					'hmm-looks-like-this-item-does-not-have-a-preview-we-can-show-you'
				)}
				imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/cms_empty_state_preview.svg`}
				title={Liferay.Language.get('no-preview-available')}
			/>
		</div>
	);
}
