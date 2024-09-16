/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLink from '@clayui/link';
import {
	ExperienceSelector,
	SegmentExperience,
} from '@liferay/layout-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import SegmentEntry from '../../types/SegmentEntry';
import PreviewSelector from './PreviewSelector';
import SegmentSelector from './SegmentSelector';

interface Props {
	namespace: string;
	portletNamespace: string;
	segmentationEnabled: boolean;
	segmentsCompanyConfigurationURL: string;
	segmentsEntries: SegmentEntry[];
	segmentsExperiences: SegmentExperience[];
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

function PageContentSelectors({
	namespace,
	segmentationEnabled,
	segmentsCompanyConfigurationURL,
	segmentsEntries,
	segmentsExperiences,
}: Props) {
	const [alertVisible, setAlertVisible] = useState(!segmentationEnabled);
	const [selectedPreviewOption, setSelectedPreviewOption] = useState(
		DEFAULT_PREVIEW_OPTION
	);
	const [selectedSegmentEntry, setSelectedSegmentEntry] = useState(
		segmentsEntries?.[0]
	);
	const [selectedSegmentsExperience, setSelectedSegmentsExperience] =
		useState(segmentsExperiences?.[0]);

	const formRef = useRef(null);
	const firstRenderRef = useRef(true);

	const simulateSegmentsEntries = useCallback(
		(segmentsEntryId) => {
			const iframe = document.querySelector('iframe');

			if (iframe) {
				Liferay.componentReady('SimulationPreview').then(
					(simulationPreview) => {
						simulationPreview.setMessage(
							sub(
								Liferay.Language.get(
									'showing-content-for-the-segment-x'
								),
								selectedSegmentEntry?.name
							)
						);
					}
				);
				const url = new URL(iframe.src);

				url.searchParams.set('segmentsEntryId', segmentsEntryId);
				iframe.src = url.toString();
			}
		},
		[selectedSegmentEntry]
	);

	const simulateSegmentsExperience = useCallback(
		(experience) => {
			const iframe = document.querySelector('iframe');

			if (iframe) {
				Liferay.componentReady('SimulationPreview').then(
					(simulationPreview) => {
						simulationPreview.setMessage(
							sub(
								Liferay.Language.get(
									'showing-content-for-the-experience-x'
								),
								selectedSegmentsExperience?.segmentsExperienceName
							)
						);
					}
				);
				const url = new URL(iframe.src);

				url.searchParams.set('segmentsExperienceId', experience);
				iframe.src = url.toString();
			}
		},
		[selectedSegmentsExperience]
	);

	useEffect(() => {
		const simulationToggle = document.querySelector(
			'.product-menu-toggle.lfr-has-simulation-panel'
		);

		if (!simulationToggle) {
			return;
		}

		const sidenavInstance =

			// @ts-ignore

			Liferay.SideNavigation.initialize(simulationToggle);

		sidenavInstance.on('open.lexicon.sidenav', () => {
			if (!firstRenderRef.current) {
				simulateSegmentsEntries(selectedSegmentEntry?.id);
			}
		});

		if (firstRenderRef.current) {
			if (sidenavInstance && sidenavInstance.visible()) {
				firstRenderRef.current = false;
				simulateSegmentsEntries(selectedSegmentEntry?.id);
			}
		}

		return () => {

			// @ts-ignore

			Liferay.SideNavigation.destroy(simulationToggle);
		};

		// eslint-disable-next-line react-hooks/exhaustive-deps
	});

	useEffect(() => {
		if (!firstRenderRef.current) {
			simulateSegmentsEntries(selectedSegmentEntry?.id);
		}
	}, [selectedSegmentEntry, simulateSegmentsEntries]);

	useEffect(() => {
		if (!firstRenderRef.current) {
			simulateSegmentsExperience(
				selectedSegmentsExperience?.segmentsExperienceId
			);
		}
	}, [selectedSegmentsExperience, simulateSegmentsExperience]);

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

			simulateSegmentsEntries(selectedSegmentEntry?.id);
		}
		else {
			simulateSegmentsExperience(
				selectedSegmentsExperience?.segmentsExperienceId
			);
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

			<PreviewSelector
				namespace={namespace}
				onSelectPreviewOption={(key: React.Key) => {
					const selectedOption = PREVIEW_OPTIONS.find(
						({value}) => value === key
					);

					if (selectedOption) {
						setSelectedPreviewOption(selectedOption);
					}
				}}
				previewOptions={PREVIEW_OPTIONS}
				selectedPreviewOption={selectedPreviewOption}
			/>

			{selectedPreviewOption.value === 'segments' && (
				<SegmentSelector
					namespace={namespace}
					onSelectSegmentEntry={(key: React.Key) => {
						const selectedSegment = segmentsEntries.find(
							({id}) => id.toString() === key
						);

						if (selectedSegment) {
							setSelectedSegmentEntry(selectedSegment);
						}
					}}
					segmentsEntries={segmentsEntries}
					selectedSegmentEntry={selectedSegmentEntry}
				/>
			)}

			{selectedPreviewOption.value === 'experiences' &&
				(segmentsExperiences.length < 2 ? (
					<p>
						{Liferay.Language.get(
							'no-experiences-have-been-added-yet'
						)}
					</p>
				) : (
					<ExperienceSelector
						label={Liferay.Language.get('experience')}
						onChangeExperience={(key: React.Key) => {
							const selectedExperience = segmentsExperiences.find(
								({segmentsExperienceId}) =>
									segmentsExperienceId === key
							);

							if (selectedExperience) {
								setSelectedSegmentsExperience(
									selectedExperience
								);
							}
						}}
						segmentsExperiences={segmentsExperiences}
						selectedSegmentsExperience={selectedSegmentsExperience}
					/>
				))}
		</form>
	);
}

export default PageContentSelectors;
