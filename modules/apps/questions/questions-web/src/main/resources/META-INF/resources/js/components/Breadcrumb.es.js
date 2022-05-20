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
import {useManualQuery} from 'graphql-hooks';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {getSectionQuery, getSectionsQuery} from '../utils/client.es';
import {ALL_SECTIONS_ID} from '../utils/contants.es';
import {historyPushWithSlug, stringToSlug} from '../utils/utils.es';
import Alert from './Alert.es';
import BreadcrumbNode from './BreadcrumbNode.es';
import NewTopicModal from './NewTopicModal.es';

export default withRouter(
	({allowCreateTopicInRootTopic, history, match, section}) => {
		const context = useContext(AppContext);

		const rootTopicId = context.rootTopicId;
		const sections = context.sections;
		const sectionTitle = match.params.sectionTitle;

		const MAX_SECTIONS_IN_BREADCRUMB = 3;
		const historyPushParser = historyPushWithSlug(history.push);
		const [breadcrumbNodes, setBreadcrumbNodes] = useState([]);
		const [error, setError] = useState({});
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
					return {id: section.id, title: section.title};
				});

			return {subSections: sections, title: ''};
		};

		const [findParent] = useManualQuery(getSectionQuery);
		const [getSectionByRootSection] = useManualQuery(getSectionsQuery);

		const buildBreadcrumbNodesData = useCallback(
			(rootSection, section, acc = []) => {
				acc.push({
					id: section.id,
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

						return findParent({
							variables: {
								messageBoardSectionId:
									section.parentMessageBoardSectionId,
							},
						}).then(({data}) =>
							buildBreadcrumbNodesData(
								rootSection,
								data.messageBoardSection,
								acc
							)
						);
					}
				}

				return +rootSection === 0
					? Promise.resolve(
							getSectionByRootSection({
								variables: {siteKey: context.siteKey},
							})
								.then(({data: {messageBoardSections}}) => ({
									actions: messageBoardSections.actions,
									id: 0,
									messageBoardSections,
									numberOfMessageBoardSections:
										messageBoardSections &&
										messageBoardSections.items &&
										messageBoardSections.items.length,
								}))
								.then((data) => {
									acc.push({
										id: data.id,
										subSections:
											data.messageBoardSections.items,
										title: rootSection,
									});

									return acc.reverse();
								})
					  ).then(acc)
					: Promise.resolve(acc.reverse());
			},
			[context.siteKey, findParent, getSectionByRootSection]
		);

		useEffect(() => {
			if (!section) {
				return;
			}

			buildBreadcrumbNodesData(rootTopicId, section).then((nodes) => {
				let _nodes = nodes;

				const subSections = [
					{
						href: 'all',
						title: Liferay.Language.get('all-questions'),
					},
					...sections,
				];

				_nodes = _nodes.map((node, index) => {
					if (index === _nodes.length - 1) {
						if (sectionTitle === ALL_SECTIONS_ID) {
							node = {
								...node,
								href: 'all',
								subSections: sections,
								title: Liferay.Language.get('all-questions'),
							};
						}

						return {
							...node,
							subSections: node.subSections.length
								? node.subSections
								: subSections,
						};
					}

					return node;
				});

				setBreadcrumbNodes(_nodes);
			});
		}, [
			buildBreadcrumbNodesData,
			rootTopicId,
			section,
			sectionTitle,
			sections,
		]);

		return (
			<>
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
								onCreateNavigateTo={(topic) =>
									historyPushParser(
										`/questions/${stringToSlug(topic)}`
									)
								}
								setError={setError}
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
				<Alert info={error} />
			</>
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
						.map((section, index) => (
							<BreadcrumbNode
								hasDropdown={true}
								key={index}
								section={section}
								showDropdownSections={index === 0}
								ui={section.title}
							/>
						))}
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
	}
);
