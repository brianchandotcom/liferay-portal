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

import ClayIcon from '@clayui/icon';
import React from 'react';

import {redirectTo} from '../../../../../common/utils/liferay';
import Bubble from '../Bubble';

const SubSection = ({section, subSection}) => (
	<div
		className="dashboard-tasks-subsection my-2 row text-neutral-9 text-paragraph"
		onClick={() => redirectTo(section.name)}
	>
		<div className="col-9">{subSection.name}</div>

		<div className="align-items-center col-3 d-flex flex-row justify-content-between p-0">
			<Bubble value={subSection.value} />

			<div className="arrow-icon mr-2">
				<ClayIcon
					className="text-neutral-5"
					symbol="angle-right-small"
				/>
			</div>
		</div>
	</div>
);

export default SubSection;
