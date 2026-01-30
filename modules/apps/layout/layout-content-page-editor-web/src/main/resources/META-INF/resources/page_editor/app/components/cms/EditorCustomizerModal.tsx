/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CardStyleModal} from '@liferay/layout-js-components-web';
import React, {useEffect, useState} from 'react';

import {config} from '../../config/index';

export default function EditorCustomizerModal() {
	if (!config.isCMS) {
		return null;
	}

	if (Liferay.FeatureFlags['LPD-74377']) {
		return <EnterpriseModal />;
	}

	return <IntroModal />;
}

function hasIntroModalBeenShown() {
	return Liferay.Util.Session.get(
		`${config.portletNamespace}hasExperienceModalBeenShown`
	);
}

function markIntroModalAsShown() {
	Liferay.Util.Session.set(
		`${config.portletNamespace}hasExperienceModalBeenShown`,
		'true'
	);
}

function IntroModal() {
	const [visible, setVisible] = useState(false);

	useEffect(() => {
		const handleVisibility = async () => {
			const shown = await hasIntroModalBeenShown();

			setVisible(shown !== 'true');
		};

		handleVisibility();
	}, []);

	if (!visible) {
		return null;
	}

	const handleClose = () => {
		setVisible(false);
		markIntroModalAsShown();
	};

	return (
		<CardStyleModal
			body={Liferay.Language.get(
				'you-can-now-tailor-the-content-editor-to-fit-your-unique-requirements'
			)}
			buttons={[
				{
					displayType: 'primary',
					label: Liferay.Language.get('got-it'),
					onClick: handleClose,
				},
			]}
			imageSrc={`${config.imagesPath}/editor_customizer.svg`}
			onCloseModal={handleClose}
			title={Liferay.Language.get('introducing-editor-customizer')}
		/>
	);
}

function EnterpriseModal() {
	return (
		<CardStyleModal
			badgeText={Liferay.Language.get('enterprise')}
			body={Liferay.Language.get(
				'editor-customization-is-available-on-enterprise-plans'
			)}
			buttons={[
				{
					displayType: 'secondary',
					label: Liferay.Language.get('contact-sales'),
				},
				{
					displayType: 'primary',
					label: Liferay.Language.get('try-it'),
				},
			]}
			imageSrc={`${config.imagesPath}/editor_customizer.svg`}
			title={Liferay.Language.get(
				'upgrade-to-unlock-the-editor-customizer'
			)}
		/>
	);
}
