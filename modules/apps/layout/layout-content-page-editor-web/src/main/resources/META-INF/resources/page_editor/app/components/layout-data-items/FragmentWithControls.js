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

import React, {useCallback, useMemo} from 'react';

import useSetRef from '../../../core/hooks/useSetRef';
import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/layoutDataFloatingToolbarButtons';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../../selectors/selectCanUpdatePageStructure';
import selectShowFloatingToolbar from '../../selectors/selectShowFloatingToolbar';
import {useDispatch, useSelector} from '../../store/index';
import duplicateItem from '../../thunks/duplicateItem';
import {useSelectItem} from '../Controls';
import Topper from '../Topper';
import FloatingToolbar from '../floating-toolbar/FloatingToolbar';
import FragmentContent from '../fragment-content/FragmentContent';

const FragmentWithControls = React.forwardRef(({item, layoutData}, ref) => {
	const dispatch = useDispatch();
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const segmentsExperienceId = useSelector(
		(state) => state.segmentsExperienceId
	);
	const selectItem = useSelectItem();
	const showFloatingToolbar = useSelector(selectShowFloatingToolbar);
	const widgets = useSelector((state) => state.widgets);

	const fragmentEntryLink =
		fragmentEntryLinks[item.config.fragmentEntryLinkId];

	const [setRef, itemElement] = useSetRef(ref);

	const handleButtonClick = useCallback(
		(id) => {
			if (id === LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateItem.id) {
				dispatch(
					duplicateItem({
						itemId: item.itemId,
						segmentsExperienceId,
						selectItem,
					})
				);
			}
		},
		[dispatch, item.itemId, segmentsExperienceId, selectItem]
	);

	const floatingToolbarButtons = useMemo(() => {
		const buttons = [];

		if (canUpdatePageStructure) {
			const portletId = fragmentEntryLink.editableValues.portletId;

			const widget = portletId && getWidget(widgets, portletId);

			if (!widget || widget.instanceable) {
				buttons.push(
					LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateItem
				);
			}
		}

		const configuration = fragmentEntryLink.configuration;

		if (
			canUpdateItemConfiguration &&
			configuration &&
			Array.isArray(configuration.fieldSets) &&
			configuration.fieldSets.length
		) {
			buttons.push(
				LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.fragmentConfiguration
			);
		}

		return buttons;
	}, [
		canUpdateItemConfiguration,
		canUpdatePageStructure,
		fragmentEntryLink.configuration,
		fragmentEntryLink.editableValues.portletId,
		widgets,
	]);

	return (
		<Topper item={item} itemElement={itemElement} layoutData={layoutData}>
			<>
				{showFloatingToolbar && (
					<FloatingToolbar
						buttons={floatingToolbarButtons}
						item={item}
						itemElement={itemElement}
						onButtonClick={handleButtonClick}
					/>
				)}

				<FragmentContent
					fragmentEntryLinkId={fragmentEntryLink.fragmentEntryLinkId}
					itemId={item.itemId}
					ref={setRef}
				/>
			</>
		</Topper>
	);
});

function getWidget(widgets, portletId) {
	let widget = null;

	const widgetsLength = widgets.length;

	for (let i = 0; i < widgetsLength; i++) {
		const {categories = [], portlets = []} = widgets[i];
		const categoryPortlet = portlets.find(
			(_portlet) => _portlet.portletId === portletId
		);
		const subCategoryPortlet = getWidget(categories, portletId);

		if (categoryPortlet) {
			widget = categoryPortlet;
		}

		if (subCategoryPortlet) {
			widget = subCategoryPortlet;
		}
	}

	return widget;
}

FragmentWithControls.displayName = 'FragmentWithControls';

FragmentWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

export default FragmentWithControls;
