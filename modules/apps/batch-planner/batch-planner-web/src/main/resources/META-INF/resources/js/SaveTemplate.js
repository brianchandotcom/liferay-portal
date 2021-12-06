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
import {SCHEMA_SELECTED_EVENT} from './constants';

function SaveTemplate({
	formSaveAsTemplateDataQuerySelector,
	formSaveAsTemplateURL,
	portletNamespace,
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
		function handleSchemaChange({schema}) {
			if (schema) {
				setDisable(false);
			}
		}
		Liferay.on(SCHEMA_SELECTED_EVENT, handleSchemaChange);

		return () => Liferay.detach(SCHEMA_SELECTED_EVENT, handleSchemaChange);
	}, [portletNamespace]);

	return (
		<span className="mr-3">
			<ClayButton
				disabled={disable}
				displayType="secondary"
				id={`${portletNamespace}saveTemplate`}
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
					namespace={portletNamespace}
					observer={observer}
				/>
			)}
		</span>
	);
}

export default SaveTemplate;
