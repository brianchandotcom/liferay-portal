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
import React, {useContext, useState} from 'react';

import {normalizeLanguageId} from '../../../utils/string';
import Panel from '../../Panel/Panel';
import LayoutContext, {TYPES} from '../context';
import DropdownWithDeleteButton from './DropdownWithDeleteButton';
import ModalAddObjectLayoutBox from './ModalAddObjectLayoutBox';
import ObjectLayoutBox from './ObjectLayoutBox';
import ObjectLayoutRelationship from './ObjectLayoutRelationship';

const defaultLanguageId = normalizeLanguageId(
	Liferay.ThemeDisplay.getDefaultLanguageId()
);

const ObjectLayoutTabs: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const [{objectLayout}, dispatch] = useContext(LayoutContext);
	const [visibleModal, setVisibleModal] = useState(false);
	const [selectedTabIndex, setSelectedTabIndex] = useState(0);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	return (
		<>
			{objectLayout?.objectLayoutTabs?.map(
				({name, objectLayoutBoxes, objectRelationshipId}, tabIndex) => {
					const isRelationshipType = objectRelationshipId !== 0;
					const labelDisplayType = isRelationshipType
						? 'warning'
						: 'info';

					return (
						<Panel
							className="layout-tab__tab"
							key={`layout_${tabIndex}`}
						>
							<Panel.Header
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

										<DropdownWithDeleteButton
											onClick={() => {
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
								title={name[defaultLanguageId]}
							/>

							{!!objectLayoutBoxes?.length &&
								!isRelationshipType && (
									<Panel.Body>
										{objectLayoutBoxes.map(
											(
												{
													collapsable,
													name,
													objectLayoutRows,
												},
												boxIndex
											) => (
												<ObjectLayoutBox
													boxIndex={boxIndex}
													collapsable={collapsable}
													key={`box_${boxIndex}`}
													label={
														name[defaultLanguageId]
													}
													objectLayoutRows={
														objectLayoutRows
													}
													tabIndex={tabIndex}
												/>
											)
										)}
									</Panel.Body>
								)}

							{isRelationshipType && (
								<Panel.Body>
									<ObjectLayoutRelationship
										objectRelationshipId={
											objectRelationshipId
										}
									/>
								</Panel.Body>
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
