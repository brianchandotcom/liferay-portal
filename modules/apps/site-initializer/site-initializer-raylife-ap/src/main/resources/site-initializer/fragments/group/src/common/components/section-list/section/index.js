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
