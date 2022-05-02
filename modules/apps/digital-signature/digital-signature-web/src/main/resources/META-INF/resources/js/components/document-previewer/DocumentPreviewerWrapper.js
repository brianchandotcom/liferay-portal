/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayPaginationWithBasicItems} from '@clayui/pagination';
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
			description={Liferay.Util.sub(
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
					{Liferay.Util.sub(
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
