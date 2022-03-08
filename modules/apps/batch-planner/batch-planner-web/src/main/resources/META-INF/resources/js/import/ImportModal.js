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

import ClayModal from '@clayui/modal';
import PropTypes from 'prop-types';
import React from 'react';

import ImportPreviewModal from './ImportPreviewModal';
import ImportProcessModal from './ImportProcessModal';

const ImportModal = ({
	closeModal,
	dbFields,
	fieldsSelections,
	fileContent,
	fileFields,
	formDataQuerySelector,
	formSubmitURL,
	observer,
	portletNamespace,
	setFileContent,
	setStartImport,
	startImport,
}) => {
	const handleEditCell = (newValue, cellIndex, rowIndex) => {
		const newRow = fileContent[rowIndex];
		const newFileContent = fileContent;
		Object.entries(newRow).forEach(([key], index) => {
			if (cellIndex === index) {
				newRow[key] = newValue;
			}
		});

		newFileContent.splice(rowIndex, 1, newRow);
		setFileContent(newFileContent);
	};

	return (
		<ClayModal observer={observer} size={startImport ? 'lg' : 'md'}>
			{!startImport && (
				<ImportPreviewModal
					closeModal={closeModal}
					dbFields={dbFields}
					fieldsSelections={fieldsSelections}
					fileContent={fileContent}
					fileFields={fileFields}
					handleEditCell={handleEditCell}
					setStartImport={setStartImport}
				/>
			)}

			{startImport && (
				<ImportProcessModal
					closeModal={closeModal}
					formDataQuerySelector={formDataQuerySelector}
					formSubmitURL={formSubmitURL}
					namespace={portletNamespace}
					observer={observer}
				/>
			)}
		</ClayModal>
	);
};

ImportModal.propTypes = {
	closeModal: PropTypes.func.isRequired,
	formSubmitURL: PropTypes.string.isRequired,
	observer: PropTypes.object,
};

export default ImportModal;
