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
	const MAX_SECTIONS_IN_BREADCRUMB = 3;
	const [breadcrumbNodes, setBreadcrumbNodes] = useState([]);

	const getParentSubSections = (section) =>
		(section && section.messageBoardSections.items) || [];

	const getSectionById = (id) =>
		section.messageBoardSections.items.find((section) => section.id === id);

	const createEllipsisSectionData = () => {
		const categories = breadcrumbNodes
			.slice(1, breadcrumbNodes.length - 1)
			.map((section) => {
				return {title: section.title};
			});

		return {subCategories: categories, title: ''};
	};

	const buildBreadcrumbNodesData = useCallback(
		(sectionId = section && section.id, acc = []) => {
			if (!sectionId) {
				return [];
			}
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
		[getSectionById, section]
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
