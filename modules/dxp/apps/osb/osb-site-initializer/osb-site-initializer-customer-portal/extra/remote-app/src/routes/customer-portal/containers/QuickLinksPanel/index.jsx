/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import DOMPurify from 'dompurify';
import {useCallback, useEffect, useMemo} from 'react';
import useSWR from 'swr';
import i18n from '../../../../common/I18n';
import {fetchHeadless} from '../../../../common/services/liferay/api';
import {storage} from '../../../../common/services/liferay/storage';
import {STORAGE_KEYS} from '../../../../common/utils/constants';
import routerPath from '../../../../common/utils/routerPath';
import {useCustomerPortal} from '../../context';
import {actionTypes} from '../../context/reducer';
import QuickLinksSkeleton from './Skeleton';

DOMPurify.addHook('afterSanitizeAttributes', (node) => {
	if ('target' in node) {
		node.setAttribute('target', '_blank');
		node.setAttribute('rel', 'noopener noreferrer');
	}
});

const QuickLinksPanel = () => {
	const [
		{isQuickLinksExpanded, project, quickLinks, structuredContents},
		dispatch,
	] = useCustomerPortal();

	const pageRoutes = useMemo(() => routerPath(), []);

	const fetchQuickLinksPanelContent = useCallback(async () => {
		const renderedQuickLinksContents = await quickLinks.reduce(
			async (quickLinkAccumulator, quickLink) => {
				const accumulator = await quickLinkAccumulator;

				const structuredContentId = structuredContents?.find(
					({friendlyUrlPath, key}) =>
						friendlyUrlPath === quickLink ||
						key === quickLink.toUpperCase()
				)?.id;

				if (structuredContentId) {
					const structuredComponent = await fetchHeadless({
						resolveAsJson: false,
						url: `/structured-contents/${structuredContentId}/rendered-content/ACTION-CARD`,
					});

					const htmlBody = await structuredComponent.text();

					accumulator.push(
						htmlBody
							.replace('{{accountKey}}', project?.accountKey)
							.replace(
								'{{projectURL}}',
								pageRoutes.project(project?.accountKey)
							)
					);
				}

				return accumulator;
			},
			Promise.resolve([])
		);

		return renderedQuickLinksContents;
	}, [pageRoutes, project?.accountKey, quickLinks, structuredContents]);

	const {data: quickLinksContents = [], isLoading} = useSWR(
		'overview/banner',
		fetchQuickLinksPanelContent
	);

	useEffect(() => {
		const quickLinksExpandedStorage = storage.getItem(
			STORAGE_KEYS.quickLinksExpanded
		);

		if (quickLinksExpandedStorage) {
			dispatch({
				payload: JSON.parse(quickLinksExpandedStorage),
				type: actionTypes.UPDATE_QUICK_LINKS_EXPANDED_PANEL,
			});
		}
	}, [dispatch]);

	if (isLoading || !quickLinksContents.length) {
		return <QuickLinksSkeleton />;
	}

	return (
		<div
			className={classNames(
				'cp-link-body quick-links-container rounded',
				{
					'p-4': isQuickLinksExpanded,
					'position-absolute px-3 py-3': !isQuickLinksExpanded,
				}
			)}
		>
			<div className="align-items-center d-flex justify-content-between">
				<h5 className="m-0 text-neutral-10">
					{i18n.translate('quick-links')}
				</h5>

				<a
					className={classNames(
						'btn font-weight-bold p-2 text-neutral-8 text-paragraph-sm',
						{
							'pl-3': !isQuickLinksExpanded,
						}
					)}
					onClick={() => {
						dispatch({
							payload: !isQuickLinksExpanded,
							type: actionTypes.UPDATE_QUICK_LINKS_EXPANDED_PANEL,
						});

						storage.setItem(
							STORAGE_KEYS.quickLinksExpanded,
							JSON.stringify(!isQuickLinksExpanded)
						);
					}}
				>
					<ClayIcon
						className="mr-1"
						symbol={isQuickLinksExpanded ? 'hr' : 'plus'}
					/>

					{isQuickLinksExpanded && i18n.translate('hide')}
				</a>
			</div>

			{isQuickLinksExpanded &&
				quickLinksContents.map((quickLinkContent) => (
					<div
						className="bg-white cp-link-body my-3 p-3 quick-links-card rounded-lg"
						dangerouslySetInnerHTML={{
							__html: DOMPurify.sanitize(quickLinkContent, {
								USE_PROFILES: {html: true},
							}),
						}}
						key={quickLinkContent}
					/>
				))}
		</div>
	);
};

QuickLinksPanel.Skeleton = QuickLinksSkeleton;

export default QuickLinksPanel;
