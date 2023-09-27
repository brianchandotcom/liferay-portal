/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

import {redirectTo} from '../../../utils/liferay';
import Bubble from '../../bubble';

const SubSection = ({section, subSection}) => (
	<div
		className={classNames(
			'my-2 row subsection-container text-neutral-9 text-paragraph',
			{
				'd-none': !subSection.active,
			}
		)}
		onClick={() => redirectTo(section.link)}
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
