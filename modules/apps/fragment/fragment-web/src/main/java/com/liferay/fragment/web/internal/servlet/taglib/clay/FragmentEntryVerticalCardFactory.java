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

package com.liferay.fragment.web.internal.servlet.taglib.clay;

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.web.internal.constants.FragmentTypeConstants;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.portal.kernel.dao.search.RowChecker;

import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Jürgen Kappler
 */
public class FragmentEntryVerticalCardFactory {

	public static FragmentEntryVerticalCardFactory getInstance() {
		return _fragmentEntryVerticalCardFactory;
	}

	public VerticalCard getVerticalCard(
		FragmentEntry fragmentEntry, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker, String type) {

		if (Objects.equals(type, FragmentTypeConstants.BASIC_FRAGMENT_TYPE)) {
			return new BasicFragmentEntryVerticalCard(
				fragmentEntry, renderRequest, renderResponse, rowChecker);
		}

		if (Objects.equals(
				type, FragmentTypeConstants.INHERITED_FRAGMENT_TYPE)) {

			return new InheritedFragmentEntryVerticalCard(
				fragmentEntry, renderRequest, renderResponse, rowChecker);
		}

		return null;
	}

	private FragmentEntryVerticalCardFactory() {
	}

	private static final FragmentEntryVerticalCardFactory
		_fragmentEntryVerticalCardFactory =
			new FragmentEntryVerticalCardFactory();

}