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
import createDocumentFolderDocument from '../../../common/services/liferay/headless-delivery/createDocumentFolderDocument';
import renameFileKeepingExtention from './RenameFile';
import generateRandomNumber from './generateRandomNumber';

const uploadDocument = async (
	document: LiferayFile | File,
	claimParentFolderId: number
) => {
	const allContentDocumentRenamed = renameFileKeepingExtention(
		document,
		`${document.name}#${generateRandomNumber()}`
	);

	if (allContentDocumentRenamed) {
		const dtoAllContentDocument = await createDocumentFolderDocument(
			claimParentFolderId,
			allContentDocumentRenamed
		);

		return dtoAllContentDocument;
	}
};

export default uploadDocument;
