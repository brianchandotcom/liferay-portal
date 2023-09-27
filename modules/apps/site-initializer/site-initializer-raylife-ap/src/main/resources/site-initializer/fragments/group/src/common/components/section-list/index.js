/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import Section from './section';

import './index.css';

const SectionList = ({sections}) => {
	return (
		<div className="d-flex flex-column mx-3 section-list-container">
			{sections.map((section, index) => {
				if (section.active) {
					return (
						<Section index={index} key={index} section={section} />
					);
				}
			})}
		</div>
	);
};

export default SectionList;
