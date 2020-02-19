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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayCardTag;

import java.util.Map;

/**
 * @author Julien Castelain
 */
public class UserCardTag extends BaseClayCardTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("ClayUserCard");

		if (_userCard != null) {
			_populateContext();
		}

		return super.doStartTag();
	}

	public void setImageAlt(String imageAlt) {
		putValue("imageAlt", imageAlt);
	}

	public void setImageSrc(String imageSrc) {
		putValue("imageSrc", imageSrc);
	}

	public void setName(String name) {
		putValue("name", name);
	}

	public void setSubtitle(String subtitle) {
		putValue("subtitle", subtitle);
	}

	public void setUserCard(UserCard userCard) {
		_userCard = userCard;

		super.setBaseClayCard(userCard);
	}

	public void setUserColorClass(String userColorClass) {
		putValue("userColorClass", userColorClass);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setUserColorClass(String)}
	 */
	@Deprecated
	public void setUserColorCssClass(String userColorCssClass) {
		setUserColorClass(userColorCssClass);
	}

	private void _populateContext() {
		Map<String, Object> context = getContext();

		if (context.get("imageAlt") == null) {
			setImageAlt(_userCard.getImageAlt());
		}

		if (context.get("imageSrc") == null) {
			setImageSrc(_userCard.getImageSrc());
		}

		if (context.get("name") == null) {
			setName(_userCard.getName());
		}

		if (context.get("subtitle") == null) {
			setSubtitle(_userCard.getSubtitle());
		}

		if (context.get("userColorClass") == null) {
			setUserColorClass(_userCard.getUserColorClass());
		}
	}

	private UserCard _userCard;

}