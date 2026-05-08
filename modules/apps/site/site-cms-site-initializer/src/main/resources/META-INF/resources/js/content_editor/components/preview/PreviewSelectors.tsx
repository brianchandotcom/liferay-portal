/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import AsyncPicker, {Status} from '../../../common/components/AsyncPicker';
import {Site} from './usePreviewState';

type Channel = {icon?: string; id: number; logoURL?: string; name: string};

type PreviewSelectorsProps = {
	channels: Channel[];
	displayPageTemplates: Site['displayPageTemplates'] | undefined;
	externalURL: string;
	isExternalURL: Boolean;
	loadSites: () => Promise<Site[]>;
	previewURL: string | undefined;
	selectChannel: (key: React.Key) => void;
	selectedChannelKey: React.Key | undefined;
	selectedDisplayPageKey: React.Key | undefined;
	setExternalURL: (value: string) => void;
	setSelectedDisplayPageKey: (key: React.Key) => void;
	showPreviewInNewTabLink?: boolean;
	sitesStatus: Status;
	vertical?: boolean;
};

export default function PreviewSelectors({
	channels,
	displayPageTemplates,
	externalURL,
	isExternalURL,
	loadSites,
	previewURL,
	selectChannel,
	selectedChannelKey,
	selectedDisplayPageKey,
	setExternalURL,
	setSelectedDisplayPageKey,
	showPreviewInNewTabLink,
	sitesStatus,
	vertical = false,
}: PreviewSelectorsProps) {
	const labelClassName = classNames('font-weight-semi-bold text-3', {
		'flex-shrink-0 mb-0': !vertical,
	});

	return (
		<>
			<div
				className={classNames({
					'align-items-center c-gap-3 d-flex': !vertical,
					'mb-3': vertical,
				})}
			>
				<span className={labelClassName}>
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

			<div
				className={classNames({
					'align-items-center c-gap-3 d-flex': !vertical,
					'flex-grow-1': isExternalURL,
				})}
			>
				{isExternalURL ? (
					<ExternalURLInput
						externalURL={externalURL}
						setExternalURL={setExternalURL}
						vertical={vertical}
					/>
				) : displayPageTemplates?.length ? (
					<>
						<span className={labelClassName}>
							{Liferay.Language.get('display-page')}
						</span>

						<Picker
							aria-label={Liferay.Language.get(
								'select-display-page'
							)}
							className={classNames({
								'form-control-sm': !vertical,
							})}
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
					</>
				) : null}

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
		</>
	);
}

type ExternalURLInputProps = {
	externalURL: string;
	setExternalURL: (value: string) => void;
	vertical: boolean;
};

function ExternalURLInput({
	externalURL,
	setExternalURL,
	vertical,
}: ExternalURLInputProps) {
	const [value, setValue] = useState<string>(externalURL);

	return (
		<ClayInput.Group>
			<ClayInput.GroupItem>
				<ClayInput
					aria-label={Liferay.Language.get('external-url')}
					insetAfter={!vertical}
					onBlur={(event) => {
						const absoluteURL = toAbsoluteURL(event.target.value);

						setValue(absoluteURL);
						setExternalURL(absoluteURL);
					}}
					onChange={(event) => setValue(event.target.value)}
					sizing={!vertical ? 'sm' : undefined}
					type="text"
					value={value}
				/>

				{!vertical ? (
					<ClayInput.GroupInsetItem after tag="span">
						<ClayButtonWithIcon
							displayType="unstyled"
							symbol="reload"
							title={Liferay.Language.get('refresh')}
						/>
					</ClayInput.GroupInsetItem>
				) : null}
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
}

function toAbsoluteURL(url: string): string {
	const trimmedURL = url.trim();

	if (!trimmedURL || /^https?:\/\//i.test(trimmedURL)) {
		return trimmedURL;
	}

	return `https://${trimmedURL}`;
}
