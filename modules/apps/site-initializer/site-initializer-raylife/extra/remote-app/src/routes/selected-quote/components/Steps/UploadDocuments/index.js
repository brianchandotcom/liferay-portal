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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useContext, useState} from 'react';
import {WarningBadge} from '../../../../../common/components/fragments/Badges/Warning';
import {ApplicationPropertiesContext} from '../../../../../common/context/ApplicationPropertiesProvider';
import {smoothScroll} from '../../../../../common/utils/scroll';
import {
	ACTIONS,
	SelectedQuoteContext,
} from '../../../context/SelectedQuoteContextProvider';
import {
	createDocumentInFolder,
	createFolderIfNotExist,
	createRootFolders,
} from '../../../services/DocumentsAndMedia';

import UploadFiles from './UploadFiles';

import {sectionsHasError} from './utils/upload';

const dropAreaProps = {
	heightContainer: '120px',
	limitFiles: 4,
	widthContainer: '100%',
};

const UploadDocuments = () => {
	const properties = useContext(ApplicationPropertiesContext);
	const [{sections}, dispatch] = useContext(SelectedQuoteContext);
	const [loading, setLoading] = useState(false);

	const setSections = (newSections) => {
		dispatch({
			payload: newSections,
			type: ACTIONS.SET_SECTIONS,
		});
	};

	const onSetError = (_section, value) => {
		setSections(
			sections.map((section) => {
				if (section.title === _section.title) {
					section.error = value;
				}

				return section;
			})
		);
	};

	const onSetFiles = (_section, files) => {
		setSections(
			sections.map((section) => {
				if (section.title === _section.title) {
					section.files = files;
					section.error = false;
				}

				return section;
			})
		);
	};

	const setFilePropertyValue = (id, key, value) => {
		setSections(
			sections.map((section) => ({
				...section,
				files: section.files.map((fileEntry) => {
					if (fileEntry.id === id) {
						fileEntry[key] = value;
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
				}
				catch (error) {
					console.error(error);
				}
			}
		}

		setLoading(false);

		if (!sectionsHasError(sections)) {
			dispatch({
				payload: {panelKey: 'selectPaymentMethod', value: true},
				type: ACTIONS.SET_EXPANDED,
			});
			dispatch({
				payload: {panelKey: 'uploadDocuments', value: false},
				type: ACTIONS.SET_EXPANDED,
			});
			dispatch({
				payload: {panelKey: 'uploadDocuments', value: true},
				type: ACTIONS.SET_STEP_CHECKED,
			});
		}

		smoothScroll();
	};

	return (
		<div className="d-flex flex-column flex-wrap upload-container">
			{sections.map((section, index) => (
				<section
					className="c-pl-4 c-pr-0 c-pt-6 upload-section"
					key={index}
				>
					<header className="c-mb-3 header">
						<h5 className="c-mb-1 font-weight-bolder h5 upload-title">
							{section.title}

							{section.required ? (
								<span className="reference-mark">
									<ClayIcon
										className="text-warning upload-asterisk"
										symbol="asterisk"
									/>
								</span>
							) : (
								<span className="text-neutral-8">
									{` (optional)`}
								</span>
							)}
						</h5>

						<span className="font-weight-normal text-neutral-8 text-paragraph">
							{section.subtitle}
						</span>
					</header>

					<div className="d-flex flex-column upload-content">
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
						<div className="c-mt-3 upload-alert">
							<WarningBadge>
								<div className="alert-content">
									<div className="alert-description">
										{section.errorMessage}
									</div>
								</div>
							</WarningBadge>
						</div>
					)}
				</section>
			))}

			<div className="d-flex justify-content-end mt-6">
				<ClayButton
					className="font-weight-bolder rounded-sm text-neutral-0 text-paragraph"
					disabled={loading}
					displayType="primary"
					onClick={onClickConfirmUpload}
				>
					CONFIRM UPLOADS
				</ClayButton>
			</div>
		</div>
	);
};

export default UploadDocuments;
