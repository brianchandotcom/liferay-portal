/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import ClayPanel from '@clayui/panel';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import normalizeFailingElements from '../../utils/normalizeFailingElements';

const HtmlPanel = ({content, title}) => (
	<ClayPanel
		collapsable
		displayTitle={
			<ClayPanel.Title className="align-self-center panel-title">
				{title}
			</ClayPanel.Title>
		}
		displayType="unstyled"
		showCollapseIcon={true}
	>
		<ClayPanel.Body>
			<div
				className="text-secondary"
				dangerouslySetInnerHTML={{
					__html: content,
				}}
			></div>
		</ClayPanel.Body>
	</ClayPanel>
);

HtmlPanel.propTypes = {
	content: PropTypes.string.isRequired,
	title: PropTypes.string.isRequired,
};

const FailingElementsPanel = ({failingElements, issueType}) => {
	const [shownElements, setShownElements] = useState(10);

	const normalizedFailingElements = normalizeFailingElements(
		failingElements,
		issueType
	);

	const totalElements = normalizedFailingElements.length;

	const onViewMore = () => {
		const newShownElements = shownElements + 10;

		setShownElements(
			newShownElements < totalElements ? newShownElements : totalElements
		);
	};

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={
				<ClayPanel.Title>
					<ClayLayout.ContentRow>
						<ClayLayout.ContentCol
							className="align-self-center panel-title"
							expand
						>
							{Liferay.Language.get('failing-elements')}
						</ClayLayout.ContentCol>

						<ClayLayout.ContentCol>
							<ClayBadge
								displayType={
									totalElements === 0 ? 'success' : 'info'
								}
								label={
									totalElements >= 100
										? '+100'
										: totalElements
								}
							/>
						</ClayLayout.ContentCol>
					</ClayLayout.ContentRow>
				</ClayPanel.Title>
			}
			displayType="unstyled"
			showCollapseIcon={true}
		>
			<ClayPanel.Body>
				<ClayList>
					{normalizedFailingElements
						.slice(0, shownElements)
						.map((element, index) => (
							<FailingElement element={element} key={index} />
						))}
				</ClayList>

				{shownElements < totalElements && (
					<ClayButton displayType="secondary" onClick={onViewMore}>
						{Liferay.Language.get('view-more')}
					</ClayButton>
				)}
			</ClayPanel.Body>
		</ClayPanel>
	);
};

FailingElementsPanel.propTypes = {
	failingElements: PropTypes.array.isRequired,
	issueType: PropTypes.string.isRequired,
};

const FailingElement = ({element}) => {
	return (
		<ClayList.Item className="border-0 c-mb-2 c-p-0 failing-element" flex>
			<ClayList.ItemField className="c-mb-2 c-p-0" expand>
				{element.title && (
					<ClayList.ItemText className="c-mb-2 font-weight-semi-bold">
						{element.title}
					</ClayList.ItemText>
				)}

				{element.content && (
					<ClayList.ItemText className="text-secondary">
						{element.content}
					</ClayList.ItemText>
				)}

				{element.htmlContent && (
					<div
						className="text-secondary"
						dangerouslySetInnerHTML={{
							__html: element.htmlContent,
						}}
					/>
				)}

				{element.snippet && (
					<ClayList.ItemText className="bg-lighter border border-light c-mb-2 c-px-2 c-py-1 rounded">
						<code className="text-secondary">
							{element.snippet}
						</code>
					</ClayList.ItemText>
				)}

				{element.sections &&
					element.sections.map((section, index) => (
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
};

FailingElement.propTypes = {
	element: PropTypes.object.isRequired,
};
