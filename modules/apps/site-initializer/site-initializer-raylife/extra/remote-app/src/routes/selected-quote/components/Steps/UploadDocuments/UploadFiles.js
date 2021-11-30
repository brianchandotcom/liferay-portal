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
			<div className="upload-file">
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
				<div className="upload-alert">
					<InfoBadge>
						<div className="alert-content">
							<div className="alert-description">
								{dropAreaProps.limitFiles}
								file upload limit reached for {title}.
							</div>

							<div
								className="closeIcon"
								onClick={() => setShowBadgeInfo(!showBadgeInfo)}
							>
								<ClayIcon symbol="times" />
							</div>
						</div>
					</InfoBadge>
				</div>
			)}
		</>
	);
};

export default UploadFiles;
