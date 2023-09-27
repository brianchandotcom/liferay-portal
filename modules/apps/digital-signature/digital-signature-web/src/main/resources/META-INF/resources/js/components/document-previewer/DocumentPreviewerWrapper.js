/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayPaginationWithBasicItems} from '@clayui/pagination';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {OriginalDocumentTag} from '../original-document-tag/OriginalDocumentTag';
import EmptyState from '../table/EmptyState';
import DocumentPreviewer from './DocumentPreviewer';
import ImagePreviewer from './ImagePreviewer';

const UnavailablePreview = ({
	description = Liferay.Language.get(
		'the-envelope-does-not-have-a-document-to-preview'
	),
}) => (
	<div className="preview-file">
		<div className="preview-file-container unavailable-preview">
			<EmptyState
				className="mb-2 mt-4"
				description={description}
				title={Liferay.Language.get('no-preview-available')}
			/>

			<OriginalDocumentTag />
		</div>
	</div>
);

const DocumentPreview = ({fileEntry}) => {
	if (fileEntry.imageURL) {
		return <ImagePreviewer alt="document" imageURL={fileEntry.imageURL} />;
	}

	if (fileEntry.previewFileURL) {
		return (
			<DocumentPreviewer
				baseImageURL={fileEntry.previewFileURL}
				initialPage={fileEntry.initialPage}
				totalPages={fileEntry.previewFileCount}
			/>
		);
	}

	return (
		<UnavailablePreview
			description={sub(
				Liferay.Language.get('the-document-x-does-not-have-a-preview'),
				fileEntry.title
			)}
		/>
	);
};

const DocumentPreviewerWrapper = ({fileEntries = []}) => {
	const [documentPage, setDocumentPage] = useState(1);
	const fileEntry = fileEntries[documentPage - 1];

	if (!fileEntries.length) {
		return <UnavailablePreview />;
	}

	return (
		<>
			<DocumentPreview fileEntry={fileEntry} />

			<OriginalDocumentTag id={documentPage} />

			<div className="align-items-center d-flex flex-column justify-content-center">
				<ClayPaginationWithBasicItems
					active={documentPage}
					ellipsisBuffer={2}
					onActiveChange={setDocumentPage}
					totalPages={fileEntries.length}
				/>

				<span>
					{sub(
						Liferay.Language.get('document-x-of-x'),
						documentPage,
						fileEntries.length
					)}
				</span>
			</div>
		</>
	);
};

export default DocumentPreviewerWrapper;
