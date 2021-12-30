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
import React, {useCallback, useEffect, useRef, useState} from 'react';

import SaveTemplateModal from './SaveTemplateModal';
import {
	SCHEMA_SELECTED_EVENT,
	TEMPLATE_SELECTED_EVENT,
	TEMPLATE_SOILED,
} from './constants';

function SaveTemplate({
	forceDisable,
	formSaveAsTemplateDataQuerySelector,
	formSaveAsTemplateURL,
	portletNamespace,
}) {
	const [disable, setDisable] = useState(true);
	const {observer, onClose} = useModal({
		onClose: () => setVisible(false),
	});
	const useTemplateMappingRef = useRef();

	const [visible, setVisible] = useState(false);
	const onButtonClick = useCallback(() => {
		setVisible(true);
	}, [setVisible]);

	useEffect(() => {
		function handleSchemaChange({schema}) {
			if (schema && !useTemplateMappingRef.current) {
				setDisable(false);
			}
			else if (!schema) {
				setDisable(true);
			}
		}

		function handleTemplateSelection({template}) {
			setDisable(!!template);
			useTemplateMappingRef.current = !!template;
		}

		function handleTemplateSoiled() {
			useTemplateMappingRef.current = false;
			setDisable(false);
		}

		Liferay.on(SCHEMA_SELECTED_EVENT, handleSchemaChange);
		Liferay.on(TEMPLATE_SELECTED_EVENT, handleTemplateSelection);
		Liferay.on(TEMPLATE_SOILED, handleTemplateSoiled);

		return () => {
			Liferay.detach(SCHEMA_SELECTED_EVENT, handleSchemaChange);
			Liferay.detach(TEMPLATE_SELECTED_EVENT, handleTemplateSelection);
			Liferay.detach(TEMPLATE_SOILED, handleTemplateSoiled);
		};
	}, [portletNamespace]);

	return (
		<span className="mr-3">
			<ClayButton
				disabled={disable || forceDisable}
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

SaveTemplate.propTypes = {
	forceDisable: PropTypes.bool,
	formSaveAsTemplateDataQuerySelector: PropTypes.string.isRequired,
	formSaveAsTemplateURL: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};

export default SaveTemplate;
