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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayDropDown, {Align} from '@clayui/drop-down';
import {ClaySelectWithOption} from '@clayui/form';
import Label from '@clayui/label';
import Layout from '@clayui/layout';
import ClayLink from '@clayui/link';
import {fetch} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

const PREVIEW_OPTIONS = [
	{
		label: Liferay.Language.get('segments'),
		value: 'segments',
	},
	{
		label: Liferay.Language.get('experiences'),
		value: 'experiences',
	},
];

const MAXIMUM_DROPDOWN_ENTRIES = 8;

function SegmentsAndExperiencesSelector({
	deactivateSimulationURL,
	namespace,
	segmentationEnabled,
	segmentsCompanyConfigurationURL,
	segmentsEntries,
	segmentsExperiences,
	showEmptyMessage,
	simulateSegmentsEntriesURL,
}) {
	const [alertVisible, setAlertVisible] = useState(!segmentationEnabled);
	const [selectedPreviewOption, setSelectedPreviewOption] = useState(
		'segments'
	);
	const [selectedSegmentEntry, setSelectedSegmentEntry] = useState(
		segmentsEntries?.[0]
	);
	const [
		selectedSegmentsExperience,
		setSelectedSegmentsExperience,
	] = useState(segmentsExperiences?.[0]);
	const [segmentSelectorActive, setSegmentSelectorActive] = useState(false);
	const [
		segmentExperienceSelectorActive,
		setSegmentExperienceSelectorActive,
	] = useState(false);

	const segmentEntriesShortList =
		segmentsEntries.length > 8
			? segmentsEntries.slice(0, 8)
			: segmentsEntries;

	const segmentExperiencesShortList =
		segmentsExperiences.length > 8
			? segmentsExperiences.slice(0, 8)
			: segmentsExperiences;

	const formRef = useRef(null);
	const firstRenderRef = useRef(true);

	const fetchDeactivateSimulation = useCallback(() => {
		fetch(deactivateSimulationURL, {
			body: new FormData(formRef.current),
			method: 'POST',
		});
	}, [deactivateSimulationURL]);

	const simulateSegmentsEntries = useCallback(() => {
		fetch(simulateSegmentsEntriesURL, {
			body: new FormData(formRef.current),
			method: 'POST',
		}).then(() => {
			const iframe = document.querySelector('iframe');

			if (iframe?.contentWindow) {
				iframe.contentWindow.location.reload();
			}
		});
	}, [simulateSegmentsEntriesURL]);

	const simulateSegmentsExperiment = useCallback((experience) => {
		const iframe = document.querySelector('iframe');

		if (iframe?.contentWindow) {
			const url = new URL(iframe.contentWindow.location.href);

			url.searchParams.set('segmentsExperienceId', experience);
			iframe.src = url.toString();
		}
	}, []);

	const handleMoreButtonClick = useCallback(() => {

		// TODO

	}, []);

	useEffect(() => {
		const deactivateSimulationEventHandler = Liferay.on(
			'SimulationMenu:closeSimulationPanel',
			fetchDeactivateSimulation
		);

		const openSimulationPanelEventHandler = Liferay.on(
			'SimulationMenu:openSimulationPanel',
			() => {
				firstRenderRef.current = false;
				simulateSegmentsEntries();
			}
		);

		return () => {
			deactivateSimulationEventHandler.detach();
			openSimulationPanelEventHandler.detach();
		};
	}, [fetchDeactivateSimulation, simulateSegmentsEntries]);

	useEffect(() => {
		if (!firstRenderRef.current) {
			simulateSegmentsEntries();
		}
	}, [selectedSegmentEntry, simulateSegmentsEntries]);

	useEffect(() => {
		if (!firstRenderRef.current) {
			simulateSegmentsExperiment(
				selectedSegmentsExperience.segmentsExperienceId
			);
		}
	}, [selectedSegmentsExperience, simulateSegmentsExperiment]);

	useEffect(() => {
		if (firstRenderRef.current) {
			return;
		}

		const iframe = document.querySelector('iframe');

		if (selectedPreviewOption === 'segments') {
			const url = new URL(iframe.contentWindow.location.href);
			url.searchParams.delete('segmentsExperienceId');

			iframe.src = url.toString();

			simulateSegmentsEntries();
		}
		else {
			fetch(deactivateSimulationURL, {
				body: new FormData(formRef.current),
				method: 'POST',
			}).then(() => {
				simulateSegmentsExperiment(
					selectedSegmentsExperience.segmentsExperienceId
				);
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		selectedPreviewOption,
		simulateSegmentsEntries,
		simulateSegmentsExperiment,
	]);

	return (
		<>
			{showEmptyMessage ? (
				<p className="mb-4 mt-1 small">
					{Liferay.Language.get('no-segments-have-been-added-yet')}
				</p>
			) : (
				<form method="post" name="segmentsSimulationFm" ref={formRef}>
					{alertVisible && (
						<ClayAlert
							dismissible
							displayType="warning"
							onClose={() => {
								setAlertVisible(false);
							}}
						>
							<strong>
								{Liferay.Language.get(
									'experiences-cannot-be-displayed-because-segmentation-is-disabled'
								)}
							</strong>

							{segmentsCompanyConfigurationURL ? (
								<ClayLink
									href={segmentsCompanyConfigurationURL}
								>
									{Liferay.Language.get(
										'to-enable,-go-to-instance-settings'
									)}
								</ClayLink>
							) : (
								<span>
									{Liferay.Language.get(
										'contact-your-system-administrator-to-enable-it'
									)}
								</span>
							)}
						</ClayAlert>
					)}

					{!!segmentsEntries.length &&
						segmentsExperiences.length > 1 && (
							<div className="form-group">
								<label
									htmlFor={`${namespace}segmentsOrExperiences`}
								>
									{Liferay.Language.get('preview-by')}
								</label>

								<ClaySelectWithOption
									id={`${namespace}segmentsOrExperiences`}
									onChange={({target}) => {
										setSelectedPreviewOption(target.value);
									}}
									options={PREVIEW_OPTIONS}
									value={selectedPreviewOption}
								/>
							</div>
						)}

					{selectedPreviewOption === 'segments' && (
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
									{segmentEntriesShortList.map(
										(segmentEntry) => (
											<ClayDropDown.Item
												key={segmentEntry.id}
												onClick={() => {
													setSegmentSelectorActive(
														false
													);
													setSelectedSegmentEntry(
														segmentEntry
													);
												}}
											>
												{segmentEntry.name}
											</ClayDropDown.Item>
										)
									)}

									{segmentsEntries.length >
										MAXIMUM_DROPDOWN_ENTRIES && (
										<ClayDropDown.Section>
											<ClayButton
												displayType="secondary w-100"
												onClick={() => {
													setSegmentSelectorActive(
														false
													);

													handleMoreButtonClick();
												}}
											>
												{Liferay.Language.get(
													'more-segments'
												)}
											</ClayButton>
										</ClayDropDown.Section>
									)}
								</ClayDropDown.ItemList>
							</ClayDropDown>
						</div>
					)}

					{selectedPreviewOption === 'experiences' && (
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
								value={
									selectedSegmentsExperience.segmentsExperienceId
								}
							/>

							<ClayDropDown
								active={segmentExperienceSelectorActive}
								alignmentPosition={Align.BottomLeft}
								menuElementAttrs={{
									containerProps: {
										className: 'cadmin',
									},
								}}
								onActiveChange={
									setSegmentExperienceSelectorActive
								}
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
												key={
													segmentsExperience.segmentsExperienceId
												}
												onClick={() => {
													setSegmentExperienceSelectorActive(
														false
													);
													setSelectedSegmentsExperience(
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
															aria-hidden
															color="secondary"
															id={`${segmentsExperience.segmentsExperienceId}-description`}
															size={3}
														>
															{`${Liferay.Language.get(
																'segment'
															)}:
														${segmentsExperience.segmentsEntryName}`}
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
										MAXIMUM_DROPDOWN_ENTRIES && (
										<ClayDropDown.Section>
											<ClayButton
												displayType="secondary w-100"
												onClick={() => {
													setSegmentExperienceSelectorActive(
														false
													);

													handleMoreButtonClick();
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
				</form>
			)}
		</>
	);
}

export default SegmentsAndExperiencesSelector;
