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

import ClayIcon from '@clayui/icon';
import React, {useCallback, useEffect, useState} from 'react';

import {client, getSectionsByIdQuery} from '../utils/client.es';
import BreadcrumbDropdown from './BreadcrumbDropdown.es';
import Link from './Link.es';
import NewTopic from './NewTopic.es';

export default ({section}) => {
	const MAX_SECTIONS_IN_BREADCRUMB = 3;
	const [breadcrumbNodes, setBreadcrumbNodes] = useState([]);

	const getSubSections = (section) =>
		(section &&
			section.messageBoardSections &&
			section.messageBoardSections.items) ||
		[];

	const createEllipsisSectionData = () => {
		const categories = breadcrumbNodes
			.slice(1, breadcrumbNodes.length - 1)
			.map((section) => {
				return {title: section.title};
			});

		return {subCategories: categories, title: ''};
	};

	const findParent = (messageBoardSectionId) =>
		client
			.query({
				query: getSectionsByIdQuery,
				variables: {messageBoardSectionId},
			})
			.then(({data}) => data.messageBoardSection);

	const buildBreadcrumbNodesData = useCallback((section, acc = []) => {
		acc.push({
			subCategories: getSubSections(section),
			title: section.title,
		});

		if (section.parentMessageBoardSectionId) {
			if (section.parentMessageBoardSection) {
				return Promise.resolve(
					buildBreadcrumbNodesData(
						section.parentMessageBoardSection,
						acc
					)
				);
			}

			return findParent(
				section.parentMessageBoardSectionId
			).then((section) => buildBreadcrumbNodesData(section, acc));
		}

		return Promise.resolve(acc.reverse());
	}, []);

	useEffect(() => {
		if (!section) {
			return;
		}

		buildBreadcrumbNodesData(section).then((acc) =>
			setBreadcrumbNodes(acc)
		);
	}, [buildBreadcrumbNodesData, section]);

	return (
		<section className="align-items-center d-flex mb-0 questions-breadcrumb">
			<ol className="breadcrumb ml-0">
				{breadcrumbNodes.length > MAX_SECTIONS_IN_BREADCRUMB ? (
					<ShortenedBreadcrumb />
				) : (
					<AllBreadcrumb />
				)}
			</ol>
			{section &&
				section.actions &&
				section.actions['add-subcategory'] && (
					<NewTopic currentSectionId={section && section.id} />
				)}
		</section>
	);

	function AllBreadcrumb() {
		return (
			<>
				<li className="breadcrumb-item breadcrumb-text-truncate mr-0">
					<Link
						className="breadcrumb-item questions-breadcrumb-unstyled"
						to={'/'}
					>
						<ClayIcon symbol="home-full" />
					</Link>
				</li>
				<BreadcrumbNode />
			</>
		);
	}

	function ShortenedBreadcrumb() {
		return (
			<>
				<li className="breadcrumb-item breadcrumb-text-truncate mr-0">
					<Link
						className="breadcrumb-item questions-breadcrumb-unstyled"
						to={'/'}
					>
						<ClayIcon symbol="home-full" />
					</Link>
				</li>
				<BreadcrumbNode end={1} start={0} />
				<li className="breadcrumb-item breadcrumb-text-truncate mr-0">
					<BreadcrumbDropdown
						className="breadcrumb-item breadcrumb-text-truncate"
						createLink={false}
						section={createEllipsisSectionData()}
					/>
				</li>
				<BreadcrumbNode start={-1} />
			</>
		);
	}

	function BreadcrumbNode({
		createLink,
		end = breadcrumbNodes.length,
		start = 0,
	}) {
		return breadcrumbNodes.slice(start, end).map((section, i) => (
			<li
				className="breadcrumb-item breadcrumb-text-truncate mr-0"
				key={i}
			>
				{section.subCategories.length <= 0 ? (
					section.title
				) : (
					<BreadcrumbDropdown
						className="breadcrumb-item breadcrumb-text-truncate"
						createLink={createLink}
						section={section}
					/>
				)}
			</li>
		));
	}
};
