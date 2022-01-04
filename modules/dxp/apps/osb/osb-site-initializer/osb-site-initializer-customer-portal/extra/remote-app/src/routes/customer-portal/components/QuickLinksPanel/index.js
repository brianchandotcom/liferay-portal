import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import DOMPurify from 'dompurify';
import {useCallback, useEffect, useState} from 'react';
import {fetchHeadless} from '../../../../common/services/liferay/api';
import {
	STORAGE_KEYS,
	Storage,
} from '../../../../common/services/liferay/storage';
import {useCustomerPortal} from '../../context';
import QuickLinksSkeleton from './Skeleton';

const QuickLinksPanel = ({accountKey}) => {
	const [{quickLinks, structuredContents}] = useCustomerPortal();
	const [expandedPanel, setExpandedPanel] = useState(true);
	const [quickLinksContents, setQuickLinksContents] = useState([]);

	useEffect(() => {
		const quickLinksExpandedStorage = Storage.getItem(
			STORAGE_KEYS.QUICK_LINKS_EXPANDED
		);

		if (quickLinksExpandedStorage) {
			setExpandedPanel(JSON.parse(quickLinksExpandedStorage));
		}
	}, []);

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
						htmlBody.replace('{{accountKey}}', accountKey)
					);
				}

				return accumulator;
			},
			Promise.resolve([])
		);

		setQuickLinksContents(renderedQuickLinksContents);
	}, [accountKey, quickLinks, structuredContents]);

	useEffect(() => {
		if (quickLinks) {
			fetchQuickLinksPanelContent();
		}
	}, [quickLinks, fetchQuickLinksPanelContent]);

	DOMPurify.addHook('afterSanitizeAttributes', (node) => {
		if ('target' in node) {
			node.setAttribute('target', '_blank');
			node.setAttribute('rel', 'noopener noreferrer');
		}
	});

	return (
		<div>
			{quickLinksContents.length ? (
				<div
					className={classNames(
						'link-body p-4 quick-links-container rounded',
						{
							'position-absolute': !expandedPanel,
						}
					)}
				>
					<div className="align-items-center d-flex justify-content-between">
						<h5 className="m-0 text-neutral-10">Quick Links</h5>

						<a
							className="btn p-2 text-neutral-8 text-paragraph-sm"
							onClick={() => {
								setExpandedPanel(!expandedPanel);
								Storage.setItem(
									STORAGE_KEYS.QUICK_LINKS_EXPANDED,
									JSON.stringify(!expandedPanel)
								);
							}}
						>
							<ClayIcon
								className="mr-1"
								symbol={
									expandedPanel ? 'hr' : 'order-arrow-left'
								}
							/>

							{expandedPanel ? 'Hide' : 'Show'}
						</a>
					</div>

					{expandedPanel && (
						<div>
							{quickLinksContents.map((quickLinkContent) => (
								<div
									className="bg-white link-body my-3 p-3 rounded-lg"
									dangerouslySetInnerHTML={{
										__html: DOMPurify.sanitize(
											quickLinkContent,
											{
												USE_PROFILES: {html: true},
											}
										),
									}}
									key={quickLinkContent}
								></div>
							))}
						</div>
					)}
				</div>
			) : (
				<QuickLinksSkeleton />
			)}
		</div>
	);
};

QuickLinksPanel.Skeleton = QuickLinksSkeleton;
export default QuickLinksPanel;
