/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import React from 'react';

import {FormikFieldRadioGroup} from '../../../components/forms/formik';

export const SETTINGS_STEP_INITIAL_VALUES = {
	dataStrategy: 'MIRROR',
	userIdStrategy: 'CURRENT_USER_ID',
};

export default function SettingsStep({instance}: {instance: boolean}) {
	return (
		<>
			<ClayLayout.Sheet>
				<SectionHeader
					name="userIdStrategy"
					symbol="pencil"
					title={Liferay.Language.get('authorship-of-the-content')}
				/>

				<FormikFieldRadioGroup
					aria-labelledby="userIdStrategy-label"
					name="userIdStrategy"
					options={[
						{
							description: Liferay.Language.get(
								'use-the-original-author-help'
							),
							label: Liferay.Language.get(
								'use-the-original-author'
							),
							value: 'CURRENT_USER_ID',
						},
						{
							description: Liferay.Language.get(
								'use-the-current-user-as-author-help'
							),
							label: Liferay.Language.get(
								'use-the-current-user-as-author'
							),
							value: 'ALWAYS_CURRENT_USER_ID',
						},
					]}
				/>
			</ClayLayout.Sheet>

			<ClayLayout.Sheet>
				<SectionHeader
					name="dataStrategy"
					symbol="restore"
					title={Liferay.Language.get('update-data')}
				/>

				{instance ? (
					<div className="d-flex mb-2">
						<span
							aria-hidden="true"
							className="inline-item inline-item-before invisible small text-secondary"
						>
							<ClayIcon className="mr-1" symbol="restore" />
						</span>

						<div>
							<span className="d-block font-weight-semi-bold text-dark">
								{Liferay.Language.get('mirror')}
							</span>

							<span className="d-block small text-secondary">
								{Liferay.Language.get(
									'import-data-strategy-mirror-help'
								)}
							</span>
						</div>
					</div>
				) : (
					<FormikFieldRadioGroup
						aria-labelledby="dataStrategy-label"
						name="dataStrategy"
						options={[
							{
								description: Liferay.Language.get(
									'import-data-strategy-mirror-help'
								),
								label: Liferay.Language.get('mirror'),
								value: 'MIRROR',
							},
							{
								description: Liferay.Language.get(
									'import-data-strategy-mirror-with-overwriting-help'
								),
								label: Liferay.Language.get(
									'mirror-with-overwriting'
								),
								value: 'MIRROR_OVERWRITE',
							},
							{
								description: Liferay.Language.get(
									'import-data-strategy-copy-as-new-help'
								),
								label: Liferay.Language.get('copy-as-new'),
								value: 'COPY_AS_NEW',
							},
						]}
					/>
				)}
			</ClayLayout.Sheet>
		</>
	);
}

function SectionHeader({
	name,
	symbol,
	title,
}: {
	name?: string;
	symbol: string;
	title: string;
}) {
	return (
		<ClayLayout.SheetHeader className="mb-1">
			<div
				className="mb-2 sheet-title"
				id={name ? `${name}-label` : undefined}
			>
				<span className="inline-item inline-item-before small text-secondary">
					<ClayIcon className="mr-1" symbol={symbol} />
				</span>

				{title}
			</div>
		</ClayLayout.SheetHeader>
	);
}
