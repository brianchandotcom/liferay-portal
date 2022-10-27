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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {useModal} from '@clayui/modal';
import {Panel, PanelBody, PanelHeader} from '@liferay/object-js-components-web';
import React, {useState} from 'react';

import {TYPES, useLayoutContext} from '../objectLayoutContext';
import {HeaderDropdown} from './HeaderDropdown';
import {ModalAddObjectLayoutBox} from './ModalAddObjectLayoutBox';
import {ObjectLayoutBox} from './ObjectLayoutBox';
import {ObjectLayoutRelationship} from './ObjectLayoutRelationship';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const ObjectLayoutTabs: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const [{isViewOnly, objectLayout}, dispatch] = useLayoutContext();
	const [visibleModal, setVisibleModal] = useState(false);
	const [selectedTabIndex, setSelectedTabIndex] = useState(0);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	return (
		<>
			{objectLayout?.objectLayoutTabs?.map(
				({name, objectLayoutBoxes, objectRelationshipId}, tabIndex) => {
					const isRelationshipType =
						objectRelationshipId && objectRelationshipId !== 0;
					const labelDisplayType = isRelationshipType
						? 'warning'
						: 'info';

					return (
						<Panel
							className="layout-tab__tab"
							key={`layout_${tabIndex}`}
						>
							<PanelHeader
								contentLeft={
									<ClayLabel displayType={labelDisplayType}>
										{isRelationshipType
											? Liferay.Language.get(
													'relationships'
											  )
											: Liferay.Language.get('fields')}
									</ClayLabel>
								}
								contentRight={
									<>
										{!isRelationshipType && (
											<ClayButton
												disabled={isViewOnly}
												displayType="secondary"
												onClick={() => {
													setVisibleModal(true);
													setSelectedTabIndex(
														tabIndex
													);
												}}
												small
											>
												<ClayIcon symbol="plus" />

												<span className="ml-2">
													{Liferay.Language.get(
														'add-block'
													)}
												</span>
											</ClayButton>
										)}

										<HeaderDropdown
											addCategorization={() => {
												dispatch({
													payload: {
														name: {
															[defaultLanguageId]: Liferay.Language.get(
																'categorization'
															),
														},
														tabIndex,
														type: 'categorization',
													},
													type:
														TYPES.ADD_OBJECT_LAYOUT_BOX,
												});
											}}
											deleteElement={() => {
												dispatch({
													payload: {
														tabIndex,
													},
													type:
														TYPES.DELETE_OBJECT_LAYOUT_TAB,
												});
											}}
										/>
									</>
								}
								title={name[defaultLanguageId]!}
								type="regular"
							/>

							{!!objectLayoutBoxes?.length &&
								!isRelationshipType && (
									<PanelBody>
										{objectLayoutBoxes.map(
											(
												{
													collapsable,
													name,
													objectLayoutRows,
													type,
												},
												boxIndex
											) => (
												<ObjectLayoutBox
													boxIndex={boxIndex}
													collapsable={collapsable}
													key={`box_${boxIndex}`}
													label={
														name[defaultLanguageId]!
													}
													objectLayoutRows={
														objectLayoutRows
													}
													tabIndex={tabIndex}
													type={type}
												/>
											)
										)}
									</PanelBody>
								)}

							{isRelationshipType && (
								<PanelBody>
									<ObjectLayoutRelationship
										objectRelationshipId={
											objectRelationshipId
										}
									/>
								</PanelBody>
							)}
						</Panel>
					);
				}
			)}

			{visibleModal && (
				<ModalAddObjectLayoutBox
					observer={observer}
					onClose={onClose}
					tabIndex={selectedTabIndex}
				/>
			)}
		</>
	);
};

export default ObjectLayoutTabs;
