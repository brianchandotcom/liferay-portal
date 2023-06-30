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
import ClayDropDown, {Align} from '@clayui/drop-down';
import React, {useState} from 'react';

import SegmentEntry from '../../types/SegmentEntry';

interface Props {
	maximumDropdownEntries: number;
	namespace: string;
	onMoreSegmentEntriesButtonClick: () => void;
	onSelectSegmentEntry: React.Dispatch<SegmentEntry>;
	segmentsEntries: SegmentEntry[];
	selectedSegmentEntry: SegmentEntry;
}

function SegmentSelector({
	maximumDropdownEntries,
	namespace,
	onMoreSegmentEntriesButtonClick,
	onSelectSegmentEntry,
	segmentsEntries,
	selectedSegmentEntry,
}: Props) {
	const [segmentSelectorActive, setSegmentSelectorActive] = useState(false);

	const segmentEntriesShortList = segmentsEntries.slice(
		0,
		maximumDropdownEntries
	);

	return (
		<>
			{segmentsEntries.length < 2 ? (
				<p>{Liferay.Language.get('no-segments-have-been-added-yet')}</p>
			) : (
				<div className="form-group">
					<label htmlFor={`${namespace}segmentsEntryId`}>
						{Liferay.Language.get('segment')}
					</label>

					<input
						id={`${namespace}segmentsEntryId`}
						name={`${namespace}segmentsEntryId`}
						type="hidden"
						value={selectedSegmentEntry.id}
					/>

					<ClayDropDown
						active={segmentSelectorActive}
						alignmentPosition={Align.BottomLeft}
						menuElementAttrs={{
							containerProps: {
								className: 'cadmin',
							},
						}}
						onActiveChange={setSegmentSelectorActive}
						trigger={
							<ClayButton
								className="form-control-select text-left w-100"
								displayType="secondary"
								size="sm"
								type="button"
							>
								<span>{selectedSegmentEntry.name}</span>
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							{segmentEntriesShortList.map((segmentEntry) => (
								<ClayDropDown.Item
									active={
										segmentEntry.id ===
										selectedSegmentEntry.id
									}
									key={segmentEntry.id}
									onClick={() => {
										setSegmentSelectorActive(false);
										onSelectSegmentEntry(segmentEntry);
									}}
								>
									{segmentEntry.name}
								</ClayDropDown.Item>
							))}

							{segmentsEntries.length >
								maximumDropdownEntries && (
								<ClayDropDown.Section>
									<ClayButton
										className="w-100"
										displayType="secondary"
										onClick={() => {
											setSegmentSelectorActive(false);

											onMoreSegmentEntriesButtonClick();
										}}
									>
										{Liferay.Language.get('more-segments')}
									</ClayButton>
								</ClayDropDown.Section>
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</div>
			)}
		</>
	);
}

export default SegmentSelector;
