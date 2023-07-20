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
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayLink from '@clayui/link';
import {fetch, openSelectionModal, sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import SegmentEntry from '../../types/SegmentEntry';
import SegmentExperience from '../../types/SegmentExperience';
import ExperienceSelector from './ExperienceSelector';
import SegmentSelector from './SegmentSelector';

interface Props {
	deactivateSimulationURL: string;
	namespace: string;
	portletNamespace: string;
	segmentationEnabled: boolean;
	segmentsCompanyConfigurationURL: string;
	segmentsEntries: SegmentEntry[];
	segmentsExperiences: SegmentExperience[];
	selectSegmentsEntryURL: string;
	selectSegmentsExperienceURL: string;
	simulateSegmentsEntriesURL: string;
}

const DEFAULT_PREVIEW_OPTION = {
	label: Liferay.Language.get('segments'),
	value: 'segments',
};
const PREVIEW_OPTIONS = [
	DEFAULT_PREVIEW_OPTION,
	{
		label: Liferay.Language.get('experiences'),
		value: 'experiences',
	},
];

const SEGMENT_SIMULATION_EVENT = 'SegmentSimulation:changeSegment';

const MAXIMUM_DROPDOWN_ENTRIES = 8;

function PageContentSelectors({
	deactivateSimulationURL,
	namespace,
	portletNamespace,
	segmentationEnabled,
	segmentsCompanyConfigurationURL,
	segmentsEntries,
	segmentsExperiences,
	selectSegmentsEntryURL,
	selectSegmentsExperienceURL,
	simulateSegmentsEntriesURL,
}: Props) {
	const [
		isSegmentOrExperienceSelectorActive,
		setIsSegmentOrExperienceSelectorActive,
	] = useState(false);
	const [alertVisible, setAlertVisible] = useState(!segmentationEnabled);
	const [selectedPreviewOption, setSelectedPreviewOption] = useState(
		DEFAULT_PREVIEW_OPTION
	);
	const [selectedSegmentEntry, setSelectedSegmentEntry] = useState(
		segmentsEntries?.[0]
	);
	const [
		selectedSegmentsExperience,
		setSelectedSegmentsExperience,
	] = useState(segmentsExperiences?.[0]);

	const formRef = useRef(null);
	const firstRenderRef = useRef(true);

	const fetchDeactivateSimulation = useCallback(() => {
		if (formRef.current) {
			fetch(deactivateSimulationURL, {
				body: new FormData(formRef.current),
				method: 'POST',
			});
		}
	}, [deactivateSimulationURL]);

	const simulateSegmentsEntries = useCallback(() => {
		if (formRef.current) {
			Liferay.fire(SEGMENT_SIMULATION_EVENT, {
				message: sub(
					Liferay.Language.get('showing-content-for-the-segment-x'),
					selectedSegmentEntry.name
				),
			});
			fetch(simulateSegmentsEntriesURL, {
				body: new FormData(
					formRef.current ? formRef.current : undefined
				),
				method: 'POST',
			}).then(() => {
				const iframe = document.querySelector('iframe');

				if (iframe) {

					// LPS-191073 Reload the iframe content by updating its src because
					// iframe.contentWindow.location.reload() does not always work correctly

					iframe.src += '';
				}
			});
		}
	}, [selectedSegmentEntry, simulateSegmentsEntriesURL]);

	const simulateSegmentsExperiment = useCallback(
		(experience) => {
			const iframe = document.querySelector('iframe');

			if (iframe) {
				Liferay.fire(SEGMENT_SIMULATION_EVENT, {
					message: sub(
						Liferay.Language.get(
							'showing-content-for-the-experience-x'
						),
						selectedSegmentsExperience.segmentsExperienceName
					),
				});

				const url = new URL(iframe.src);

				url.searchParams.set('segmentsExperienceId', experience);
				iframe.src = url.toString();
			}
		},
		[selectedSegmentsExperience]
	);

	const handleMoreSegmentEntriesButtonClick = () => {
		openSelectionModal({
			onSelect: (selectedItem: {value: string}) => {
				const valueJSON = JSON.parse(selectedItem.value);
				setSelectedSegmentEntry({
					id: valueJSON.segmentsEntryId,
					name: valueJSON.segmentsEntryName,
				});
			},
			selectEventName: `${portletNamespace}selectSegmentsEntry`,
			title: sub(
				Liferay.Language.get('select-x'),
				Liferay.Language.get('segment')
			),
			url: selectSegmentsEntryURL,
		});
	};

	const handleMoreSegmentExperiencesButtonClick = () => {
		openSelectionModal({
			onSelect: (selectedItem: {value: string}) => {
				const valueJSON = JSON.parse(selectedItem.value);
				const selectedExperience:
					| SegmentExperience
					| undefined = segmentsExperiences.find(
					(exp) =>
						exp.segmentsExperienceId ===
						valueJSON.segmentsExperienceId
				);

				if (!selectedExperience) {
					return;
				}
				setSelectedSegmentsExperience(selectedExperience);
			},
			selectEventName: `${portletNamespace}selectSegmentsExperience`,
			title: Liferay.Language.get('select-experience'),
			url: selectSegmentsExperienceURL,
		});
	};

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

			// @ts-ignore

			deactivateSimulationEventHandler.detach();

			// @ts-ignore

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

		if (!iframe) {
			return;
		}

		if (selectedPreviewOption.value === 'segments') {
			const url = new URL(iframe.src);

			url.searchParams.delete('segmentsExperienceId');
			iframe.src = url.toString();

			simulateSegmentsEntries();
		}
		else {
			fetch(deactivateSimulationURL, {
				body: new FormData(
					formRef.current ? formRef.current : undefined
				),
				method: 'POST',
			}).then(() => {
				simulateSegmentsExperiment(
					selectedSegmentsExperience.segmentsExperienceId
				);
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [selectedPreviewOption]);

	return (
		<form method="post" name="segmentsSimulationFm" ref={formRef}>
			{alertVisible && (
				<ClayAlert
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
						<ClayLink href={segmentsCompanyConfigurationURL}>
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

			<div className="form-group">
				<label htmlFor={`${namespace}segmentsOrExperiences`}>
					{Liferay.Language.get('preview-by')}
				</label>

				<input
					id={`${namespace}segmentsOrExperiences`}
					name={`${namespace}segmentsOrExperiences`}
					type="hidden"
					value={selectedPreviewOption.value}
				/>

				<ClayDropDown
					active={isSegmentOrExperienceSelectorActive}
					alignmentPosition={Align.BottomLeft}
					menuElementAttrs={{
						containerProps: {
							className: 'cadmin',
						},
					}}
					onActiveChange={setIsSegmentOrExperienceSelectorActive}
					trigger={
						<ClayButton
							className="form-control-select text-left w-100"
							displayType="secondary"
							size="sm"
							type="button"
						>
							{selectedPreviewOption.label}
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList>
						{PREVIEW_OPTIONS.map((option) => (
							<ClayDropDown.Item
								active={
									option.value === selectedPreviewOption.value
								}
								key={option.value}
								onClick={() => {
									setIsSegmentOrExperienceSelectorActive(
										false
									);
									setSelectedPreviewOption(option);
								}}
							>
								{option.label}
							</ClayDropDown.Item>
						))}
					</ClayDropDown.ItemList>
				</ClayDropDown>
			</div>

			{selectedPreviewOption.value === 'segments' && (
				<SegmentSelector
					maximumDropdownEntries={MAXIMUM_DROPDOWN_ENTRIES}
					namespace={namespace}
					onMoreSegmentEntriesButtonClick={
						handleMoreSegmentEntriesButtonClick
					}
					onSelectSegmentEntry={setSelectedSegmentEntry}
					segmentsEntries={segmentsEntries}
					selectedSegmentEntry={selectedSegmentEntry}
				/>
			)}

			{selectedPreviewOption.value === 'experiences' && (
				<ExperienceSelector
					maximumDropdownEntries={MAXIMUM_DROPDOWN_ENTRIES}
					namespace={namespace}
					onMoreSegmentExperiencesButtonClick={
						handleMoreSegmentExperiencesButtonClick
					}
					onSelectSegmentExperience={setSelectedSegmentsExperience}
					segmentsExperiences={segmentsExperiences}
					selectedSegmentsExperience={selectedSegmentsExperience}
				/>
			)}
		</form>
	);
}

export default PageContentSelectors;
