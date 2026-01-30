/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CardStyleModal} from '@liferay/layout-js-components-web';
import React, {useEffect, useState} from 'react';

import {config} from '../../config/index';

function hasBeenShown() {
	return Liferay.Util.Session.get(
		`${config.portletNamespace}hasExperienceModalBeenShown`
	);
}

function markAsShown() {
	Liferay.Util.Session.set(
		`${config.portletNamespace}hasExperienceModalBeenShown`,
		'true'
	);
}

export default function EditorCustomizerModal() {
	const [visible, setVisible] = useState(false);

	useEffect(() => {
		const handleVisibility = async () => {
			const shown = await hasBeenShown();

			setVisible(shown !== 'true');
		};

		handleVisibility();
	}, []);

	if (!config.isCMS || !visible) {
		return null;
	}

	const handleClose = () => {
		setVisible(false);
		markAsShown();
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
			heading={Liferay.Language.get('introducing-editor-customizer')}
			imageSrc={`${config.imagesPath}/editor_customizer.svg`}
			onCloseModal={handleClose}
		/>
	);
}
