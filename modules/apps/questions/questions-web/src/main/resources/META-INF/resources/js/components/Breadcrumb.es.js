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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import Link from './Link.es';
import ShortenedBreadcrumb from './ShortenedBreadcrumb.es';

export default ({section}) => {
	const MAX_SECTIONS_IN_BREADCRUMB = 8;
	const [active, setActive] = useState(false);

	const getParentSubSections = () =>
		(section &&
			section.parentSection &&
			section.parentSection.messageBoardSections.items) ||
		[];

	return (
		<section className="align-items-center d-flex">
			<div className="questions-breadcrumb">
				<ol className="breadcrumb mb-0 ml-2">
					{hardcodedSections.length > MAX_SECTIONS_IN_BREADCRUMB ? (
						ShortenedBreadcrumb()
					) : (
						<AllBreadcrumb sections={hardcodedSections} />
					)}
				</ol>
			</div>
		</section>
	);

	function AllBreadcrumb() {
		return (
			<>
				<li className="breadcrumb-item breadcrumb-text-truncate">
					<ClayIcon symbol="home" />
				</li>
				{hardcodedSections.map((section) => (
					<>
						<li className="breadcrumb-item breadcrumb-text-truncate">
							{section.title}
						</li>
					</>
				))}
			</>
		);
	}

	function ShortenedBreadcrumb() {
		return (
			<>
				<li className="breadcrumb-item breadcrumb-text-truncate">
					<ClayIcon symbol="home" />
				</li>
				<li className="breadcrumb-item breadcrumb-text-truncate">
					{hardcodedSections[0].title}
				</li>
				<li className="breadcrumb-item breadcrumb-text-truncate">
					{hardcodedSections[1].title}
				</li>
				<li className="breadcrumb-item breadcrumb-text-truncate">
					<ClayDropDown
						active={active}
						onActiveChange={setActive}
						trigger={
							<ClayButton
								className="c-p-0 questions-breadcrumb-unstyled text-truncate"
								displayType="unstyled"
							>
								<ClayIcon symbol="ellipsis-h" />
								<ClayIcon symbol="caret-bottom-l" />
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							<ClayDropDown.Group>
								{hardcodedSections.map((section, i) => (
									<ClayDropDown.Item key={i}>
										{section.title}
									</ClayDropDown.Item>
								))}
							</ClayDropDown.Group>
						</ClayDropDown.ItemList>
					</ClayDropDown>
				</li>
				<li className="breadcrumb-item breadcrumb-text-truncate">
					{hardcodedSections[hardcodedSections.length - 2].title}
				</li>
				<li className="breadcrumb-item breadcrumb-text-truncate">
					{hardcodedSections[hardcodedSections.length - 1].title}
				</li>
			</>
		);
	}
};

const hardcodedSections = [
	{
		id: 1,
		parentSection: {
			id: 1,
			title: 'Root',
		},
		title: 'Root',
	},
	{
		id: 2,
		parentSection: {
			id: 1,
			title: 'Root',
		},
		title: 'Level 1',
	},
	{
		id: 3,
		parentSection: {
			id: 2,
			title: 'Level 1',
		},
		title: 'Level 2',
	},
	{
		id: 4,
		parentSection: {
			id: 3,
			title: 'Level 2',
		},
		title: 'Level 3',
	},
	{
		id: 5,
		parentSection: {
			id: 4,
			title: 'Level 3',
		},
		title: 'Level 4',
	},
	{
		id: 6,
		parentSection: {
			id: 5,
			title: 'Level 4',
		},
		title: 'Level 5',
	},
	{
		id: 7,
		parentSection: {
			id: 6,
			title: 'Level 5',
		},
		title: 'Level 6',
	},
	{
		id: 8,
		parentSection: {
			id: 7,
			title: 'Level 6',
		},
		title: 'Level 7',
	},
	{
		id: 9,
		parentSection: {
			id: 8,
			title: 'Level 7',
		},
		title: 'Level 8',
	},
	{
		id: 10,
		parentSection: {
			id: 9,
			title: 'Level 8',
		},
		title: 'Level 9',
	},
	{
		id: 11,
		parentSection: {
			id: 10,
			title: 'Level 9',
		},
		title: 'Level 10',
	},
	{
		id: 12,
		parentSection: {
			id: 11,
			title: 'Level 10',
		},
		title: 'Level 11',
	},
	{
		id: 13,
		parentSection: {
			id: 12,
			title: 'Level 11',
		},
		title: 'Level 12',
	},
	{
		id: 14,
		parentSection: {
			id: 13,
			title: 'Level 12',
		},
		title: 'Level 13',
	},
	{
		id: 15,
		parentSection: {
			id: 14,
			title: 'Level 13',
		},
		title: 'Level 14',
	},
	{
		id: 16,
		parentSection: {
			id: 15,
			title: 'Level 14',
		},
		title: 'Level 15',
	},
	{
		id: 17,
		parentSection: {
			id: 16,
			title: 'Level 15',
		},
		title: 'Level 16',
	},
	{
		id: 18,
		parentSection: {
			id: 17,
			title: 'Level 16',
		},
		title: 'Level 17',
	},
	{
		id: 19,
		parentSection: {
			id: 18,
			title: 'Level 17',
		},
		title: 'Level 18',
	},
	{
		id: 20,
		parentSection: {
			id: 19,
			title: 'Level 18',
		},
		title: 'Level 19',
	},
];

const mockSection = [
	{
		data: {
			messageBoardSections: {
				items: [
					{
						id: 36812,
						messageBoardSections: {
							items: [
								{
									id: 36826,
									numberOfMessageBoardSections: 2,
									parentMessageBoardSectionId: 36812,
									title: 'Collaboration',
								},
								{
									id: 36827,
									numberOfMessageBoardSections: 1,
									parentMessageBoardSectionId: 36812,
									title: 'Web Experience Management',
								},
								{
									id: 36828,
									numberOfMessageBoardSections: 0,
									parentMessageBoardSectionId: 36812,
									title: 'Headless',
								},
							],
						},
						numberOfMessageBoardSections: 3,
						parentMessageBoardSectionId: null,
						title: 'Portal',
					},
				],
			},
		},
	},
	{
		data: {
			messageBoardSections: {
				items: [
					{
						id: 36826,
						messageBoardSections: {
							items: [
								{
									id: 36829,
									numberOfMessageBoardSections: 0,
									parentMessageBoardSectionId: 36826,
									title: 'Blogs',
								},
								{
									id: 36830,
									numberOfMessageBoardSections: 0,
									parentMessageBoardSectionId: 36826,
									title: 'Documents & Media',
								},
							],
						},
						numberOfMessageBoardSections: 2,
						parentMessageBoardSectionId: 36812,
						title: 'Collaboration',
					},
				],
			},
		},
	},
	{
		data: {
			messageBoardSections: {
				items: [
					{
						id: 36827,
						messageBoardSections: {
							items: [
								{
									id: 36831,
									numberOfMessageBoardSections: 0,
									parentMessageBoardSectionId: 36827,
									title: 'Web Contents',
								},
							],
						},
						numberOfMessageBoardSections: 1,
						parentMessageBoardSectionId: null,
						title: 'Web Experience Management',
					},
				],
			},
		},
	}
];
