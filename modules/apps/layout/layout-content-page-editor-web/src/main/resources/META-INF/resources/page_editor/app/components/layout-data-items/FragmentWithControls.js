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
import {config} from '../../config/index';
import {
	useHoveredItemId,
	useHoveredItemType,
} from '../../contexts/ControlsContext';
import {useSelector, useSelectorCallback} from '../../contexts/StoreContext';
import {getFrontendTokenValue} from '../../utils/getFrontendTokenValue';
import getLayoutDataItemTopperUniqueClassName from '../../utils/getLayoutDataItemTopperUniqueClassName';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import hasInnerCommonStyles from '../../utils/hasInnerCustomStyles';
import {isValidSpacingOption} from '../../utils/isValidSpacingOption';
import FragmentContent from '../fragment-content/FragmentContent';
import Topper from '../topper/Topper';
import getAllPortals from './getAllPortals';
import isHovered from './isHovered';

const FIELD_TYPES = ['itemSelector', 'collectionSelector'];

const FragmentWithControls = React.forwardRef(({item}, ref) => {
	const [hovered, setHovered] = useState(false);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const fragmentEntryLink = useSelectorCallback(
		(state) => state.fragmentEntryLinks[item.config.fragmentEntryLinkId],
		[item.config.fragmentEntryLinkId]
	);

	const getPortals = useCallback((element) => getAllPortals(element), []);
	const itemConfig = getResponsiveConfig(item.config, selectedViewportSize);
	const [setRef, itemElement] = useSetRef(ref);

	const {
		display,
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
	style.display = display;
	style.maxWidth = maxWidth;
	style.minWidth = minWidth;
	style.width = width;

	return (
		<>
			<HoverHandler
				fragmentEntryLink={fragmentEntryLink}
				hovered={hovered}
				setHovered={setHovered}
			/>
			<Topper
				className={classNames({
					[getLayoutDataItemTopperUniqueClassName(item.itemId)]:
						config.featureFlagLps132571 &&
						!hasInnerCommonStyles(fragmentEntryLink),
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
				<FragmentContent
					elementRef={setRef}
					fragmentEntryLinkId={item.config.fragmentEntryLinkId}
					getPortals={getPortals}
					item={item}
					withinTopper
				/>
			</Topper>
		</>
	);
});

FragmentWithControls.displayName = 'FragmentWithControls';

FragmentWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default FragmentWithControls;

const HoverHandler = ({fragmentEntryLink, hovered, setHovered}) => {
	const hoveredItemType = useHoveredItemType();
	const hoveredItemId = useHoveredItemId();

	const mappedEditableValues = useMemo(() => {
		const fieldNames = [];

		if (fragmentEntryLink) {
			fragmentEntryLink.configuration?.fieldSets?.forEach((fieldSet) => {
				fieldSet.fields.forEach((field) => {
					if (FIELD_TYPES.includes(field.type)) {
						fieldNames.push(field.name);
					}
				});
			});

			const filteredFieldNames = fieldNames.filter(
				(fieldName) =>
					fragmentEntryLink.editableValues[
						FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
					]?.[fieldName]?.classPK
			);

			return filteredFieldNames.map(
				(fieldName) =>
					fragmentEntryLink.editableValues[
						FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
					]?.[fieldName] || {}
			);
		}
	}, [fragmentEntryLink]);

	useEffect(() => {
		if (mappedEditableValues.length) {
			const someEditableIsHovered = mappedEditableValues.some(
				(editableValue) =>
					isHovered({
						editableValue,
						hoveredItemId,
						hoveredItemType,
					})
			);

			if (hovered !== someEditableIsHovered) {
				setHovered(someEditableIsHovered);
			}
		}
	}, [
		hoveredItemType,
		hoveredItemId,
		mappedEditableValues,
		setHovered,
		hovered,
	]);

	return null;
};
