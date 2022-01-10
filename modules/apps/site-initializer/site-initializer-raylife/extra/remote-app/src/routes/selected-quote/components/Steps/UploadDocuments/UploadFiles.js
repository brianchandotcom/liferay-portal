/* eslint-disable @liferay/empty-line-between-elements */
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import {InfoBadge} from '../../../../../common/components/fragments/Badges/Info';
import {removeDocumentById} from '../../../services/DocumentsAndMedia';
import DropArea from '../../DropArea';
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
			<div className="d-flex upload-file">
				<PreviewDocuments
					files={files}
					onRemoveFile={onRemoveFile}
					type={dropAreaProps.type}
				/>

				<DropArea
					dropAreaProps={dropAreaProps}
					files={files}
					setFiles={setFiles}
					setShowBadgeInfo={setShowBadgeInfo}
				/>
			</div>

			{showBadgeInfo && (
				<div className="c-mt-3 upload-alert">
					<InfoBadge>
						<div className="alert-content align-items-center d-flex justify-content-between w-100">
							<div className="alert-description font-weight-normal text-paragraph">
								{dropAreaProps.limitFiles} files upload limit
								reached for {title}.
							</div>

							<div
								className="align-items-center c-mr-4 close-icon d-flex justify-content-center"
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
