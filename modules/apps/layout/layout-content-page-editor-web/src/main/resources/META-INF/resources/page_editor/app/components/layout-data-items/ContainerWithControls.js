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

import {useModal} from '@clayui/modal';
import classNames from 'classnames';
import {useIsMounted} from 'frontend-js-react-web';
import React, {useCallback, useState} from 'react';

import useSetRef from '../../../core/hooks/useSetRef';
import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/layoutDataFloatingToolbarButtons';
import {config} from '../../config/index';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../../selectors/selectCanUpdatePageStructure';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import selectShowFloatingToolbar from '../../selectors/selectShowFloatingToolbar';
import {useDispatch, useSelector} from '../../store/index';
import duplicateItem from '../../thunks/duplicateItem';
import {useSelectItem} from '../Controls';
import Topper from '../Topper';
import FloatingToolbar from '../floating-toolbar/FloatingToolbar';
import SaveFragmentCompositionModal from '../floating-toolbar/SaveFragmentCompositionModal';
import Container from './Container';
import hasDropZoneChild from './hasDropZoneChild';

const ContainerWithControls = React.forwardRef(
	({children, item, layoutData}, ref) => {
		const dispatch = useDispatch();
		const isMounted = useIsMounted();
		const [
			openSaveFragmentCompositionModal,
			setOpenSaveFragmentCompositionModal,
		] = useState(false);
		const {observer, onClose} = useModal({
			onClose: () => {
				if (isMounted()) {
					setOpenSaveFragmentCompositionModal(false);
				}
			},
		});

		const canUpdateItemConfiguration = useSelector(
			selectCanUpdateItemConfiguration
		);
		const canUpdatePageStructure = useSelector(
			selectCanUpdatePageStructure
		);
		const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
		const showFloatingToolbar = useSelector(selectShowFloatingToolbar);
		const selectItem = useSelectItem();

		const [setRef, itemElement] = useSetRef(ref);

		const handleButtonClick = useCallback(
			(id) => {
				if (
					id === LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateItem.id
				) {
					dispatch(
						duplicateItem({
							itemId: item.itemId,
							segmentsExperienceId,
							selectItem,
						})
					);
				}
				else if (
					id ===
					LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.saveFragmentComposition
						.id
				) {
					setOpenSaveFragmentCompositionModal(true);
				}
			},
			[dispatch, item.itemId, segmentsExperienceId, selectItem]
		);

		const buttons = [];

		if (canUpdatePageStructure && !hasDropZoneChild(item, layoutData)) {
			buttons.push(LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateItem);
			buttons.push(
				LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.saveFragmentComposition
			);
		}

		if (canUpdateItemConfiguration) {
			if (config.containerItemEnabled) {
				buttons.push(
					LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.containerLink
				);
			}

			buttons.push(
				LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.containerConfiguration
			);
		}

		return (
			<>
				{showFloatingToolbar && (
					<FloatingToolbar
						buttons={buttons}
						item={item}
						itemElement={itemElement}
						onButtonClick={handleButtonClick}
					/>
				)}

				{openSaveFragmentCompositionModal && (
					<SaveFragmentCompositionModal
						errorMessage={''}
						itemId={item.itemId}
						observer={observer}
						onClose={onClose}
						onErrorDismiss={() => true}
					/>
				)}

				<Topper
					item={item}
					itemElement={itemElement}
					layoutData={layoutData}
				>
					<Container
						className={classNames({
							empty: !item.children.length,
							'page-editor__container':
								canUpdatePageStructure ||
								canUpdateItemConfiguration,
						})}
						item={item}
						ref={setRef}
					>
						{children}
					</Container>
				</Topper>
			</>
		);
	}
);

ContainerWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

export default ContainerWithControls;
