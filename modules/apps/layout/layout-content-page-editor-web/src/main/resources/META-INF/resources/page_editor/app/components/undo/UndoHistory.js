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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayDropDownDivider from '@clayui/drop-down/lib/Divider';
import React, {useState} from 'react';

import {config} from '../../config/index';
import {useDispatch, useSelector} from '../../store/index';
import multipleUndo from '../../thunks/multipleUndo';
import getSegmentsExperienceName from '../../utils/getSegmentsExperienceName';
import {getActionLabel} from './getActionLabel';

export default function UndoHistory() {
	const dispatch = useDispatch();
	const store = useSelector((state) => state);
	const undoHistory = useSelector((state) => state.undoHistory);

	const [active, setActive] = useState(false);

	const isSelectedAction = (index) => index === 0;

	return (
		<>
			<ClayDropDown
				active={active}
				alignmentPosition={Align.BottomRight}
				className="mr-3"
				onActiveChange={setActive}
				trigger={
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('undo-history')}
						className="btn-monospaced"
						disabled={!undoHistory || !undoHistory.length}
						displayType="secondary"
						small
						symbol="time"
						title={Liferay.Language.get('undo-history')}
					/>
				}
			>
				<ClayDropDown.ItemList>
					{undoHistory &&
						undoHistory.map((undoHistoryItem, index) => (
							<ClayDropDown.Item
								disabled={isSelectedAction(index)}
								key={index}
								onClick={(event) => {
									event.preventDefault();

									dispatch(
										multipleUndo({
											numberOfActions: index,
											store,
										})
									);
								}}
								symbolRight={
									isSelectedAction(index) ? 'check' : ''
								}
							>
								{getActionLabel(undoHistoryItem)}

								{undoHistoryItem.segmentsExperienceId !==
									config.defaultSegmentsExperienceId && (
									<span>
										{getSegmentsExperienceName(
											undoHistoryItem.segmentsExperienceId,
											store.availableSegmentsExperiences
										)}
									</span>
								)}
							</ClayDropDown.Item>
						))}
					<ClayDropDownDivider />
					<ClayDropDown.Item
						onClick={(event) => {
							event.preventDefault();

							dispatch(
								multipleUndo({
									numberOfActions: undoHistory.length,
									store,
								})
							);

							setActive(false);
						}}
					>
						{Liferay.Language.get('undo-all')}
					</ClayDropDown.Item>
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</>
	);
}
