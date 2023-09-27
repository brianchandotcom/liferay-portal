/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import {InfoBadge} from '../../../../../common/components/fragments/Badges/Info';
import {removeDocumentById} from '../../../services/DocumentsAndMedia';
import PreviewDocuments from './PreviewDocuments';

const UploadFiles = ({dropAreaProps, files, setFiles, title}) => {
	const [showBadgeInfo, setShowBadgeInfo] = useState(false);

	const onRemoveFile = ({documentId, id}) => {
		try {
			if (documentId) {
				removeDocumentById(documentId);
			}

			const filteredFiles = files.filter((file) => file.id !== id);

			setFiles(filteredFiles);
		}
		catch (error) {
			console.error(error);
		}
	};

	return (
		<>
			<PreviewDocuments
				dropAreaProps={dropAreaProps}
				files={files}
				onRemoveFile={onRemoveFile}
				setFiles={setFiles}
				setShowBadgeInfo={setShowBadgeInfo}
				type={dropAreaProps.type}
			/>

			{showBadgeInfo && (
				<div className="c-mt-3 upload-alert">
					<InfoBadge>
						<div className="alert-content align-items-center d-flex justify-content-between w-100">
							<div className="alert-description font-weight-normal text-paragraph">
								{`${dropAreaProps.limitFiles} files upload limit reached for ${title}.`}
							</div>

							<div
								className="close-icon mr-4"
								onClick={() => setShowBadgeInfo(!showBadgeInfo)}
							>
								<ClayIcon
									className="flex-shrink-0"
									symbol="times"
								/>
							</div>
						</div>
					</InfoBadge>
				</div>
			)}
		</>
	);
};

export default UploadFiles;
