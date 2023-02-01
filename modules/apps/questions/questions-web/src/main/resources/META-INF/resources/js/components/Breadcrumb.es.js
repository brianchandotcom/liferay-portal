/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useManualQuery} from 'graphql-hooks';
import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../AppContext.es';
import {getSectionQuery, getSectionsQuery} from '../utils/client.es';
import {ALL_SECTIONS_ID} from '../utils/contants.es';
import {historyPushWithSlug, slugToText, stringToSlug} from '../utils/utils.es';
import Alert from './Alert.es';
import BreadcrumbNode from './BreadcrumbNode.es';
import NewTopicModal from './NewTopicModal.es';
import {getSectionTitle} from './SectionLabel.es';

const MAX_SECTIONS_IN_BREADCRUMB = 3;

export default withRouter(
	({allowCreateTopicInRootTopic, history, match, section}) => {
		const context = useContext(AppContext);

		const rootTopicId = context.rootTopicId;
		const sections = useMemo(
			() =>
				(context.sections ?? []).map((section) => ({
					...section,
					title: getSectionTitle(section),
				})),
			[context.sections]
		);

		const sectionTitle = slugToText(match.params.sectionTitle);

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
					return {
						friendlyUrlPath: section.friendlyUrlPath,
						id: section.id,
						title: getSectionTitle(section),
					};
				});

			return {subSections: sections, title: ''};
		};

		const [findParent] = useManualQuery(getSectionQuery);
		const [getSectionByRootSection] = useManualQuery(getSectionsQuery);

		const buildBreadcrumbNodesData = useCallback(
			(rootSection, section, acc = []) => {
				acc.push({
					friendlyUrlPath: section.friendlyUrlPath,
					id: section.id,
					subSections: getSubSections(section),
					title: getSectionTitle(section),
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
										friendlyUrlPath: data.friendlyUrlPath,
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
								aria-label={Liferay.Language.get('new-topic')}
								className="breadcrumb-button c-ml-3 c-p-2"
								displayType="unstyled"
								onClick={() => setVisible(true)}
							>
								<ClayIcon
									aria-label={Liferay.Language.get(
										'new-topic'
									)}
									className="c-mr-2"
									symbol="plus"
								/>

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
						ui={
							<ClayIcon
								aria-label="Icon Home"
								symbol="home-full"
							/>
						}
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
						ui={
							<ClayIcon
								aria-label="Icon Home"
								symbol="home-full"
							/>
						}
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
