/* eslint-disable no-console */
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
import classNames from 'classnames';
import {useEffect, useState} from 'react';

import './index.scss';
import {getApplicationsById} from '../../services/Application';
import {
	getUserNotification,
	putUserNotificationRead,
} from '../../services/notification';
import {getLiferaySiteName} from '../../utils/liferay';
import {PostType} from './postTypes';

const initialPagination = {order: 'desc', page: 1, pageSize: 2, totalCount: 0};

const NotificationSidebar: React.FC = () => {
	const [posts, setPosts] = useState<any[]>([]);
	const [totalCount, setTotalCount] = useState<number>(
		initialPagination.totalCount
	);
	const [postsWithLinks, setArrayOfLinks] = useState<PostType[]>([]);
	const [page, setPage] = useState<number>(initialPagination.page);
	const hasMorePostsToLoad = posts.length < totalCount;

	const notificationCategory = {
		Application: 'Application ',
	};

	const creatRoute = (externalReferenceCode: string, entity: string) => {
		const currentSiteName = getLiferaySiteName();
		const link = `${currentSiteName}/${entity}?externalReferenceCode=${externalReferenceCode}`;

		return link;
	};

	const markAsRead = (post: PostType) => {
		if (!post.read) {
			putUserNotificationRead(post.id);
		}
	};

	const extractNumber = (message: string) => {
		const number = message?.match(/\d/g);

		return Number(number?.join(''));
	};

	async function getNotifications() {
		try {
			const response = await getUserNotification(
				initialPagination.pageSize,
				page,
				initialPagination.order
			);
			const notifications = response?.data;

			if (notifications) {
				setTotalCount(notifications.totalCount);
				setPosts((previousPosts: PostType[]) => [
					...previousPosts,
					...notifications.items,
				]);
			}

			return response;
		} catch (error) {
			console.error('Error getting notifications:', error);
			throw error;
		}
	}

	async function getExternalReferenceCode(id: number) {
		try {
			const response = await getApplicationsById(
				id,
				'externalReferenceCode'
			);
			const data = response?.data?.items?.[0]?.externalReferenceCode;

			if (!data) {
				throw new Error('External reference code not found');
			}

			return data;
		} catch (error) {
			console.error(
				`Error fetching external reference code for ID ${id}: ${error}`
			);
			throw error;
		}
	}

	const generateLinks = async () => {
		const newLinks = await Promise.all(
			posts.map(async (post) => {
				const postId = extractNumber(post.message);

				const isMatchingApplication = post.message?.includes(
					notificationCategory.Application + postId
				);

				if (isMatchingApplication) {
					const referenceCode = extractNumber(post.message);
					const externalReferenceCodeUpdated = await getExternalReferenceCode(
						referenceCode
					);
					const route = creatRoute(
						externalReferenceCodeUpdated,
						'app-details'
					);

					return {...post, link: route};
				} else {
					const genericRoute = '#!';

					return {...post, link: genericRoute};
				}
			})
		);

		const sortedLinks = newLinks;
		setArrayOfLinks(sortedLinks);
	};

	const loadMore = () => {
		const nextPage = page + 1;
		setPage(nextPage);
	};

	useEffect(() => {
		getNotifications();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [page]);

	useEffect(() => {
		generateLinks();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [posts]);

	return (
		<div className="notification-container">
			{!postsWithLinks && (
				<p className="align-items-center d-flex justify-content-center pt-8 vh-100">
					No notifications
				</p>
			)}

			{!!postsWithLinks && (
				<div className="vh-100">
					{postsWithLinks.map((item: PostType, _index: number) => (
						<div
							className={classNames({
								'post-container-unread bubble-unread': !item.read,
							})}
							key={item.id}
							onClick={() => {
								markAsRead(item);
							}}
						>
							<div className="align-items-center dotted-line h-100 post-container">
								<a href={item.link}>
									<p className="mt-0 my-0">{item.message}</p>
								</a>

								<h5 className="font-italic mt-2">
									{item.dateCreated}
								</h5>
							</div>
						</div>
					))}

					{hasMorePostsToLoad && (
						<ClayButton
							className="align-items-center mb-7 mt-9 pb-7 shadow-none w-100"
							displayType="link"
							onClick={() => loadMore()}
						>
							Load More
						</ClayButton>
					)}
				</div>
			)}
		</div>
	);
};

export default NotificationSidebar;
