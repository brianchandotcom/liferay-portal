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
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {
	HEADLESS_ENDPOINT_POLICY_NAME,
	NULL_TEMPLATE_VALUE,
	TEMPLATE_CREATED_EVENT,
	TEMPLATE_SELECTED_EVENT,
	TEMPLATE_SOILED_EVENT,
} from './constants';
import {fetchTemplateDetails} from './utilities/dataFetch';

const TemplateSelect = ({
	initialTemplate,
	initialTemplateOptions = [],
	portletNamespace,
}) => {
	const [templateOptions, setTemplateOptions] = useState(
		initialTemplateOptions
	);
	const [selectedTemplateId, setSelectedTemplateId] = useState(() => {
		const id = initialTemplateOptions.find((option) => option.selected)
			?.value;

		return id || NULL_TEMPLATE_VALUE;
	});

	useEffect(() => {
		function handleTemplateCreated({template}) {
			setSelectedTemplateId(template.batchPlannerPlanId);

			setTemplateOptions((options) => [
				{label: template.name, value: template.batchPlannerPlanId},
				...options,
			]);
		}

		Liferay.on(TEMPLATE_CREATED_EVENT, handleTemplateCreated);

		return () => Liferay.detach(TEMPLATE_CREATED_EVENT);
	}, []);

	useEffect(() => {
		if (initialTemplate) {
			Liferay.fire(TEMPLATE_SELECTED_EVENT, {
				template: {
					...initialTemplate,
					mappings:
						initialTemplate.mappings ||
						initialTemplate.mapping ||
						{},
				},
			});
		}
	}, [initialTemplate]);

	useEffect(() => {
		function handleTemplateDirty() {
			setSelectedTemplateId(NULL_TEMPLATE_VALUE);
		}

		Liferay.on(TEMPLATE_SOILED_EVENT, handleTemplateDirty);

		return () => {
			Liferay.detach(TEMPLATE_SOILED_EVENT, handleTemplateDirty);
		};
	}, []);

	const onChange = async (event) => {
		const newTemplateId = event.target.value;

		setSelectedTemplateId(newTemplateId);

		if (!newTemplateId) {
			Liferay.fire(TEMPLATE_SELECTED_EVENT, {
				template: null,
			});

			return;
		}

		const templateDetails = await fetchTemplateDetails(newTemplateId);

		const headlessEndpointPolicy = templateDetails.policies.find(
			(policy) => policy.name === HEADLESS_ENDPOINT_POLICY_NAME
		);

		Liferay.fire(TEMPLATE_SELECTED_EVENT, {
			template: {
				externalType: templateDetails.externalType,
				headlessEndpoint: headlessEndpointPolicy?.value,
				internalClassName: templateDetails.internalClassName,
				mappings: templateDetails.mappings.reduce(
					(mappings, mapping) => ({
						...mappings,
						[mapping.internalFieldName]: mapping.externalFieldName,
					}),
					{}
				),
			},
		});
	};

	const selectId = `${portletNamespace}templateName`;

	return (
		<ClayForm.Group>
			<label htmlFor={selectId}>{Liferay.Language.get('template')}</label>

			<ClaySelect
				id={selectId}
				name={selectId}
				onChange={onChange}
				value={selectedTemplateId || ''}
			>
				<ClaySelect.Option value={NULL_TEMPLATE_VALUE} />

				{templateOptions.map((option) => (
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
	selectedTemplateClassName: PropTypes.string,
	selectedTemplateHeadlessEndpoint: PropTypes.string,
	selectedTemplateMapping: PropTypes.object,
	templateOptions: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.string,
			value: PropTypes.number,
		})
	),
};

export default TemplateSelect;
