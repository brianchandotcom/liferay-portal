/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React, {PropsWithChildren, useId} from 'react';

import {getImage} from '../util/getImage';

export interface NewSpaceFormSectionProps {
	description: string;
	onSubmit?: (event: React.FormEvent<HTMLFormElement>) => void;
	step: 1 | 2;
	title: string;
	withForm?: boolean;
}

export function NewSpaceFormSection({
	children,
	description,
	onSubmit,
	step,
	title,
	withForm = true,
}: PropsWithChildren<NewSpaceFormSectionProps>) {
	const logoDescriptionId = useId();

	const pageContent = (
		<>
			<ClayLayout.Container className="mb-5 p-0">
				<ClayLayout.ContentRow className="align-items-center mb-6">
					<img
						aria-labelledby={logoDescriptionId}
						src={getImage('cms_logo.svg')}
					></img>

					<span
						className="font-weight-bold ms-3 text-7"
						id={logoDescriptionId}
					>
						{Liferay.Language.get('cms-product')}
					</span>
				</ClayLayout.ContentRow>

				<p className="mb-2 text-secondary">
					{sub(Liferay.Language.get('step-x-of-x'), [step, 2])}
				</p>

				<h1 className="font-semibold mb-4 text-7">{title}</h1>

				<p className="text-5 text-secondary">{description}</p>
			</ClayLayout.Container>

			{children}
		</>
	);

	return withForm ? (
		<ClayForm onSubmit={onSubmit}>{pageContent}</ClayForm>
	) : (
		<ClayLayout.Container>{pageContent}</ClayLayout.Container>
	);
}
