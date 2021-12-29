import ClayIcon from '@clayui/icon';
import DOMPurify from 'dompurify';
import {useCallback, useEffect, useState} from 'react';
import {fetchHeadless} from '../../../../common/services/liferay/api';
import {useCustomerPortal} from '../../context';

const QuickLinksPanel = () => {
	const [{quickLinks}] = useCustomerPortal();
	const [expandedPanel, setExpandedPanel] = useState(true);
	const [quickLinksData, setQuickLinksData] = useState([]);

	const fetchQuickLinksPanelContent = useCallback(async () => {
		const webContentFolderName = 'actions';
		const webContentTemplateName = 'Action Card';

		const siteGroupId = Liferay.ThemeDisplay.getSiteGroupId();

		const structuredContentFolders = await fetchHeadless({
			url: `/sites/${siteGroupId}/structured-content-folders`,
		});

		const {id: quickLinksInstructionsFolderID} =
			structuredContentFolders.items.find(
				({name}) => name === webContentFolderName
			) || {};

		const contentTemplates = await fetchHeadless({
			url: `/sites/${siteGroupId}/content-templates`,
		});

		const contentTemplate = contentTemplates.items.find(
			({name}) => name === webContentTemplateName
		);

		const structuredContents = await fetchHeadless({
			url: `/structured-content-folders/${quickLinksInstructionsFolderID}/structured-contents`,
		});

		const renderedQuickLinksData = await quickLinks.reduce(
			async (webContentList, webContent) => {
				const promiseStructuredContentList = await webContentList;

				const structuredContent =
					structuredContents?.items.find(
						({friendlyUrlPath, key}) =>
							friendlyUrlPath === webContent ||
							key === webContent.toUpperCase()
					)?.id || {};

				const structuredComponent = await fetchHeadless({
					resolveAsJson: false,
					url: `/structured-contents/${structuredContent}/rendered-content/${contentTemplate?.id}`,
				});

				promiseStructuredContentList.push(
					await structuredComponent.text()
				);

				return webContentList;
			},
			Promise.resolve([])
		);

		setQuickLinksData(renderedQuickLinksData);
	}, [quickLinks]);

	useEffect(() => {
		if (quickLinks) {
			fetchQuickLinksPanelContent();
		}
	}, [quickLinks, fetchQuickLinksPanelContent]);

	return (
		<div>
			<div
				className={`${
					!expandedPanel ? 'position-absolute' : ''
				} link-body mr-4 p-4 quick-links-container rounded`}
			>
				<div className="align-items-center d-flex justify-content-between">
					<h5 className="c-my-0 c-py-2 text-neutral-10">
						Quick Links
					</h5>

					<a
						className="borderless btn c-my-0 c-pr-3 c-py-4 h6 neutral text-neutral-8"
						id="hide-link"
						onClick={() =>
							setExpandedPanel(
								(prevExpandedPanel) => !prevExpandedPanel
							)
						}
					>
						<svg
							className="lexicon-icon lexicon-icon-hr"
							focusable="false"
							id="customer-portal-arrow"
							role="presentation"
						>
							<ClayIcon
								symbol={
									expandedPanel ? 'hr' : 'order-arrow-left'
								}
							/>
						</svg>

						<u>{expandedPanel ? 'Hide' : 'Show'}</u>
					</a>
				</div>

				{expandedPanel && (
					<div className="c-pt-3 cp-tip-container">
						{quickLinksData.map((quickLinkContent) => (
							<div
								className="bg-white card-body card-container link-body mb-3 rounded-lg"
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
		</div>
	);
};

export default QuickLinksPanel;
