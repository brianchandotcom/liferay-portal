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
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import useSetRef from '../../../core/hooks/useSetRef';
import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../../config/constants/freemarkerFragmentEntryProcessor';
import {
	useHoveredItemId,
	useHoveredItemType,
} from '../../contexts/ControlsContext';
import {useSelector} from '../../contexts/StoreContext';
import {getFrontendTokenValue} from '../../utils/getFrontendTokenValue';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import {isValidSpacingOption} from '../../utils/isValidSpacingOption';
import Topper from '../Topper';
import FragmentContent from '../fragment-content/FragmentContent';
import FragmentContentInteractionsFilter from '../fragment-content/FragmentContentInteractionsFilter';
import FragmentContentProcessor from '../fragment-content/FragmentContentProcessor';
import getAllPortals from './getAllPortals';
import isHovered from './isHovered';

const FIELD_TYPES = ['itemSelector', 'collectionSelector'];

const FragmentWithControls = React.forwardRef(({item}, ref) => {
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const hoveredItemType = useHoveredItemType();
	const hoveredItemId = useHoveredItemId();
	const [hovered, setHovered] = useState(false);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const getPortals = useCallback((element) => getAllPortals(element), []);
	const itemConfig = getResponsiveConfig(item.config, selectedViewportSize);
	const [setRef, itemElement] = useSetRef(ref);

	const editableValues = useMemo(() => {
		const fieldNames = [];
		const fragment = fragmentEntryLinks[item.config.fragmentEntryLinkId];

		if (fragment) {
			fragment.configuration?.fieldSets?.forEach((fieldSet) => {
				fieldSet.fields.forEach((field) => {
					if (FIELD_TYPES.includes(field.type)) {
						fieldNames.push(field.name);
					}
				});
			});

			const filteredFieldNames = fieldNames.filter(
				(fieldName) =>
					fragment.editableValues[
						FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
					][fieldName].classPK
			);

			return filteredFieldNames.map(
				(fieldName) =>
					fragment.editableValues[
						FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
					][fieldName] || {}
			);
		}
	}, [item, fragmentEntryLinks]);

	useEffect(() => {
		if (editableValues.length) {
			const someEditableIsHovered = editableValues.some((editableValue) =>
				isHovered({
					editableValue,
					hoveredItemId,
					hoveredItemType,
				})
			);

			setHovered(someEditableIsHovered);
		}
	}, [hoveredItemType, hoveredItemId, editableValues]);

	const {
		marginBottom,
		marginLeft,
		marginRight,
		marginTop,
		maxWidth,
		minWidth,
		shadow,
		width,
	} = itemConfig.styles;

	const style = {};

	style.boxShadow = getFrontendTokenValue(shadow);
	style.maxWidth = maxWidth;
	style.minWidth = minWidth;
	style.width = width;

	return (
		<Topper
			className={classNames({
				[`mb-${marginBottom}`]: isValidSpacingOption(marginBottom),
				[`ml-${marginLeft}`]: isValidSpacingOption(marginLeft),
				[`mr-${marginRight}`]: isValidSpacingOption(marginRight),
				[`mt-${marginTop}`]: isValidSpacingOption(marginTop),
				'page-editor__topper--hovered': hovered,
			})}
			item={item}
			itemElement={itemElement}
			style={style}
		>
			<FragmentContentInteractionsFilter
				fragmentEntryLinkId={item.config.fragmentEntryLinkId}
				itemId={item.itemId}
			>
				<FragmentContent
					elementRef={setRef}
					fragmentEntryLinkId={item.config.fragmentEntryLinkId}
					getPortals={getPortals}
					item={item}
					withinTopper
				/>

				<FragmentContentProcessor
					fragmentEntryLinkId={item.config.fragmentEntryLinkId}
					itemId={item.itemId}
				/>
			</FragmentContentInteractionsFilter>
		</Topper>
	);
});

FragmentWithControls.displayName = 'FragmentWithControls';

FragmentWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default FragmentWithControls;
