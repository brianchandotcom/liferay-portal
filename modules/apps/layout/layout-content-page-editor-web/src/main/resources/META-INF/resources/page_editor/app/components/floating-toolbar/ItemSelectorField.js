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

import ClayForm from '@clayui/form';
import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import ItemSelector from '../../../common/components/ItemSelector';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {ConfigContext} from '../../config/index';
import InfoItemService from '../../services/InfoItemService';

export const ItemSelectorField = ({field, onValueSelect, value}) => {
	const {typeOptions = {}} = field;

	return (
		<>
			<ClayForm.Group small>
				<ItemSelector
					label={field.label}
					onItemSelect={item => {
						onValueSelect(field.name, {
							className: item.className,
							classNameId: item.classNameId,
							classPK: item.classPK,
							title: item.title
						});
					}}
					selectedItemTitle={value.title}
				/>
			</ClayForm.Group>

			{typeOptions.enableSelectTemplate && value.className && (
				<ClayForm.Group small>
					<TemplateSelector
						item={value}
						onTemplateSelect={template => {
							onValueSelect(field.name, {...value, template});
						}}
						selectedTemplate={value.template}
					/>
				</ClayForm.Group>
			)}
		</>
	);
};

ItemSelectorField.propTypes = {
	field: PropTypes.shape({
		...ConfigurationFieldPropTypes,
		typeOptions: PropTypes.shape({
			enableSelectTemplate: PropTypes.bool
		})
	}),
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.object])
};

const TemplateSelector = ({item, onTemplateSelect, selectedTemplate}) => {
	const config = useContext(ConfigContext);
	const [availableTemplates, setAvailableTemplates] = useState([]);
	const isMounted = useIsMounted();

	useEffect(() => {
		if (isMounted()) {
			InfoItemService.getAvailableTemplates({
				className: item.className,
				classPK: item.classPK,
				config
			}).then(response => {
				setAvailableTemplates(response);
			});
		}
	}, [config, isMounted, item.className, item.classPK]);

	return (
		<>
			<ClayForm.Group small>
				<label htmlFor="template">
					{Liferay.Language.get('template')}
				</label>

				<select
					className="form-control"
					id="itemSelectorTemplateSelect"
					onChange={event => {
						onTemplateSelect(
							event.target.options[event.target.selectedIndex]
								.dataset
						);
					}}
				>
					{availableTemplates.map(entry => {
						if (entry.templates) {
							return (
								<optgroup key={entry.label} label={entry.label}>
									{entry.templates.map(template => (
										<option
											data-info-item-renderer-key={
												template.infoItemRendererKey
											}
											data-template-key={
												template.templateKey
											}
											key={template.label}
											selected={
												selectedTemplate &&
												selectedTemplate.infoItemRendererKey ===
													template.infoItemRendererKey &&
												(!selectedTemplate.templateKey ||
													(selectedTemplate.templateKey &&
														selectedTemplate.templateKey ===
															template.templateKey))
											}
										>
											{template.label}
										</option>
									))}
								</optgroup>
							);
						}
						else {
							return (
								<option
									data-info-item-renderer-key={
										entry.infoItemRendererKey
									}
									key={entry.label}
									selected={
										selectedTemplate &&
										selectedTemplate.infoItemRendererKey ===
											entry.infoItemRendererKey
									}
								>
									{entry.label}
								</option>
							);
						}
					})}
				</select>
			</ClayForm.Group>
		</>
	);
};

TemplateSelector.propTypes = {
	item: PropTypes.shape(ConfigurationFieldPropTypes),
	onTemplateSelect: PropTypes.func.isRequired,
	selectedTemplate: PropTypes.shape({
		infoItemRendererKey: PropTypes.string,
		templateKey: PropTypes.string
	})
};
