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
import React from 'react';

export function ListItemStyleSelector({
	availableListItemStyles,
	collectionListItemStyleId,
	handleConfigurationChanged,
	item,
}) {
	const handleCollectionListItemStyleChanged = ({target}) => {
		const options = target.options;

		handleConfigurationChanged({
			listItemStyle: options[target.selectedIndex].dataset.key,
			templateKey: options[target.selectedIndex].dataset.templateKey,
		});
	};

	return (
		<ClayForm.Group small>
			<label htmlFor={collectionListItemStyleId}>
				{Liferay.Language.get('list-item-style')}
			</label>

			<ClaySelect
				aria-label={Liferay.Language.get('list-item-style')}
				id={collectionListItemStyleId}
				onChange={handleCollectionListItemStyleChanged}
			>
				<ListItemStylesOptions
					item={item}
					listItemStyles={availableListItemStyles}
				/>
			</ClaySelect>
		</ClayForm.Group>
	);
}

ListItemStyleSelector.propTypes = {
	availableListItemStyles: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
		})
	),
	collectionListItemStyleId: PropTypes.string.isRequired,
	handleConfigurationChanged: PropTypes.func.isRequired,
	item: PropTypes.object.isRequired,
};

function ListItemStylesOptions({item, listItemStyles}) {
	return listItemStyles.map((listItemStyle) =>
		listItemStyle.templates ? (
			<ClaySelect.OptGroup
				key={listItemStyle.label}
				label={listItemStyle.label}
			>
				{listItemStyle.templates.map((template) => (
					<ClaySelect.Option
						data-key={template.key}
						data-template-key={template.templateKey}
						key={`${template.key}_${template.templateKey}`}
						label={template.label}
						selected={
							item.config.listItemStyle === template.key &&
							(!item.config.templateKey ||
								item.config.templateKey ===
									template.templateKey)
						}
					/>
				))}
			</ClaySelect.OptGroup>
		) : (
			<ClaySelect.Option
				data-key={listItemStyle.key}
				key={listItemStyle.label}
				label={listItemStyle.label}
				selected={item.config.listItemStyle === listItemStyle.key}
			/>
		)
	);
}

ListItemStylesOptions.propTypes = {
	item: PropTypes.object.isRequired,
	listItemStyles: PropTypes.array.isRequired,
};
