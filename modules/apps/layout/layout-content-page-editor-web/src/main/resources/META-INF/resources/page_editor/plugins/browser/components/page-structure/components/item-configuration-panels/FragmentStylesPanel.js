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

import PropTypes from 'prop-types';
import React, {useCallback} from 'react';

import {FRAGMENT_CONFIGURATION_ROLES} from '../../../../../../app/config/constants/fragmentConfigurationRoles';
import {VIEWPORT_SIZES} from '../../../../../../app/config/constants/viewportSizes';
import {config} from '../../../../../../app/config/index';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import getFragmentConfigurationValues from '../../../../../../app/utils/getFragmentConfigurationValues';
import {getResponsiveConfig} from '../../../../../../app/utils/getResponsiveConfig';
import updateConfigurationValue from '../../../../../../app/utils/updateConfigurationValue';
import {getLayoutDataItemPropTypes} from '../../../../../../prop-types/index';
import {CommonStyles} from './CommonStyles';
import {FieldSet} from './FieldSet';

export function FragmentStylesPanel({item}) {
	const dispatch = useDispatch();

	const fragmentEntryLink = useSelectorCallback(
		(state) => state.fragmentEntryLinks[item.config.fragmentEntryLinkId],
		[item.config.fragmentEntryLinkId]
	);

	const languageId = useSelector(selectLanguageId);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const itemConfig = getResponsiveConfig(item.config, selectedViewportSize);

	const hasCustomStyles =
		fragmentEntryLink.configuration?.fieldSets?.filter(
			(fieldSet) =>
				fieldSet.configurationRole ===
				FRAGMENT_CONFIGURATION_ROLES.style
		).length && selectedViewportSize === VIEWPORT_SIZES.desktop;

	const onCustomStyleValueSelect = useCallback(
		(name, value) => {
			updateConfigurationValue({
				configuration: fragmentEntryLink.configuration,
				dispatch,
				fragmentEntryLink,
				languageId,
				name,
				value,
			});
		},
		[dispatch, fragmentEntryLink, languageId]
	);

	return (
		<>
			{selectedViewportSize === VIEWPORT_SIZES.desktop && (
				<CustomStyles
					fragmentEntryLink={fragmentEntryLink}
					onValueSelect={onCustomStyleValueSelect}
				/>
			)}

			<CommonStyles
				className={hasCustomStyles ? 'mt-3' : null}
				commonStylesValues={itemConfig.styles}
				item={item}
			/>
		</>
	);
}

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
		<div className="page-editor__page-structure__section__custom-styles pb-0">
			<h1 className="sr-only">{Liferay.Language.get('custom-styles')}</h1>

			{fieldSets.map((fieldSet, index) => {
				return (
					<FieldSet
						fields={fieldSet.fields}
						key={index}
						label={fieldSet.label}
						languageId={config.defaultLanguageId}
						onValueSelect={onValueSelect}
						values={getFragmentConfigurationValues(
							fragmentEntryLink
						)}
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
