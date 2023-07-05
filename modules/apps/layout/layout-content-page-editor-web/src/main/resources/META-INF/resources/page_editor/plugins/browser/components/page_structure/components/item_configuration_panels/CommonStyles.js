/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	useSelectorRef,
} from '../../../../../../app/contexts/StoreContext';
import updateItemStyle from '../../../../../../app/utils/updateItemStyle';
import {FieldSet, fieldIsDisabled} from './FieldSet';

export function CommonStyles({
	className,
	commonStylesValues,
	role = COMMON_STYLES_ROLES.styles,
	item,
}) {
	const dispatch = useDispatch();
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const fragmentEntryLinksRef = useSelectorRef(
		(state) => state.fragmentEntryLinks
	);

	const permissions = useSelector((state) => state.permissions);

	let styles = filterCommonStyles({
		item,
		permissions,
		role,
		styles: config.commonStyles,
	});

	const handleValueSelect = (name, value) => {
		updateItemStyle({
			dispatch,
			itemId: item.itemId,
			selectedViewportSize,
			styleName: name,
			styleValue: value,
		});
	};

	const spacingFieldSets = styles
		.filter((fieldSet) => isSpacingFieldSet(fieldSet))
		.map((fieldSet) => ({
			...fieldSet,
			styles: fieldSet.styles.map((field) => ({
				...field,
				disabled: fieldIsDisabled(item, field),
			})),
		}));

	if (spacingFieldSets.length) {
		styles = styles.filter((fieldSet) => !isSpacingFieldSet(fieldSet));
	}

	return (
		<>
			<div
				className={classNames(
					'page-editor__common-styles panel-group-sm',
					className
				)}
			>
				{spacingFieldSets.length ? (
					<FieldSet
						fields={[
							{
								displaySize: '',
								label: '',
								name: '',
								responsive: true,
								type: 'spacing',
								typeOptions: {spacingFieldSets},
							},
						]}
						fragmentEntryLinks={fragmentEntryLinksRef.current}
						item={item}
						label={Liferay.Language.get('spacing')}
						languageId={config.defaultLanguageId}
						onValueSelect={handleValueSelect}
						selectedViewportSize={selectedViewportSize}
						values={commonStylesValues}
					/>
				) : null}

				{styles.map((fieldSet, index) => {
					return (
						<FieldSet
							description={fieldSet.description}
							fields={fieldSet.styles}
							fragmentEntryLinks={fragmentEntryLinksRef.current}
							item={item}
							key={index}
							label={fieldSet.label}
							languageId={config.defaultLanguageId}
							onValueSelect={handleValueSelect}
							selectedViewportSize={selectedViewportSize}
							values={commonStylesValues}
						/>
					);
				})}
			</div>
		</>
	);
}

CommonStyles.propTypes = {
	commonStylesValues: PropTypes.object.isRequired,
	item: PropTypes.object.isRequired,
};

function filterCommonStyles({item, permissions, role, styles}) {
	let nextStyles = styles.filter((fieldSet) =>
		role === COMMON_STYLES_ROLES.general
			? fieldSet.configurationRole === COMMON_STYLES_ROLES.general
			: fieldSet.configurationRole !== COMMON_STYLES_ROLES.general
	);

	if (item.type === LAYOUT_DATA_ITEM_TYPES.collection) {
		nextStyles = nextStyles
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

	// Filter styles based on permissions
	// For UPDATE_LAYOUT_LIMTED and UPDATE_LAYOUT_BASIC we show the frame
	// styles only in grid and container fragments.
	// For UPDATE_LAYOUT_BASIC we only show the styles tab for grid and container fragments,
	// allowing the user only to change paddings and margins

	if (
		item.type !== LAYOUT_DATA_ITEM_TYPES.container &&
		item.type !== LAYOUT_DATA_ITEM_TYPES.row &&
		!permissions.UPDATE
	) {
		nextStyles = nextStyles.map((fieldSet) => {
			return {
				...fieldSet,
				styles: fieldSet.styles.filter((style) => !isFrameStyle(style)),
			};
		});
	}
	else if (
		!permissions.UPDATE &&
		!permissions.UPDATE_LAYOUT_LIMITED &&
		role === COMMON_STYLES_ROLES.styles
	) {
		nextStyles = nextStyles.map((fieldSet) => {
			return {
				...fieldSet,
				styles: fieldSet.styles.filter((style) =>
					isSpacingStyle(style)
				),
			};
		});
	}

	return nextStyles;
}

function isSpacingFieldSet(fieldSet) {
	return fieldSet.styles.every((field) => isSpacingStyle(field));
}

function isSpacingStyle(style) {
	return style.name.startsWith('margin') || style.name.startsWith('padding');
}

function isFrameStyle(style) {
	return (
		style.name.toLowerCase().endsWith('height') ||
		style.name.toLowerCase().endsWith('width')
	);
}
