/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import SubSection from '../subsection';

const Section = ({index, section}) => (
	<div
		className={classNames('border-bottom-dashed section-box', {
			'mt-2': index !== 0,
		})}
	>
		<div className="h6 py-2 section-header text-neutral-8 text-small-caps">
			{section.name}
		</div>

		<div className="d-flex flex-column section-content">
			{section.subSections.map((subSection, index) => (
				<SubSection
					key={index}
					section={section}
					subSection={subSection}
				/>
			))}
		</div>
	</div>
);

export default Section;
