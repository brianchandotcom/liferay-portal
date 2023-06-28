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
import {Text} from '@clayui/core';
import ClayDropDown, {Align} from '@clayui/drop-down';
import Label from '@clayui/label';
import Layout from '@clayui/layout';
import React, {useState} from 'react';

interface segmentExperience {
	segmentsEntryName: string;
	segmentsExperienceActive: boolean;
	segmentsExperienceId: string;
	segmentsExperienceName: string;
	segmentsExperienceStatusLabel: string;
}

interface Props {
	maximumDropdownEntries: number;
	namespace: string;
	onMoreSegmentExperiencesButtonClick: () => void;
	onSelectSegmentExperience: React.Dispatch<segmentExperience>;
	segmentsExperiences: segmentExperience[];
	selectedSegmentsExperience: segmentExperience;
}

function ExperienceSelector({
	maximumDropdownEntries,
	namespace,
	onMoreSegmentExperiencesButtonClick,
	onSelectSegmentExperience,
	segmentsExperiences,
	selectedSegmentsExperience,
}: Props) {
	const [
		segmentExperienceSelectorActive,
		setSegmentExperienceSelectorActive,
	] = useState(false);

	const segmentExperiencesShortList = segmentsExperiences.slice(
		0,
		maximumDropdownEntries
	);

	return (
		<>
			{segmentsExperiences.length < 2 ? (
				<p>
					{Liferay.Language.get('no-experiences-have-been-added-yet')}
				</p>
			) : (
				<div className="form-group">
					<label
						htmlFor={`${namespace}segmentsExperienceSelector`}
						id={`${namespace}segmentsExperienceLabelId`}
					>
						{Liferay.Language.get('experience')}
					</label>

					<input
						id={`${namespace}segmentsExperienceId`}
						name={`${namespace}segmentsExperienceId`}
						type="hidden"
						value={selectedSegmentsExperience.segmentsExperienceId}
					/>

					<ClayDropDown
						active={segmentExperienceSelectorActive}
						alignmentPosition={Align.BottomLeft}
						menuElementAttrs={{
							containerProps: {
								className: 'cadmin',
							},
						}}
						onActiveChange={setSegmentExperienceSelectorActive}
						trigger={
							<ClayButton
								className="form-control-select text-left w-100"
								displayType="secondary"
								size="sm"
								type="button"
							>
								<span>
									{
										selectedSegmentsExperience.segmentsExperienceName
									}
								</span>
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							{segmentExperiencesShortList.map(
								(segmentsExperience) => (
									<ClayDropDown.Item
										active={
											segmentsExperience.segmentsExperienceId ===
											selectedSegmentsExperience.segmentsExperienceId
										}
										aria-label={`${
											segmentsExperience.segmentsExperienceName
										}, ${Liferay.Language.get(
											'segment'
										)}: ${
											segmentsExperience.segmentsEntryName
										}, ${
											segmentsExperience.segmentsExperienceStatusLabel
										}`}
										key={
											segmentsExperience.segmentsExperienceId
										}
										onClick={() => {
											setSegmentExperienceSelectorActive(
												false
											);
											onSelectSegmentExperience(
												segmentsExperience
											);
										}}
									>
										<Layout.ContentRow>
											<Layout.ContentCol
												className="pl-0"
												expand
											>
												<Text
													id={`${segmentsExperience.segmentsExperienceId}-title`}
													size={3}
													weight="semi-bold"
												>
													{
														segmentsExperience.segmentsExperienceName
													}
												</Text>

												<Text
													color="secondary"
													id={`${segmentsExperience.segmentsExperienceId}-description`}
													size={3}
												>
													{`${Liferay.Language.get(
														'segment'
													)}:
                                        ${
											segmentsExperience.segmentsEntryName
										}`}
												</Text>
											</Layout.ContentCol>

											<Layout.ContentCol className="pr-0">
												<Label
													aria-hidden
													className="mr-0"
													displayType={
														segmentsExperience.segmentsExperienceActive
															? 'success'
															: 'secondary'
													}
													id={`${segmentsExperience.segmentsExperienceId}-status`}
												>
													{
														segmentsExperience.segmentsExperienceStatusLabel
													}
												</Label>
											</Layout.ContentCol>
										</Layout.ContentRow>
									</ClayDropDown.Item>
								)
							)}

							{segmentsExperiences.length >
								maximumDropdownEntries && (
								<ClayDropDown.Section>
									<ClayButton
										className="w-100"
										displayType="secondary"
										onClick={() => {
											setSegmentExperienceSelectorActive(
												false
											);

											onMoreSegmentExperiencesButtonClick();
										}}
									>
										{Liferay.Language.get(
											'more-experiences'
										)}
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

export default ExperienceSelector;
