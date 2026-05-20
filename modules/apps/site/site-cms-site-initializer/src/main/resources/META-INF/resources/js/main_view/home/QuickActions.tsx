/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {navigate, sub} from 'frontend-js-web';
import React, {MouseEvent} from 'react';

import {AssetLibrary} from '../../common/types/AssetLibrary';
import createAssetAction, {
	AssetData,
} from '../props_transformer/actions/createAssetAction';

import '../../../css/home/QuickActions.scss';

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';

export type QuickActionAssetData = {
	action: 'createAsset' | 'createVocabulary';
	assetLibraries?: AssetLibrary[];
	icon: string;
	redirect: string;
	title: string;
};

export default function QuickActions({
	quickActions,
}: {
	quickActions: QuickActionAssetData[];
}) {
	const handleActionClick = (
		event: MouseEvent<HTMLButtonElement>,
		quickAction: QuickActionAssetData
	) => {
		event.preventDefault();

		if (quickAction.action === 'createVocabulary') {
			navigate(quickAction.redirect);
		}
		else {
			createAssetAction({
				...(quickAction as AssetData),
			});
		}
	};

	return (
		<div className="quick-actions">
			<div className="pb-2 pt-2 row w-100">
				<div className="col">
					<span
						aria-level={2}
						className="font-weight-semi-bold text-4"
						role="heading"
					>
						{Liferay.Language.get('quick-actions')}
					</span>
				</div>
			</div>

			<div className="row row-cols-lg-5 row-cols-md-3 row-cols-sm-1">
				{quickActions.map((quickAction: QuickActionAssetData) => (
					<div
						className="col d-flex pb-2"
						key={`${quickAction.title}-${quickAction.redirect}`}
					>
						<ClayButton
							className="flex-fill mr-2 pb-3 pl-4 pr-4 pt-3 quick-action text-dark text-left w-100"
							data-canonical-name={sub(
								Liferay.Language.get('quick-action-x'),
								quickAction.title
							)}
							displayType="secondary"
							onClick={(event) =>
								handleActionClick(event, quickAction)
							}
						>
							<span className="inline-item inline-item-before">
								<ClayIcon symbol={quickAction.icon} />
							</span>

							{quickAction.title}
						</ClayButton>
					</div>
				))}
			</div>
		</div>
	);
}
