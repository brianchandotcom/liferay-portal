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
	const [selectedSegment, setSelectedSegment] = useState(null);

	const formRef = useRef(null);

	const fetchDeactivateSimulation = useCallback(() => {
		fetch(deactivateSimulationURL, {
			body: new FormData(formRef.current),
			method: 'POST',
		}).then(() => {
			const simulationElements = document.querySelectorAll(
				`#${formRef.current.id} input`
			);

			for (let i = 0; i < simulationElements.length; i++) {
				simulationElements[i].setAttribute('checked', false);
			}
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

	useEffect(() => {
		formRef.current.addEventListener('change', simulateSegmentsEntries);

		const deactivateSimulationEventHandler = Liferay.on(
			'SimulationMenu:closeSimulationPanel',
			fetchDeactivateSimulation
		);

		const openSimulationPanelEventHandler = Liferay.on(
			'SimulationMenu:openSimulationPanel',
			simulateSegmentsEntries
		);

		return () => {
			deactivateSimulationEventHandler.detach();
			openSimulationPanelEventHandler.detach();
			formRef.removeEventListener('change', simulateSegmentsEntries);
		};
	}, [fetchDeactivateSimulation, simulateSegmentsEntries]);

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

					<ul className="list-unstyled">
						{selectedPreviewOption === 'segments' && (
							<div className="form-group">
								<label htmlFor={`${namespace}segmentsEntryId`}>
									{Liferay.Language.get('segments')}
								</label>

								<ClaySelectWithOption
									id={`${namespace}segmentsEntryId`}
									name={`${namespace}segmentsEntryId`}
									onChange={({target}) => {
										setSelectedSegment(target.value);
									}}
									options={segmentsEntries.map((segment) => {
										return {
											label: segment.name,
											value: segment.id,
										};
									})}
									value={selectedSegment}
								/>
							</div>
						)}
					</ul>
				</form>
			)}
		</>
	);
}

export default SegmentsAndExperiencesSelector;
