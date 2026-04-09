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

	if (config.freeTier) {
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
				},
			]}
			imageSrc={`${config.imagesPath}/editor_customizer.svg`}
			onCloseModal={handleClose}
			title={Liferay.Language.get('introducing-editor-customization')}
		/>
	);
}

function EnterpriseModal() {
	const [visible, setVisible] = useState(true);

	if (!visible) {
		return null;
	}

	return (
		<CardStyleModal
			body={Liferay.Language.get(
				'editor-customization-is-available-on-the-enterprise-subscription'
			)}
			buttons={[
				{
					displayType: 'secondary',
					label: Liferay.Language.get('try-it'),
				},
				{
					displayType: 'primary',
					href: 'https://www.liferay.com/web/lr/cms-upgrade',
					icon: 'shortcut',
					label: Liferay.Language.get('contact-sales'),
				},
			]}
			imageSrc={`${config.imagesPath}/editor_customizer.svg`}
			onCloseModal={() => setVisible(false)}
			showEnterpriseIndicator
			title={Liferay.Language.get(
				'upgrade-to-unlock-the-editor-customization'
			)}
		/>
	);
}
