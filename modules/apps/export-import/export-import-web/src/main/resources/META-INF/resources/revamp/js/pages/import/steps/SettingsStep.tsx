/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import React from 'react';

import {FormikFieldRadioGroup} from '../../../components/forms/formik';
import {
	DATA_STRATEGIES,
	DataStrategy,
	USER_ID_STRATEGIES,
} from '../../../types/exportImportProcess';
import {SCOPES, Scope} from '../../../types/scope';

export const SETTINGS_STEP_INITIAL_VALUES = {
	dataStrategy: DATA_STRATEGIES.MIRROR,
	userIdStrategy: USER_ID_STRATEGIES.CURRENT_USER_ID,
};

const DATA_STRATEGY_LABELS: Record<
	DataStrategy,
	{description: string; label: string}
> = {
	[DATA_STRATEGIES.MIRROR]: {
		description: Liferay.Language.get('import-data-strategy-mirror-help'),
		label: Liferay.Language.get('mirror'),
	},
	[DATA_STRATEGIES.MIRROR_OVERWRITE]: {
		description: Liferay.Language.get(
			'import-data-strategy-mirror-with-overwriting-help'
		),
		label: Liferay.Language.get('mirror-with-overwriting'),
	},
};

const DATA_STRATEGY_OPTIONS: Record<Scope, DataStrategy[]> = {
	[SCOPES.COMPANY]: [DATA_STRATEGIES.MIRROR],
	[SCOPES.DEPOT]: [DATA_STRATEGIES.MIRROR, DATA_STRATEGIES.MIRROR_OVERWRITE],
	[SCOPES.PORTLET]: [
		DATA_STRATEGIES.MIRROR,
		DATA_STRATEGIES.MIRROR_OVERWRITE,
	],
	[SCOPES.SITE]: [DATA_STRATEGIES.MIRROR, DATA_STRATEGIES.MIRROR_OVERWRITE],
};

export default function SettingsStep({scope}: {scope: Scope}) {
	const dataStrategyOptions = (
		DATA_STRATEGY_OPTIONS[scope] ?? DATA_STRATEGY_OPTIONS[SCOPES.SITE]
	).map((value) => ({
		...DATA_STRATEGY_LABELS[value],
		value,
	}));

	return (
		<>
			<ClayLayout.Sheet>
				<SectionHeader
					name="userIdStrategy"
					symbol="signature"
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
							value: USER_ID_STRATEGIES.CURRENT_USER_ID,
						},
						{
							description: Liferay.Language.get(
								'use-the-current-user-as-author-help'
							),
							label: Liferay.Language.get(
								'use-the-current-user-as-author'
							),
							value: USER_ID_STRATEGIES.ALWAYS_CURRENT_USER_ID,
						},
					]}
				/>
			</ClayLayout.Sheet>

			<ClayLayout.Sheet className="mt-4">
				<SectionHeader
					name="dataStrategy"
					symbol="reset"
					title={Liferay.Language.get('update-data')}
				/>

				{dataStrategyOptions.length === 1 ? (
					<ReadOnlyOption
						option={dataStrategyOptions[0]}
						symbol="reset"
					/>
				) : (
					<FormikFieldRadioGroup
						aria-labelledby="dataStrategy-label"
						name="dataStrategy"
						options={dataStrategyOptions}
					/>
				)}
			</ClayLayout.Sheet>
		</>
	);
}

function ReadOnlyOption({
	option,
	symbol,
}: {
	option: {description: string; label: string};
	symbol: string;
}) {
	return (
		<ClayLayout.ContentRow className="mb-2">
			<ClayLayout.ContentCol expand={false}>
				<span
					aria-hidden="true"
					className="inline-item inline-item-before invisible small text-secondary"
				>
					<ClayIcon className="mr-1" symbol={symbol} />
				</span>
			</ClayLayout.ContentCol>

			<ClayLayout.ContentCol expand>
				<span className="d-block font-weight-semi-bold text-dark">
					{option.label}
				</span>

				<span className="d-block small text-secondary">
					{option.description}
				</span>
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
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
