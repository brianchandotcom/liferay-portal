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
} from '../../../common/services/Notification';
import {PostType} from './postTypes';

const initialItems = {page: 1, pageSize: 7, totalCount: 0};

const NotificationSidebar: React.FC = () => {
	const [posts, setPosts] = useState<PostType[]>([]);
	const [totalCount, setTotalCount] = useState<number>(
		initialItems.totalCount
	);
	const [linkUrl, setLinkUrl] = useState<string>();
	const [page, setPage] = useState<number>(initialItems.page);
	const loremUrl =
		'http://localhost:8080/group/raylife-ap/policy-details?externalReferenceCode=PO-56-334-5276';

	const loremUrl2 =
		'http://localhost:8080/group/raylife-ap/~/control_panel/manage?p_p_id=com_liferay_portal_workflow_task_web_portlet_MyWorkflowTaskPortlet&p_p_lifecycle=0&p_p_state=maximized&_com_liferay_portal_workflow_task_web_portlet_MyWorkflowTaskPortlet_mvcPath=%2Fedit_workflow_task.jsp&_com_liferay_portal_workflow_task_web_portlet_MyWorkflowTaskPortlet_workflowTaskId=50116';

	useEffect(() => {
		getNotifications();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [page]);

	async function getNotifications() {
		const response = await getUserNotification(initialItems.pageSize, page);
		setTotalCount(response.data.totalCount);
		setPosts((oldArray: PostType[]) => [
			...oldArray,
			...response.data.items,
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
		if (post.message?.includes('Test')) {
			setLinkUrl(loremUrl);
		} else {
			setLinkUrl(loremUrl2);
		}
	};

	return (
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
					<div className="dotted-line post-container">
						<a href={linkUrl}>
							<h2>title</h2>

							<p className="mb-0 mt-0">{post.message}</p>
						</a>

						<h5>{post.dateCreated}</h5>
					</div>
				</div>
			))}

			{posts.length < totalCount ? (
				<ClayButton
					className="align-items-center mb-7 mt-9 pb-7 w-100"
					displayType="link"
					onClick={() => loadMore()}
				>
					Load More
				</ClayButton>
			) : null}
		</div>
	);
};

export default NotificationSidebar;
