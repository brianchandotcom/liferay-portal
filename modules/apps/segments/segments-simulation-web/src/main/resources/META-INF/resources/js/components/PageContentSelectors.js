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
import {ClaySelectWithOption} from '@clayui/form';
import ClayLink from '@clayui/link';
import {fetch, openSelectionModal, sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import ExperienceSelector from './ExperienceSelector';
import SegmentSelector from './SegmentSelector';

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

	const handleMoreSegmentEntriesButtonClick = () => {
		openSelectionModal({
			onSelect: (selectedItem) => {
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
			onSelect: (selectedItem) => {
				const valueJSON = JSON.parse(selectedItem.value);
				const selectedExperience = segmentsExperiences.find(
					(exp) =>
						exp.segmentsExperienceId ===
						valueJSON.segmentsExperienceId
				);
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

				<ClaySelectWithOption
					id={`${namespace}segmentsOrExperiences`}
					onChange={({target}) => {
						setSelectedPreviewOption(target.value);
					}}
					options={PREVIEW_OPTIONS}
					value={selectedPreviewOption}
				/>
			</div>

			{selectedPreviewOption === 'segments' && (
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

			{selectedPreviewOption === 'experiences' && (
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
