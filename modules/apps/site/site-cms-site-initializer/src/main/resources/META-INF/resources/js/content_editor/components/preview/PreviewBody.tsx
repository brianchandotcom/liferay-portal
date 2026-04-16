/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {Option, Picker} from '@clayui/core';
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {sub} from 'frontend-js-web';
import React, {useCallback, useId, useMemo, useState} from 'react';

import ApiHelper from '../../../common/services/ApiHelper';
import AsyncPicker from '../../../structure_builder/components/AsyncPicker';
import {useCache} from '../../../structure_builder/contexts/CacheContext';

export type Site = {
	displayPageTemplates: {
		name: string;
		plid: string;
		url: string;
	}[];
	groupId: string;
	logoURL: string;
	name: string;
};

export default function PreviewBody({
	getPreviewDataURL,
}: {
	getPreviewDataURL: string;
}) {
	const [selectedChannelKey, setSelectedChannelKey] = useState<React.Key>('');
	const [selectedDisplayPageKey, setSelectedDisplayPageKey] =
		useState<React.Key>('');

	const channelPickerId = useId();
	const displayPageTemplatePickerId = useId();

	const getSites = useCallback(async () => {
		const {data, error} = await ApiHelper.get<Site[]>(getPreviewDataURL);

		if (data) {
			return data;
		}

		throw new Error(error);
	}, [getPreviewDataURL]);

	const {
		data: sites = [],
		load: loadSites,
		status,
	} = useCache<Site[]>('sites', getSites);

	const channels = useMemo(
		() => [
			...sites.map(({groupId, logoURL, name}) => ({
				id: Number(groupId),
				logoURL,
				name,
			})),
			{
				icon: 'chain-broken',
				id: 1,
				name: Liferay.Language.get('external-url'),
			},
		],
		[sites]
	);

	const displayPageTemplates = useMemo(
		() =>
			sites.find(({groupId}) => selectedChannelKey === groupId)
				?.displayPageTemplates,
		[selectedChannelKey, sites]
	);

	const previewURL = useMemo(
		() =>
			displayPageTemplates?.find(
				({plid}) => plid === selectedDisplayPageKey
			)?.url,
		[displayPageTemplates, selectedDisplayPageKey]
	);

	return (
		<>
			<div className="border-bottom c-gap-3 d-flex flex-wrap mb-0 p-3">
				<div className="align-items-center c-gap-3 d-flex">
					<label
						className="flex-shrink-0 mb-0"
						htmlFor={channelPickerId}
					>
						{Liferay.Language.get('Channel')}
					</label>

					<AsyncPicker
						aria-label={Liferay.Language.get('select-channel')}
						id={channelPickerId}
						items={channels}
						loader={loadSites}
						onSelectionChange={setSelectedChannelKey}
						placeholder={Liferay.Language.get('select-channel')}
						selectedKey={selectedChannelKey}
						small
						status={status}
						width={240}
					/>
				</div>

				{displayPageTemplates?.length ? (
					<div className="align-items-center c-gap-3 d-flex">
						<label
							className="flex-shrink-0 mb-0"
							htmlFor={displayPageTemplatePickerId}
						>
							{Liferay.Language.get('display-page')}
						</label>

						<Picker
							aria-label={Liferay.Language.get(
								'select-display-page'
							)}
							className="form-control-sm"
							id={displayPageTemplatePickerId}
							items={displayPageTemplates}
							onSelectionChange={setSelectedDisplayPageKey}
							placeholder={Liferay.Language.get(
								'select-display-page'
							)}
							selectedKey={selectedDisplayPageKey}
							width={240}
						>
							{({name, plid}) => (
								<Option key={plid}>{name}</Option>
							)}
						</Picker>

						{previewURL ? (
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
			</div>

			<div
				className="align-items-center content-editor__preview__content d-flex position-relative"
				{...(previewURL && {inert: ''})}
			>
				{selectedChannelKey &&
				displayPageTemplates !== undefined &&
				!displayPageTemplates.length ? (
					<ClayAlert
						className="fixed-top m-3 position-absolute"
						displayType="info"
						title={Liferay.Language.get('info')}
					>
						{Liferay.Language.get(
							'no-display-page-templates-available-for-preview-in-this-channel'
						)}
					</ClayAlert>
				) : null}

				{previewURL ? (
					<iframe
						className="border-0 d-block h-100 w-100"
						src={previewURL}
						tabIndex={-1}
						title={Liferay.Language.get('preview')}
					/>
				) : (
					<ClayEmptyState
						className="mt-0"
						description={Liferay.Language.get(
							'select-a-channel-and-save-as-draft-or-publish-to-see-your-changes-here'
						)}
						imgSrc={`${Liferay.ThemeDisplay.getPathContext()}/o/fragment-collection-contributor-inputs/drag_drop_illustration.svg`}
						small
						title={Liferay.Language.get('nothing-to-show-yet')}
					/>
				)}
			</div>
		</>
	);
}
