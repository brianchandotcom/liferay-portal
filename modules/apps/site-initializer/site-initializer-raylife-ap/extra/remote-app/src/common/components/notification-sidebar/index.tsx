/* eslint-disable no-console */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import createUrlByERC from '../../utils/createUrlByERC';
import {PostType} from './postTypes';

const initialPagination = {
	pageSize: 7,
	totalCount: 0,
};

const NotificationSidebar: React.FC = () => {
	const [posts, setPosts] = useState<PostType[]>([]);
	const [totalCount, setTotalCount] = useState<number>(
		initialPagination.totalCount
	);
	const [postsWithLinks, setPostsWithLinks] = useState<PostType[]>([]);
	const hasMorePostsToLoad = posts.length < totalCount;
	const [isRead, setIsRead] = useState<boolean[]>([]);
	const [pageSize, setPageSize] = useState<number>(
		initialPagination.pageSize
	);

	const parameters = {
		order: 'desc',
		pageSize,
		sortBy: 'dateCreated',
	};
	const notificationCategory = 'Application ';

	const markAsRead = (post: PostType) => {
		if (!post.read) {
			putUserNotificationRead(post.id);
		}
	};

	const markAsReadState = (_post: PostType, index: number) => {
		if (!isRead[index]) {
			const arrayOfReads = [...isRead];
			arrayOfReads[index] = !arrayOfReads[index];
			setIsRead(arrayOfReads);
		}
	};

	const extractNumber = (message: string) => {
		const number = message?.match(/\d/g);

		return Number(number?.join(''));
	};

	async function getNotifications() {
		try {
			const response = await getUserNotification(parameters);
			const notifications = response?.data;

			if (notifications) {
				setTotalCount(notifications.totalCount);
				setPosts(() => [...notifications.items]);
			}

			return response;
		}
		catch (error) {
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
		}
		catch (error) {
			console.error(
				`Error fetching external reference code for ID ${id}: ${error}`
			);
			throw error;
		}
	}

	const generateLinks = async () => {
		const arrayRead: boolean[] = [];
		const newLinks = await Promise.all(
			posts.map(async (post) => {
				arrayRead.push(post.read as boolean);
				const postId = extractNumber(post.message as string);
				const isMatchingApplication = post.message?.includes(
					notificationCategory + postId
				);
				const genericRoute = '#!';

				if (isMatchingApplication) {
					const referenceCode = extractNumber(post.message as string);
					const externalReferenceCodeUpdated = await getExternalReferenceCode(
						referenceCode
					);
					const route = createUrlByERC(
						externalReferenceCodeUpdated,
						'app-details'
					);

					return {...post, link: route};
				}

				return {...post, link: genericRoute};
			})
		);
		setIsRead(arrayRead);
		setPostsWithLinks(newLinks);
	};

	const loadMore = () => {
		const nextPage = pageSize + 7;
		setPageSize(nextPage);
	};

	useEffect(() => {
		getNotifications();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [pageSize]);

	useEffect(() => {
		generateLinks();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [posts]);

	return (
		<div className="notification-container">
			{!postsWithLinks.length && (
				<p className="align-items-center d-flex justify-content-center pt-8 vh-80">
					No notifications
				</p>
			)}

			{!!postsWithLinks.length && (
				<div>
					{postsWithLinks.map((item: PostType, index: number) => (
						<div
							className={classNames({
								'post-container-unread align-items-center justify-content-center position-relative bubble-unread': !isRead[
									index
								],
							})}
							key={index}
						>
							<div className="align-items-center dotted-line h-100 post-container">
								<a
									href={item.link}
									onClick={() => {
										if (!item.read) {
											markAsRead(item);
											markAsReadState(item, index);

											return true;
										}

										return false;
									}}
								>
									{item.message?.includes(
										notificationCategory
									) && (
										<p className="align-items-center d-flex text-left text-uppercase title">
											{notificationCategory}
										</p>
									)}

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
							className="align-items-center mt-5 pt-7 shadow-none w-100"
							displayType="link"
							onClick={() => loadMore()}
						>
							Load older notifications
						</ClayButton>
					)}
				</div>
			)}
		</div>
	);
};

export default NotificationSidebar;
