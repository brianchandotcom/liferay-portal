import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import {InfoBadge} from '~/common/components/fragments/Badges/Info';
import {removeDocumentById} from '~/routes/selected-quote/services/DocumentsAndMedia';

import DropArea from '../../drop-area';

import ViewFiles from './ViewFiles';

const UploadFiles = ({dropAreaProps, files, setFiles, title}) => {
	const [showBadgeInfo, setShowBadgeInfo] = useState(false);

	const onRemoveFile = ({documentId, id}) => {
		try {
			if (documentId) {
				removeDocumentById(documentId);
			}

			const newList = files.filter((file) => file.id !== id);

			setFiles(newList);
		} catch (error) {
			console.error(error);
		}
	};

	return (
		<>
			<div className="upload-file">
				<ViewFiles
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
								{dropAreaProps.limitFiles} file upload limit
								reached for {title}.
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
