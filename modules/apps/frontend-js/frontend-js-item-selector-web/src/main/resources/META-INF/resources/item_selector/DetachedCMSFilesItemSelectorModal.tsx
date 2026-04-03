/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import {
	IInlineNotificationComponent,
	TSort,
} from '@liferay/frontend-data-set-web';
import {useBrowserTabVisibility} from '@liferay/frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

import ItemSelectorModal from './ItemSelectorModal';
import {TDetachedItemSelectorModal} from './types';

import '../css/DetachedCMSFilesItemSelectorModal.scss';

async function checkNewCMSFiles(
	cmsRootFilesURL: string,
	lastRequestTime: string
) {
	const response = await fetch(
		`${cmsRootFilesURL} and dateCreated gt ${lastRequestTime}`
	);

	if (!response.ok) {
		return {totalCount: 0};
	}

	return (await response.json()) as {totalCount: number};
}

const DetachedCMSFilesItemSelectorModal = <T extends Record<string, any>>(
	props: TDetachedItemSelectorModal<T>
) => {
	const {observer, onOpenChange, open} = useModal();
	const [newItemsCount, setNewItemsCount] = useState(0);
	const [showInlineNotification, setShowInlineNotification] = useState(false);

	const isBrowserTabVisible = useBrowserTabVisibility();
	const lastRequestTimeRef = useRef(new Date().toISOString());

	useEffect(() => {
		onOpenChange(true);
	}, [onOpenChange]);

	useEffect(() => {
		if (isBrowserTabVisible && open) {
			checkNewCMSFiles(props.apiURL, lastRequestTimeRef.current).then(
				(response) => {
					if (response.totalCount > 0) {
						setNewItemsCount(response.totalCount);
						setShowInlineNotification(true);

						lastRequestTimeRef.current = new Date().toISOString();
					}
				}
			);
		}
	}, [isBrowserTabVisible, open, props.apiURL]);

	const NewItemsNotificationComponent = ({
		context,
	}: {
		context: IInlineNotificationComponent['context'];
	}) => {
		if (!showInlineNotification) {
			return null;
		}

		return (
			<ClayAlert
				className="detached-cms-files-alert mx-n3 pl-5 pr-1"
				displayType="info"
				onClose={() => setShowInlineNotification(false)}
				title={Liferay.Language.get('info')}
				variant="stripe"
			>
				{Liferay.Util.sub(
					Liferay.Language.get(
						'x-new-items-are-not-visible-in-this-view'
					),
					[newItemsCount]
				)}

				<ClayButton.Group className="pl-3" spaced>
					<ClayButton
						displayType="info"
						onClick={() => {
							const updatedSorts: TSort[] = (context?.sorts || [])
								.filter((sort) => sort.key !== 'dateCreated')
								.map((sort) => {
									sort.active = false;

									return sort;
								});

							updatedSorts.push({
								active: true,
								direction: 'desc',
								key: 'dateCreated',
								label: Liferay.Language.get('by-creation-date'),
							});

							context && context.onClearResultsBar();
							context && context.forceSortsUpdate(updatedSorts);

							setShowInlineNotification(false);
						}}
						size="sm"
					>
						{Liferay.Language.get('reload')}
					</ClayButton>

					<ClayButton
						alert
						onClick={() => setShowInlineNotification(false)}
						size="sm"
					>
						{Liferay.Language.get('dismiss')}
					</ClayButton>
				</ClayButton.Group>
			</ClayAlert>
		);
	};

	return (
		<>
			{open && (
				<ItemSelectorModal
					{...props}
					fdsProps={{
						...props.fdsProps,
						inlineNotificationComponent:
							NewItemsNotificationComponent,
					}}
					observer={observer}
					onOpenChange={onOpenChange}
					open={open}
				/>
			)}
		</>
	);
};

export default DetachedCMSFilesItemSelectorModal;
