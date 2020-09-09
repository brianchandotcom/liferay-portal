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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {
	client,
	getSectionByRootSection,
	getSectionQuery,
} from '../utils/client.es';
import {historyPushWithSlug, stringToSlug} from '../utils/utils.es';
import BreadcrumbNode from './BreadcrumbNode.es';
import NewTopicModal from './NewTopicModal.es';

export default withRouter(({allowCreateTopicInRootTopic, history, section}) => {
	const context = useContext(AppContext);

	const rootTopicId = context.rootTopicId;

	const MAX_SECTIONS_IN_BREADCRUMB = 3;
	const historyPushParser = historyPushWithSlug(history.push);
	const [breadcrumbNodes, setBreadcrumbNodes] = useState([]);
	const [visible, setVisible] = useState(false);

	const getSubSections = (section) =>
		(section &&
			section.messageBoardSections &&
			section.messageBoardSections.items) ||
		[];

	const createEllipsisSectionData = () => {
		const sections = breadcrumbNodes
			.slice(1, breadcrumbNodes.length - 1)
			.map((section) => {
				return {title: section.title};
			});

		return {subSections: sections, title: ''};
	};

	const findParent = (messageBoardSectionId) =>
		client
			.query({
				query: getSectionQuery,
				variables: {messageBoardSectionId},
			})
			.then(({data}) => data.messageBoardSection);

	const buildBreadcrumbNodesData = useCallback(
		(rootSection, section, acc = []) => {
			acc.push({
				subSections: getSubSections(section),
				title: section.title,
			});
			if (+rootSection !== +section.id) {
				if (section.parentMessageBoardSectionId) {
					if (section.parentMessageBoardSection) {
						return Promise.resolve(
							buildBreadcrumbNodesData(
								rootSection,
								section.parentMessageBoardSection,
								acc
							)
						);
					}

					return findParent(
						section.parentMessageBoardSectionId
					).then((section) =>
						buildBreadcrumbNodesData(rootSection, section, acc)
					);
				}
			}

			return +rootSection === 0
				? Promise.resolve(
						getSectionByRootSection(
							context.siteKey,
							rootSection
						).then((data) => {
							acc.push({
								subSections: data.messageBoardSections.items,
								title: rootSection,
							});

							return acc.reverse();
						})
				  ).then(acc)
				: Promise.resolve(acc.reverse());
		},
		[context.siteKey]
	);

	useEffect(() => {
		if (!section) {
			return;
		}

		buildBreadcrumbNodesData(rootTopicId, section).then((acc) =>
			setBreadcrumbNodes(acc)
		);
	}, [buildBreadcrumbNodesData, rootTopicId, section]);

	return (
		<section className="align-items-center d-flex mb-0 questions-breadcrumb">
			<ol className="breadcrumb m-0">
				{breadcrumbNodes.length > MAX_SECTIONS_IN_BREADCRUMB ? (
					<ShortenedBreadcrumb />
				) : (
					<AllBreadcrumb />
				)}
			</ol>
			{((section &&
				section.actions &&
				section.actions['add-subcategory']) ||
				allowCreateTopicInRootTopic) && (
				<>
					<NewTopicModal
						currentSectionId={section && section.id}
						onClose={() => setVisible(false)}
						onCreateNavigateTo={(topicName) =>
							historyPushParser(
								`/questions/${stringToSlug(topicName)}`
							)
						}
						visible={visible}
					/>
					<ClayButton
						className="breadcrumb-button c-ml-3 c-p-2"
						displayType="unstyled"
						onClick={() => setVisible(true)}
					>
						<ClayIcon className="c-mr-2" symbol="plus" />
						{Liferay.Language.get('new-topic')}
					</ClayButton>
				</>
			)}
		</section>
	);

	function AllBreadcrumb() {
		return (
			<>
				<BreadcrumbNode
					hasDropdown={!context.showCardsForTopicNavigation}
					isFirstNode={true}
					section={breadcrumbNodes[0]}
					ui={<ClayIcon symbol="home-full" />}
				/>
				{breadcrumbNodes
					.filter((section) => section.title)
					.slice(1, breadcrumbNodes.length)
					.map((section, i) => {
						return (
							<BreadcrumbNode
								hasDropdown={true}
								key={i}
								section={section}
								ui={section.title}
							/>
						);
					})}
			</>
		);
	}

	function ShortenedBreadcrumb() {
		return (
			<>
				<BreadcrumbNode
					hasDropdown={!context.showCardsForTopicNavigation}
					isFirstNode={true}
					section={breadcrumbNodes[0]}
					ui={<ClayIcon symbol="home-full" />}
				/>
				<BreadcrumbNode
					hasDropdown={true}
					isEllipsis={true}
					section={createEllipsisSectionData()}
				/>
				<BreadcrumbNode
					hasDropdown={true}
					section={breadcrumbNodes[breadcrumbNodes.length - 1]}
				/>
			</>
		);
	}
});
