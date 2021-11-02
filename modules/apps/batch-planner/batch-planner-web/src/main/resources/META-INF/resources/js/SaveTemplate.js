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
import React, {useCallback, useEffect, useState} from 'react';

import SaveTemplateModal from './SaveTemplateModal';

function SaveTemplate({
	formSaveAsTemplateDataQuerySelector,
	formSaveAsTemplateURL,
	namespace,
}) {
	const [disable, setDisable] = useState(true);
	const [visible, setVisible] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});
	const onButtonClick = useCallback(() => {
		setVisible(true);
	}, [setVisible]);

	useEffect(() => {
		const externalInput = document.querySelector(
			`#${namespace}internalClassName`
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
	}, []);

	return (
		<span className="mr-3">
			<ClayButton
				disabled={disable}
				displayType="secondary"
				id={`${namespace}saveTemplate`}
				onClick={onButtonClick}
				type="button"
			>
				{Liferay.Language.get('save-as-template')}
			</ClayButton>

			{visible && (
				<SaveTemplateModal
					closeModal={onClose}
					formDataQuerySelector={formSaveAsTemplateDataQuerySelector}
					formSubmitURL={formSaveAsTemplateURL}
					namespace={namespace}
					observer={observer}
				/>
			)}
		</span>
	);
}

export default SaveTemplate;
