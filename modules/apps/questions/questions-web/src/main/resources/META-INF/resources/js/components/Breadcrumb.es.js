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

import BreadcrumbDropdown from './BreadcrumbDropdown.es';
import Link from './Link.es';

export default ({section}) => {
	const CURRENT_SECTION_ID = 37124;
	const MAX_SECTIONS_IN_BREADCRUMB = 5;
	const [breadcrumbNodes, setBreadcrumbNodes] = useState([]);

	const getParentSubSections = (section) =>
		(section && section.messageBoardSections.items) || [];

	const getSectionById = (id) =>
		mockApiResponse.messageBoardSections.items.find(
			(section) => section.id === id
		);

	const createEllipsisSectionData = () => {
		const categories = breadcrumbNodes
			.slice(1, breadcrumbNodes.length - 1)
			.map((section) => {
				return {title: section.title};
			});

		return {subCategories: categories, title: ''};
	};

	const buildBreadcrumbNodesData = useCallback(
		(sectionId = CURRENT_SECTION_ID, acc = []) => {
			const section = getSectionById(sectionId);
			acc.push({
				subCategories: getParentSubSections(section),
				title: section.title,
			});

			return section.parentMessageBoardSectionId === null
				? acc.reverse()
				: buildBreadcrumbNodesData(
						section.parentMessageBoardSectionId,
						acc
				  );
		},
		[]
	);

	useEffect(() => {
		setBreadcrumbNodes(buildBreadcrumbNodesData());
	}, [buildBreadcrumbNodesData]);

	return (
		<section className="align-items-center d-flex">
			<div className="questions-breadcrumb">
				<ol className="breadcrumb mb-0 ml-2">
					{breadcrumbNodes.length > MAX_SECTIONS_IN_BREADCRUMB ? (
						<ShortenedBreadcrumb />
					) : (
						<AllBreadcrumb />
					)}
				</ol>
			</div>
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
						<ClayIcon symbol="home" />
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
						<ClayIcon symbol="home" />
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

const mockApiResponse = {
	messageBoardSections: {
		items: [
			{
				id: 37116,
				messageBoardSections: {
					items: [
						{
							id: 37132,
							numberOfMessageBoardSections: 0,
							parentMessageBoardSectionId: 37116,
							title: 'Site builder',
						},
						{
							id: 37130,
							numberOfMessageBoardSections: 0,
							parentMessageBoardSectionId: 37116,
							title: 'Web content',
						},
					],
				},
				numberOfMessageBoardSections: 2,
				parentMessageBoardSectionId: 36812,
				title: 'Web Experience Management',
			},
			{
				id: 37130,
				messageBoardSections: {
					items: [],
				},
				numberOfMessageBoardSections: 0,
				parentMessageBoardSectionId: 37116,
				title: 'Web content',
			},
			{
				id: 37128,
				messageBoardSections: {
					items: [],
				},
				numberOfMessageBoardSections: 0,
				parentMessageBoardSectionId: 37118,
				title: 'Vulcan',
			},
			{
				id: 37103,
				messageBoardSections: {
					items: [],
				},
				numberOfMessageBoardSections: 0,
				parentMessageBoardSectionId: 37087,
				title: 'Spain',
			},
			{
				id: 37132,
				messageBoardSections: {
					items: [],
				},
				numberOfMessageBoardSections: 0,
				parentMessageBoardSectionId: 37116,
				title: 'Site builder',
			},
			{
				id: 37099,
				messageBoardSections: {
					items: [],
				},
				numberOfMessageBoardSections: 0,
				parentMessageBoardSectionId: 37085,
				title: 'Shopping cart',
			},
			{
				id: 37124,
				messageBoardSections: {
					items: [],
				},
				numberOfMessageBoardSections: 0,
				parentMessageBoardSectionId: 37120,
				title: 'Sharing',
			},
			{
				id: 37126,
				messageBoardSections: {
					items: [],
				},
				numberOfMessageBoardSections: 0,
				parentMessageBoardSectionId: 37118,
				title: 'Rest builder',
			},
			{
				id: 37105,
				messageBoardSections: {
					items: [],
				},
				numberOfMessageBoardSections: 0,
				parentMessageBoardSectionId: 37087,
				title: 'Portugal',
			},
			{
				id: 36812,
				messageBoardSections: {
					items: [
						{
							id: 37114,
							numberOfMessageBoardSections: 2,
							parentMessageBoardSectionId: 36812,
							title: 'Collaboration',
						},
						{
							id: 37116,
							numberOfMessageBoardSections: 2,
							parentMessageBoardSectionId: 36812,
							title: 'Web Experience Management',
						},
						{
							id: 37118,
							numberOfMessageBoardSections: 2,
							parentMessageBoardSectionId: 36812,
							title: 'Headless',
						},
					],
				},
				numberOfMessageBoardSections: 3,
				parentMessageBoardSectionId: null,
				title: 'Portal',
			},
			{
				id: 37118,
				messageBoardSections: {
					items: [
						{
							id: 37126,
							numberOfMessageBoardSections: 0,
							parentMessageBoardSectionId: 37118,
							title: 'Rest builder',
						},
						{
							id: 37128,
							numberOfMessageBoardSections: 0,
							parentMessageBoardSectionId: 37118,
							title: 'Vulcan',
						},
					],
				},
				numberOfMessageBoardSections: 2,
				parentMessageBoardSectionId: 36812,
				title: 'Headless',
			},
			{
				id: 37122,
				messageBoardSections: {
					items: [],
				},
				numberOfMessageBoardSections: 0,
				parentMessageBoardSectionId: 37114,
				title: 'Documents & Media',
			},
			{
				id: 37101,
				messageBoardSections: {
					items: [],
				},
				numberOfMessageBoardSections: 0,
				parentMessageBoardSectionId: 37085,
				title: 'Commerce integration',
			},
			{
				id: 37114,
				messageBoardSections: {
					items: [
						{
							id: 37122,
							numberOfMessageBoardSections: 0,
							parentMessageBoardSectionId: 37114,
							title: 'Documents & Media',
						},
						{
							id: 37120,
							numberOfMessageBoardSections: 1,
							parentMessageBoardSectionId: 37114,
							title: 'Blogs',
						},
					],
				},
				numberOfMessageBoardSections: 2,
				parentMessageBoardSectionId: 36812,
				title: 'Collaboration',
			},
			{
				id: 37120,
				messageBoardSections: {
					items: [
						{
							id: 37124,
							numberOfMessageBoardSections: 0,
							parentMessageBoardSectionId: 37120,
							title: 'Sharing',
						},
					],
				},
				numberOfMessageBoardSections: 1,
				parentMessageBoardSectionId: 37114,
				title: 'Blogs',
			},
		],
	},
};
