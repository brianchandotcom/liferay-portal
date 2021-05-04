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
import {useIsMounted} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import ItemSelector from '../../../common/components/ItemSelector';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {CollectionItemContext} from '../../contexts/CollectionItemContext';
import {useDispatch} from '../../contexts/StoreContext';
import InfoItemService from '../../services/InfoItemService';
import itemSelectorValueToInfoItem from '../../utils/item-selector-value/itemSelectorValueToInfoItem';
import {useId} from '../../utils/useId';

export const ItemSelectorField = ({field, onValueSelect, value}) => {
	const collectionItemContext = useContext(CollectionItemContext);

	const {typeOptions = {}} = field;

	const collectionItem = collectionItemContext.collectionItem;

	const isWithinCollection = collectionItem !== null;

	const className = isWithinCollection
		? collectionItem.className
		: value.className;

	const classPK = isWithinCollection ? collectionItem.classPK : value.classPK;

	return (
		<>
			<ItemSelector
				itemSelectorURL={typeOptions.infoItemSelectorURL}
				label={field.label}
				onItemSelect={(item) => {
					onValueSelect(field.name, item);
				}}
				selectedItemTitle={
					isWithinCollection
						? collectionItem.title ||
						  Liferay.Language.get('collection-item')
						: value.title
				}
				showAddButton={!isWithinCollection}
				transformValueCallback={itemSelectorValueToInfoItem}
			/>

			{typeOptions.enableSelectTemplate && className && (
				<ClayForm.Group small>
					<TemplateSelector
						className={className}
						classPK={classPK}
						onTemplateSelect={(template) => {
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
			enableSelectTemplate: PropTypes.bool,
		}),
	}),
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
};

const TemplateSelector = ({
	className,
	classPK,
	onTemplateSelect,
	selectedTemplate,
}) => {
	const dispatch = useDispatch();
	const [availableTemplates, setAvailableTemplates] = useState([]);
	const isMounted = useIsMounted();
	const itemSelectorTemplateSelectId = useId();

	useEffect(() => {
		if (isMounted()) {
			InfoItemService.getAvailableTemplates({
				className,
				classPK,
				onNetworkStatus: dispatch,
			}).then((response) => {
				setAvailableTemplates(response);
			});
		}
	}, [className, classPK, dispatch, isMounted]);

	return (
		<>
			<ClayForm.Group small>
				<label htmlFor={itemSelectorTemplateSelectId}>
					{Liferay.Language.get('template')}
				</label>

				<select
					className="form-control"
					id={itemSelectorTemplateSelectId}
					onChange={(event) => {
						onTemplateSelect(
							event.target.options[event.target.selectedIndex]
								.dataset
						);
					}}
				>
					{availableTemplates.map((entry) => {
						if (entry.templates) {
							return (
								<optgroup key={entry.label} label={entry.label}>
									{entry.templates.map((template) => (
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
	className: PropTypes.string.isRequired,
	classPK: PropTypes.string.isRequired,
	onTemplateSelect: PropTypes.func.isRequired,
	selectedTemplate: PropTypes.shape({
		infoItemRendererKey: PropTypes.string,
		templateKey: PropTypes.string,
	}),
};
