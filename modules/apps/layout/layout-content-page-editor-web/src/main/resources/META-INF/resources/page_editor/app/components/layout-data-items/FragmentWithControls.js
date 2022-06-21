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
import {useSelectorCallback} from '../../contexts/StoreContext';
import getLayoutDataItemTopperUniqueClassName from '../../utils/getLayoutDataItemTopperUniqueClassName';
import hasInnerCommonStyles from '../../utils/hasInnerCustomStyles';
import FragmentContent from '../fragment-content/FragmentContent';
import Topper from '../topper/Topper';
import getAllPortals from './getAllPortals';
import isHovered from './isHovered';

const FIELD_TYPES = ['itemSelector', 'collectionSelector'];

const FragmentWithControls = React.forwardRef(({item}, ref) => {
	const [hovered, setHovered] = useState(false);
	const fragmentEntryLink = useSelectorCallback(
		(state) => state.fragmentEntryLinks[item.config.fragmentEntryLinkId],
		[item.config.fragmentEntryLinkId]
	);

	const getPortals = useCallback((element) => getAllPortals(element), []);
	const [setRef, itemElement] = useSetRef(ref);

	return (
		<>
			<HoverHandler
				fragmentEntryLink={fragmentEntryLink}
				hovered={hovered}
				setHovered={setHovered}
			/>
			<Topper
				className={classNames({
					[getLayoutDataItemTopperUniqueClassName(
						item.itemId
					)]: !hasInnerCommonStyles(fragmentEntryLink),
					'page-editor__topper--hovered': hovered,
				})}
				item={item}
				itemElement={itemElement}
			>
				<FragmentContent
					computeEditables
					elementRef={setRef}
					fragmentEntryLinkId={item.config.fragmentEntryLinkId}
					getPortals={getPortals}
					item={item}
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
