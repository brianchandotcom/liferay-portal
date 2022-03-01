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

import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
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

export function FragmentGeneralPanel({item}) {
	const dispatch = useDispatch();

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const fragmentEntryLink = useSelectorCallback(
		(state) => state.fragmentEntryLinks[item.config.fragmentEntryLinkId],
		[item.config.fragmentEntryLinkId]
	);

	const languageId = useSelector(selectLanguageId);

	const fieldSets = fragmentEntryLink.configuration?.fieldSets.filter(
		(fieldSet) =>
			config.fragmentAdvancedOptionsEnabled
				? !fieldSet.configurationRole
				: fieldSet.configurationRole !==
				  FRAGMENT_CONFIGURATION_ROLES.style
	);

	const itemConfig = getResponsiveConfig(item.config, selectedViewportSize);

	const onValueSelect = useCallback(
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
			{selectedViewportSize === VIEWPORT_SIZES.desktop &&
				fieldSets.map((fieldSet, index) => {
					return (
						<div className="mb-1" key={index}>
							<FieldSet
								fields={fieldSet.fields}
								label={fieldSet.label}
								languageId={languageId}
								onValueSelect={onValueSelect}
								values={getFragmentConfigurationValues(
									fragmentEntryLink
								)}
							/>
						</div>
					);
				})}

			<CommonStyles
				commonStylesValues={itemConfig.styles}
				item={item}
				role={COMMON_STYLES_ROLES.general}
			/>
		</>
	);
}

FragmentGeneralPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({
			fragmentEntryLinkId: PropTypes.string.isRequired,
		}).isRequired,
	}),
};
