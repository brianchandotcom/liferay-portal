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

import ClayForm, {ClaySelect} from '@clayui/form';
import {fetch, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {HEADERS_BATCH_PLANNER_URL, TEMPLATE_SELECTED_EVENT} from './constants';

const TemplateSelect = ({
	portletNamespace,
	selectedTemplateClassName,
	selectedTemplateMapping,
	templatesOptions,
}) => {
	const [selectedTemplateId, setTemplate] = useState(
		templatesOptions.find((option) => option.selected)?.value
	);

	useEffect(() => {
		if (selectedTemplateMapping && selectedTemplateClassName) {
			Liferay.fire(TEMPLATE_SELECTED_EVENT, {
				templateClassName: selectedTemplateClassName,
				templateMapping: selectedTemplateMapping,
			});
		}
	}, [selectedTemplateClassName, selectedTemplateMapping]);

	const onChange = (event) => {
		const newTemplateId = event.target.value;
		setTemplate(newTemplateId);
		fireTemplateSelectionEvent(newTemplateId);
	};

	const selectId = `${portletNamespace}template`;

	return (
		<ClayForm.Group className="form-group-sm">
			<label htmlFor={selectId}>{Liferay.Language.get('template')}</label>

			<ClaySelect
				id={selectId}
				name={selectId}
				onChange={onChange}
				value={selectedTemplateId}
			>
				<ClaySelect.Option key={0} value={0} />

				{templatesOptions.map((option) => (
					<ClaySelect.Option
						key={option.value}
						label={option.label}
						value={option.value}
					/>
				))}
			</ClaySelect>
		</ClayForm.Group>
	);
};

TemplateSelect.propTypes = {
	portletNamespace: PropTypes.string.isRequired,
	selectedTemplate: PropTypes.object,
	selectedTemplateClassName: PropTypes.string,
	templatesOptions: PropTypes.arrayOf(PropTypes.object),
};

function getMappingFromTemplate(template) {
	const {mappings} = template;

	return mappings.reduce((accumulator, map) => {
		accumulator[map.internalFieldName] = map.externalFieldName;

		return accumulator;
	}, {});
}

async function fireTemplateSelectionEvent(templateId) {
	if (templateId === '0') {
		return Liferay.fire(TEMPLATE_SELECTED_EVENT, {
			templateClassName: null,
			templateMapping: null,
		});
	}

	const request = await fetch(
		`${HEADERS_BATCH_PLANNER_URL}/plans/${templateId}`
	);

	if (!request.ok) {
		return openToast({
			message: Liferay.Language.get('your-request-has-failed'),
			type: 'danger',
		});
	}

	const template = await request.json();

	Liferay.fire(TEMPLATE_SELECTED_EVENT, {
		templateClassName: template.internalClassName,
		templateMapping: getMappingFromTemplate(template),
	});
}

export default TemplateSelect;
