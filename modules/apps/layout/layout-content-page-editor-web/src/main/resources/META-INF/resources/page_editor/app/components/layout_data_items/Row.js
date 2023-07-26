/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useMemo} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {useGetFieldValue} from '../../contexts/CollectionItemContext';
import {useSelector} from '../../contexts/StoreContext';
import getLayoutDataItemClassName from '../../utils/getLayoutDataItemClassName';
import getLayoutDataItemCssClasses from '../../utils/getLayoutDataItemCssClasses';
import getLayoutDataItemUniqueClassName from '../../utils/getLayoutDataItemUniqueClassName';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import useBackgroundImageValue from '../../utils/useBackgroundImageValue';

const Row = React.forwardRef(({children, className, item}, ref) => {
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const itemConfig = getResponsiveConfig(item.config, selectedViewportSize);
	const {modulesPerRow, reverseOrder} = itemConfig;

	const {backgroundImage} = itemConfig.styles;

	const elementId = useId();
	const getFieldValue = useGetFieldValue();
	const backgroundImageValue = useBackgroundImageValue(
		elementId,
		backgroundImage,
		getFieldValue
	);

	const style = {};

	if (backgroundImageValue.url) {
		style[
			`--lfr-background-image-${item.itemId}`
		] = `url(${backgroundImageValue.url})`;

		if (backgroundImage?.fileEntryId) {
			style['--background-image-file-entry-id'] =
				backgroundImage.fileEntryId;
		}
	}

	const rowContent = (
		<div
			className={classNames(
				getLayoutDataItemClassName(item.type),
				getLayoutDataItemCssClasses(item),
				getLayoutDataItemUniqueClassName(item.itemId)
			)}
		>
			<ClayLayout.Row
				className={classNames(
					className,

					{
						'flex-column-reverse':
							item.config.numberOfColumns === 2 &&
							modulesPerRow === 1 &&
							reverseOrder,
						'no-gutters': !item.config.gutters,
					}
				)}
				id={elementId}
				ref={ref}
				style={style}
			>
				{backgroundImageValue.mediaQueries ? (
					<style>{backgroundImageValue.mediaQueries}</style>
				) : null}

				{children}
			</ClayLayout.Row>
		</div>
	);

	const masterLayoutData = useSelector(
		(state) => state.masterLayout?.masterLayoutData
	);

	const masterParent = useMemo(() => {
		const dropZone =
			masterLayoutData &&
			masterLayoutData.items[masterLayoutData.rootItems.dropZone];

		return dropZone ? getItemParent(dropZone, masterLayoutData) : undefined;
	}, [masterLayoutData]);

	const shouldAddContainer = useSelector(
		(state) => !getItemParent(item, state.layoutData) && !masterParent
	);

	return shouldAddContainer ? (
		<ClayLayout.ContainerFluid className="p-0" size={false}>
			{rowContent}
		</ClayLayout.ContainerFluid>
	) : (
		rowContent
	);
});

Row.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({gutters: PropTypes.bool}),
	}).isRequired,
};

function getItemParent(item, itemLayoutData) {
	const parent = itemLayoutData.items[item.parentId];

	return parent &&
		(parent.type === LAYOUT_DATA_ITEM_TYPES.root ||
			parent.type === LAYOUT_DATA_ITEM_TYPES.fragmentDropZone)
		? getItemParent(parent, itemLayoutData)
		: parent;
}

export default Row;
