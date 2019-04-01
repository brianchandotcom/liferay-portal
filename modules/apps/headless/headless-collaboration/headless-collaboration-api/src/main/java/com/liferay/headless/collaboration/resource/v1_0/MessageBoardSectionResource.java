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

package com.liferay.headless.collaboration.resource.v1_0;

import com.liferay.headless.collaboration.dto.v1_0.MessageBoardSection;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-collaboration/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface MessageBoardSectionResource {

	public Page<MessageBoardSection> getContentSpaceMessageBoardSectionsPage(
			Long contentSpaceId, Boolean flatten, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception;

	public MessageBoardSection postContentSpaceMessageBoardSection(
			Long contentSpaceId, MessageBoardSection messageBoardSection)
		throws Exception;

	public void deleteMessageBoardSection(Long messageBoardSectionId)
		throws Exception;

	public MessageBoardSection getMessageBoardSection(
			Long messageBoardSectionId)
		throws Exception;

	public MessageBoardSection patchMessageBoardSection(
			Long messageBoardSectionId, MessageBoardSection messageBoardSection)
		throws Exception;

	public MessageBoardSection putMessageBoardSection(
			Long messageBoardSectionId, MessageBoardSection messageBoardSection)
		throws Exception;

	public Page<MessageBoardSection>
			getMessageBoardSectionMessageBoardSectionsPage(
				Long messageBoardSectionId, String search, Filter filter,
				Pagination pagination, Sort[] sorts)
		throws Exception;

	public MessageBoardSection postMessageBoardSectionMessageBoardSection(
			Long messageBoardSectionId, MessageBoardSection messageBoardSection)
		throws Exception;

	public void setContextCompany(Company contextCompany);

}