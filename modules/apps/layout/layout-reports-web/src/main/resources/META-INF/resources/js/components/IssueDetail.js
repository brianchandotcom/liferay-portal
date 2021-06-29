/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import ClayPanel from '@clayui/panel';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import {StoreStateContext} from '../context/StoreContext';
import normalizeFailingElements from '../utils/normalizeFailingElements';

export default function IssueDetail() {
	const {selectedIssue} = useContext(StoreStateContext);

	return (
		<div className="pb-3 px-3">
			<ClayPanel.Group className="panel-group-flush panel-group-sm">
				<HtmlPanel
					content={selectedIssue.description}
					title={Liferay.Language.get('description')}
				/>
				<HtmlPanel
					content={selectedIssue.tips}
					title={Liferay.Language.get('tips')}
				/>
				<FailingElementsPanel
					failingElements={selectedIssue.failingElements}
					issueType={selectedIssue.key}
				/>
			</ClayPanel.Group>
		</div>
	);
}

const HtmlPanel = ({content, title}) => (
	<ClayPanel
		collapsable
		displayTitle={
			<span className="c-inner" tabIndex="-1">
				<ClayPanel.Title className="align-self-center panel-title">
					{title}
				</ClayPanel.Title>
			</span>
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
				<span className="c-inner" tabIndex="-1">
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
				</span>
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
		<ClayList.Item className="failing-element mb-2 p-0" flex>
			<ClayList.ItemField className="mb-2 p-0" expand>
				{element.title && (
					<ClayList.ItemText className="font-weight-semi-bold mb-2">
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
					<ClayList.ItemText className="bg-lighter border border-light mb-2 px-2 py-1 rounded">
						<code className="text-secondary">
							{element.snippet}
						</code>
					</ClayList.ItemText>
				)}
				{element.sections &&
					element.sections.map((section, index) => (
						<ClayList.ItemText
							className="mb-2 text-nowrap text-truncate"
							key={index}
						>
							<span className="mr-1 section-label text-secondary">{`${section.label}:`}</span>
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
