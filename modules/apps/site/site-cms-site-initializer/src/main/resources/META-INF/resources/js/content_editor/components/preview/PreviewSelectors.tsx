/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {sub} from 'frontend-js-web';
import React, {useId} from 'react';

import AsyncPicker from '../../../structure_builder/components/AsyncPicker';
import {CacheStatus} from '../../../structure_builder/contexts/CacheContext';
import {Site} from './usePreviewState';

type Channel = {icon?: string; id: number; logoURL?: string; name: string};

type Props = {
	channels: Channel[];
	displayPageTemplates: Site['displayPageTemplates'] | undefined;
	loadSites: () => Promise<Site[]>;
	previewURL: string | undefined;
	selectChannel: (key: React.Key) => void;
	selectedChannelKey: React.Key;
	selectedDisplayPageKey: React.Key;
	setSelectedDisplayPageKey: (key: React.Key) => void;
	showPreviewInNewTabLink?: boolean;
	sitesStatus: CacheStatus;
};

export default function PreviewSelectors({
	channels,
	displayPageTemplates,
	loadSites,
	previewURL,
	selectChannel,
	selectedChannelKey,
	selectedDisplayPageKey,
	setSelectedDisplayPageKey,
	showPreviewInNewTabLink,
	sitesStatus,
}: Props) {
	return (
		<>
			<div className="align-items-center c-gap-3 d-flex">
				<span className="'font-weight-semi-bold flex-shrink-0 mb-0 text-3'">
					{Liferay.Language.get('channel')}
				</span>

				<AsyncPicker
					aria-label={Liferay.Language.get('select-channel')}
					items={channels}
					loader={loadSites}
					onSelectionChange={selectChannel}
					placeholder={Liferay.Language.get('select-channel')}
					selectedKey={selectedChannelKey}
					small
					status={sitesStatus}
					width={240}
				/>
			</div>

			{displayPageTemplates?.length ? (
				<div className="align-items-center c-gap-3 d-flex">
					<span className="'font-weight-semi-bold flex-shrink-0 mb-0 text-3'">
						{Liferay.Language.get('display-page')}
					</span>

					<Picker
						aria-label={Liferay.Language.get('select-display-page')}
						className="form-control-sm"
						items={displayPageTemplates}
						onSelectionChange={setSelectedDisplayPageKey}
						placeholder={Liferay.Language.get(
							'select-display-page'
						)}
						selectedKey={selectedDisplayPageKey}
						width={240}
					>
						{({name, plid}) => <Option key={plid}>{name}</Option>}
					</Picker>

					{showPreviewInNewTabLink && previewURL ? (
						<ClayLink
							borderless
							className="flex-shrink-0"
							displayType="primary"
							href={previewURL}
							monospaced
							outline
							rel="noopener noreferrer"
							target="_blank"
							title={sub(
								Liferay.Language.get('open-x-in-a-new-tab'),
								Liferay.Language.get('preview')
							)}
						>
							<ClayIcon symbol="shortcut" />
						</ClayLink>
					) : null}
				</div>
			) : null}
		</>
	);
}
