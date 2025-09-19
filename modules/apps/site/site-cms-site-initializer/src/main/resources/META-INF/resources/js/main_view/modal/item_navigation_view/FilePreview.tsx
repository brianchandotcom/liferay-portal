/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';

// @ts-ignore

import {ImagePreviewer} from 'document-library-preview-image';
import {DLVideoIframe} from 'document-library-video';
import React from 'react';

import {File} from '../FilePreviewerModalContent';

export default function FilePreview({file}: {file: File}) {
	const {link, mimeType, name, previewURL, thumbnailURL} = file;
	const params = new URLSearchParams(thumbnailURL);
	const hasImagePreview = params.has('imageThumbnail');
	const isVideo = mimeType.startsWith('video/') && previewURL;

	return hasImagePreview ? (
		<ImagePreviewer alt={name} imageURL={link.href} />
	) : isVideo ? (
		<DLVideoIframe videoPreviewURL={previewURL} />
	) : (
		<div className="bg-light d-flex h-100">
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
