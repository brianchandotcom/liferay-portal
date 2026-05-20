/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import React from 'react';

import {FormikFieldRadioGroup} from '../../../components/forms/formik';

export const SETTINGS_STEP_INITIAL_VALUES = {
	authorshipStrategy: 'useOriginalAuthor',
	updateDataStrategy: 'mirror',
};

export default function SettingsStep() {
	return (
		<>
			<ClayLayout.Sheet>
				<SectionHeader
					name="authorshipStrategy"
					symbol="pencil"
					title={Liferay.Language.get('authorship-of-the-content')}
				/>

				<FormikFieldRadioGroup
					aria-labelledby="authorshipStrategy-label"
					name="authorshipStrategy"
					options={[
						{
							description: Liferay.Language.get(
								'use-the-original-author-help'
							),
							label: Liferay.Language.get(
								'use-the-original-author'
							),
							value: 'useOriginalAuthor',
						},
						{
							description: Liferay.Language.get(
								'use-the-current-user-as-author-help'
							),
							label: Liferay.Language.get(
								'use-the-current-user-as-author'
							),
							value: 'useCurrentUserAsAuthor',
						},
					]}
				/>
			</ClayLayout.Sheet>

			<ClayLayout.Sheet>
				<SectionHeader
					name="updateDataStrategy"
					symbol="restore"
					title={Liferay.Language.get('update-data')}
				/>

				<FormikFieldRadioGroup
					aria-labelledby="updateDataStrategy-label"
					name="updateDataStrategy"
					options={[
						{
							description: Liferay.Language.get(
								'import-data-strategy-mirror-help'
							),
							label: Liferay.Language.get('mirror'),
							value: 'mirror',
						},
						{
							description: Liferay.Language.get(
								'import-data-strategy-mirror-with-overwriting-help'
							),
							label: Liferay.Language.get(
								'mirror-with-overwriting'
							),
							value: 'mirrorWithOverwriting',
						},
						{
							description: Liferay.Language.get(
								'import-data-strategy-copy-as-new-help'
							),
							label: Liferay.Language.get('copy-as-new'),
							value: 'copyAsNew',
						},
					]}
				/>
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
