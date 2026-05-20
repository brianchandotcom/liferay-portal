/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React from 'react';

import AsyncPicker, {Status} from '../../../common/components/AsyncPicker';
import {Site} from './usePreviewState';

type Channel = {icon?: string; id: number; logoURL?: string; name: string};

type Props = {
	channels: Channel[];
	displayPageTemplates: Site['displayPageTemplates'] | undefined;
	loadSites: () => Promise<Site[]>;
	previewURL: string | undefined;
	selectChannel: (key: React.Key) => void;
	selectedChannelKey: React.Key | undefined;
	selectedDisplayPageKey: React.Key | undefined;
	setSelectedDisplayPageKey: (key: React.Key) => void;
	showPreviewInNewTabLink?: boolean;
	sitesStatus: Status;
	vertical?: boolean;
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
	vertical = false,
}: Props) {
	return (
		<>
			<div
				className={classNames({
					'align-items-center c-gap-3 d-flex': !vertical,
					'mb-3': vertical,
				})}
			>
				<span
					className={classNames('font-weight-semi-bold text-3', {
						'flex-shrink-0 mb-0': !vertical,
					})}
				>
					{Liferay.Language.get('channel')}
				</span>

				<AsyncPicker
					aria-label={Liferay.Language.get('select-channel')}
					items={channels}
					loader={loadSites}
					onSelectionChange={selectChannel}
					placeholder={Liferay.Language.get('select-channel')}
					selectedKey={selectedChannelKey}
					small={!vertical}
					status={sitesStatus}
					width={240}
				/>
			</div>

			{displayPageTemplates?.length ? (
				<div
					className={classNames({
						'align-items-center c-gap-3 d-flex': !vertical,
					})}
				>
					<span
						className={classNames('font-weight-semi-bold text-3', {
							'flex-shrink-0 mb-0': !vertical,
						})}
					>
						{Liferay.Language.get('display-page')}
					</span>

					<Picker
						aria-label={Liferay.Language.get('select-display-page')}
						className={classNames({'form-control-sm': !vertical})}
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
