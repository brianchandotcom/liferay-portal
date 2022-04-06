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

import {usePrevious} from '@liferay/frontend-js-react-web';
import {useEffect, useRef} from 'react';

import {CONTAINER_WIDTH_TYPES} from '../../config/constants/containerWidthTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../config/constants/viewportSizes';
import {config} from '../../config/index';
import {useGlobalContext} from '../../contexts/GlobalContext';
import {useSelector} from '../../contexts/StoreContext';
import {deepEqual} from '../../utils/checkDeepEqual';
import generateStyleSheet from '../../utils/generateStyleSheet';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';

const LAYOUT_DATA_ITEMS_WITH_COMMON_STYLES = [
	LAYOUT_DATA_ITEM_TYPES.collection,
	LAYOUT_DATA_ITEM_TYPES.container,
	LAYOUT_DATA_ITEM_TYPES.row,
	LAYOUT_DATA_ITEM_TYPES.fragment,
];

export default function CommonStylesManager() {
	const layoutData = useSelector((state) => state.layoutData);
	const masterLayoutData = useSelector(
		(state) => state.masterLayout?.masterLayoutData
	);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const previousMasterLayoutData = usePrevious(masterLayoutData);

	const globalContext = useGlobalContext();

	const stylesPerViewportRef = useRef({});
	const masterStylesPerViewportRef = useRef({});

	useEffect(() => {
		const items = Object.values(layoutData.items);

		const {styles: currentStyles} = stylesPerViewportRef.current[
			selectedViewportSize
		];

		const nextStyles = {};

		items.forEach((item) => {
			if (hasCommonStyles(item)) {
				const styles = getResponsiveConfig(
					item.config,
					selectedViewportSize
				)?.styles;

				nextStyles[item.itemId] = filterStyles({
					item,
					selectedViewportSize,
					styles,
				});
			}
		});

		if (!currentStyles || !deepEqual(nextStyles, currentStyles)) {
			stylesPerViewportRef.current[selectedViewportSize] = {
				styleSheet,
				styles: nextStyles,
			};

			const styleSheet = generateStyleSheet(nextStyles);

			createOrUpdateStyleTag({
				globalContext,
				id: 'layout-common-styles',
				styleSheet,
			});
		}
	}, [layoutData.items, selectedViewportSize, globalContext]);

	useEffect(() => {
		if (previousMasterLayoutData === masterLayoutData) {
			return;
		}

		const items = Object.values(masterLayoutData.items);

		const {styles: currentStyles} = masterStylesPerViewportRef.current[
			selectedViewportSize
		];

		const nextStyles = {};

		items.forEach((item) => {
			if (hasCommonStyles(item)) {
				const styles = getResponsiveConfig(
					item.config,
					selectedViewportSize
				)?.styles;

				nextStyles[item.itemId] = filterStyles({
					item,
					selectedViewportSize,
					styles,
				});
			}
		});

		if (!currentStyles || !deepEqual(nextStyles, currentStyles)) {
			const styleSheet = generateStyleSheet(nextStyles, {
				hasTopper: false,
			});

			masterStylesPerViewportRef.current[selectedViewportSize] = {
				styleSheet,
				styles: nextStyles,
			};

			createOrUpdateStyleTag({
				globalContext,
				id: 'layout-master-common-styles',
				styleSheet,
			});
		}
	}, [
		masterLayoutData,
		selectedViewportSize,
		globalContext,
		previousMasterLayoutData,
	]);

	return null;
}

function createOrUpdateStyleTag({globalContext, id, styleSheet}) {
	let styleTag = globalContext.document.getElementById(id);

	if (!styleTag) {
		styleTag = globalContext.document.createElement('style');
		styleTag.id = id;
		styleTag.type = 'text/css';
		styleTag.dataset.sennaTrack = 'temporary';

		globalContext.document.head.appendChild(styleTag);
	}

	styleTag.innerHTML = styleSheet;

	return styleTag;
}

function hasCommonStyles(item) {
	return LAYOUT_DATA_ITEMS_WITH_COMMON_STYLES.includes(item.type);
}

/**
 * Filter the styles that we don't need to include in the CSS.
 *
 * The values that are rejected are:
 *  - Empty style values
 *  - Values equals to the default value (in desktop).
 *  - Margin left and margin right in a fixed Container (fixed container already have defined margins).
 */
function filterStyles({item, selectedViewportSize, styles}) {
	const filteredStyles = {};

	Object.entries(styles).forEach(([styleName, styleValue]) => {
		const {defaultValue} = config.commonStylesFields[styleName];

		const isContainerFixed =
			item.config?.widthType === CONTAINER_WIDTH_TYPES.fixed;

		if (
			styleValue &&
			defaultValue !== styleValue &&
			selectedViewportSize === VIEWPORT_SIZES.desktop &&
			(!isContainerFixed ||
				(styleName !== 'marginRight' && styleName !== 'marginLeft'))
		) {
			filteredStyles[styleName] = styleValue;
		}
	});

	return filteredStyles;
}
