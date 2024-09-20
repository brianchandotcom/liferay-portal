/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiStepNav from '@clayui/multi-step-nav';
import React from 'react';

const StepTracker = ({stepModels}) => {
	return (
		<ClayMultiStepNav indicatorLabel="top">
			{stepModels.map(({label, state}, i) => {
				const complete = state === 'completed';

				return (
					<ClayMultiStepNav.Item
						active={state === 'active'}
						expand={i + 1 !== stepModels.length}
						key={i}
						state={complete ? 'complete' : undefined}
					>
						<ClayMultiStepNav.Divider />

						<ClayMultiStepNav.Indicator
							complete={complete}
							subTitle={label}
						/>
					</ClayMultiStepNav.Item>
				);
			})}
		</ClayMultiStepNav>
	);
};

export default StepTracker;
