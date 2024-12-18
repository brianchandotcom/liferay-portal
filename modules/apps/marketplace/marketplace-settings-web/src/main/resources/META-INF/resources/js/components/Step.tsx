/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiStepNav from '@clayui/multi-step-nav';
import React from 'react';

type StepProps = {
	step: number;
	steps: {subTitle?: string; title: string}[];
};

export default function Step({step, steps}: StepProps) {
	return (
		<ClayMultiStepNav>
			{steps.map(({subTitle, title}, i) => {
				const complete = step > i;

				return (
					<ClayMultiStepNav.Item
						active={step === i}
						expand={i + 1 !== steps.length}
						key={i}
						state={complete ? 'complete' : undefined}
					>
						<ClayMultiStepNav.Title>{title}</ClayMultiStepNav.Title>

						<ClayMultiStepNav.Divider />

						<ClayMultiStepNav.Indicator
							complete={complete}
							label={1 + i}
							subTitle={subTitle}
						/>
					</ClayMultiStepNav.Item>
				);
			})}
		</ClayMultiStepNav>
	);
}
