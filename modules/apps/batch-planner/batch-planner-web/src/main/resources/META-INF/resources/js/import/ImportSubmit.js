/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
