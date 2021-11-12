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
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import ExportModal from './ExportModal';

function Export({
	formExportDataQuerySelector,
	formExportURL,
	portletNamespace,
}) {
	const [disable, setDisable] = useState(true);
	const [visible, setVisible] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});

	const onButtonClick = useCallback(
		(event) => {
			event.preventDefault();
			setVisible(true);
		},
		[setVisible]
	);

	useEffect(() => {
		const externalInput = document.querySelector(
			`#${portletNamespace}internalClassName`
		);

		if (!externalInput) {
			setDisable(false);

			return;
		}

		function handleExternalInputChange() {
			setDisable(false);
		}

		externalInput.addEventListener('change', handleExternalInputChange);

		return () =>
			externalInput.removeEventListener(
				'change',
				handleExternalInputChange
			);
	}, [portletNamespace]);

	return (
		<span>
			<ClayButton
				disabled={disable}
				displayType="primary"
				id={`${portletNamespace}saveTemplate`}
				onClick={onButtonClick}
				type="submit"
			>
				{Liferay.Language.get('export')}
			</ClayButton>

			{visible && (
				<ExportModal
					closeModal={onClose}
					formDataQuerySelector={formExportDataQuerySelector}
					formSubmitURL={formExportURL}
					namespace={portletNamespace}
					observer={observer}
				/>
			)}
		</span>
	);
}

Export.propTypes = {
	formExportDataQuerySelector: PropTypes.string.isRequired,
	formExportURL: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};

export default Export;
