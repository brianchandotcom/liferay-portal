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
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

import ImportModal from './ImportModal';
import ImportPreviewModal from './ImportPreviewModal';

function ImportSubmit({
	evaluateForm,
	fieldsSelections,
	fileContent,
	formDataQuerySelector,
	formImportURL,
	formIsValid,
}) {
	const [modalVisibile, setModalVisibile] = useState(false);
	const [stage, setStage] = useState('preview');

	const showPreviewModal = useCallback(() => {
		evaluateForm();

		if (formIsValid) {
			setModalVisibile(true);
		}
	}, [evaluateForm, formIsValid]);

	return (
		<span className="mr-3">
			<ClayButton
				displayType="primary"
				onClick={showPreviewModal}
				type="button"
			>
				{Liferay.Language.get('next')}
			</ClayButton>

			{modalVisibile && stage === 'preview' && (
				<ImportPreviewModal
					closeModal={() => setModalVisibile(false)}
					fieldsSelections={fieldsSelections}
					fileContent={fileContent}
					startImport={() => setStage('import')}
				/>
			)}

			{modalVisibile && stage === 'import' && (
				<ImportModal
					closeModal={() => {
						setModalVisibile(false);
						setStage('preview');
					}}
					formDataQuerySelector={formDataQuerySelector}
					formImportURL={formImportURL}
				/>
			)}
		</span>
	);
}

ImportSubmit.propTypes = {
	formDataQuerySelector: PropTypes.string.isRequired,
	formImportURL: PropTypes.string.isRequired,
};

export default ImportSubmit;
