/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
