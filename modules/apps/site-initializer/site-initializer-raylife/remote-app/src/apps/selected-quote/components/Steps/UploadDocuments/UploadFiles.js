import ClayIcon from '@clayui/icon';
import React, {useRef, useState} from 'react';

import {InfoBadge} from '~/shared/components/fragments/Badges/Info';
import DropArea from '../../drop-area';

import ViewFiles from './ViewFiles';

const UploadDocuments = ({dropAreaProps, title}) => {
	const [files, setFiles] = useState([]);

	const [showBadgeInfo, setShowBadgeInfo] = useState(false);

	const filesRef = useRef(files);

	const _setFiles = (data) => {
		filesRef.current = data;

		setFiles(data);
	};

	const onRemoveFile = ({id}) => {
		const newList = files.filter((file) => file.id !== id);

		_setFiles(newList);
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
				<div className="upload-info">
					<InfoBadge>
						<div className="info-content">
							<div className="info-description">
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

export default UploadDocuments;
