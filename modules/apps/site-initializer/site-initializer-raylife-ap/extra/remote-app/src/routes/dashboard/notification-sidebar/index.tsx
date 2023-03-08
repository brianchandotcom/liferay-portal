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
import {
	getUserNotification,
	putUserNotificationRead,
} from '../../../common/services/notification';
import {PostType} from './postTypes';

const initialItems = {page: 1, pageSize: 4, totalCount: 0};

const NotificationSidebar: React.FC = () => {
	const [posts, setPosts] = useState<PostType[]>([]);
	const [totalCount, setTotalCount] = useState<number>(
		initialItems.totalCount
	);
	const [linkUrl, setLinkUrl] = useState<string>();
	const [page, setPage] = useState<number>(initialItems.page);

	useEffect(() => {
		getNotifications();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [page]);

	async function getNotifications() {
		const response = await getUserNotification(initialItems.pageSize, page);

		const notifications = response?.data;

		notifications?.totalCount;

		setTotalCount(notifications?.totalCount);
		setPosts((previousPostArray: PostType[]) => [
			...previousPostArray,
			...notifications?.items,
		]);

		return response;
	}

	const markAsRead = (post: PostType) => {
		if (!post.read) {
			putUserNotificationRead(post.id);
		}
	};

	const loadMore = () => {
		const nextPage = page + 1;
		setPage(nextPage);
	};

	const redirectUrl = (post: PostType): void => {
		if (post.message?.includes('')) {
			setLinkUrl('javascript:void(0)');
		}
		else {
			setLinkUrl('javascript:void(0)');
		}
	};

	return (
		<div className="notification-container">
			<div className="vh-100">
				{posts.map((post: PostType) => (
					<div
						className={classNames({
							'post-container-unread': !post.read,
						})}
						key={post.id}
						onClick={() => {
							markAsRead(post);
							redirectUrl(post);
						}}
					>
						<div className="align-items-center dotted-line h-100 post-container">
							<a href={linkUrl}>
								<h2>title</h2>

								<p className="mt-0 my-0">{post.message}</p>
							</a>

							<h5 className="font-italic">{post.dateCreated}</h5>
						</div>
					</div>
				))}

				{posts.length < totalCount && (
					<ClayButton
						className="align-items-center mb-7 mt-9 pb-7 w-100"
						displayType="link"
						onClick={() => loadMore()}
					>
						Load More
					</ClayButton>
				)}
			</div>
		</div>
	);
};

export default NotificationSidebar;
