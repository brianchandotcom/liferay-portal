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
import PropTypes from 'prop-types';
import React, {useCallback, useState} from 'react';

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
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import {ResizeContextProvider} from '../ResizeContext';
import Topper from '../Topper';
import FloatingToolbar from '../floating-toolbar/FloatingToolbar';
import SaveFragmentCompositionModal from '../floating-toolbar/SaveFragmentCompositionModal';
import Row from './Row';
import hasDropZoneChild from './hasDropZoneChild';

const RowWithControls = React.forwardRef(
	({children, item, layoutData}, ref) => {
		const {config} = layoutData.items[item.itemId];
		const dispatch = useDispatch();
		const isMounted = useIsMounted();
		const [resizing, setResizing] = useState(false);
		const [updatedLayoutData, setUpdatedLayoutData] = useState(null);
		const [customRow, setCustomRow] = useState(false);

		const canUpdatePageStructure = useSelector(
			selectCanUpdatePageStructure
		);

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

		const segmentsExperienceId = useSelector(
			(state) => state.segmentsExperienceId
		);
		const selectedViewportSize = useSelector(
			(state) => state.selectedViewportSize
		);
		const showFloatingToolbar = useSelector(selectShowFloatingToolbar);

		const rowConfig = getResponsiveConfig(config, selectedViewportSize);

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
			[dispatch, item.itemId, segmentsExperienceId]
		);

		const buttons = [];

		if (canUpdatePageStructure && !hasDropZoneChild(item, layoutData)) {
			buttons.push(LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateItem);
			buttons.push(
				LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.saveFragmentComposition
			);
		}

		if (selectCanUpdateItemConfiguration) {
			buttons.push(LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.rowConfiguration);
		}

		const {verticalAlignment} = rowConfig;

		return (
			<Topper
				item={item}
				itemElement={itemElement}
				layoutData={layoutData}
			>
				<Row
					className={classNames({
						'align-bottom': verticalAlignment === 'bottom',
						'align-middle': verticalAlignment === 'middle',
						'page-editor__row': canUpdatePageStructure,
						'page-editor__row-overlay-grid': resizing,
					})}
					item={item}
					layoutData={layoutData}
					ref={setRef}
				>
					<ResizeContextProvider
						value={{
							customRow,
							resizing,
							setCustomRow,
							setResizing,
							setUpdatedLayoutData,
							updatedLayoutData,
						}}
					>
						{showFloatingToolbar && (
							<FloatingToolbar
								buttons={buttons}
								item={item}
								itemElement={itemElement}
								onButtonClick={handleButtonClick}
							/>
						)}
						{children}
					</ResizeContextProvider>

					{openSaveFragmentCompositionModal && (
						<SaveFragmentCompositionModal
							errorMessage={''}
							itemId={item.itemId}
							observer={observer}
							onClose={onClose}
							onErrorDismiss={() => true}
						/>
					)}
				</Row>
			</Topper>
		);
	}
);

RowWithControls.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({gutters: PropTypes.bool}),
	}).isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

export default RowWithControls;
