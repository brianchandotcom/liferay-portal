/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.polls.model.v2;

import com.liferay.kernel.persistence.LazyResult;
import com.liferay.portal.model.v2.BaseModel;


/**
 * @author Carlos Sierra Andrés
 */
public interface PollsQuestion extends BaseModel<PollsQuestion> {

	public void addChoice(PollsChoice choice);

	public LazyResult<PollsChoice> getChoices();

	public void delete();

	public boolean save();

	public long getId();

	public PollsChoice createChoice();

}
