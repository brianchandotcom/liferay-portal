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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../../app/config/constants/layoutDataItemTypes';
import {config} from '../../../../../../app/config/index';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectSegmentsExperienceId from '../../../../../../app/selectors/selectSegmentsExperienceId';
import updateItemStyle from '../../../../../../app/utils/updateItemStyle';
import {FieldSet} from './FieldSet';

export function CommonStyles({
	className,
	commonStylesValues,
	role = COMMON_STYLES_ROLES.styles,
	item,
}) {
	const {commonStyles} = config;
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	let styles = commonStyles;

	styles = styles.filter((fieldSet) =>
		role === COMMON_STYLES_ROLES.general
			? fieldSet.configurationRole === COMMON_STYLES_ROLES.general
			: fieldSet.configurationRole !== COMMON_STYLES_ROLES.general
	);

	if (item.type === LAYOUT_DATA_ITEM_TYPES.collection) {
		styles = styles
			.filter((fieldSet) =>
				fieldSet.styles.find((field) => field.name === 'display')
			)
			.map((fieldSet) => {
				return {
					...fieldSet,
					styles: fieldSet.styles.filter(
						(field) => field.name === 'display'
					),
				};
			});
	}

	const handleValueSelect = (name, value) => {
		updateItemStyle({
			dispatch,
			itemId: item.itemId,
			segmentsExperienceId,
			selectedViewportSize,
			styleName: name,
			styleValue: value,
		});
	};

	let spacingField = null;

	if (config['feature.flag.LPS-141410']) {
		spacingField = styles.find((fieldSet) => isSpacingFieldSet(fieldSet))
			?.styles[0];

		if (spacingField) {
			styles = styles.filter((fieldSet) => !isSpacingFieldSet(fieldSet));
		}
	}

	return (
		<>
			<div
				className={classNames('page-editor__common-styles', className)}
			>
				{spacingField ? (
					<FieldSet
						fields={[
							{
								...spacingField,
								displaySize: '',
								label: '',
								name: '',
								type: 'spacing',
							},
						]}
						item={item}
						label={Liferay.Language.get('spacing')}
						languageId={config.defaultLanguageId}
						onValueSelect={handleValueSelect}
						values={commonStylesValues}
					/>
				) : null}

				{styles.map((fieldSet, index) => {
					return (
						<FieldSet
							fields={fieldSet.styles}
							item={item}
							key={index}
							label={fieldSet.label}
							languageId={config.defaultLanguageId}
							onValueSelect={handleValueSelect}
							values={commonStylesValues}
						/>
					);
				})}
			</div>
		</>
	);
}

function isSpacingFieldSet(fieldSet) {
	return (
		fieldSet.styles.every((field) => field.name.startsWith('margin')) ||
		fieldSet.styles.every((field) => field.name.startsWith('padding'))
	);
}

CommonStyles.propTypes = {
	commonStylesValues: PropTypes.object.isRequired,
	item: PropTypes.object.isRequired,
};
