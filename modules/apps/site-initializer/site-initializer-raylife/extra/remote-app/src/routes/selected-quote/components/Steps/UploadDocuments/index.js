import {useContext, useEffect, useState} from 'react';
import {WarningBadge} from '~/common/components/fragments/Badges/Warning';

import {ApplicationPropertiesContext} from '~/common/context/ApplicationPropertiesProvider';
import {smoothScroll} from '~/common/utils/scroll';

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

const UploadDocuments = ({setExpanded, setSection, setStepChecked}) => {
	const properties = useContext(ApplicationPropertiesContext);
	const [loading, setLoading] = useState(false);

	const [sections, setSections] = useState([
		{
			error: false,
			errorMessage: 'Please upload a copy of your business license.',
			files: [],
			required: true,
			sectionId: null,
			subtitle: 'Upload a copy of your business license',
			title: 'Business License',
			type: 'document',
		},
		{
			error: false,
			files: [],
			required: false,
			sectionId: null,
			subtitle: 'Upload a copy of your additional documents.',
			title: 'Additional Documents',
			type: 'document',
		},
		{
			error: false,
			errorMessage: 'Please upload 4 photos of your building interior',
			files: [],
			required: true,
			sectionId: null,
			subtitle: 'Upload 4 photos of your building interior',
			title: 'Building Interior Photos',
			type: 'image',
		},
	]);

	const onSetError = (_section, value) => {
		setSections((sections) =>
			sections.map((section) => {
				if (section.title === _section.title) {
					return {
						...section,
						error: value,
					};
				}

				return section;
			})
		);
	};

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

	const setFilePropertyValue = (id, key, value) => {
		setSections((sections) =>
			sections.map((section) => ({
				...section,
				files: section.files.map((fileEntry) => {
					if (fileEntry.id === id) {
						fileEntry[key] = value;

						return fileEntry;
					}

					return fileEntry;
				}),
			}))
		);
	};

	const onClickConfirmUpload = async () => {
		setLoading(true);

		const quoteFolder = await createRootFolders(
			properties.applicationsfoldername
		);

		for (const section of sections) {
			onSetError(section, false);

			const sectionFolder = await createFolderIfNotExist(
				quoteFolder.id,
				section.title,
				true
			);

			if (section.required && section.files.length === 0) {
				onSetError(section, true);

				continue;
			}

			for (const fileEntry of section.files) {
				if (fileEntry.documentId) {
					continue;
				}

				try {
					const {
						data,
					} = await createDocumentInFolder(
						sectionFolder.id,
						fileEntry,
						(progress) =>
							setFilePropertyValue(
								fileEntry.id,
								'progress',
								progress
							)
					);

					setFilePropertyValue(fileEntry.id, 'documentId', data.id);
				} catch (error) {
					console.error(error);
				}
			}
		}

		setLoading(false);
		setExpanded('selectPaymentMethod');
		setExpanded('uploadDocuments');
		setStepChecked('uploadDocuments', true);
		smoothScroll();
	};

	useEffect(() => {
		setSection(sections);
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

					{section.error && (
						<div className="upload-alert">
							<WarningBadge>
								<div className="alert-content">
									<div className="alert-description">
										{section.errorMessage}
									</div>
								</div>
							</WarningBadge>
						</div>
					)}
				</div>
			))}
			<div className="upload-footer">
				<button
					className="btn btn-lg btn-primary"
					disabled={loading}
					onClick={onClickConfirmUpload}
				>
					CONFIRM UPLOADS
				</button>
			</div>
		</div>
	);
};

export default UploadDocuments;
