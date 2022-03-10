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
import {useModal} from '@clayui/modal';
import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

import ImportModal from './ImportModal';

function ImportSubmit({
	evaluateForm,
	formDataQuerySelector,
	formImportURL,
	formIsValid,
	formIsVisible,
	portletNamespace,
}) {
	const [visible, setVisible] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});
	const onButtonClick = useCallback(() => {
		evaluateForm(true);

		if (!formIsVisible) {
			openToast({
				message: Liferay.Language.get(
					'please-upload-a-file-and-select-the-required-columns-before-saving-a-template'
				),
				type: 'danger',
			});
		}

		if (formIsValid) {
			setVisible(true);
		}
	}, [evaluateForm, formIsValid, formIsVisible]);

	return (
		<span className="mr-3">
			<ClayButton
				displayType="primary"
				id={`${portletNamespace}-import-submit`}
				onClick={onButtonClick}
				type="button"
			>
				{Liferay.Language.get('import')}
			</ClayButton>

			{visible && (
				<ImportModal
					closeModal={onClose}
					formDataQuerySelector={formDataQuerySelector}
					formSubmitURL={formImportURL}
					namespace={portletNamespace}
					observer={observer}
				/>
			)}
		</span>
	);
}

ImportSubmit.propTypes = {
	formDataQuerySelector: PropTypes.string.isRequired,
	formImportURL: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};

export default ImportSubmit;
