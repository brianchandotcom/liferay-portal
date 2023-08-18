/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import ClayPanel from '@clayui/panel';
import React, {useState} from 'react';

import normalizeFailingElements from '../utils/normalizeFailingElements';

const ITEM_PAGE_SIZE = 10;

export default function ItemDetail({selectedIssue}) {
	return (
		<ClayPanel.Group className="c-px-3 panel-group-flush panel-group-sm">
			<IssueDetail issue={selectedIssue} />
		</ClayPanel.Group>
	);
}

function IssueDetail({issue}) {
	const {description, failingElements, key, tips} = issue;

	const badge = {
		label: failingElements.length >= 100 ? '+100' : failingElements.length,
		type: failingElements.length ? 'info' : 'success',
	};

	return (
		<>
			<DetailPanel title={Liferay.Language.get('description')}>
				<Html>{description}</Html>
			</DetailPanel>

			<DetailPanel title={Liferay.Language.get('tips')}>
				<Html>{tips}</Html>
			</DetailPanel>

			<DetailPanel badge={badge} title={Liferay.Language.get('tips')}>
				<List
					ItemComponent={FailingElement}
					items={normalizeFailingElements(failingElements, key)}
				/>
			</DetailPanel>
		</>
	);
}

function DetailPanel({badge, children, title}) {
	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={
				<ClayPanel.Title>
					<ClayLayout.ContentRow className="align-items-center c-gap-2">
						<ClayLayout.ContentCol className="panel-title">
							{title}
						</ClayLayout.ContentCol>

						{badge ? (
							<ClayLayout.ContentCol>
								<ClayBadge
									displayType={badge.type}
									label={badge.label}
								/>
							</ClayLayout.ContentCol>
						) : null}
					</ClayLayout.ContentRow>
				</ClayPanel.Title>
			}
			displayType="unstyled"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>{children}</ClayPanel.Body>
		</ClayPanel>
	);
}

function Html({children}) {
	return (
		<div
			className="text-secondary"
			dangerouslySetInnerHTML={{
				__html: children,
			}}
		/>
	);
}

function List({ItemComponent, items}) {
	const [shownItems, setShownItems] = useState(ITEM_PAGE_SIZE);

	return (
		<>
			<ClayList>
				{items.slice(0, shownItems).map((item, index) => (
					<ItemComponent item={item} key={index} />
				))}
			</ClayList>

			{shownItems < items.length && (
				<ClayButton
					displayType="secondary"
					onClick={() =>
						setShownItems(
							Math.min(shownItems + ITEM_PAGE_SIZE, items.length)
						)
					}
				>
					{Liferay.Language.get('view-more')}
				</ClayButton>
			)}
		</>
	);
}

function FailingElement({item}) {
	return (
		<ClayList.Item className="border-0 c-mb-2 c-p-0 failing-element" flex>
			<ClayList.ItemField className="c-mb-2 c-p-0" expand>
				{item.title && (
					<ClayList.ItemText className="c-mb-2 font-weight-semi-bold">
						{item.title}
					</ClayList.ItemText>
				)}

				{item.content && (
					<ClayList.ItemText className="text-secondary">
						{item.content}
					</ClayList.ItemText>
				)}

				{item.htmlContent && <Html>{item.htmlContent}</Html>}

				{item.snippet && (
					<ClayList.ItemText className="bg-lighter border border-light c-mb-2 c-px-2 c-py-1 rounded">
						<code className="text-secondary">{item.snippet}</code>
					</ClayList.ItemText>
				)}

				{item.sections &&
					item.sections.map((section, index) => (
						<ClayList.ItemText
							className="c-mb-2 text-nowrap text-truncate"
							key={index}
						>
							<span className="c-mr-1 section-label text-secondary">{`${section.label}:`}</span>

							<span
								className="font-weight-semi-bold"
								data-tooltip-align="bottom"
								title={section.value}
							>
								{section.value}
							</span>
						</ClayList.ItemText>
					))}
			</ClayList.ItemField>
		</ClayList.Item>
	);
}
