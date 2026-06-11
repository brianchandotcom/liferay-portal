/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayToolbar from '@clayui/toolbar';
import {ExperienceSelector} from '@liferay/layout-js-components-web';
import React, {useState} from 'react';

import {config} from '../config';
import LocaleSelector from './LocaleSelector';

interface Props {
	isSidePanelOpen: boolean;
	openSidePanel: () => void;
}

export default function Toolbar({isSidePanelOpen, openSidePanel}: Props) {
	const {availableSegmentsExperiences} = config;

	const [selectedExperienceERC, setSelectedExperienceERC] = useState<
		React.Key | undefined
	>(availableSegmentsExperiences[0]?.segmentsExperienceERC);

	const selectedExperience = availableSegmentsExperiences.find(
		({segmentsExperienceERC}) =>
			segmentsExperienceERC === selectedExperienceERC
	);

	return (
		<ClayToolbar className="bg-white px-3 version-history__toolbar">
			<ClayToolbar.Nav>
				{!isSidePanelOpen ? (
					<ClayToolbar.Item>
						<ClayButtonWithIcon
							displayType="secondary"
							onClick={openSidePanel}
							size="sm"
							symbol="angle-double-right"
							title={Liferay.Language.get(
								'open-version-history-panel'
							)}
						/>
					</ClayToolbar.Item>
				) : null}

				{selectedExperience ? (
					<ClayToolbar.Item className="align-items-center d-flex flex-row version-history__toolbar-experience">
						<span className="font-weight-semi-bold mr-1 text-dark text-nowrap">
							{Liferay.Language.get('experience')}
						</span>

						<ExperienceSelector
							className="mb-0"
							onChangeExperience={setSelectedExperienceERC}
							segmentsExperiences={availableSegmentsExperiences}
							selectedSegmentsExperience={selectedExperience}
						/>
					</ClayToolbar.Item>
				) : null}

				<ClayToolbar.Item className="align-items-center d-flex">
					<LocaleSelector />
				</ClayToolbar.Item>
			</ClayToolbar.Nav>
		</ClayToolbar>
	);
}
