/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import LiferayFile from '../../../common/interfaces/liferayFile';
import uploadDocument from './uploadDocument';

const uploadDocuments = async (
	documents: LiferayFile[],
	claimParentFolderId: number
) => {
	const liferayFiles: LiferayFile[] & number[] = [];

	for (const document of documents) {
		if (!document.documentId) {
			document.documentId = await uploadDocument(
				document,
				claimParentFolderId
			);
		}
		liferayFiles.push(document);
	}

	return liferayFiles;
};

export default uploadDocuments;
