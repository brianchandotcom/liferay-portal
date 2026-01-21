/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React from 'react';

import BasePage from '../components/BasePage';
import {IGenericStepProps} from '../components/Wizard';

const Footer: React.FC<
	{children?: React.ReactNode | undefined} & IGenericStepProps
> = ({backURL, exportURL, nextFn, previousFn}) => (
	<BasePage.Footer>
		{previousFn !== undefined && (
			<ClayButton displayType="unstyled" onClick={previousFn}>
				<span className="inline-item inline-item-before text-semibold">
					<ClayIcon className="mr-1" symbol="order-arrow-left" />

					{Liferay.Language.get('previous')}
				</span>
			</ClayButton>
		)}

		<div className="d-flex justify-content-end w-100">
			<ClayLink
				button
				className="mr-2"
				displayType="secondary"
				href={backURL}
			>
				{Liferay.Language.get('cancel')}
			</ClayLink>

			{nextFn && (
				<ClayButton onClick={nextFn}>
					{Liferay.Language.get('continue')}
				</ClayButton>
			)}

			{exportURL && (
				<ClayButton onClick={() => {}}>
					<span className="inline-item inline-item-before">
						<ClayIcon className="mr-1" symbol="export" />

						{Liferay.Language.get('export')}
					</span>
				</ClayButton>
			)}
		</div>
	</BasePage.Footer>
);

export default Footer;
