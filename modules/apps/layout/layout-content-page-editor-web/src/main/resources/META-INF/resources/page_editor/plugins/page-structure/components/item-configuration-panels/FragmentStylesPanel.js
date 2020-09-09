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

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useCallback} from 'react';

import {FRAGMENT_CONFIGURATION_ROLES} from '../../../../app/config/constants/fragmentConfigurationRoles';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../../../app/config/constants/freemarkerFragmentEntryProcessor';
import {VIEWPORT_SIZES} from '../../../../app/config/constants/viewportSizes';
import {config} from '../../../../app/config/index';
import selectSegmentsExperienceId from '../../../../app/selectors/selectSegmentsExperienceId';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../../../app/store/index';
import updateFragmentConfiguration from '../../../../app/thunks/updateFragmentConfiguration';
import {getResponsiveConfig} from '../../../../app/utils/getResponsiveConfig';
import {getLayoutDataItemPropTypes} from '../../../../prop-types/index';
import {CommonStyles} from './CommonStyles';
import {FieldSet} from './FieldSet';

export const FragmentStylesPanel = ({item}) => {
	const dispatch = useDispatch();

	const {availableViewportSizes} = config;

	const fragmentEntryLink = useSelectorCallback(
		(state) => state.fragmentEntryLinks[item.config.fragmentEntryLinkId],
		[item.config.fragmentEntryLinkId]
	);

	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const viewportSize = availableViewportSizes[selectedViewportSize];

	const itemConfig = getResponsiveConfig(item.config, selectedViewportSize);

	const onCustomStyleValueSelect = useCallback(
		(name, value) => {
			const configurationValues = getConfigurationValues(
				fragmentEntryLink
			);

			const nextConfigurationValues = {
				...configurationValues,
				[name]: value,
			};

			dispatch(
				updateFragmentConfiguration({
					configurationValues: nextConfigurationValues,
					fragmentEntryLink,
					segmentsExperienceId,
				})
			);
		},
		[dispatch, fragmentEntryLink, segmentsExperienceId]
	);

	return (
		<>
			<p className="page-editor__row-styles-panel__viewport-label">
				<ClayIcon className="mr-2" symbol={viewportSize.icon} />
				{viewportSize.label}
			</p>

			{selectedViewportSize === VIEWPORT_SIZES.desktop && (
				<CustomStyles
					fragmentEntryLink={fragmentEntryLink}
					onValueSelect={onCustomStyleValueSelect}
				/>
			)}

			<CommonStyles commonStylesValues={itemConfig.styles} item={item} />
		</>
	);
};

FragmentStylesPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			fragmentEntryLinkId: PropTypes.string.isRequired,
		}).isRequired,
	}),
};

const CustomStyles = ({fragmentEntryLink, onValueSelect}) => {
	const fieldSets = fragmentEntryLink.configuration?.fieldSets?.filter(
		(fieldSet) =>
			fieldSet.configurationRole === FRAGMENT_CONFIGURATION_ROLES.style
	);

	return fieldSets?.length ? (
		<div className="page-editor__page-structure__section__custom-styles">
			<h1 className="sr-only">{Liferay.Language.get('custom-styles')}</h1>

			{fieldSets.map((fieldSet, index) => {
				return (
					<FieldSet
						fields={fieldSet.fields}
						key={index}
						label={fieldSet.label}
						onValueSelect={onValueSelect}
						values={getConfigurationValues(fragmentEntryLink)}
					/>
				);
			})}
		</div>
	) : null;
};

CustomStyles.propTypes = {
	fragmentEntryLink: PropTypes.object.isRequired,
	onValueSelect: PropTypes.func.isRequired,
};

function getConfigurationValues(fragmentEntryLink) {
	return {
		...fragmentEntryLink.defaultConfigurationValues,
		...fragmentEntryLink.editableValues[
			FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
		],
	};
}
