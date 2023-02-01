/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import React from 'react';

import {slugToText, stringToSlug} from '../utils/utils.es';

const getSectionTitle = (section) => {
	if (section.friendlyUrlPath === undefined) {
		return section.title;
	}

	if (stringToSlug(section.title) === section.friendlyUrlPath) {
		return section.title;
	}

	return `${section.title} (${slugToText(section.friendlyUrlPath)})`;
};

export default function SectionLabel({section}) {
	if (!section) {
		return null;
	}

	return (
		<ClayLabel
			className="bg-light border-0 stretched-link-layer text-uppercase"
			displayType="secondary"
			large
		>
			{getSectionTitle(section)}
		</ClayLabel>
	);
}

export {getSectionTitle};
