/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {DocumentPreviewer} from 'document-library-preview-document';
import {ImagePreviewer} from 'document-library-preview-image';
import {DLVideoIframe} from 'document-library-video';
import React from 'react';

export type File = {
	externalReferenceCode: string;
	id: number;
	link: {
		href: string;
		label: string;
	};
	metadata?: {
		numberOfPages?: number;
	};
	mimeType: string;
	name: string;
	previewURL: string;
	thumbnailURL: string;
};

interface FilePreviewerModalContentProps {
	file: File;
	headerName?: string;
}

export default function FilePreviewerModalContent({
	file: {
		link,
		metadata: {numberOfPages} = {},
		mimeType,
		name,
		previewURL,
		thumbnailURL,
	},
	headerName,
}: FilePreviewerModalContentProps) {
	const params = new URLSearchParams(thumbnailURL);
	const hasDocumentPreview = numberOfPages && previewURL;
	const baseDocumentImageURL = new URL(previewURL, window.location.href);
	const hasImagePreview = params.has('imageThumbnail');
	const isVideo = mimeType.startsWith('video/') && previewURL;

	return (
		<>
			<ClayModal.Header>
				<div className="autofit-row autofit-row-center">
					<div className="autofit-col autofit-col-expand">
						<div className="text-truncate">
							{headerName ? headerName : name}
						</div>
					</div>

					<div className="autofit-col pr-3">
						<ClayLink
							button
							displayType="primary"
							href={link.href}
							small
						>
							<span className="inline-item inline-item-before">
								<ClayIcon symbol="download" />
							</span>

							{Liferay.Language.get('download')}
						</ClayLink>
					</div>
				</div>
			</ClayModal.Header>

			<ClayModal.Body
				className={classNames({
					'bg-light': !hasImagePreview,
				})}
			>
				{hasDocumentPreview ? (
					<DocumentPreviewer
						baseImageURL={baseDocumentImageURL.toString()}
						totalPages={numberOfPages}
					/>
				) : hasImagePreview ? (
					<ImagePreviewer alt={name} imageURL={link.href} />
				) : isVideo ? (
					<DLVideoIframe videoPreviewURL={previewURL} />
				) : (
					<ClayEmptyState
						description={Liferay.Language.get(
							'hmm-looks-like-this-item-does-not-have-a-preview-we-can-show-you'
						)}
						imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/cms_empty_state_preview.svg`}
						title={Liferay.Language.get('no-preview-available')}
					/>
				)}
			</ClayModal.Body>
		</>
	);
}
