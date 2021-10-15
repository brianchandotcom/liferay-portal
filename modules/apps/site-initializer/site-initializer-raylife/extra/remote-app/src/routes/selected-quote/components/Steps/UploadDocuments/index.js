import {useContext, useEffect, useState} from 'react';

import {ApplicationPropertiesContext} from '~/common/context/ApplicationPropertiesProvider';

import {
	createDocumentInFolder,
	createFolderIfNotExist,
	createRootFolders,
} from '../../../services/DocumentsAndMedia';

import UploadFiles from './UploadFiles';

const dropAreaProps = {
	heightContainer: '120px',
	limitFiles: 4,
	widthContainer: '100%',
};

const UploadDocuments = ({_setSection, _setStepChecked}) => {
	const properties = useContext(ApplicationPropertiesContext);

	const [sections, setSections] = useState([
		{
			files: [],
			required: true,
			subtitle: 'Upload a copy of your business license',
			title: 'Business License',
			type: 'document',
		},
		{
			files: [],
			required: false,
			subtitle: 'Upload a copy of your additional documents.',
			title: 'Additional Documents',
			type: 'document',
		},
		{
			files: [],
			required: true,
			subtitle: 'Upload 4 photos of your building interior',
			title: 'Building Interior Photos',
			type: 'image',
		},
	]);

	const onSetFiles = (_section, files) => {
		setSections((sections) =>
			sections.map((section) => {
				if (section.title === _section.title) {
					return {
						...section,
						files,
					};
				}

				return section;
			})
		);
	};

	const onProgressChange = (id, progress) => {
		setSections((sections) =>
			sections.map((section) => ({
				...section,
				files: section.files.map((fileEntry) => {
					if (fileEntry.id === id) {
						fileEntry.progress = progress;

						return fileEntry;
					}

					return fileEntry;
				}),
			}))
		);
	};

	const onClickConfirmUpload = async () => {
		const quoteFolder = await createRootFolders(
			properties.applicationsfoldername
		);

		for (const section of sections) {
			const sectionFolder = await createFolderIfNotExist(
				quoteFolder.id,
				section.title,
				true
			);

			for (const fileEntry of section.files) {
				try {
					await createDocumentInFolder(
						sectionFolder.id,
						fileEntry,
						(progress) => onProgressChange(fileEntry.id, progress)
					);
				} catch (error) {
					console.error(error);
				}
			}
		}

		_setStepChecked(true);
	};

	useEffect(() => {
		_setSection(sections);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [sections]);

	return (
		<div className="upload-container">
			{sections.map((section, index) => (
				<div className="upload-section" key={index}>
					<div className="header">
						<h3 className="title">
							{section.title}
							{section.required ? (
								<span className="required">*</span>
							) : (
								<span className="optional">(optional)</span>
							)}
						</h3>

						<span className="subtitle">{section.subtitle}</span>
					</div>

					<div className="upload-content">
						<UploadFiles
							dropAreaProps={{
								...dropAreaProps,
								type: section.type,
							}}
							files={section.files}
							setFiles={(files) => onSetFiles(section, files)}
							title={section.title}
						/>
					</div>
				</div>
			))}
			<div className="upload-footer">
				<button onClick={onClickConfirmUpload}>CONFIRM UPLOADS</button>
			</div>
		</div>
	);
};

export default UploadDocuments;
