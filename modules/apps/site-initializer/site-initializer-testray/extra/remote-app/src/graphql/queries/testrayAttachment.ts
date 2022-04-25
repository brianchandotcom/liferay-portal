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

import {gql} from '@apollo/client';

import {testrayAttachmentFragment} from '../fragments';

export type TestrayAttachment = {
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: string;
	name: string;
	status: string;
	url: string;
	value: string;
};

export const getAttachment = gql`
	${testrayAttachmentFragment}

	query getAttachment($id: Long!) {
		c {
			attachment(attachmentId: $id) {
				...AttachmentFragment
			}
		}
	}
`;

export const getAttachments = gql`
	${testrayAttachmentFragment}

	query getAttachments($filter: String, $page: Int = 1, $pageSize: Int = 20) {
		c {
			attachments(filter: $filter, page: $page, pageSize: $pageSize) {
				items {
					...AttachmentFragment
				}
				lastPage
				page
				pageSize
				totalCount
			}
		}
	}
`;
